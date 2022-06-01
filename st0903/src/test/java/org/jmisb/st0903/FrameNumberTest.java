package org.jmisb.st0903;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Frame Number (ST0903 Tag 7). */
public class FrameNumberTest {
    @Test
    public void testConstructFromValue() {
        FrameNumber frameNum = new FrameNumber(78000);
        assertEquals(frameNum.getBytes(), new byte[] {(byte) 0x01, (byte) 0x30, (byte) 0xB0});
        assertEquals(frameNum.getDisplayName(), "Frame Number");
        assertEquals(frameNum.getDisplayableValue(), "78000");
        assertEquals(frameNum.getValue(), 78000);
    }

    @Test
    public void testConstructFromValueMin() {
        FrameNumber frameNum = new FrameNumber(0);
        assertEquals(frameNum.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(frameNum.getDisplayName(), "Frame Number");
        assertEquals(frameNum.getDisplayableValue(), "0");
        assertEquals(frameNum.getValue(), 0);
    }

    @Test
    public void testConstructFromValueMax() {
        FrameNumber frameNum = new FrameNumber(16777215);
        assertEquals(frameNum.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        assertEquals(frameNum.getDisplayName(), "Frame Number");
        assertEquals(frameNum.getDisplayableValue(), "16777215");
        assertEquals(frameNum.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        FrameNumber frameNum = new FrameNumber(new byte[] {(byte) 0x01, (byte) 0x30, (byte) 0xB0});
        assertEquals(frameNum.getBytes(), new byte[] {(byte) 0x01, (byte) 0x30, (byte) 0xB0});
        assertEquals(frameNum.getDisplayName(), "Frame Number");
        assertEquals(frameNum.getDisplayableValue(), "78000");
        assertEquals(frameNum.getValue(), 78000);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.FrameNumber,
                        new byte[] {(byte) 0x01, (byte) 0x30, (byte) 0xB0},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof FrameNumber);
        FrameNumber frameNum = (FrameNumber) value;
        assertEquals(frameNum.getBytes(), new byte[] {(byte) 0x01, (byte) 0x30, (byte) 0xB0});
        assertEquals(frameNum.getDisplayName(), "Frame Number");
        assertEquals(frameNum.getDisplayableValue(), "78000");
        assertEquals(frameNum.getValue(), 78000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameNumber(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameNumber(16777216);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameNumber(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}
