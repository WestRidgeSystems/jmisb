package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetWidthTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        TargetWidth width = new TargetWidth(0.0);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // Max
        width = new TargetWidth(10000.0);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0xff, (byte)0xff});

        // From ST:
        width = new TargetWidth(722.8199);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0x12, (byte)0x81});
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        TargetWidth width = new TargetWidth(new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(width.getMeters(), 0.0);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // Max
        width = new TargetWidth(new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(width.getMeters(), 10000.0);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0xff, (byte)0xff});

        // From ST:
        width = new TargetWidth(new byte[]{(byte)0x12, (byte)0x81});
        Assert.assertEquals(width.getMeters(), 722.8199, TargetWidth.DELTA);
        Assert.assertEquals(width.getBytes(), new byte[]{(byte)0x12, (byte)0x81});

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TargetWidth(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new TargetWidth(10000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TargetWidth(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
