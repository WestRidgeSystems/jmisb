package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorLatitudeTest {
    @Test
    public void testConstructFromValue() {
        // Min
        SensorLatitude latitude = new SensorLatitude(-90);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(latitude.getDegrees(), -90.0);

        // Max
        latitude = new SensorLatitude(90);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(latitude.getDegrees(), 90.0);

        // Zero
        latitude = new SensorLatitude(0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDegrees(), 0.0);

        // ST example
        latitude = new SensorLatitude(60.1768229669783);
        Assert.assertEquals(latitude.getDegrees(), 60.1768229669783);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x55, (byte) 0x95, (byte) 0xb6, (byte) 0x6d});

        // Error
        latitude = new SensorLatitude(Double.POSITIVE_INFINITY);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDegrees(), Double.POSITIVE_INFINITY);

        Assert.assertEquals(latitude.getDisplayName(), "Sensor Latitude");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        SensorLatitude latitude =
                new SensorLatitude(new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        Assert.assertEquals(latitude.getDegrees(), -90.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});

        // Max
        latitude =
                new SensorLatitude(new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(latitude.getDegrees(), 90.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        // Zero
        latitude =
                new SensorLatitude(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDegrees(), 0.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        // ST example
        // Resolution is 42 nano degrees, so max error should be +/-21 nano degrees
        final double delta = 21e-9;
        latitude =
                new SensorLatitude(new byte[] {(byte) 0x55, (byte) 0x95, (byte) 0xb6, (byte) 0x6d});
        Assert.assertEquals(latitude.getDegrees(), 60.1768229669783, delta);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x55, (byte) 0x95, (byte) 0xb6, (byte) 0x6d});

        // Error
        latitude =
                new SensorLatitude(new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLatitude, bytes);
        Assert.assertTrue(v instanceof SensorLatitude);
        SensorLatitude latitude = (SensorLatitude) v;
        Assert.assertEquals(latitude.getDegrees(), -90.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01});

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLatitude, bytes);
        Assert.assertTrue(v instanceof SensorLatitude);
        latitude = (SensorLatitude) v;
        Assert.assertEquals(latitude.getDegrees(), 90.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLatitude, bytes);
        Assert.assertTrue(v instanceof SensorLatitude);
        latitude = (SensorLatitude) v;
        Assert.assertEquals(latitude.getDegrees(), 0.0);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        bytes = new byte[] {(byte) 0x55, (byte) 0x95, (byte) 0xb6, (byte) 0x6d};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLatitude, bytes);
        Assert.assertTrue(v instanceof SensorLatitude);
        latitude = (SensorLatitude) v;
        Assert.assertEquals(latitude.getDegrees(), 60.17682, 0.00001);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x55, (byte) 0x95, (byte) 0xb6, (byte) 0x6d});

        bytes = new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLatitude, bytes);
        Assert.assertTrue(v instanceof SensorLatitude);
        latitude = (SensorLatitude) v;
        Assert.assertEquals(latitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorLatitude(-90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorLatitude(90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorLatitude(new byte[] {0x00, 0x00});
    }
}
