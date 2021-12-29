package org.jmisb.api.klv;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for the ArrayBuilder class. */
public class ArrayBuilderTest {

    public ArrayBuilderTest() {}

    @Test
    public void checkBuildNoParts() {
        ArrayBuilder builder = new ArrayBuilder();
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 0);
    }

    @Test
    public void checkBuildTypicalLocalSet() {
        byte[] someData = new byte[] {0x04, 0x02, 0x00, 0x4f};
        ArrayBuilder builder =
                new ArrayBuilder()
                        .append(KlvConstants.RvtLocalSetUl)
                        .appendAsBerLength(someData.length)
                        .append(someData);
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x04, 0x04, 0x02, 0x00, 0x4f
                });
    }

    @Test
    public void checkIntegerTypes() {
        byte[] bytes =
                new ArrayBuilder()
                        .appendAsInt32Primitive(-8723909)
                        .appendAsUInt32Primitive(876)
                        .toBytes();
        assertEquals(
                bytes,
                new byte[] {
                    (byte) 0xff,
                    (byte) 0x7a,
                    (byte) 0xe2,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x6c
                });
    }

    @Test
    public void checkFloatTypes() {
        byte[] bytes =
                new ArrayBuilder()
                        .appendAsFloat32Primitive(3.14f)
                        .appendAsFloat64Primitive(-3.1415)
                        .toBytes();
        assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x48,
                    (byte) 0xf5,
                    (byte) 0xc3,
                    (byte) 0xc0,
                    (byte) 0x09,
                    (byte) 0x21,
                    (byte) 0xca,
                    (byte) 0xc0,
                    (byte) 0x83,
                    (byte) 0x12,
                    (byte) 0x6f
                });
    }

    @Test
    public void checkBuildBits() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.append(new byte[] {(byte) 0xFF});
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.flushBitBuffer();
        builder.append(new byte[] {(byte) 0x08});
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 3);
        assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0b10100000, (byte) 0x08});
    }

    @Test
    public void checkBuildBitsNoFlush() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.append(new byte[] {(byte) 0xFF});
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.append(new byte[] {(byte) 0x08});
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 3);
        assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0b10100000, (byte) 0x08});
    }

    @Test
    public void checkBuildBitsToByteBoundary() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.append(new byte[] {(byte) 0xFF});
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.flushBitBuffer();
        builder.append(new byte[] {(byte) 0x08});
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 3);
        assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0b10100001, (byte) 0x08});
    }

    @Test
    public void checkBuildBitsWithOverflow() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.append(new byte[] {(byte) 0xFF});
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.appendAsBit(false);
        builder.appendAsBit(true);
        builder.flushBitBuffer();
        builder.append(new byte[] {(byte) 0x08});
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 4);
        assertEquals(
                bytes, new byte[] {(byte) 0xFF, (byte) 0b10100001, (byte) 0b01000000, (byte) 0x08});
    }

    @Test
    public void checkBuildBasicByte() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.appendByte((byte) 0xFF);
        byte[] bytes = builder.toBytes();
        assertNotNull(bytes);
        assertEquals(bytes.length, 1);
        assertEquals(bytes, new byte[] {(byte) 0xFF});
    }
}
