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

    @Parameter(defaultValue = "org.jmisb.api.klv.st190x", property = "packageName", required = true)
    private String packageName;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    private File sourceDirectory;

    private Configuration cfg;

    @Override
    public void execute() throws MojoExecutionException {
        createOutputDirectories();
        setupTemplateEngine();
        processFiles();
        project.addCompileSourceRoot(outputDirectory.getPath());
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
        String packagePath = packageName.replace('.', '/');
        sourceDirectory = new File(outputDirectory, packagePath);
        sourceDirectory.mkdirs();
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
        enumeration.setPackagename(packageName);
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
    }

    private void processClassBlock(MimlTextBlock textBlock) {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
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

    private void generateEnumeration(EnumerationModel enumeration) {
        try {
            Template temp = cfg.getTemplate("enumeration.ftl");
            File enumerationFile = new File(sourceDirectory, enumeration.getName() + ".java");
            Writer out = new FileWriter(enumerationFile);
            temp.process(enumeration, out);
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
}
