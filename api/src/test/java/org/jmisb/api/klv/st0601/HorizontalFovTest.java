package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HorizontalFovTest {
    @Test
    public void testConstructFromValue() {
        // Min
        HorizontalFov fov = new HorizontalFov(0.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(fov.getDegrees(), 0.0);
        Assert.assertEquals(fov.getDisplayableValue(), "0.0000\u00B0");

        // Max
        fov = new HorizontalFov(180.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(fov.getDegrees(), 180.0);
        Assert.assertEquals(fov.getDisplayableValue(), "180.0000\u00B0");

        // ST example
        fov = new HorizontalFov(144.5713);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xcd, (byte) 0x9c});
        Assert.assertEquals(fov.getDegrees(), 144.5713);
        Assert.assertEquals(fov.getDisplayableValue(), "144.5713\u00B0");

        Assert.assertEquals(fov.getDisplayName(), "Sensor Horizontal Field of View");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        HorizontalFov fov = new HorizontalFov(new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(fov.getDegrees(), 0.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});

        // Max
        fov = new HorizontalFov(new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(fov.getDegrees(), 180.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});

        // ST example
        // Resolution is 2.7 milli degrees, so error is +/- 1.35 milli degrees
        final double delta = 1.35e-3;
        fov = new HorizontalFov(new byte[] {(byte) 0xcd, (byte) 0x9c});
        Assert.assertEquals(fov.getDegrees(), 144.5713, delta);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xcd, (byte) 0x9c});
        Assert.assertEquals(fov.getDisplayName(), "Sensor Horizontal Field of View");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {0x00, 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorHorizontalFov, bytes);
        Assert.assertEquals(v.getDisplayName(), "Sensor Horizontal Field of View");
        Assert.assertTrue(v instanceof HorizontalFov);
        HorizontalFov fov = (HorizontalFov) v;
        Assert.assertEquals(fov.getDegrees(), 0.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorHorizontalFov, bytes);
        Assert.assertEquals(v.getDisplayName(), "Sensor Horizontal Field of View");
        Assert.assertTrue(v instanceof HorizontalFov);
        fov = (HorizontalFov) v;
        Assert.assertEquals(fov.getDegrees(), 180.0);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});

        bytes = new byte[] {(byte) 0xcd, (byte) 0x9c};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorHorizontalFov, bytes);
        Assert.assertEquals(v.getDisplayName(), "Sensor Horizontal Field of View");
        Assert.assertTrue(v instanceof HorizontalFov);
        fov = (HorizontalFov) v;
        Assert.assertEquals(fov.getDegrees(), 144.5713, 0.0001);
        Assert.assertEquals(fov.getBytes(), new byte[] {(byte) 0xcd, (byte) 0x9c});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new HorizontalFov(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new HorizontalFov(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new HorizontalFov(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
