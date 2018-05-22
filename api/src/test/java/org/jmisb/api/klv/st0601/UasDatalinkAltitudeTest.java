package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkAltitudeTest
{
    @Test
    public void testSubClasses()
    {
        // Resolution is 0.3m, so error is +/-0.15m
        final double delta = 0.15;

        // Frame Center Elevation
        UasDatalinkAltitude altitude = new FrameCenterElevation(3216.037);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getMeters(), 3216.037);

        altitude = new FrameCenterElevation(new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getMeters(), 3216.037, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});

        // Density Altitude
        altitude = new DensityAltitude(14818.68);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68);

        altitude = new DensityAltitude(new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});

        // Alternate Platform Altitude
        altitude = new AlternatePlatformAltitude(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);

        altitude = new AlternatePlatformAltitude(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});

        // Frame Center Height Above Ellipsoid
        altitude = new FrameCenterHae(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);

        altitude = new FrameCenterHae(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
    }
}
