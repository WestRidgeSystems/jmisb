package org.jmisb.maven;

import java.io.File;
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

    @Override
    public void execute() throws MojoExecutionException {
        createOutputDirectories();
        Models models = processFiles();
        CodeGenerator codeGenerator =
                new CodeGenerator(generatedSourceDirectory, generatedTestDirectory, models);
        codeGenerator.generateCode();
        project.addCompileSourceRoot(outputDirectory.getPath());
        project.addTestCompileSourceRoot(outputTestDirectory.getPath());
    }

    private Models processFiles() {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setPackageNameBase(packageNameBase);
        parserConfiguration.setTopLevelClassName(topLevelClassName);
        Parser parser = new Parser(parserConfiguration);
        Models models = new Models();
        File[] files = new File(inputDirectory, "src/main/miml").listFiles();
        for (File inFile : files) {
            if (inFile.getName().endsWith(".miml")) {
                models.mergeAll(parser.processMimlFile(inFile));
            }
        }
        return models;
    }

    // TODO: move to CodeGenerator
    private void createOutputDirectories() {
        String packagePath = packageNameBase.replace('.', '/');
        generatedSourceDirectory = new File(outputDirectory, packagePath);
        generatedSourceDirectory.mkdirs();
        generatedTestDirectory = new File(outputTestDirectory, packagePath);
        generatedTestDirectory.mkdirs();
    }
}
