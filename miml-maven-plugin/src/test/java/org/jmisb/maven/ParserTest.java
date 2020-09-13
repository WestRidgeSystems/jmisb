package org.jmisb.maven;

import static org.testng.Assert.*;

import java.io.File;
import org.testng.annotations.Test;

/** Unit tests for Parser. */
public class ParserTest {

    public ParserTest() {}

    @Test
    public void checkEnumerationFile() {
        File enumFile = new File("src/test/miml/enumeration.miml");
        assertTrue(enumFile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(enumFile);
        assertEquals(models.getEnumerationModels().size(), 4);
        assertEquals(models.getClassModels().size(), 0);
    }

    @Test
    public void checkClassFile() {
        File classFile = new File("src/test/miml/class.miml");
        assertTrue(classFile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(classFile);
        assertEquals(models.getEnumerationModels().size(), 0);
        assertEquals(models.getClassModels().size(), 6);
    }

    @Test
    public void checkFiles() {
        File directory = new File("src/test/miml/");
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setTopLevelClassName("MIMD");
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processFiles(directory);
        assertEquals(models.getEnumerationModels().size(), 4);
        assertEquals(models.getClassModels().size(), 6);
        ClassModel mimd = models.findClassByName("MIMD");
        assertTrue(mimd.isTopLevel());
        ClassModel base = models.findClassByName("Base");
        assertTrue(base.isIsAbstract());
    }
}
