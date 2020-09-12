package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ClassModel. */
public class ClassModelTest {

    public ClassModelTest() {}

    @Test
    public void fromBlock() {
        MimlTextBlock textBlock = new MimlTextBlock();
        textBlock.addLine("class Platform includes Base {");
        textBlock.addLine("    Document = ST1905;".trim());
        textBlock.addLine("    33_name : String (100) {None};".trim());
        textBlock.addLine("    36_differentialPressure : Real (0, 5000)          {hPa};".trim());
        textBlock.addLine("}");
        ClassModel uut = MimlToJava.processClassBlock(textBlock);
        assertEquals(uut.getName(), "Platform");
        assertEquals(uut.getEntries().size(), 2);
        assertEquals(uut.getDocument(), "ST1905");
        assertEquals(uut.getIncludes(), "Base");
        assertFalse(uut.isIsAbstract());
        assertFalse(uut.isTopLevel());
        assertNull(uut.getPackageName());
        uut.setPackageNameBase("test.miml");
        assertEquals(uut.getPackageName(), "test.miml.st1905");
    }
}
