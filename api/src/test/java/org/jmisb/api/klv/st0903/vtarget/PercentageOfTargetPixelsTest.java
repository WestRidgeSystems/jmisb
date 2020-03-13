package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.VTargetMetadataKey;
import org.jmisb.api.klv.st0903.VTargetPack;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Percentage of Target Pixels (Tag 7)
 */
public class PercentageOfTargetPixelsTest {
    
    public PercentageOfTargetPixelsTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        PercentageOfTargetPixels percentage = new PercentageOfTargetPixels((short)50);
        assertEquals(percentage.getBytes(), new byte[]{(byte)0x32});
        assertEquals(percentage.getDisplayName(), "Percentage Target Pixels");
        assertEquals(percentage.getDisplayableValue(), "50%");
        assertEquals(percentage.getPercentageOfTargetPixels(), 50);
    }
    
    @Test
    public void testConstructFromEncodedBytes()
    {
        PercentageOfTargetPixels percentage = new PercentageOfTargetPixels(new byte[]{(byte)0x32});
        assertEquals(percentage.getBytes(), new byte[]{(byte)0x32});
        assertEquals(percentage.getDisplayName(), "Percentage Target Pixels");
        assertEquals(percentage.getDisplayableValue(), "50%");
        assertEquals(percentage.getPercentageOfTargetPixels(), 50);
    }
    
    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.PercentageOfTargetPixels, new byte[]{(byte)0x32});
        assertTrue(value instanceof PercentageOfTargetPixels);
        PercentageOfTargetPixels percentage = (PercentageOfTargetPixels)value;
        assertEquals(percentage.getBytes(), new byte[]{(byte)0x32});
        assertEquals(percentage.getDisplayName(), "Percentage Target Pixels");
        assertEquals(percentage.getDisplayableValue(), "50%");
        assertEquals(percentage.getPercentageOfTargetPixels(), 50);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PercentageOfTargetPixels((short)0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PercentageOfTargetPixels((short)101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PercentageOfTargetPixels(new byte[]{0x01, 0x02});
    }
}
