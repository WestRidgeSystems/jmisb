package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformHeadingAngleTest
{
    @Test
    public void testConstructFromValue()
    {
        PlatformHeadingAngle platformHeadingAngle = new PlatformHeadingAngle(0.0);
        byte[] bytes = platformHeadingAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00});
        Assert.assertEquals(platformHeadingAngle.getDegrees(), 0.0);

        platformHeadingAngle = new PlatformHeadingAngle(360.0);
        bytes = platformHeadingAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(platformHeadingAngle.getDegrees(), 360.0);

        // Example from standard
        platformHeadingAngle = new PlatformHeadingAngle(159.9744);
        bytes = platformHeadingAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x71, (byte)0xc2});
        Assert.assertEquals(platformHeadingAngle.getDegrees(), 159.9744);
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        PlatformHeadingAngle platformHeadingAngle = new PlatformHeadingAngle(bytes);
        Assert.assertEquals(platformHeadingAngle.getDegrees(), 0.0);
        Assert.assertEquals(platformHeadingAngle.getBytes(), bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        platformHeadingAngle = new PlatformHeadingAngle(bytes);
        Assert.assertEquals(platformHeadingAngle.getDegrees(), 360.0);
        Assert.assertEquals(platformHeadingAngle.getBytes(), bytes);

        // Some random byte arrays
        bytes = new byte[]{(byte)0xa8, (byte)0x73};
        platformHeadingAngle = new PlatformHeadingAngle(bytes);
        Assert.assertEquals(platformHeadingAngle.getBytes(), bytes);

        bytes = new byte[]{(byte)0x34, (byte)0x9d};
        platformHeadingAngle = new PlatformHeadingAngle(bytes);
        Assert.assertEquals(platformHeadingAngle.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PlatformHeadingAngle(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PlatformHeadingAngle(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PlatformHeadingAngle(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
