package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for FrameHeight (ST0903 Tag 9).
 */
public class FrameHeightTest
{
    @Test
    public void testConstructFromValue()
    {
        FrameHeight height = new FrameHeight(1080);
        assertEquals(height.getBytes(), new byte[]{(byte)0x04, (byte)0x38});
        assertEquals(height.getDisplayName(), "Frame Height");
        assertEquals(height.getDisplayableValue(), "1080px");
        assertEquals(height.getValue(), 1080);
    }
    
    @Test
    public void testConstructFromValueMin()
    {
        FrameHeight height = new FrameHeight(1);
        assertEquals(height.getBytes(), new byte[]{(byte)0x01});
        assertEquals(height.getDisplayName(), "Frame Height");
        assertEquals(height.getDisplayableValue(), "1px");
        assertEquals(height.getValue(), 1);
    }

    @Test
    public void testConstructFromValueMax()
    {
        FrameHeight height = new FrameHeight(16777215);
        assertEquals(height.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(height.getDisplayName(), "Frame Height");
        assertEquals(height.getDisplayableValue(), "16777215px");
        assertEquals(height.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        FrameHeight height = new FrameHeight(new byte[]{(byte)0x04, (byte)0x38});
        assertEquals(height.getBytes(), new byte[]{(byte)0x04, (byte)0x38});
        assertEquals(height.getDisplayName(), "Frame Height");
        assertEquals(height.getDisplayableValue(), "1080px");
        assertEquals(height.getValue(), 1080);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiLocalSet.createValue(VmtiMetadataKey.FrameHeight, new byte[]{(byte)0x04, (byte)0x38});
        assertTrue(value instanceof FrameHeight);
        FrameHeight height = (FrameHeight)value;
        assertEquals(height.getBytes(), new byte[]{(byte)0x04, (byte)0x38});
        assertEquals(height.getDisplayName(), "Frame Height");
        assertEquals(height.getDisplayableValue(), "1080px");
        assertEquals(height.getValue(), 1080);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameHeight(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameHeight(16777216);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameHeight(new byte[]{0x01, 0x02, 0x03, 0x04});
    }
}
