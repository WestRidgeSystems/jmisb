package org.jmisb.core.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimitiveConverterTest {

    @Test
    public void testToInt16() {
        int intVal = PrimitiveConverter.toInt16(new byte[] {0x00, 0x00, 0x00, 0x0f}, 2);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt16(new byte[] {0x00, 0x00, 0x0f, 0x00}, 1);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt16(new byte[] {0x00, 0x01, 0x01, 0x00}, 1);
        Assert.assertEquals(intVal, 257);

        intVal = PrimitiveConverter.toInt16(new byte[] {0x00, 0x03});
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt16(new byte[] {0x00, 0x03}, 0);
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt16(new byte[] {(byte) 0x7f, (byte) 0xff});
        Assert.assertEquals(intVal, 32767);

        intVal = PrimitiveConverter.toInt16(new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(intVal, -1);
    }

    @Test
    public void testToInt32Offset() {
        int intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00, 0x0f}, 0);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt32(new byte[] {(byte) 0xff, 0x00, 0x00, 0x00, 0x0f}, 1);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x40, 0x00, 0x01, 0x01, 0x00}, 1);
        Assert.assertEquals(intVal, 65536 + 256);

        intVal =
                PrimitiveConverter.toInt16(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, 0);
        Assert.assertEquals(intVal, -1);

        intVal =
                PrimitiveConverter.toInt16(
                        new byte[] {
                            (byte) 0x1f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                        },
                        1);
        Assert.assertEquals(intVal, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt32OffsetOverrun() {
        PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00, 0x0f}, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_1() {
        PrimitiveConverter.toInt16(new byte[] {(byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_3() {
        PrimitiveConverter.toInt16(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_2_offset() {
        PrimitiveConverter.toInt16(new byte[] {(byte) 0xff, (byte) 0xff}, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_3_offset() {
        PrimitiveConverter.toInt16(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff}, 2);
    }

    @Test
    public void testToInt32() {
        int intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00, 0x0f});
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00, 0x00});
        Assert.assertEquals(intVal, 0);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00});
        Assert.assertEquals(intVal, 0);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x03});
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x03});
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt32(new byte[] {0x11});
        Assert.assertEquals(intVal, 17);

        intVal =
                PrimitiveConverter.toInt32(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(intVal, -1);

        intVal = PrimitiveConverter.toInt32(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(intVal, -1);
    }

    @Test
    public void testSignedInt16ToBytes() {
        short shortVal = -1;
        byte[] bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});

        shortVal = 10;
        bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x0a});

        shortVal = 32767;
        bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff});
    }

    @Test
    public void testSignedInt16ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.int16ToBytes((short) 1);
        Assert.assertEquals(bytes1, new byte[] {(byte) 0x00, (byte) 0x01});

        byte[] bytes2 = PrimitiveConverter.int16ToBytes((short) 2);
        Assert.assertEquals(bytes2, new byte[] {(byte) 0x00, (byte) 0x02});

        // Verify the original array is still OK
        Assert.assertEquals(bytes1, new byte[] {(byte) 0x00, (byte) 0x01});
    }

    @Test
    public void testSignedInt32ToBytes() {
        int intVal = -1;
        byte[] bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        intVal = 10;
        bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x00, 0x00, 0x00, 0x0a});
    }

    @Test
    public void testSignedInt32ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.int32ToBytes(1);
        Assert.assertEquals(
                bytes1, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});

        byte[] bytes2 = PrimitiveConverter.int32ToBytes(2);
        Assert.assertEquals(bytes2, new byte[] {0x00, 0x00, 0x00, 0x02});

        // Verify the original array is still OK
        Assert.assertEquals(
                bytes1, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
    }

    @Test
    public void testSignedInt32ToVariableBytes() {
        int intVal = -1;
        byte[] bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});

        intVal = 0;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x00});

        intVal = 10;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x0a});

        intVal = 127;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x7f});

        intVal = -127;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x81});

        intVal = -128;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80});

        intVal = 128;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x80});

        intVal = -129;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0x7F});

        intVal = 32767;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7F, (byte) 0xFF});

        intVal = -32767;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01});

        intVal = -32768;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00});

        intVal = 32768;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00});

        intVal = -32769;
        bytes = PrimitiveConverter.int32ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0x7F, (byte) 0xFF});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint8BadLength_2() {
        PrimitiveConverter.toUint8(new byte[] {(byte) 0xff, (byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint16BadLength_1() {
        PrimitiveConverter.toUint16(new byte[] {(byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint16BadLength_3() {
        PrimitiveConverter.toUint16(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test
    public void testToUint32() {
        long longVal =
                PrimitiveConverter.toUint32(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 32) - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint32BadLength() {
        PrimitiveConverter.toUint32(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test
    public void testVariableBytesToUint32_1() {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[] {(byte) 0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 8) - 1);
    }

    @Test
    public void testVariableBytesToInt64_1() {
        long longVal = PrimitiveConverter.variableBytesToInt64(new byte[] {(byte) 0xff}, 0, 1);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToUint32_2() {
        long longVal =
                PrimitiveConverter.variableBytesToUint32(new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 16) - 1);
    }

    @Test
    public void testVariableBytesToInt64_2() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {(byte) 0xff, (byte) 0xff}, 0, 2);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToInt64_2_1() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff}, 1, 2);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToUint32_3_ff() {
        long longVal =
                PrimitiveConverter.variableBytesToUint32(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 24) - 1);
    }

    @Test
    public void testVariableBytesToInt64_3() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff}, 0, 3);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToUint32_3_0() {
        long longVal =
                PrimitiveConverter.variableBytesToUint32(
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(longVal, 0L);
    }

    @Test
    public void testVariableBytesToInt64_3_0() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00}, 0, 3);
        Assert.assertEquals(longVal, 0L);
    }

    @Test
    public void testVariableBytesToUint32_4() {
        long longVal =
                PrimitiveConverter.variableBytesToUint32(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 32) - 1);
    }

    @Test
    public void testVariableBytesToInt64_4_0() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}, 0, 4);
        Assert.assertEquals(longVal, 0L);
    }

    @Test
    public void testVariableBytesToInt64_5() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {
                            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                        },
                        0,
                        5);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToInt64_6() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        0,
                        6);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToInt64_7() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        0,
                        7);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToInt64_7_1() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {
                            (byte) 0x4e,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        1,
                        7);
        Assert.assertEquals(longVal, -1L);
    }

    @Test
    public void testVariableBytesToInt64_8() {
        long longVal =
                PrimitiveConverter.variableBytesToInt64(
                        new byte[] {
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        0,
                        8);
        Assert.assertEquals(longVal, -1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToInt64_9() {
        PrimitiveConverter.variableBytesToInt64(
                new byte[] {
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                },
                0,
                9);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToInt64_0() {
        PrimitiveConverter.variableBytesToInt64(
                new byte[] {
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                },
                0,
                0);
    }

    @Test
    public void testVariableBytesToUint16_1() {
        int intVal = PrimitiveConverter.variableBytesToUint16(new byte[] {(byte) 0xff});
        Assert.assertEquals(intVal, Math.pow(2, 8) - 1);
    }

    @Test
    public void testVariableBytesToUint16_2() {
        int intVal =
                PrimitiveConverter.variableBytesToUint16(new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(intVal, Math.pow(2, 16) - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUint16BadLength() {
        PrimitiveConverter.variableBytesToUint16(
                new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0x00});
    }

    @Test
    public void checkUintInternalVsStandard_1() {
        byte[] bytes = new byte[] {(byte) 0x9c};
        long standard = PrimitiveConverter.toUint8(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test
    public void checkUintInternalVsStandard_2() {
        byte[] bytes = new byte[] {(byte) 0x9c, (byte) 0xba};
        long standard = PrimitiveConverter.toUint16(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test
    public void checkUintInternalVsStandard_4() {
        byte[] bytes = new byte[] {(byte) 0x9c, (byte) 0xba, (byte) 0xab, (byte) 0xd4};
        long standard = PrimitiveConverter.toUint32(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUint32BadLength() {
        PrimitiveConverter.variableBytesToUint32(
                new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testUnsignedInt16ToBytes() {
        int intVal = 1;
        byte[] bytes = PrimitiveConverter.uint16ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x00, 0x01});

        intVal = (int) Math.pow(2, 16) - 1;
        bytes = PrimitiveConverter.uint16ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt16ToBytesTooSmall() {
        int val = -1;
        PrimitiveConverter.uint16ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt16ToBytesTooBig() {
        int val = (int) Math.pow(2, 16);
        PrimitiveConverter.uint16ToBytes(val);
    }

    @Test
    public void testUnsignedInt32ToBytes() {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uint32ToBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {0x00, 0x00, 0x00, 0x01});

        longVal = (long) Math.pow(2, 32) - 1;
        bytes = PrimitiveConverter.uint32ToBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt32ToBytesTooSmall() {
        long longVal = -1L;
        PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt32ToBytesTooBig() {
        long longVal = (long) Math.pow(2, 32);
        PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Test
    public void testUnsignedInt32ToVariableBytes() {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {0x01});

        longVal = 255L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});

        longVal = 256L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00});

        longVal = 65535L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});

        longVal = 65536L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00});

        longVal = 16777215L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 16777216L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        longVal = (long) Math.pow(2, 32) - 1;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test
    public void testUnsignedIntToVariableBytesV6() {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {0x01});

        longVal = 255L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});

        longVal = 256L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00});

        longVal = 65535L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});

        longVal = 65536L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00});

        longVal = 16777215L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 16777216L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        longVal = 4294967295L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 4294967296L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        longVal = 1099511627775L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 1099511627776L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                });

        longVal = 281474976710655L;
        bytes = PrimitiveConverter.uintToVariableBytesV6(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                });
    }

    @Test
    public void testUnsignedIntToVariableBytes() {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {0x01});

        longVal = 255L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});

        longVal = 256L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00});

        longVal = 65535L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});

        longVal = 65536L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00});

        longVal = 16777215L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 16777216L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        longVal = 4294967295L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 4294967296L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        longVal = 1099511627775L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        longVal = 1099511627776L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                });

        longVal = 281474976710655L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                });

        longVal = 72057594037927935L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                });

        longVal = 72057594037927936L;
        bytes = PrimitiveConverter.uintToVariableBytes(longVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUintBadLength() {
        PrimitiveConverter.variableBytesToUint64(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09
                });
    }

    @Test
    public void testVariableBytesToUint() {
        long val = PrimitiveConverter.variableBytesToUint64(new byte[] {0x01});
        Assert.assertEquals(val, 1L);

        val = PrimitiveConverter.variableBytesToUint64(new byte[] {(byte) 0xFF});
        Assert.assertEquals(val, 255L);

        val = PrimitiveConverter.variableBytesToUint64(new byte[] {(byte) 0x01, (byte) 0x00});
        Assert.assertEquals(val, 256L);

        val = PrimitiveConverter.variableBytesToUint64(new byte[] {(byte) 0xFF, (byte) 0xFF});
        Assert.assertEquals(val, 65535L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(val, 65536L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(val, 16777215L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(val, 16777216L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(val, 4294967295L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                        });
        Assert.assertEquals(val, 4294967296L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                        });
        Assert.assertEquals(val, 1099511627775L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        Assert.assertEquals(val, 1099511627776L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        });
        Assert.assertEquals(val, 281474976710655L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        });
        Assert.assertEquals(val, 72057594037927935L);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        Assert.assertEquals(val, 72057594037927936L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUintBadLength0Offset() {
        PrimitiveConverter.variableBytesToUint64(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09,
                    (byte) 0x0A
                },
                1,
                0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUintBadLengthOffset() {
        PrimitiveConverter.variableBytesToUint64(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09,
                    (byte) 0x0A
                },
                1,
                9);
    }

    @Test
    public void testVariableBytesToUintOffset1() {
        long val = PrimitiveConverter.variableBytesToUint64(new byte[] {(byte) 0x7F, 0x01}, 1, 1);
        Assert.assertEquals(val, 1L);
    }

    @Test
    public void testVariableBytesToUintOffset255() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x7F, (byte) 0xFF}, 1, 1);
        Assert.assertEquals(val, 255L);
    }

    @Test
    public void testVariableBytesToUintOffset256() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x7F, (byte) 0x01, (byte) 0x00}, 1, 2);
        Assert.assertEquals(val, 256L);
    }

    @Test
    public void testVariableBytesToUintOffset65535() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x7F, (byte) 0xFF, (byte) 0xFF}, 1, 2);
        Assert.assertEquals(val, 65535L);
    }

    @Test
    public void testVariableBytesToUintOffset65536() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x7F, (byte) 0x01, (byte) 0x00, (byte) 0x00}, 1, 3);
        Assert.assertEquals(val, 65536L);
    }

    @Test
    public void testVariableBytesToUintOffset16777215L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff}, 1, 3);
        Assert.assertEquals(val, 16777215L);
    }

    @Test
    public void testVariableBytesToUintOffset16777216L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00
                        },
                        1,
                        4);
        Assert.assertEquals(val, 16777216L);
    }

    @Test
    public void testVariableBytesToUintOffset4294967295L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                        },
                        1,
                        4);
        Assert.assertEquals(val, 4294967295L);
    }

    @Test
    public void testVariableBytesToUintOffset4294967296L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        1,
                        5);
        Assert.assertEquals(val, 4294967296L);
    }

    @Test
    public void testVariableBytesToUintOffset1099511627775L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        1,
                        5);
        Assert.assertEquals(val, 1099511627775L);
    }

    @Test
    public void testVariableBytesToUintOffset1099511627776L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        1,
                        6);
        Assert.assertEquals(val, 1099511627776L);
    }

    @Test
    public void testVariableBytesToUintOffset281474976710655L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        1,
                        6);
        Assert.assertEquals(val, 281474976710655L);
    }

    @Test
    public void testVariableBytesToUintOffset72057594037927935L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff,
                            (byte) 0xff
                        },
                        1,
                        7);
        Assert.assertEquals(val, 72057594037927935L);
    }

    @Test
    public void testVariableBytesToUintOffset72057594037927936L() {
        long val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {
                            (byte) 0x7F,
                            (byte) 0x01,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        1,
                        8);
        Assert.assertEquals(val, 72057594037927936L);
    }

    @Test
    public void testUnsignedInt8ToBytes() {
        short shortVal = 0;
        byte[] bytes = PrimitiveConverter.uint8ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[] {0x00});

        shortVal = 255;
        bytes = PrimitiveConverter.uint8ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});
    }

    @Test
    public void testUnsignedInt8ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.uint8ToBytes((short) 1);
        Assert.assertEquals(bytes1, new byte[] {0x01});

        byte[] bytes2 = PrimitiveConverter.uint8ToBytes((short) 255);
        Assert.assertEquals(bytes2, new byte[] {(byte) 0xff});

        Assert.assertEquals(bytes1, new byte[] {0x01});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt8ToBytesTooSmall() {
        short val = -1;
        PrimitiveConverter.uint8ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt8ToBytesTooBig() {
        short val = (short) Math.pow(2, 8);
        PrimitiveConverter.uint8ToBytes(val);
    }

    @Test
    public void testToUint8() {
        int val = PrimitiveConverter.toUint8(new byte[] {(byte) 0xff});
        Assert.assertEquals(val, 255);

        val = PrimitiveConverter.toUint8(new byte[] {0x00});
        Assert.assertEquals(val, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt32InvalidArg() {
        PrimitiveConverter.toInt32(new byte[] {0x00, 0x00, 0x00, 0x0f, 0x00});
    }

    @Test
    public void testToInt64() {
        long val =
                PrimitiveConverter.toInt64(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x04,
                            (byte) 0x59,
                            (byte) 0xF4,
                            (byte) 0xA6,
                            (byte) 0xAA,
                            (byte) 0x4A,
                            (byte) 0xA8
                        });
        Assert.assertEquals(val, 1224807209913000L);
    }

    @Test
    public void testToInt64Offset() {
        long val =
                PrimitiveConverter.toInt64(
                        new byte[] {
                            (byte) 0x12,
                            (byte) 0x34,
                            (byte) 0x00,
                            (byte) 0x04,
                            (byte) 0x59,
                            (byte) 0xF4,
                            (byte) 0xA6,
                            (byte) 0xAA,
                            (byte) 0x4A,
                            (byte) 0xA8,
                            (byte) 0x56
                        },
                        2);
        Assert.assertEquals(val, 1224807209913000L);
    }

    @Test
    public void testToInt64OffsetExact() {
        long val =
                PrimitiveConverter.toInt64(
                        new byte[] {
                            (byte) 0x12,
                            (byte) 0x34,
                            (byte) 0x00,
                            (byte) 0x04,
                            (byte) 0x59,
                            (byte) 0xF4,
                            (byte) 0xA6,
                            (byte) 0xAA,
                            (byte) 0x4A,
                            (byte) 0xA8
                        },
                        2);
        Assert.assertEquals(val, 1224807209913000L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt64InvalidArg() {
        PrimitiveConverter.toInt64(
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xF4,
                    (byte) 0xA6,
                    (byte) 0xAA,
                    (byte) 0x4A
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt64OffsetInvalidArg() {
        PrimitiveConverter.toInt64(
                new byte[] {
                    (byte) 0x12,
                    (byte) 0x34,
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xF4,
                    (byte) 0xA6,
                    (byte) 0xAA,
                    (byte) 0x4A
                },
                2);
    }

    @Test
    public void testVariableBytesToSignedInt64() {
        byte[] bytes = new byte[] {(byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes = new byte[] {(byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes = new byte[] {(byte) 0x80};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -128);

        bytes = new byte[] {(byte) 0x7F};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 127);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -32768);

        bytes = new byte[] {(byte) 0x7F, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 32767);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -8388608);

        bytes = new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 8388607);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -2147483648);

        bytes = new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 2147483647);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -549755813888L);

        bytes = new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 549755813887L);

        bytes =
                new byte[] {
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes =
                new byte[] {
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes =
                new byte[] {
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -140737488355328L);

        bytes =
                new byte[] {
                    (byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 140737488355327L);

        bytes =
                new byte[] {
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes =
                new byte[] {
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -36028797018963968L);

        bytes =
                new byte[] {
                    (byte) 0x7F,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 36028797018963967L);

        bytes =
                new byte[] {
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), -1);

        bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), 0);

        bytes =
                new byte[] {
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), Long.MIN_VALUE);

        bytes =
                new byte[] {
                    (byte) 0x7F,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                };
        Assert.assertEquals(PrimitiveConverter.variableBytesToInt64(bytes), Long.MAX_VALUE);
    }

    @Test
    public void testSignedInt64ToVariableBytes() {
        long intVal = -1;
        byte[] bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff});

        intVal = 0;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x00});

        intVal = 10;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x0a});

        intVal = 127;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {0x7f});

        intVal = -127;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x81});

        intVal = -128;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80});

        intVal = 128;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x80});

        intVal = -129;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0x7F});

        intVal = 32767;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7F, (byte) 0xFF});

        intVal = -32767;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01});

        intVal = -32768;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00});

        intVal = 32768;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x80, (byte) 0x00});

        intVal = -32769;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xFF, (byte) 0x7F, (byte) 0xFF});

        intVal = 32768;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x80, (byte) 0x00});

        intVal = -8388608;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00});

        intVal = 8388607;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff});

        intVal = -2147483648;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        intVal = 2147483647;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        intVal = -549755813888L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        intVal = 549755813887L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {(byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        intVal = -140737488355328L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
                });

        intVal = 140737488355327L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x7F, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                });

        intVal = -36028797018963968L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });

        intVal = 36028797018963967L;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x7F,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                });

        intVal = Long.MIN_VALUE;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });

        intVal = Long.MAX_VALUE;
        bytes = PrimitiveConverter.int64ToVariableBytes(intVal);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x7F,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff,
                    (byte) 0xff
                });
    }

    @Test
    public void testSignedInt64ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.int64ToBytes(1);
        Assert.assertEquals(
                bytes1,
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01
                });

        byte[] bytes2 = PrimitiveConverter.int64ToBytes(2);
        Assert.assertEquals(
                bytes2,
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02
                });

        // Verify the original array is still OK
        Assert.assertEquals(
                bytes1,
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01
                });
    }

    @Test
    public void testSignedInt64ToVariableBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.int64ToVariableBytes(1);
        Assert.assertEquals(bytes1, new byte[] {(byte) 0x01});

        byte[] bytes2 = PrimitiveConverter.int64ToVariableBytes(2);
        Assert.assertEquals(bytes2, new byte[] {0x02});

        // Verify the original array is still OK
        Assert.assertEquals(bytes1, new byte[] {(byte) 0x01});
    }

    @Test
    public void testFloat32ToBytes() {
        byte[] bytes = PrimitiveConverter.float32ToBytes(2.0f);
        Assert.assertEquals(bytes, new byte[] {(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        bytes = PrimitiveConverter.float32ToBytes(-4.0f);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xC0, (byte) 0x80, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testToFloat32() {
        float f =
                PrimitiveConverter.toFloat32(
                        new byte[] {(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(f, 2.0f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToFloat32_badLength() {
        PrimitiveConverter.toFloat32(new byte[] {(byte) 0x40, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testToDouble64_4bytes() {
        double d =
                PrimitiveConverter.toFloat64(
                        new byte[] {(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(d, 2.0);
    }

    @Test
    public void testToDouble64_8bytes() {
        double d =
                PrimitiveConverter.toFloat64(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        Assert.assertEquals(d, 2.0);
    }

    @Test
    public void testToDouble64_offset() {
        double d =
                PrimitiveConverter.toFloat64(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x40,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        1);
        Assert.assertEquals(d, 2.0);
    }

    @Test
    public void testToDouble64_offset_length_8() {
        double d =
                PrimitiveConverter.toFloat64(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x40,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        1,
                        8);
        Assert.assertEquals(d, 2.0);
    }

    @Test
    public void testToDouble64_offset_length_4() {
        double d =
                PrimitiveConverter.toFloat64(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x40,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        2,
                        4);
        Assert.assertEquals(d, 2.0f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToDouble64_offset_len_badLength() {
        PrimitiveConverter.toFloat64(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                1,
                5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToDouble64_offset_badLength() {
        PrimitiveConverter.toFloat64(
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                },
                1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToDouble64_badLength() {
        PrimitiveConverter.toFloat64(
                new byte[] {(byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x05});
    }

    @Test
    public void testToFloat32Negative() {
        float f =
                PrimitiveConverter.toFloat32(
                        new byte[] {(byte) 0xC0, (byte) 0x80, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(f, -4.0f);
    }

    @Test
    public void testToFloat32WithOffset() {
        float f =
                PrimitiveConverter.toFloat32(
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x40,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        },
                        2);
        Assert.assertEquals(f, 2.0f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToFloat32WithOffset_badLength() {
        PrimitiveConverter.toFloat32(
                new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x40, (byte) 0x00, (byte) 0x00}, 2);
    }

    @Test
    public void testFloat32ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.float32ToBytes(1.0f);
        Assert.assertEquals(
                bytes1, new byte[] {(byte) 0x3f, (byte) 0x80, (byte) 0x00, (byte) 0x00});

        byte[] bytes2 = PrimitiveConverter.float32ToBytes(-3.0f);
        Assert.assertEquals(
                bytes2, new byte[] {(byte) 0xC0, (byte) 0x40, (byte) 0x00, (byte) 0x00});

        // Verify the original array is still OK
        Assert.assertEquals(
                bytes1, new byte[] {(byte) 0x3f, (byte) 0x80, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testFloat64ToBytes() {
        byte[] bytes = PrimitiveConverter.float64ToBytes(2.0f);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });

        bytes = PrimitiveConverter.float64ToBytes(-4.0f);
        Assert.assertEquals(
                bytes,
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x10,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testFloat64ToBytesReuse() {
        byte[] bytes1 = PrimitiveConverter.float64ToBytes(2.0f);
        Assert.assertEquals(
                bytes1,
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });

        byte[] bytes2 = PrimitiveConverter.float64ToBytes(-4.0f);
        Assert.assertEquals(
                bytes2,
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x10,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });

        Assert.assertEquals(
                bytes1,
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testToUint24() {
        long val = PrimitiveConverter.toUint24(new byte[] {0x00, 0x00, 0x00});
        Assert.assertEquals(val, 0);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(val, 65536);

        val =
                PrimitiveConverter.variableBytesToUint64(
                        new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(val, 16777215);
    }

    @Test
    public void testUnsignedInt24ToBytes() {
        byte[] bytes = PrimitiveConverter.uint24ToBytes(1);
        Assert.assertEquals(bytes, new byte[] {0x00, 0x00, 0x01});
        int v = (int) (Math.pow(2, 24) - 1);
        bytes = PrimitiveConverter.uint24ToBytes(v);
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt24ToBytesTooSmall() {
        int val = -1;
        PrimitiveConverter.uint24ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt24ToBytesTooBig() {
        int val = (int) Math.pow(2, 24);
        PrimitiveConverter.uint24ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint24BadArrayLength() {
        PrimitiveConverter.toUint24(new byte[] {0x00, 0x00});
    }
}
