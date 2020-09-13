package org.jmisb.maven;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/** Goal which generates Java sources for a Motion Imagery Modeling Language input. */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class MimlToJava extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", property = "inputDir", required = true)
    private File inputDirectory;

    @Parameter(
            defaultValue = "${project.build.directory}/generated-sources/miml",
            property = "outputDir",
            required = true)
    private File outputDirectory;

    @Parameter(
            defaultValue = "${project.build.directory}/generated-test-sources/miml",
            property = "outputTestDir",
            required = true)
    private File outputTestDirectory;

    @Parameter(defaultValue = "org.jmisb.api.klv", property = "packageNameBase", required = true)
    private String packageNameBase;

    @Parameter(defaultValue = "MIMD", property = "topLevelClass", required = true)
    private String topLevelClassName;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    private File generatedSourceDirectory;
    private File generatedTestDirectory;

    private Models models = new Models();

    @Override
    public void execute() throws MojoExecutionException {
        createOutputDirectories();
        processFiles();
        CodeGenerator codeGenerator =
                new CodeGenerator(generatedSourceDirectory, generatedTestDirectory, models);
        codeGenerator.generateCode();
        project.addCompileSourceRoot(outputDirectory.getPath());
        project.addTestCompileSourceRoot(outputTestDirectory.getPath());
    }

    private void processFiles() {
        File[] files = new File(inputDirectory, "src/main/miml").listFiles();
        for (File inFile : files) {
            if (inFile.getName().endsWith(".miml")) {
                processMimlFile(inFile);
            }
        }
    }

    // TODO: move to CodeGenerator
    private void createOutputDirectories() {
        String packagePath = packageNameBase.replace('.', '/');
        generatedSourceDirectory = new File(outputDirectory, packagePath);
        generatedSourceDirectory.mkdirs();
        generatedTestDirectory = new File(outputTestDirectory, packagePath);
        generatedTestDirectory.mkdirs();
    }

    private void processMimlFile(File inFile) {
        try {
            getLog().info("Processing: " + inFile.getAbsolutePath());
            List<String> lines =
                    Files.readAllLines(Paths.get(inFile.getAbsolutePath()), StandardCharsets.UTF_8);
            List<MimlTextBlock> textBlocks = getBlocks(lines);
            processBlocks(textBlocks);
        } catch (IOException ex) {
            getLog().error("Failed to read lines from " + inFile);
        }
    }

    private List<MimlTextBlock> getBlocks(List<String> lines) {
        List<MimlTextBlock> blocks = new ArrayList<>();
        MimlTextBlock block = new MimlTextBlock();
        for (String line : lines) {
            String trimmedLine = line.trim();
            block.addLine(trimmedLine);
            if ("}".equals(trimmedLine)) {
                blocks.add(block);
                block = new MimlTextBlock();
            }
        }
        return blocks;
    }

    private void processBlocks(List<MimlTextBlock> textBlocks) {
        for (MimlTextBlock textBlock : textBlocks) {
            processBlock(textBlock);
        }
    }

    private void processBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        for (String line : lines) {
            if (line.startsWith("MIML_Grammar")) {
                continue;
            }
            if (line.startsWith("enumeration")) {
                EnumerationModel enumeration = processEnumerationBlock(textBlock);
                enumeration.setPackageNameBase(packageNameBase);
                models.addEnumerationModel(enumeration);
                break;
            } else if (line.startsWith("class") || line.startsWith("abstract class")) {
                ClassModel classModel = processClassBlock(textBlock);
                classModel.setTopLevel(classModel.getName().equals(this.topLevelClassName));
                classModel.setPackageNameBase(packageNameBase);
                models.addClassModel(classModel);
                break;
            } else {
                throw new UnsupportedOperationException("Don't know how to handle: " + line);
            }
        }
    }

    static EnumerationModel processEnumerationBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        EnumerationModel enumeration = new EnumerationModel();
        for (String line : lines) {
            if (line.equals("}")) {
                break;
            }
            if (line.startsWith("enumeration")) {
                enumeration.setName(parseEnumerationName(line));
                continue;
            }
            if (line.startsWith("Document")) {
                enumeration.setDocument(parseDocumentName(line));
                continue;
            }
            if (line.contains(" = ")) {
                enumeration.addEntry(parseEnumerationEntry(line));
                continue;
            }
        }
        return enumeration;
    }

    static ClassModel processClassBlock(MimlTextBlock textBlock) {
        // System.out.println("processing class block line: " + textBlock);
        List<String> lines = textBlock.getText();
        ClassModel classModel = new ClassModel();
        for (String line : lines) {
            if (line.equals("}")) {
                break;
            }
            if (line.startsWith("class")) {
                classModel.parseClassLine(line);
                continue;
            }
            if (line.startsWith("abstract class")) {
                classModel.parseAbstractClassLine(line);
                continue;
            }
            if (line.startsWith("Document")) {
                classModel.setDocument(parseDocumentName(line));
                continue;
            }
            if (line.contains(" : ")) {
                ClassModelEntry entry = parseClassEntry(line);
                entry.setParent(classModel);
                classModel.addEntry(entry);
                continue;
            }
        }
        return classModel;
    }

    static String parseEnumerationName(String line) {
        String[] lineParts = line.split(" ");
        return lineParts[1];
    }

    static String parseDocumentName(String line) {
        String[] lineParts = line.split("=");
        String documentPart = lineParts[1].trim();
        if (documentPart.endsWith(";")) {
            documentPart = documentPart.substring(0, documentPart.length() - 1);
        }
        return documentPart;
    }

    static EnumerationModelEntry parseEnumerationEntry(String line) {
        EnumerationModelEntry entry = new EnumerationModelEntry();
        String[] partsEquals = line.split(" = ");
        entry.setNumber(Integer.parseInt(partsEquals[0].trim()));
        String[] partsBracket = partsEquals[1].split("\\{");
        entry.setName(partsBracket[0].trim());
        String[] descriptionSplit = partsBracket[1].split("}");
        entry.setDescription(descriptionSplit[0]);
        return entry;
    }

    static ClassModelEntry parseClassEntry(String line) {
        ClassModelEntry entry = new ClassModelEntry();
        String[] partsEquals = line.split(" : ");
        String idAndNamePart = partsEquals[0];
        String[] idAndNameParts = idAndNamePart.split("_");
        String idAsString = idAndNameParts[0].trim();
        int id = Integer.parseInt(idAsString);
        entry.setNumber(id);
        String name = idAndNameParts[1].trim();
        entry.setName(name);
        String typePart = partsEquals[1];
        String[] partsBracket = typePart.split("\\{");
        String[] unitsSplit = partsBracket[1].split("}");
        // TODO: there is a case where this can be "<units>, DEPRECATED"
        entry.setUnits(unitsSplit[0].trim());
        String[] typeSplit = partsBracket[0].split("\\(");
        String typeName = typeSplit[0].trim();
        entry.setTypeName(typeName);
        if (typeSplit.length > 1) {
            String typeModifiers = typeSplit[1].replace(")", "").trim();
            // getLog().info(typeModifiers.toString());
            parseTypeModifierPartsToEntry(entry, typeModifiers);
        }
        // getLog().info(entry.toString());
        return entry;
    }

    private static void parseTypeModifierPartsToEntry(ClassModelEntry entry, String typeModifiers) {
        String[] typeModifierParts = typeModifiers.split(",");
        if ("String".equals(entry.getTypeName())) {
            if (typeModifierParts.length == 1) {
                int maxLength = Integer.parseInt(typeModifierParts[0]);
                entry.setMaxLength(maxLength);
            } else {
                System.out.println("Unhandled String typeModifierParts: " + typeModifiers);
            }
        } else if ("Real".equals(entry.getTypeName())) {
            partTypeModifierPartsForReal(typeModifierParts, entry, typeModifiers);
        } else if (entry.getTypeName().startsWith("Real[]")) {
            if (typeModifierParts.length == 3) {
                double minValue = stringToDouble(typeModifierParts[0]);
                entry.setMinValue(minValue);
                double maxValue = stringToDouble(typeModifierParts[1]);
                entry.setMaxValue(maxValue);
                double resolution = stringToDouble(typeModifierParts[2]);
                entry.setResolution(resolution);
            } else {
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getTypeName().startsWith("LIST<")) {
            if (typeModifierParts.length == 2) {
                int minLength = Integer.parseInt(typeModifierParts[0]);
                entry.setMinLength(minLength);
                if (!typeModifierParts[1].trim().equals("*")) {
                    int maxLength = Integer.parseInt(typeModifierParts[1].trim());
                    entry.setMaxLength(maxLength);
                }
            } else {
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getName().equals("mimdId")) {
            // Nothing - special case
        } else {
            System.out.println(
                    "Unhandled type / typeModifierParts: "
                            + entry.getTypeName()
                            + " / "
                            + typeModifiers);
        }
    }

    private static void partTypeModifierPartsForReal(
            String[] typeModifierParts, ClassModelEntry entry, String typeModifiers)
            throws NumberFormatException {
        switch (typeModifierParts.length) {
            case 1:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    break;
                }
            case 2:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    double maxValue = stringToDouble(typeModifierParts[1]);
                    entry.setMaxValue(maxValue);
                    break;
                }
            case 3:
                {
                    double minValue = stringToDouble(typeModifierParts[0]);
                    entry.setMinValue(minValue);
                    double maxValue = stringToDouble(typeModifierParts[1]);
                    entry.setMaxValue(maxValue);
                    double resolution = stringToDouble(typeModifierParts[2]);
                    entry.setResolution(resolution);
                    break;
                }
            default:
                System.out.println("Unhandled Real typeModifierParts: " + typeModifiers);
                break;
        }
    }

    private static double stringToDouble(String typeModifierPart) throws NumberFormatException {
        switch (typeModifierPart.trim()) {
            case "TWO_PI":
                return 2.0 * Math.PI;
            case "PI":
                return Math.PI;
            case "HALF_PI":
                return Math.PI / 2.0;
            default:
                return Double.parseDouble(typeModifierPart);
        }
    }
}
