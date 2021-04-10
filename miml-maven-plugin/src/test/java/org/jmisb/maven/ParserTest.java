package org.jmisb.maven;

import static org.testng.Assert.*;

import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.Test;

/** Unit tests for Parser. */
public class ParserTest {

    public ParserTest() {}

    @Test
    public void parserTest() throws IOException {
        String filename = "src/test/miml/mimd1/mimd-1.miml";
        MIMLLexerRules_v3 lexer = new MIMLLexerRules_v3(CharStreams.fromFileName(filename));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIML_v3Parser parser = new MIML_v3Parser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        ParseTree tree = parser.declaration();
        MIML_v3Listener listener = new MIML_v3BaseListener();
        walker.walk(listener, tree);
    }
}
