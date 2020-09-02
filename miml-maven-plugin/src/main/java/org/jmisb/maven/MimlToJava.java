package org.jmisb.maven;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    private File generatedSourceDirectory;
    private File generatedTestDirectory;

    private Configuration cfg;

    @Override
    public void execute() throws MojoExecutionException {
        createOutputDirectories();
        setupTemplateEngine();
        processFiles();
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
                processEnumerationBlock(textBlock);
                break;
            } else if (line.startsWith("class")) {
                processClassBlock(textBlock);
                break;
            } else {
                throw new UnsupportedOperationException("Don't know how to handle: " + line);
            }
        }
    }

    private void processEnumerationBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        EnumerationModel enumeration = new EnumerationModel();
        enumeration.setPackageNameBase(packageNameBase);
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
        generateEnumeration(enumeration);
        generateEnumerationTests(enumeration);
    }

    private void processClassBlock(MimlTextBlock textBlock) {
        List<String> lines = textBlock.getText();
        ClassModel classModel = new ClassModel();
        classModel.setPackageNameBase(packageNameBase);
        for (String line : lines) {
            if (line.equals("}")) {
                break;
            }
            if (line.startsWith("class")) {
                classModel.parseClassLine(line);
                continue;
            }
            if (line.startsWith("Document")) {
                classModel.setDocument(parseDocumentName(line));
                continue;
            }
            if (line.contains(" : ")) {
                classModel.addEntry(parseClassEntry(line));
                continue;
            }
        }
        generateClasses(classModel);
    }

    private String parseEnumerationName(String line) {
        String[] lineParts = line.split(" ");
        return lineParts[1];
    }

    private String parseDocumentName(String line) {
        String[] lineParts = line.split("=");
        String documentPart = lineParts[1].trim();
        if (documentPart.endsWith(";")) {
            documentPart = documentPart.substring(0, documentPart.length() - 1);
        }
        return documentPart;
    }

    private EnumerationModelEntry parseEnumerationEntry(String line) {
        EnumerationModelEntry entry = new EnumerationModelEntry();
        String[] partsEquals = line.split(" = ");
        entry.setNumber(Integer.parseInt(partsEquals[0].trim()));
        String[] partsBracket = partsEquals[1].split("\\{");
        entry.setName(partsBracket[0].trim());
        String[] descriptionSplit = partsBracket[1].split("}");
        entry.setDescription(descriptionSplit[0]);
        return entry;
    }

    private ClassModelEntry parseClassEntry(String line) {
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

    private void generateEnumeration(EnumerationModel enumeration) {
        try {
            Template temp = cfg.getTemplate("enumeration.ftl");
            String packagePart = enumeration.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            targetDirectory.mkdirs();
            File enumerationFile = new File(targetDirectory, enumeration.getName() + ".java");
            Writer out = new FileWriter(enumerationFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            getLog().error(ex);
        }
    }

    private void generateEnumerationTests(EnumerationModel enumeration) {
        try {
            Template temp = cfg.getTemplate("enumerationTest.ftl");
            String packagePart = enumeration.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedTestDirectory, packagePart);
            targetDirectory.mkdirs();
            File enumerationTestFile =
                    new File(targetDirectory, enumeration.getName() + "Test.java");
            Writer out = new FileWriter(enumerationTestFile);
            temp.process(enumeration, out);
        } catch (TemplateException | IOException ex) {
            getLog().error(ex);
        }
    }

    private void generateClasses(ClassModel classModel) {
        try {
            String packagePart = classModel.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedSourceDirectory, packagePart);
            targetDirectory.mkdirs();
            generateMetadataKey(targetDirectory, classModel);
            // generateFactory(targetDirectory, classModel);
            // generateComponentClass(targetDirectory, classModel);
            generateMetadataKeyTests(classModel);
        } catch (TemplateException | IOException ex) {
            getLog().error(ex);
        }
    }

    private void generateMetadataKey(File targetDirectory, ClassModel classModel)
            throws TemplateException, IOException {
        Template temp = cfg.getTemplate("key.ftl");
        File metadataKeyFile = new File(targetDirectory, classModel.getName() + "MetadataKey.java");
        Writer out = new FileWriter(metadataKeyFile);
        temp.process(classModel, out);
    }

    private void generateMetadataKeyTests(ClassModel classModel)
            throws TemplateException, IOException {
        try {
            Template temp = cfg.getTemplate("metadataKeyTest.ftl");
            String packagePart = classModel.getDocument().toLowerCase() + "/";
            File targetDirectory = new File(generatedTestDirectory, packagePart);
            targetDirectory.mkdirs();
            File metadataKeyTestFile =
                    new File(targetDirectory, classModel.getName() + "MetadataKeyTest.java");
            Writer out = new FileWriter(metadataKeyTestFile);
            temp.process(classModel, out);
        } catch (TemplateException | IOException ex) {
            getLog().error(ex);
        }
    }

    private void setupTemplateEngine() {
        cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(getClass(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    private void parseTypeModifierPartsToEntry(ClassModelEntry entry, String typeModifiers) {
        String[] typeModifierParts = typeModifiers.split(",");
        if ("String".equals(entry.getTypeName())) {
            if (typeModifierParts.length == 1) {
                int maxLength = Integer.parseInt(typeModifierParts[0]);
                entry.setMaxLength(maxLength);
            } else {
                getLog().warn("Unhandled String typeModifierParts: " + typeModifiers);
            }
        } else if ("Real".equals(entry.getTypeName())) {
            if (typeModifierParts.length == 1) {
                double minValue = Double.parseDouble(typeModifierParts[0]);
                entry.setMinValue(minValue);
            } else {
                getLog().warn("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getTypeName().startsWith("Real[]")) {
            if (typeModifierParts.length == 3) {
                double minValue = Double.parseDouble(typeModifierParts[0]);
                entry.setMinValue(minValue);
                double maxValue = Double.parseDouble(typeModifierParts[1]);
                entry.setMaxValue(maxValue);
                double resolution = Double.parseDouble(typeModifierParts[2]);
                entry.setResolution(resolution);
            } else {
                getLog().warn("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else if (entry.getTypeName().startsWith("LIST<")) {
            if (typeModifierParts.length == 2) {
                int minLength = Integer.parseInt(typeModifierParts[0]);
                entry.setMinLength(minLength);
                if (!typeModifierParts[1].trim().equals("*")) {
                    int maxLength = Integer.parseInt(typeModifierParts[1]);
                    entry.setMaxLength(maxLength);
                }
            } else {
                getLog().warn("Unhandled Real typeModifierParts: " + typeModifiers);
            }
        } else {
            getLog().warn(
                            "Unhandled type / typeModifierParts: "
                                    + entry.getTypeName()
                                    + " / "
                                    + typeModifiers);
        }
    }
}
