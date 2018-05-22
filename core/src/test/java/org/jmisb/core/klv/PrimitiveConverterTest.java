package org.jmisb.core.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimitiveConverterTest
{
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
    public void testSignedInt32ToBytes()
    {
        int intVal = -1;
        byte[] bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});

        intVal = 10;
        bytes = PrimitiveConverter.int32ToBytes(intVal);
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00, 0x00, 0x0a});
    }

    @Test
    public void testToUint32()
    {
        long longVal = PrimitiveConverter.toUint32(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longVal, (long) Math.pow(2, 32) - 1);
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
    public void testToInt32InvalidArg()
    {
        PrimitiveConverter.toInt32(new byte[]{0x00, 0x00, 0x00, 0x0f, 0x00});
    }

}
