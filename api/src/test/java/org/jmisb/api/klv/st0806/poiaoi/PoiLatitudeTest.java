package org.jmisb.api.klv.st0806.poiaoi;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PoiLatitudeTest {
    // Resolution is 42 nano degrees, so error is +/-21 nano degrees
    private final double delta = 21e-9;

    @Test
    public void testFromValue() {
        PoiLatitude latitude = new PoiLatitude(-86.041207348947);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x85, (byte) 0xa1, (byte) 0x5a, (byte) 0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-86.0412\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "POI Latitude");
    }

    @Test
    public void testOutOfRange() {
        PoiLatitude latitude = new PoiLatitude(Double.POSITIVE_INFINITY);
        Assert.assertEquals(
                latitude.getBytes(),
                new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(latitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(latitude.getDisplayName(), "POI Latitude");
    }

    @Test
    public void testOutOfRangeFromBytes() {
        PoiLatitude latitude =
                new PoiLatitude(new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(latitude.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(latitude.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(latitude.getDisplayName(), "POI Latitude");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PoiLatitude(-90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PoiLatitude(90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testShortArray() {
        new PoiLatitude(new byte[] {0x01, 0x02, 0x03});
    }
}
