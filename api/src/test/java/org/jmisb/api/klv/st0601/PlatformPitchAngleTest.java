package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformPitchAngleTest {
    @Test
    public void testMinMax() {
        PlatformPitchAngle platformPitchAngle = new PlatformPitchAngle(-20.0);
        byte[] bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01}); // -32767 as int16
        Assert.assertEquals(platformPitchAngle.getDegrees(), -20.0);
        Assert.assertEquals("-20.0000\u00B0", platformPitchAngle.getDisplayableValue());

        platformPitchAngle = new PlatformPitchAngle(20.0);
        bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff}); // 32767 as int16
        Assert.assertEquals(platformPitchAngle.getDegrees(), 20.0);
        Assert.assertEquals("20.0000\u00B0", platformPitchAngle.getDisplayableValue());

        bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        platformPitchAngle = new PlatformPitchAngle(bytes);
        Assert.assertEquals(platformPitchAngle.getDegrees(), -20.0);
        Assert.assertEquals(platformPitchAngle.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        platformPitchAngle = new PlatformPitchAngle(bytes);
        Assert.assertEquals(platformPitchAngle.getDegrees(), 20.0);
        Assert.assertEquals(platformPitchAngle.getBytes(), bytes);

        Assert.assertEquals(platformPitchAngle.getDisplayName(), "Platform Pitch Angle");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformPitchAngle, bytes);
        Assert.assertTrue(v instanceof PlatformPitchAngle);
        PlatformPitchAngle platformPitchAngle = (PlatformPitchAngle) v;
        Assert.assertEquals(platformPitchAngle.getDegrees(), -20.0);
        Assert.assertEquals(platformPitchAngle.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformPitchAngle, bytes);
        Assert.assertTrue(v instanceof PlatformPitchAngle);
        platformPitchAngle = (PlatformPitchAngle) v;
        Assert.assertEquals(platformPitchAngle.getDegrees(), 20.0);
        Assert.assertEquals(platformPitchAngle.getBytes(), bytes);
    }

    @Test
    public void testZero() {
        PlatformPitchAngle platformPitchAngle = new PlatformPitchAngle(0.0);
        byte[] bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformPitchAngle.getDegrees(), 0.0);
        Assert.assertEquals("0.0000\u00B0", platformPitchAngle.getDisplayableValue());
    }

    @Test
    public void testExample() {
        // Resolution is 610 micro degrees, i.e., .00061 degrees. So max error should be +/- .000305
        // degrees
        double delta = 0.000305;

        // -0.4315251 -> 0xfd 0x3d
        double degrees = -0.4315251;
        PlatformPitchAngle platformPitchAngle = new PlatformPitchAngle(degrees);
        byte[] bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xfd, (byte) 0x3d});
        Assert.assertEquals(platformPitchAngle.getDegrees(), degrees);
        Assert.assertEquals("-0.4315\u00B0", platformPitchAngle.getDisplayableValue());

        // 0xfd 0x3d -> -0.4315251
        bytes = new byte[] {(byte) 0xfd, (byte) 0x3d};
        platformPitchAngle = new PlatformPitchAngle(bytes);
        Assert.assertEquals(platformPitchAngle.getDegrees(), degrees, delta);
        Assert.assertEquals(platformPitchAngle.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange() {
        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00};

        PlatformPitchAngle platformPitchAngle = new PlatformPitchAngle(Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformPitchAngle.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformPitchAngle.getBytes(), error);

        platformPitchAngle = new PlatformPitchAngle(error);
        Assert.assertEquals(platformPitchAngle.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformPitchAngle.getBytes(), error);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformPitchAngle(-20.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformPitchAngle(20.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformPitchAngle(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
