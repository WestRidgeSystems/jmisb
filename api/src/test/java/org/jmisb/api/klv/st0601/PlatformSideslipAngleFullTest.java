package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformSideslipAngleFullTest {
    private static final int RESOLUTION = 84 * 10 - 9;

    @Test
    public void testMinMax() {
        PlatformSideslipAngleFull platformSideslipAngleFull = new PlatformSideslipAngleFull(-180.0);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), -180.0);
        Assert.assertEquals(platformSideslipAngleFull.getDisplayableValue(), "-180.0000\u00B0");

        platformSideslipAngleFull = new PlatformSideslipAngleFull(180.0);
        bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), 180.0);
        Assert.assertEquals(platformSideslipAngleFull.getDisplayableValue(), "180.0000\u00B0");

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), -180.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), 180.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);

        Assert.assertEquals(
                platformSideslipAngleFull.getDisplayName(), "Platform Sideslip Angle (Full)");
    }

    @Test
    public void testOutOfBounds() {
        PlatformSideslipAngleFull platformSideslipAngleFull =
                new PlatformSideslipAngleFull(Double.POSITIVE_INFINITY);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), Double.POSITIVE_INFINITY);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);
    }

    @Test
    public void stExample() {
        double degrees = -47.683;
        byte[] expected = new byte[] {(byte) 0xDE, (byte) 0x17, (byte) 0x93, (byte) 0x23};

        // Create from value
        PlatformSideslipAngleFull platformSideslipAngleFull =
                new PlatformSideslipAngleFull(degrees);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, expected);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), degrees);
        Assert.assertEquals(platformSideslipAngleFull.getDisplayableValue(), "-47.6830\u00B0");

        // Create from bytes
        platformSideslipAngleFull = new PlatformSideslipAngleFull(expected);
        double value = platformSideslipAngleFull.getDegrees();
        Assert.assertEquals(value, degrees, RESOLUTION);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformSideslipAngleFull(-180.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformSideslipAngleFull(180.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformSideslipAngleFull(new byte[] {0x00, 0x01});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformSideSlipAngle, bytes);
        Assert.assertTrue(v instanceof PlatformSideslipAngleFull);
        PlatformSideslipAngleFull platformSideslipAngleFull = (PlatformSideslipAngleFull) v;
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), -180.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformSideSlipAngle, bytes);
        Assert.assertTrue(v instanceof PlatformSideslipAngleFull);
        platformSideslipAngleFull = (PlatformSideslipAngleFull) v;
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), 180.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);
    }
}
