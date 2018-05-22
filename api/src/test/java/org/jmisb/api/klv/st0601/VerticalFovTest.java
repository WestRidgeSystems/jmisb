package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VerticalFovTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        VerticalFov fov = new VerticalFov(0.0);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(fov.getDegrees(), 0.0);

        // Max
        fov = new VerticalFov(180.0);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(fov.getDegrees(), 180.0);

        // ST example
        fov = new VerticalFov(152.6436);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0xd9, (byte)0x17});
        Assert.assertEquals(fov.getDegrees(), 152.6436);
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        VerticalFov fov = new VerticalFov(new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(fov.getDegrees(), 0.0);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // Max
        fov = new VerticalFov(new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(fov.getDegrees(), 180.0);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0xff, (byte)0xff});

        // ST example
        // Resolution is 2.7 milli degrees, so error is +/- 1.35 milli degrees
        final double delta = 1.35e-3;
        fov = new VerticalFov(new byte[]{(byte)0xd9, (byte)0x17});
        Assert.assertEquals(fov.getDegrees(), 152.6436, delta);
        Assert.assertEquals(fov.getBytes(), new byte[]{(byte)0xd9, (byte)0x17});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new VerticalFov(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new VerticalFov(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new VerticalFov(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
