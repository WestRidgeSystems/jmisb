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
        assertEquals(models.getClassModels().size(), 7);
    }

    @Test
    public void checkFiles() {
        File directory = new File("src/test/miml/");
        ParserConfiguration parserConfiguration = makeConfig();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processFiles(directory);
        assertEquals(models.getEnumerationModels().size(), 4);
        assertEquals(models.getClassModels().size(), 7);
        ClassModel mimd = models.findClassByName("MIMD");
        assertTrue(mimd.isTopLevel());
        ClassModel base = models.findClassByName("Base");
        assertTrue(base.isIsAbstract());
    }

    private ParserConfiguration makeConfig() {
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setTopLevelClassName("MIMD");
        parserConfiguration.setPackageNameBase("test.mimlparser");
        assertEquals(parserConfiguration.getPackageNameBase(), "test.mimlparser");
        return parserConfiguration;
    }

    @Test
    public void checkNoSuchFile() {
        File nosuchfile = new File("src/not_there.miml");
        assertFalse(nosuchfile.exists());
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        Parser uut = new Parser(parserConfiguration);
        Models models = uut.processMimlFile(nosuchfile);
        assertNotNull(models);
        assertEquals(models.getClassModels().size(), 0);
        assertEquals(models.getEnumerationModels().size(), 0);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void checkBadBlockLine() {
        MimlTextBlock block = new MimlTextBlock();
        block.addLine("blahblah{}");
        Parser uut = new Parser(makeConfig());
        uut.processBlock(block);
    }

    @Test
    public void checkEmptyBlock() {
        MimlTextBlock block = new MimlTextBlock();
        Parser uut = new Parser(makeConfig());
        AbstractModel model = uut.processBlock(block);
        assertNull(model);
    }

    @Test
    public void checkEmptyEnumerationBlock() {
        MimlTextBlock block = new MimlTextBlock();
        EnumerationModel model = Parser.processEnumerationBlock(block);
        assertNotNull(model);
    }

    @Test
    public void checkNoEntriesEnumerationBlock() {
        MimlTextBlock block = new MimlTextBlock();
        block.addLine("enumeration {");
        block.addLine("");
        block.addLine("}");
        EnumerationModel model = Parser.processEnumerationBlock(block);
        assertNotNull(model);
    }

    @Test
    public void checkEmptyClassBlock() {
        MimlTextBlock block = new MimlTextBlock();
        ClassModel model = Parser.processClassBlock(block);
        assertNotNull(model);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkTypeModifierString() {
        ClassModelEntry classModelEntry = new ClassModelEntry();
        classModelEntry.setTypeName("String");
        Parser.parseTypeModifierPartsToEntry(classModelEntry, "2, 3");
    }

    @Test
    public void checkTypeModifiersRealArray() {
        ClassModelEntry classModelEntry = new ClassModelEntry();
        classModelEntry.setTypeName("Real[]");
        Parser.parseTypeModifierPartsToEntry(classModelEntry, "-10, 5000, 0.5");
        assertEquals(classModelEntry.getMinValue(), -10.0);
        assertEquals(classModelEntry.getMaxValue(), 5000.0);
        assertEquals(classModelEntry.getResolution(), 0.5);
    }
}
