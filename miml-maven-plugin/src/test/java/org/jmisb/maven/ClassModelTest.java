package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ClassModel. */
public class ClassModelTest {

    public ClassModelTest() {}

    @Test
    public void fromBlock() {
        ClassModel uut = createTestClass();
        assertEquals(uut.getName(), "Platform");
        assertEquals(uut.getEntries().size(), 2);
        assertEquals(uut.getDocument(), "ST1905");
        assertEquals(uut.getIncludes(), "Base");
        assertFalse(uut.isIsAbstract());
        assertFalse(uut.isTopLevel());
        assertNull(uut.getPackageName());
        uut.setPackageNameBase("test.miml");
        assertEquals(uut.getPackageName(), "test.miml.st1905");
        assertEquals(uut.getDirectoryPackagePart(), "st1905/");
    }

    @Test
    public void fromBlockAbstract() {
        ClassModel uut = createBaseClass();
        assertEquals(uut.getName(), "Base");
        assertEquals(uut.getEntries().size(), 3);
        assertEquals(uut.getDocument(), "ST1904");
        assertTrue(uut.isIsAbstract());
        assertFalse(uut.isTopLevel());
        assertNull(uut.getPackageName());
        uut.setPackageNameBase("x.miml");
        assertEquals(uut.getPackageName(), "x.miml.st1904");
    }

    @Test
    public void applyClass() {
        ClassModel uut = createTestClass();
        assertEquals(uut.getName(), "Platform");
        assertEquals(uut.getEntries().size(), 2);
        assertEquals(uut.getDocument(), "ST1905");
        assertEquals(uut.getIncludes(), "Base");
        ClassModel baseClass = createBaseClass();
        uut.applyBaseModel(baseClass);
        assertEquals(uut.getName(), "Platform");
        assertEquals(uut.getEntries().size(), 5);
        assertEquals(uut.getDocument(), "ST1905");
        assertEquals(uut.getIncludes(), "Base");
        assertFalse(uut.isIsAbstract());
        assertFalse(uut.isTopLevel());
    }

    private ClassModel createTestClass() {
        MimlTextBlock textBlock = new MimlTextBlock();
        textBlock.addLine("class Platform includes Base {");
        textBlock.addLine("    Document = ST1905;".trim());
        textBlock.addLine("    33_name : String (100) {None};".trim());
        textBlock.addLine("    36_differentialPressure : Real (0, 5000)          {hPa};".trim());
        textBlock.addLine("}");
        ClassModel uut = MimlToJava.processClassBlock(textBlock);
        return uut;
    }

    private ClassModel createBaseClass() {
        MimlTextBlock textBlock = new MimlTextBlock();
        textBlock.addLine("abstract class Base {");
        textBlock.addLine("    Document = ST1904;".trim());
        textBlock.addLine("    01_mimdId : UInt[<=2] (1, *) {None};".trim());
        textBlock.addLine("    02_timer : REF<Timer> {None};".trim());
        textBlock.addLine("    03_timerOffset : Integer { ns};".trim());
        textBlock.addLine("}");
        ClassModel uut = MimlToJava.processClassBlock(textBlock);
        return uut;
    }

    @Test
    public void topLevel() {
        ClassModel uut = new ClassModel();
        assertFalse(uut.isTopLevel());
        uut.setTopLevel(true);
        assertTrue(uut.isTopLevel());
    }
}
