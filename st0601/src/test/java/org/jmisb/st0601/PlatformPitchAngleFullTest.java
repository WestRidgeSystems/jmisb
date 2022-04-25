package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformPitchAngleFullTest {
    @Test
    public void testMinMax() {
        PlatformPitchAngleFull platformPitchAngleFull = new PlatformPitchAngleFull(-90.0);
        byte[] bytes = platformPitchAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformPitchAngleFull.getDisplayableValue(), "-90.0000\u00B0");

        platformPitchAngleFull = new PlatformPitchAngleFull(90.0);
        bytes = platformPitchAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformPitchAngleFull.getDisplayableValue(), "90.0000\u00B0");

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        platformPitchAngleFull = new PlatformPitchAngleFull(bytes);
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        platformPitchAngleFull = new PlatformPitchAngleFull(bytes);
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), bytes);

        Assert.assertEquals(platformPitchAngleFull.getDisplayName(), "Platform Pitch Angle (Full)");
    }

    @Test
    public void testOutOfBounds() {
        PlatformPitchAngleFull platformPitchAngleFull =
                new PlatformPitchAngleFull(Double.POSITIVE_INFINITY);
        byte[] bytes = platformPitchAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), Double.POSITIVE_INFINITY);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        platformPitchAngleFull = new PlatformPitchAngleFull(bytes);
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), bytes);
    }

    @Test
    public void stExample() {
        double degrees = -0.43152510208614414;
        byte[] expected = new byte[] {(byte) 0xff, (byte) 0x62, (byte) 0xe2, (byte) 0xf2};

        // Create from value
        PlatformPitchAngleFull platformPitchAngleFull = new PlatformPitchAngleFull(degrees);
        byte[] bytes = platformPitchAngleFull.getBytes();
        Assert.assertEquals(bytes, expected);
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), degrees);
        Assert.assertEquals(platformPitchAngleFull.getDisplayableValue(), "-0.4315\u00B0");

        // Create from bytes
        platformPitchAngleFull = new PlatformPitchAngleFull(expected);
        double value = platformPitchAngleFull.getDegrees();
        Assert.assertEquals(value, degrees);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformPitchAngleFull(-90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformPitchAngleFull(90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformPitchAngleFull(new byte[] {0x00, 0x01});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformPitchAngleFull, bytes);
        Assert.assertTrue(v instanceof PlatformPitchAngleFull);
        PlatformPitchAngleFull platformPitchAngleFull = (PlatformPitchAngleFull) v;
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformPitchAngleFull, bytes);
        Assert.assertTrue(v instanceof PlatformPitchAngleFull);
        platformPitchAngleFull = (PlatformPitchAngleFull) v;
        Assert.assertEquals(platformPitchAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformPitchAngleFull.getBytes(), bytes);
    }
}
