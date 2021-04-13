package org.jmisb.maven;

import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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
        try {
            File sourceDirectory = new File(inputDirectory, "src/main/miml");
            File[] files = sourceDirectory.listFiles();
            if (files == null) {
                throw new IOException("No files to process");
            }
            for (File inFile : files) {
                if (inFile.getName().endsWith(".miml")) {
                    processFile(inFile.getAbsolutePath());
                }
            }
            project.addCompileSourceRoot(outputDirectory.getPath());
            project.addTestCompileSourceRoot(outputTestDirectory.getPath());
        } catch (IOException | RecognitionException | TemplateException ex) {
            Logger.getLogger(MimlToJava.class.getName()).log(Level.SEVERE, null, ex);
            throw new MojoExecutionException(ex.toString());
        }
    }

    private void processFile(String filename)
            throws RecognitionException, IOException, TemplateException {
        MIMLLexerRules_v3 lexer = new MIMLLexerRules_v3(CharStreams.fromFileName(filename));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIML_v3Parser parser = new MIML_v3Parser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        ParseTree tree = parser.declaration();
        CodeGeneratorConfiguration codeGeneratorConf = new CodeGeneratorConfiguration();
        codeGeneratorConf.setOutputDirectory(outputDirectory);
        codeGeneratorConf.setOutputTestDirectory(outputTestDirectory);
        codeGeneratorConf.setPackageNameBase(packageNameBase);
        CodeGeneratorListener listener = new CodeGeneratorListener(codeGeneratorConf);
        walker.walk(listener, tree);
        listener.generateJavaClasses();
    }
}
