/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jmisb.maven;

import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.Test;

/** Unit tests for CodeGeneratorListener implementation. */
public class CodeGeneratorListenerTest {

    public CodeGeneratorListenerTest() {}

    @Test
    public void parserTest() throws IOException, TemplateException {
        String filename = "src/test/miml/mimd1/mimd-1.miml";
        MIMLLexerRules_v3 lexer = new MIMLLexerRules_v3(CharStreams.fromFileName(filename));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIML_v3Parser parser = new MIML_v3Parser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        ParseTree tree = parser.declaration();
        CodeGeneratorConfiguration codeGeneratorConf = new CodeGeneratorConfiguration();
        codeGeneratorConf.setOutputDirectory(new File("target/generator-unittest-outputs-code"));
        codeGeneratorConf.setOutputTestDirectory(
                new File("target/generator-unittest-outputs-tests"));
        codeGeneratorConf.setPackageNameBase("org.example.just.testing");
        CodeGeneratorListener listener = new CodeGeneratorListener(codeGeneratorConf);
        walker.walk(listener, tree);
        listener.generateJavaClasses();
    }
}
