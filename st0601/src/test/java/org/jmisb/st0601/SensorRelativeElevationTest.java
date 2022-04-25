package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorRelativeElevationTest {
    @Test
    public void testConstructFromValue() {
        // 0.0
        SensorRelativeElevation elevation = new SensorRelativeElevation(0.0);
        byte[] zero = elevation.getBytes();
        Assert.assertEquals(zero, new byte[] {0x00, 0x00, 0x00, 0x00});
        Assert.assertEquals(elevation.getDegrees(), 0.0);
        Assert.assertEquals("0.0000\u00B0", elevation.getDisplayableValue());

        // -180.0
        elevation = new SensorRelativeElevation(-180);
        byte[] min = elevation.getBytes();
        Assert.assertEquals(min, new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(elevation.getDegrees(), -180.0);
        Assert.assertEquals("-180.0000\u00B0", elevation.getDisplayableValue());

        // +180.0
        elevation = new SensorRelativeElevation(180);
        byte[] max = elevation.getBytes();
        Assert.assertEquals(max, new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(elevation.getDegrees(), 180.0);
        Assert.assertEquals("180.0000\u00B0", elevation.getDisplayableValue());

        // ST example
        final double val = -168.792324833941;
        elevation = new SensorRelativeElevation(val);
        byte[] ex = elevation.getBytes();
        Assert.assertEquals(ex, new byte[] {(byte) 0x87, (byte) 0xf8, (byte) 0x4b, (byte) 0x86});
        Assert.assertEquals(elevation.getDegrees(), val);
        Assert.assertEquals("-168.7923\u00B0", elevation.getDisplayableValue());

        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        elevation = new SensorRelativeElevation(Double.POSITIVE_INFINITY);
        Assert.assertEquals(elevation.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(elevation.getBytes(), error);

        Assert.assertEquals(elevation.getDisplayName(), "Sensor Relative Elevation");
    }

    @Test
    public void testConstructFromEncoded() {
        byte[] zero = new byte[] {0x00, 0x00, 0x00, 0x00};
        SensorRelativeElevation elevation = new SensorRelativeElevation(zero);
        Assert.assertEquals(elevation.getDegrees(), 0.0);
        Assert.assertEquals(elevation.getBytes(), zero);

        // -180.0
        byte[] min = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        elevation = new SensorRelativeElevation(min);
        Assert.assertEquals(elevation.getDegrees(), -180.0);
        Assert.assertEquals(elevation.getBytes(), min);

        // +180.0
        byte[] max = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        elevation = new SensorRelativeElevation(max);
        Assert.assertEquals(elevation.getDegrees(), 180.0);
        Assert.assertEquals(elevation.getBytes(), max);

        // ST example
        // Resolution is 84 nano degrees, so max error should be +/- 42 nano degrees
        double delta = 42e-9;
        byte[] example = new byte[] {(byte) 0x87, (byte) 0xf8, (byte) 0x4b, (byte) 0x86};
        elevation = new SensorRelativeElevation(example);
        Assert.assertEquals(elevation.getDegrees(), -168.792324833941, delta);
        Assert.assertEquals(elevation.getBytes(), example);

        // Error
        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        elevation = new SensorRelativeElevation(error);
        Assert.assertEquals(elevation.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(elevation.getBytes(), error);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {0x00, 0x00, 0x00, 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeElevationAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeElevation);
        SensorRelativeElevation elevation = (SensorRelativeElevation) v;
        Assert.assertEquals(elevation.getDegrees(), 0.0);
        Assert.assertEquals(elevation.getBytes(), bytes);

        // -180.0
        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeElevationAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeElevation);
        elevation = (SensorRelativeElevation) v;
        Assert.assertEquals(elevation.getDegrees(), -180.0);
        Assert.assertEquals(elevation.getBytes(), bytes);

        // +180.0
        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeElevationAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeElevation);
        elevation = (SensorRelativeElevation) v;
        Assert.assertEquals(elevation.getDegrees(), 180.0);
        Assert.assertEquals(elevation.getBytes(), bytes);

        // ST example
        bytes = new byte[] {(byte) 0x87, (byte) 0xf8, (byte) 0x4b, (byte) 0x86};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeElevationAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeElevation);
        elevation = (SensorRelativeElevation) v;
        Assert.assertEquals(elevation.getDegrees(), -168.792324833941, 0.0000001);
        Assert.assertEquals(elevation.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorRelativeElevation(-180.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorRelativeElevation(180.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorRelativeElevation(new byte[] {0x00, 0x01});
    }
}
