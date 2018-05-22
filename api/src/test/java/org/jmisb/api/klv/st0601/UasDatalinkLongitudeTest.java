package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkLongitudeTest
{
    @Test
    public void testSubClasses()
    {
        // Resolution is 84 nano degrees, so error is +/-42 nano degrees
        final double delta = 42e-9;

        // Alternate Platform Longitude
        UasDatalinkLongitude longitude = new AlternatePlatformLongitude(0.155527554524842);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});
        Assert.assertEquals(longitude.getDegrees(), 0.155527554524842);

        longitude = new AlternatePlatformLongitude(new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});
        Assert.assertEquals(longitude.getDegrees(), 0.155527554524842, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});

        // Frame Center Longitude
        longitude = new FrameCenterLongitude(29.157890122923);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923);

        longitude = new FrameCenterLongitude(new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});

        // Target Location Longitude
        longitude = new TargetLocationLongitude(166.400812960416);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});
        Assert.assertEquals(longitude.getDegrees(), 166.400812960416);

        longitude = new TargetLocationLongitude(new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});
        Assert.assertEquals(longitude.getDegrees(), 166.400812960416, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});

        // Corner Longitude Point 1
        longitude = new FullCornerLongitude(29.1273677986333);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9});
        Assert.assertEquals(longitude.getDegrees(), 29.1273677986333);

        longitude = new FullCornerLongitude(new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9});
        Assert.assertEquals(longitude.getDegrees(), 29.1273677986333, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9});
    }
}
