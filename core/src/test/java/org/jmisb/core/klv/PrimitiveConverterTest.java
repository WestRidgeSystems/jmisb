package org.jmisb.core.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimitiveConverterTest
{
    @Test
    public void testToInt16()
    {
        int intVal = PrimitiveConverter.toInt16(new byte[]{0x00, 0x00, 0x00, 0x0f}, 2);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt16(new byte[]{0x00, 0x00, 0x0f, 0x00}, 1);
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt16(new byte[]{0x00, 0x01, 0x01, 0x00}, 1);
        Assert.assertEquals(intVal, 257);

        intVal = PrimitiveConverter.toInt16(new byte[]{0x00, 0x03});
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt16(new byte[]{0x00, 0x03}, 0);
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt16(new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(intVal, 32767);

        intVal = PrimitiveConverter.toInt16(new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(intVal, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_1()
    {
        PrimitiveConverter.toInt16(new byte[]{(byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_3()
    {
        PrimitiveConverter.toInt16(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_2_offset()
    {
        PrimitiveConverter.toInt16(new byte[]{(byte)0xff, (byte)0xff}, 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt16BadLength_3_offset()
    {
        PrimitiveConverter.toInt16(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff}, 2);
    }

    @Test
    public void testToInt32()
    {
        int intVal = PrimitiveConverter.toInt32(new byte[]{0x00, 0x00, 0x00, 0x0f});
        Assert.assertEquals(intVal, 15);

        intVal = PrimitiveConverter.toInt32(new byte[]{0x00, 0x00, 0x00, 0x00});
        Assert.assertEquals(intVal, 0);

        intVal = PrimitiveConverter.toInt32(new byte[]{0x00, 0x03});
        Assert.assertEquals(intVal, 3);

        intVal = PrimitiveConverter.toInt32(new byte[]{0x11});
        Assert.assertEquals(intVal, 17);

        intVal = PrimitiveConverter.toInt32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(intVal, -1);
    }

    @Test
    public void testSignedInt16ToBytes()
    {
        short shortVal = -1;
        byte[] bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});

        shortVal = 10;
        bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x00, (byte)0x0a});

        shortVal = 32767;
        bytes = PrimitiveConverter.int16ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x7f, (byte)0xff});
    }

    @Test
    public void testSignedInt32ToBytes()
    {
        int intVal = -1;
        byte[] bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});

        intVal = 10;
        bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00, 0x00, 0x0a});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint8BadLength_2()
    {
        PrimitiveConverter.toUint8(new byte[]{(byte)0xff, (byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint16BadLength_1()
    {
        PrimitiveConverter.toUint16(new byte[]{(byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint16BadLength_3()
    {
        PrimitiveConverter.toUint16(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff});
    }

    @Test
    public void testToUint32()
    {
        long longVal = PrimitiveConverter.toUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 32) - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToUint32BadLength()
    {
        PrimitiveConverter.toUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff});
    }

    @Test
    public void testVariableBytesToUint32_1()
    {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 8) - 1);
    }

    @Test
    public void testVariableBytesToUint32_2()
    {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 16) - 1);
    }

    @Test
    public void testVariableBytesToUint32_3_ff()
    {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 24) - 1);
    }

    @Test
    public void testVariableBytesToUint32_3_0()
    {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longVal, 0L);
    }

    @Test
    public void testVariableBytesToUint32_4()
    {
        long longVal = PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 32) - 1);
    }

    @Test
    public void checkUintInternalVsStandard_1() {
        byte[] bytes = new byte[]{(byte) 0x9c};
        long standard = PrimitiveConverter.toUint8(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test
    public void checkUintInternalVsStandard_2() {
        byte[] bytes = new byte[]{(byte) 0x9c, (byte) 0xba};
        long standard = PrimitiveConverter.toUint16(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test
    public void checkUintInternalVsStandard_4() {
        byte[] bytes = new byte[]{(byte) 0x9c, (byte) 0xba, (byte)0xab, (byte)0xd4};
        long standard = PrimitiveConverter.toUint32(bytes);
        long internal = PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
        Assert.assertEquals(internal, standard);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVariableBytesToUint32BadLength()
    {
        PrimitiveConverter.variableBytesToUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00});
    }

    @Test
    public void testUnsignedInt16ToBytes()
    {
        int intVal = 1;
        byte[] bytes = PrimitiveConverter.uint16ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{0x00, 0x01});

        intVal = (int)Math.pow(2, 16) - 1;
        bytes = PrimitiveConverter.uint16ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt16ToBytesTooSmall()
    {
        int val = -1;
        PrimitiveConverter.uint16ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt16ToBytesTooBig()
    {
        int val = (int) Math.pow(2, 16);
        PrimitiveConverter.uint16ToBytes(val);
    }

    @Test
    public void testUnsignedInt32ToBytes()
    {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uint32ToBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00, 0x00, 0x01});

        longVal = (long) Math.pow(2, 32) - 1;
        bytes = PrimitiveConverter.uint32ToBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt32ToBytesTooSmall()
    {
        long longVal = -1L;
        PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt32ToBytesTooBig()
    {
        long longVal = (long) Math.pow(2, 32);
        PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Test
    public void testUnsignedInt32ToVariableBytes()
    {
        long longVal = 1L;
        byte[] bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{0x01});

        longVal = 255L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff});

        longVal = 256L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x01, (byte)0x00});

        longVal = 65535L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});

        longVal = 65536L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00});

        longVal = 16777215L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff});

        longVal = 16777216L;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00});

        longVal = (long) Math.pow(2, 32) - 1;
        bytes = PrimitiveConverter.uint32ToVariableBytes(longVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
    }

    @Test
    public void testUnsignedInt8ToBytes()
    {
        short shortVal = 0;
        byte[] bytes = PrimitiveConverter.uint8ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[]{0x00});

        shortVal = 255;
        bytes = PrimitiveConverter.uint8ToBytes(shortVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt8ToBytesTooSmall()
    {
        short val = -1;
        PrimitiveConverter.uint8ToBytes(val);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUnsignedInt8ToBytesTooBig()
    {
        short val = (short) Math.pow(2, 8);
        PrimitiveConverter.uint8ToBytes(val);
    }

    @Test
    public void testToUint8()
    {
        int val = PrimitiveConverter.toUint8(new byte[]{(byte)0xff});
        Assert.assertEquals(val, 255);

        val = PrimitiveConverter.toUint8(new byte[]{0x00});
        Assert.assertEquals(val, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToInt32InvalidArg()
    {
        PrimitiveConverter.toInt32(new byte[]{0x00, 0x00, 0x00, 0x0f, 0x00});
    }

}
