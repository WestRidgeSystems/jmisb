package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorRelativeAzimuthTest
{
    @Test
    public void testConstructFromValue()
    {
        SensorRelativeAzimuth az = new SensorRelativeAzimuth(0.0);
        byte[] min = az.getBytes();
        Assert.assertEquals(min, new byte[]{0x00, 0x00, 0x00, 0x00});
        Assert.assertEquals(az.getDegrees(), 0.0);
        Assert.assertEquals("0.0000\u00B0", az.getDisplayableValue());

        az = new SensorRelativeAzimuth(360.0);
        byte[] max = az.getBytes();
        Assert.assertEquals(max, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(az.getDegrees(), 360.0);
        Assert.assertEquals("360.0000\u00B0", az.getDisplayableValue());

        // Example from standard
        final double val = 160.719211474396;
        az = new SensorRelativeAzimuth(val);
        byte[] ex = az.getBytes();
        Assert.assertEquals(ex, new byte[]{(byte)0x72, (byte)0x4a, (byte)0x0a, (byte)0x20});
        Assert.assertEquals(az.getDegrees(), val);
        Assert.assertEquals("160.7192\u00B0", az.getDisplayableValue());

        Assert.assertEquals(az.getDisplayName(), "Sensor Relative Azimuth");
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] min = new byte[]{0x00, 0x00, 0x00, 0x00};
        SensorRelativeAzimuth az = new SensorRelativeAzimuth(min);
        Assert.assertEquals(az.getDegrees(), 0.0);
        Assert.assertEquals(az.getBytes(), min);

        byte[] max = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        az = new SensorRelativeAzimuth(max);
        Assert.assertEquals(az.getDegrees(), 360.0);
        Assert.assertEquals(az.getBytes(), max);

        // Some random byte arrays
        byte[] bytes = new byte[]{(byte)0x34, (byte)0xa8, (byte)0x0d, (byte)0x59};
        az = new SensorRelativeAzimuth(bytes);
        Assert.assertEquals(az.getBytes(), bytes);

        bytes = new byte[]{(byte)0xd7, (byte)0x03, (byte)0x9d, (byte)0xc8};
        az = new SensorRelativeAzimuth(bytes);
        Assert.assertEquals(az.getBytes(), bytes);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x00, 0x00, 0x00, 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeAzimuthAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeAzimuth);
        SensorRelativeAzimuth az = (SensorRelativeAzimuth)v;
        Assert.assertEquals(az.getDegrees(), 0.0);

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeAzimuthAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeAzimuth);
        az = (SensorRelativeAzimuth)v;
        Assert.assertEquals(az.getDegrees(), 360.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SensorRelativeAzimuth(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SensorRelativeAzimuth(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new SensorRelativeAzimuth(new byte[]{0x00, 0x00});
    }
}
