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

    @Override
    public void execute() throws MojoExecutionException {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setPackageNameBase(packageNameBase);
        parserConfiguration.setTopLevelClassName(topLevelClassName);
        Parser parser = new Parser(parserConfiguration);
        Models models = parser.processFiles(new File(inputDirectory, "src/main/miml"));
        CodeGeneratorConfiguration codeGeneratorConf = new CodeGeneratorConfiguration();
        codeGeneratorConf.setOutputDirectory(outputDirectory);
        codeGeneratorConf.setOutputTestDirectory(outputTestDirectory);
        codeGeneratorConf.setModels(models);
        codeGeneratorConf.setPackageNameBase(packageNameBase);
        CodeGenerator codeGenerator = new CodeGenerator(codeGeneratorConf);
        codeGenerator.generateCode();
        project.addCompileSourceRoot(outputDirectory.getPath());
        project.addTestCompileSourceRoot(outputTestDirectory.getPath());
    }
}
