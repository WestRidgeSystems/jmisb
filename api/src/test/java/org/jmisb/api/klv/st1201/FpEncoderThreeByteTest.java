package org.jmisb.api.klv.st1201;

import org.testng.Assert;
import org.testng.annotations.*;

public class FpEncoderThreeByteTest {
    @Test
    public void testDecode3byte_0() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x00, (byte) 0x00, (byte) 0x00};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, -900.0, 1e-8);
    }

    @Test
    public void testEncode3byte_0() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = fpEncoder.encode(-900.0);
        byte[] expected = {(byte) 0x00, (byte) 0x00, (byte) 0x00};
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecode3byte_0_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x00, (byte) 0x00, (byte) 0x01};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, -899.998046875, 1e-8);
    }

    @Test
    public void testEncode3byte_0_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = fpEncoder.encode(-899.998046875);
        byte[] expected = {(byte) 0x00, (byte) 0x00, (byte) 0x01};
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecode3byte_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x08, (byte) 0x98, (byte) 0x00};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 200, 1e-8);
    }

    @Test
    public void testEncode3byte_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = fpEncoder.encode(200.0);
        byte[] expected = {(byte) 0x08, (byte) 0x98, (byte) 0x00};
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecode3byte_1_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x08, (byte) 0x98, (byte) 0x01};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 200.001953125, 1e-8);
    }

    @Test
    public void testEncode3byte_1_1() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = fpEncoder.encode(200.001953125);
        byte[] expected = {(byte) 0x08, (byte) 0x98, (byte) 0x01};
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecode3byte_1_7F() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x08, (byte) 0x98, (byte) 0x7F};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 200.248046875, 1e-8);
    }

    @Test
    public void testDecode3byte_2() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x08, (byte) 0xFC, (byte) 0x00};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 250, 1e-8);
    }

    @Test
    public void testDecode3byte_3() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x07, (byte) 0xD0, (byte) 0x00};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 100, 1e-8);
    }

    @Test
    public void testDecode3byte_4() {
        FpEncoder fpEncoder = new FpEncoder(-900, 9000, 3, OutOfRangeBehaviour.Default);
        byte[] encoded = {(byte) 0x09, (byte) 0x60, (byte) 0x00};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 300, 1e-8);
    }
}
