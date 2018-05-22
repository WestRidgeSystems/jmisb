package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformRollAngleTest
{
    @Test
    public void testMinMax()
    {
        PlatformRollAngle platformRollAngle = new PlatformRollAngle(-50.0);
        byte[] bytes = platformRollAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x80, (byte)0x01}); // -32767 as int16
        Assert.assertEquals(platformRollAngle.getDegrees(), -50.0);

        platformRollAngle = new PlatformRollAngle(50.0);
        bytes = platformRollAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x7f, (byte)0xff}); // 32767 as int16
        Assert.assertEquals(platformRollAngle.getDegrees(), 50.0);

        bytes = new byte[]{(byte)0x80, (byte)0x01}; // -32767 as int16
        platformRollAngle = new PlatformRollAngle(bytes);
        Assert.assertEquals(platformRollAngle.getDegrees(), -50.0);
        Assert.assertEquals(platformRollAngle.getBytes(), bytes);

        bytes = new byte[]{(byte)0x7f, (byte)0xff}; // 32767 as int16
        platformRollAngle = new PlatformRollAngle(bytes);
        Assert.assertEquals(platformRollAngle.getDegrees(), 50.0);
        Assert.assertEquals(platformRollAngle.getBytes(), bytes);
    }

    @Test
    public void testZero()
    {
        PlatformRollAngle platformRollAngle = new PlatformRollAngle(0.0);
        byte[] bytes = platformRollAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(platformRollAngle.getDegrees(), 0.0);
    }

    @Test
    public void testExample()
    {
        // Resolution is 1525 micro degrees, i.e., .001525 degrees. So max error should be +/- .0007625 degrees
        double delta = 0.0007625;

        // 3.405814 -> 0x08 0xb8
        double degrees = 3.405814;
        PlatformRollAngle platformRollAngle = new PlatformRollAngle(degrees);
        byte[] bytes = platformRollAngle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x08, (byte)0xb8});
        Assert.assertEquals(platformRollAngle.getDegrees(), degrees);

        // 0x08 0xb8 -> 3.405814
        bytes = new byte[]{(byte)0x08, (byte)0xb8};
        platformRollAngle = new PlatformRollAngle(bytes);
        Assert.assertEquals(platformRollAngle.getDegrees(), degrees, delta);
        Assert.assertEquals(platformRollAngle.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange()
    {
        PlatformRollAngle platformRollAngle = new PlatformRollAngle(Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformRollAngle.getDegrees(), Double.POSITIVE_INFINITY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PlatformRollAngle(-50.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PlatformRollAngle(50.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PlatformRollAngle(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
