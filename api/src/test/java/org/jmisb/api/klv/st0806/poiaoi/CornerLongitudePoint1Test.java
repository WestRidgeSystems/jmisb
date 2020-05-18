package org.jmisb.api.klv.st0806.poiaoi;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CornerLongitudePoint1Test
{
    // Resolution is 42 nano degrees, so error is +/-21 nano degrees
    private final double delta = 21e-9;

    @Test
    public void testFromValue()
    {
        CornerLongitudePoint1 longitude = new CornerLongitudePoint1(29.157890122923);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1579\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Corner Longitude Point 1");
    }

    @Test
    public void testOutOfRange()
    {
        CornerLongitudePoint1 longitude = new CornerLongitudePoint1(Double.POSITIVE_INFINITY);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(longitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(longitude.getDisplayName(), "Corner Longitude Point 1");
    }

    @Test
    public void testOutOfRangeFromBytes()
    {
        CornerLongitudePoint1 longitude = new CornerLongitudePoint1(new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(longitude.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(longitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(longitude.getDisplayName(), "Corner Longitude Point 1");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new CornerLongitudePoint1(-180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new CornerLongitudePoint1(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testShortArray()
    {
        new CornerLongitudePoint1(new byte[]{0x01, 0x02, 0x03});
    }
}
