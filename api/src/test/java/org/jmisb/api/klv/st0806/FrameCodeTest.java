package org.jmisb.api.klv.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FrameCodeTest {
    @Test
    public void testConstructFromValue() {
        // Min
        FrameCode code = new FrameCode(0);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        // Max
        code = new FrameCode(4294967295L);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        code = new FrameCode(159);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});

        assertEquals(code.getDisplayName(), "Frame Code");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        FrameCode code =
                new FrameCode(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(code.getFrameCode(), 0);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(code.getDisplayableValue(), "0");

        // Max
        code = new FrameCode(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        assertEquals(code.getFrameCode(), 4294967295L);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        assertEquals(code.getDisplayableValue(), "4294967295");

        code = new FrameCode(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(code.getFrameCode(), 159);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(code.getDisplayableValue(), "159");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.FrameCode, bytes);
        assertTrue(v instanceof FrameCode);
        FrameCode code = (FrameCode) v;
        assertEquals(code.getFrameCode(), 0);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(code.getDisplayableValue(), "0");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f};
        v = RvtLocalSet.createValue(RvtMetadataKey.FrameCode, bytes);
        assertTrue(v instanceof FrameCode);
        code = (FrameCode) v;
        assertEquals(code.getFrameCode(), 159);
        assertEquals(
                code.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(code.getDisplayableValue(), "159");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameCode(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameCode(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameCode(new byte[] {0x00});
    }
}
