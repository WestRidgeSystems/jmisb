package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorTrueAltitudeTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        SensorTrueAltitude altitude = new SensorTrueAltitude(-900.0);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(altitude.getMeters(), -900.0);

        // Max
        altitude = new SensorTrueAltitude(19000.0);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(altitude.getMeters(), 19000.0);

        // Zero
        altitude = new SensorTrueAltitude(0.0);
        Assert.assertEquals(altitude.getMeters(), 0.0);

        // ST example
        altitude = new SensorTrueAltitude(14190.72);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xc2, (byte)0x21});
        Assert.assertEquals(altitude.getMeters(), 14190.72);
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        byte[] min = new byte[]{0x00, 0x00};
        SensorTrueAltitude altitude = new SensorTrueAltitude(min);
        Assert.assertEquals(altitude.getMeters(), -900.0);

        // Max
        byte[] max = new byte[]{(byte)0xff, (byte)0xff};
        altitude = new SensorTrueAltitude(max);
        Assert.assertEquals(altitude.getMeters(), 19000.0);

        // ST example
        // Resolution is 0.3m, so max error should be +/-0.15
        double delta = 0.15;
        byte[] ex = new byte[]{(byte)0xc2, (byte)0x21};
        altitude = new SensorTrueAltitude(ex);
        Assert.assertEquals(altitude.getMeters(), 14190.72, delta);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SensorTrueAltitude(-900.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SensorTrueAltitude(19000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new SensorTrueAltitude(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}

