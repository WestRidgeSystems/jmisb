package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorLongitudeTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        SensorLongitude longitude = new SensorLongitude(-180);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01});
        Assert.assertEquals(longitude.getDegrees(), -180.0);

        // Max
        longitude = new SensorLongitude(180);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longitude.getDegrees(), 180.0);

        // Zero
        longitude = new SensorLongitude(0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDegrees(), 0.0);

        // ST example
        longitude = new SensorLongitude(128.426759042045);
        Assert.assertEquals(longitude.getDegrees(), 128.426759042045);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x5b, (byte)0x53, (byte)0x60, (byte)0xc4});

        // Error
        longitude = new SensorLongitude(Double.POSITIVE_INFINITY);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDegrees(), Double.POSITIVE_INFINITY);

        Assert.assertEquals(longitude.getDisplayName(), "Sensor Longitude");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        SensorLongitude longitude = new SensorLongitude(new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01});
        Assert.assertEquals(longitude.getDegrees(), -180.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01});

        // Max
        longitude = new SensorLongitude(new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(longitude.getDegrees(), 180.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff});

        // Zero
        longitude = new SensorLongitude(new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDegrees(), 0.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});

        // ST example
        // Resolution is 84 nano degrees, so max error should be +/-42 nano degrees
        final double delta = 42e-9;
        longitude = new SensorLongitude(new byte[]{(byte)0x5b, (byte)0x53, (byte)0x60, (byte)0xc4});
        Assert.assertEquals(longitude.getDegrees(), 128.426759042045, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x5b, (byte)0x53, (byte)0x60, (byte)0xc4});

        // Error
        longitude = new SensorLongitude(new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDegrees(), Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLongitude, bytes);
        Assert.assertTrue(v instanceof SensorLongitude);
        SensorLongitude longitude = (SensorLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), -180.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01});

        bytes = new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLongitude, bytes);
        Assert.assertTrue(v instanceof SensorLongitude);
        longitude = (SensorLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 180.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff});

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLongitude, bytes);
        Assert.assertTrue(v instanceof SensorLongitude);
        longitude = (SensorLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 0.0);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});

        bytes = new byte[]{(byte)0x5b, (byte)0x53, (byte)0x60, (byte)0xc4};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLongitude, bytes);
        Assert.assertTrue(v instanceof SensorLongitude);
        longitude = (SensorLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 128.426759042045, 0.00001);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x5b, (byte)0x53, (byte)0x60, (byte)0xc4});

        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorLongitude, bytes);
        Assert.assertTrue(v instanceof SensorLongitude);
        longitude = (SensorLongitude)v;
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDegrees(), Double.POSITIVE_INFINITY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SensorLongitude(-180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SensorLongitude(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new SensorLongitude(new byte[]{0x00, 0x00});
    }
}
