package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkLatitudeTest
{
    @Test
    public void testSubClasses()
    {
        // Resolution is 42 nano degrees, so error is +/-21 nano degrees
        final double delta = 21e-9;

        // Alternative Platform Latitude
        UasDatalinkLatitude latitude = new AlternatePlatformLatitude(-86.041207348947);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947);

        latitude = new AlternatePlatformLatitude(new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});

        // Frame Center Latitude
        latitude = new FrameCenterLatitude(-10.5423886331461);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461);

        latitude = new FrameCenterLatitude(new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});

        // Target Location Latitude
        latitude = new TargetLocationLatitude(-79.1638500518929);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929);

        latitude = new TargetLocationLatitude(new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});

        // Corner Latitude Point 1
        latitude = new FullCornerLatitude(-10.579637999887);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887);

        latitude = new FullCornerLatitude(new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
    }
}
