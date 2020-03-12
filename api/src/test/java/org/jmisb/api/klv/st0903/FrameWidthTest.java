package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for FrameWidth (ST0903 Tag 8).
 */
public class FrameWidthTest
{
    @Test
    public void testConstructFromValue()
    {
        FrameWidth width = new FrameWidth(1920);
        assertEquals(width.getBytes(), new byte[]{(byte)0x07, (byte)0x80});
        assertEquals(width.getDisplayName(), "Frame Width");
        assertEquals(width.getDisplayableValue(), "1920px");
        assertEquals(width.getValue(), 1920);
    }
    
    @Test
    public void testConstructFromValueMin()
    {
        FrameWidth width = new FrameWidth(1);
        assertEquals(width.getBytes(), new byte[]{(byte)0x01});
        assertEquals(width.getDisplayName(), "Frame Width");
        assertEquals(width.getDisplayableValue(), "1px");
        assertEquals(width.getValue(), 1);
    }

    @Test
    public void testConstructFromValueMax()
    {
        FrameWidth width = new FrameWidth(16777215);
        assertEquals(width.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(width.getDisplayName(), "Frame Width");
        assertEquals(width.getDisplayableValue(), "16777215px");
        assertEquals(width.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        FrameWidth width = new FrameWidth(new byte[]{(byte)0x07, (byte)0x80});
        assertEquals(width.getBytes(), new byte[]{(byte)0x07, (byte)0x80});
        assertEquals(width.getDisplayName(), "Frame Width");
        assertEquals(width.getDisplayableValue(), "1920px");
        assertEquals(width.getValue(), 1920);
    }
    
    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiMetadataValueFactory.createValue(VmtiMetadataKey.FrameWidth, new byte[]{(byte)0x07, (byte)0x80});
        assertTrue(value instanceof FrameWidth);
        FrameWidth width = (FrameWidth)value;
        assertEquals(width.getBytes(), new byte[]{(byte)0x07, (byte)0x80});
        assertEquals(width.getDisplayName(), "Frame Width");
        assertEquals(width.getDisplayableValue(), "1920px");
        assertEquals(width.getValue(), 1920);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameWidth(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameWidth(16777216);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameWidth(new byte[]{0x01, 0x02, 0x03, 0x04});
    }
}
