package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformSideslipAngleTest {

    @Test
    public void testMinMax() {
        PlatformSideslipAngle angle = new PlatformSideslipAngle(-20.0);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0x80, (byte) 0x01}); // -32767 as int16
        Assert.assertEquals(angle.getDegrees(), -20.0);
        Assert.assertEquals("-20.0000\u00B0", angle.getDisplayableValue());

        angle = new PlatformSideslipAngle(20.0);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0x7f, (byte) 0xff}); // 32767 as int16
        Assert.assertEquals(angle.getDegrees(), 20.0);
        Assert.assertEquals("20.0000\u00B0", angle.getDisplayableValue());

        bytes = new byte[]{(byte) 0x80, (byte) 0x01}; // -32767 as int16
        angle = new PlatformSideslipAngle(bytes);
        Assert.assertEquals(angle.getDegrees(), -20.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[]{(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        angle = new PlatformSideslipAngle(bytes);
        Assert.assertEquals(angle.getDegrees(), 20.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Platform Sideslip Angle");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte) 0x80, (byte) 0x01}; // -32767 as int16
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformSideslipAngle, bytes);
        Assert.assertTrue(v instanceof PlatformSideslipAngle);
        PlatformSideslipAngle angle = (PlatformSideslipAngle) v;
        Assert.assertEquals(angle.getDegrees(), -20.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[]{(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformSideslipAngle, bytes);
        Assert.assertTrue(v instanceof PlatformSideslipAngle);
        angle = (PlatformSideslipAngle) v;
        Assert.assertEquals(angle.getDegrees(), 20.0);
        Assert.assertEquals(angle.getBytes(), bytes);
    }

    @Test
    public void testZero() {
        PlatformSideslipAngle angle = new PlatformSideslipAngle(0.0);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals("0.0000\u00B0", angle.getDisplayableValue());
    }

    @Test
    public void testExample() {
        // Resolution is 610 micro degrees, i.e., .00061 degrees. So max error should be +/- .000305 degrees
        double delta = 0.000305;

        double degrees = -5.08255257;
        PlatformSideslipAngle angle = new PlatformSideslipAngle(degrees);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0xdf, (byte) 0x79});
        Assert.assertEquals(angle.getDegrees(), degrees);
        Assert.assertEquals("-5.0826\u00B0", angle.getDisplayableValue());

        bytes = new byte[]{(byte) 0xdf, (byte) 0x79};
        angle = new PlatformSideslipAngle(bytes);
        Assert.assertEquals(angle.getDegrees(), degrees, delta);
        Assert.assertEquals(angle.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange() {
        byte[] error = new byte[]{(byte) 0x80, (byte) 0x00};

        PlatformSideslipAngle angle = new PlatformSideslipAngle(Double.POSITIVE_INFINITY);
        Assert.assertEquals(angle.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(angle.getBytes(), error);

        angle = new PlatformSideslipAngle(error);
        Assert.assertEquals(angle.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(angle.getBytes(), error);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformSideslipAngle(-20.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformSideslipAngle(20.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformSideslipAngle(new byte[]{0x00, 0x00, 0x00});
    }
}
