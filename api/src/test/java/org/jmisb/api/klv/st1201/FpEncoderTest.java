package org.jmisb.api.klv.st1201;

import org.testng.Assert;
import org.testng.annotations.*;

public class FpEncoderTest
{
    @Test
    public void testIntegerLength()
    {
        // 100 values, should be encoded in 1 byte
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 0.1);
        Assert.assertEquals(fpEncoder.getFieldLength(), 1);

        // 1000 values, should require 2 bytes
        FpEncoder fpEncoder2 = new FpEncoder(0.0, 100.0, 0.1);
        Assert.assertEquals(fpEncoder2.getFieldLength(), 2);

        // 2 billion values, should require 4 bytes
        FpEncoder fpEncoder4 = new FpEncoder(0.0, 2000000000.0, 1.0);
        Assert.assertEquals(fpEncoder4.getFieldLength(), 4);

        // Provide explicit integer length
        FpEncoder fpEncoder8 = new FpEncoder(0.0, 15000.0, 8);
        Assert.assertEquals(fpEncoder8.getFieldLength(), 8);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidLength()
    {
        FpEncoder invalid = new FpEncoder(0.0, 10.0, 27);
        invalid.encode(3.14159);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidPrecision()
    {
        FpEncoder invalid = new FpEncoder(0.0, 1e9, 1e-30);
        invalid.encode(3.14159);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRange()
    {
        FpEncoder encoder = new FpEncoder(-100.0, 100.0, 4);
        encoder.encode(-500.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRangeOver()
    {
        FpEncoder encoder = new FpEncoder(-100.0, 100.0, 4);
        encoder.encode(100.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMismatchedLength()
    {
        FpEncoder encoder = new FpEncoder(-100.0, +100.0, 2);
        byte[] array = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        encoder.decode(array);
    }

    @Test
    public void testEncode()
    {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        // Test a normal floating point number, pi
        double value = 3.14159;
        byte[] expected = {0x00, 0x00, 0x00, 0x06, 0x48, 0x7e, 0x7c, 0x06};
        byte[] encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded.length, 8);
        Assert.assertEquals(encoded, expected);

        // Test +infinity
        value = Double.POSITIVE_INFINITY;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte)0xc8);

        // Test -infinity
        value = Double.NEGATIVE_INFINITY;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte)0xe8);

        // Test NaN
        value = Double.NaN;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte)0xd0);
    }

    @Test
    public void testEncodeLen1()
    {
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 1);
        double value = 6.0;
        byte[] expected = {0x30};
        byte[] encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded.length, 1);
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecodeLen1()
    {
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 1);
        double expected = 6.0;
        byte[] value = {0x30};
        double decoded = fpEncoder.decode(value);
        Assert.assertEquals(decoded, expected);
    }

    @Test
    public void testDecode()
    {
        // Test a normal floating point number, pi
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);
        byte[] encoded = {0x00, 0x00, 0x00, 0x06, 0x48, 0x7e, 0x7c, 0x06};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 3.14159, 1e-8);

        // Test +infinity
        byte[] posInf = {(byte)0xc8, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        val = fpEncoder.decode(posInf);
        Assert.assertEquals(val, Double.POSITIVE_INFINITY);

        // Test -infinity
        byte[] negInf = {(byte)0xe8, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        val = fpEncoder.decode(negInf);
        Assert.assertEquals(val, Double.NEGATIVE_INFINITY);

        // Test NaN
        byte[] nan = {(byte)0xd0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        val = fpEncoder.decode(nan);
        Assert.assertTrue(Double.isNaN(val));
    }
}