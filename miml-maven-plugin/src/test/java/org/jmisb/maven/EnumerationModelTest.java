package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for EnumerationModel. */
public class EnumerationModelTest {

    public EnumerationModelTest() {}

    @Test
    public void fromBlock() {
        MimlTextBlock textBlock = new MimlTextBlock();
        textBlock.addLine("enumeration PowerStatus {");
        textBlock.addLine("    Document = ST1907;".trim());
        textBlock.addLine("    00 = Off {The device is powered off};".trim());
        textBlock.addLine("    01 = On {The device is powered on};".trim());
        textBlock.addLine("}");
        EnumerationModel uut = Parser.processEnumerationBlock(textBlock);
        assertEquals(uut.getName(), "PowerStatus");
        assertEquals(uut.getEntries().size(), 2);
        assertEquals(uut.getDocument(), "ST1907");
        assertNull(uut.getPackageName());
        uut.setPackageNameBase("test.miml");
        assertEquals(uut.getPackageName(), "test.miml.st1907");
    }
}
