package org.jmisb.api.klv.st0102;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class ClassificationTest
{
    @Test
    public void testCode()
    {
        Classification classification = Classification.UNCLASSIFIED;
        assertEquals(classification.getCode(), (byte)0x01);
        assertEquals(Classification.UNKNOWN.getCode(), (byte)0x00);
        assertEquals(Classification.RESTRICTED.getCode(), (byte)0x02);
        assertEquals(Classification.CONFIDENTIAL.getCode(), (byte)0x03);
        assertEquals(Classification.SECRET.getCode(), (byte)0x04);
        assertEquals(Classification.TOP_SECRET.getCode(), (byte)0x05);
    }

    @Test
    public void testLookup()
    {
        assertEquals(Classification.getClassification((byte)0x01), Classification.UNCLASSIFIED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueLookup()
    {
        Classification.getClassification((byte)0x06);
    }
}
