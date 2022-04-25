package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformRollAngleFullTest {
    @Test
    public void testMinMax() {
        PlatformRollAngleFull platformRollAngleFull = new PlatformRollAngleFull(-90.0);
        byte[] bytes = platformRollAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(platformRollAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformRollAngleFull.getDisplayableValue(), "-90.0000\u00B0");

        platformRollAngleFull = new PlatformRollAngleFull(90.0);
        bytes = platformRollAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(platformRollAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformRollAngleFull.getDisplayableValue(), "90.0000\u00B0");

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        platformRollAngleFull = new PlatformRollAngleFull(bytes);
        Assert.assertEquals(platformRollAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformRollAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        platformRollAngleFull = new PlatformRollAngleFull(bytes);
        Assert.assertEquals(platformRollAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformRollAngleFull.getBytes(), bytes);

        Assert.assertEquals(platformRollAngleFull.getDisplayName(), "Platform Roll Angle (Full)");
    }

    @Test
    public void testOutOfBounds() {
        PlatformRollAngleFull platformRollAngleFull =
                new PlatformRollAngleFull(Double.POSITIVE_INFINITY);
        byte[] bytes = platformRollAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformRollAngleFull.getDegrees(), Double.POSITIVE_INFINITY);

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        platformRollAngleFull = new PlatformRollAngleFull(bytes);
        Assert.assertEquals(platformRollAngleFull.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformRollAngleFull.getBytes(), bytes);
    }

    @Test
    public void stExample() {
        double degrees = 3.4058139815022304;
        byte[] expected = new byte[] {(byte) 0x04, (byte) 0xd8, (byte) 0x04, (byte) 0xdf};

        // Create from value
        PlatformRollAngleFull platformRollAngleFull = new PlatformRollAngleFull(degrees);
        byte[] bytes = platformRollAngleFull.getBytes();
        Assert.assertEquals(bytes, expected);
        Assert.assertEquals(platformRollAngleFull.getDegrees(), degrees);
        Assert.assertEquals(platformRollAngleFull.getDisplayableValue(), "3.4058\u00B0");

        // Create from bytes
        platformRollAngleFull = new PlatformRollAngleFull(expected);
        double value = platformRollAngleFull.getDegrees();
        Assert.assertEquals(value, degrees);
        Assert.assertEquals(platformRollAngleFull.getBytes(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformRollAngleFull(-90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformRollAngleFull(90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformRollAngleFull(new byte[] {0x00, 0x01});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformRollAngleFull, bytes);
        Assert.assertTrue(v instanceof PlatformRollAngleFull);
        PlatformRollAngleFull platformRollAngleFull = (PlatformRollAngleFull) v;
        Assert.assertEquals(platformRollAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformRollAngleFull.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformRollAngleFull, bytes);
        Assert.assertTrue(v instanceof PlatformRollAngleFull);
        platformRollAngleFull = (PlatformRollAngleFull) v;
        Assert.assertEquals(platformRollAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformRollAngleFull.getBytes(), bytes);
    }
}
