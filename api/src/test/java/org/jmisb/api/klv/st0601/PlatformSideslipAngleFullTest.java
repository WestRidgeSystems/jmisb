package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformSideslipAngleFullTest
{
    @Test
    public void testMinMax()
    {
        PlatformSideslipAngleFull platformSideslipAngleFull = new PlatformSideslipAngleFull(-90.0);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), -90.0);

        platformSideslipAngleFull = new PlatformSideslipAngleFull(90.0);
        bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), 90.0);

        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x01};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), -90.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);

        bytes = new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), 90.0);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);
    }

    @Test
    public void testOutOfBounds()
    {
        PlatformSideslipAngleFull platformSideslipAngleFull = new PlatformSideslipAngleFull(Double.POSITIVE_INFINITY);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), Double.POSITIVE_INFINITY);

        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
        platformSideslipAngleFull = new PlatformSideslipAngleFull(bytes);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), bytes);
    }
    
    @Test
    public void stExample()
    {
        // TODO: the ST lacks a conversion example; for now, use the example from tag 90.
        double degrees = -0.43152510208614414;
        byte[] expected = new byte[]{(byte)0xff, (byte)0x62, (byte)0xe2, (byte)0xf2};

        // Create from value
        PlatformSideslipAngleFull platformSideslipAngleFull = new PlatformSideslipAngleFull(degrees);
        byte[] bytes = platformSideslipAngleFull.getBytes();
        Assert.assertEquals(bytes, expected);
        Assert.assertEquals(platformSideslipAngleFull.getDegrees(), degrees);

        // Create from bytes
        platformSideslipAngleFull = new PlatformSideslipAngleFull(expected);
        double value = platformSideslipAngleFull.getDegrees();
        Assert.assertEquals(value, degrees);
        Assert.assertEquals(platformSideslipAngleFull.getBytes(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PlatformSideslipAngleFull(-90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PlatformSideslipAngleFull(90.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PlatformSideslipAngleFull(new byte[]{0x00, 0x01});
    }
}
