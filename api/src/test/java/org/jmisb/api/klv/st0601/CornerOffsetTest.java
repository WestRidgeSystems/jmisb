package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CornerOffsetTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        CornerOffset offset = new CornerOffset(-0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x01});
        Assert.assertEquals(offset.getDegrees(), -0.075);

        // Max
        offset = new CornerOffset(0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(offset.getDegrees(), 0.075);

        // Zero
        offset = new CornerOffset(0);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), 0.0);

        // ST example from tag 26
        // frame center lat = -10.5423886331461 (from tag 23)
        // point 1 lat = -10.579637999887
        // offset = −0.037249367
        offset = new CornerOffset(-0.037249367);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xc0, (byte)0x6e});
        Assert.assertEquals(offset.getDegrees(), -0.037249367);

        // Error condition
        offset = new CornerOffset(Double.POSITIVE_INFINITY);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), Double.POSITIVE_INFINITY);
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        CornerOffset offset = new CornerOffset(new byte[]{(byte)0x80, (byte)0x01});
        Assert.assertEquals(offset.getDegrees(), -0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x01});

        // Max
        offset = new CornerOffset(new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(offset.getDegrees(), 0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x7f, (byte)0xff});

        // Zero
        offset = new CornerOffset(new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), 0.0);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // ST example
        // Resolution is 1.2 micro degrees, so error is +/- 0.6 micro degrees
        final double delta = 0.6e-6;
        offset = new CornerOffset(new byte[]{(byte)0xc0, (byte)0x6e});
        Assert.assertEquals(offset.getDegrees(), -0.037249367, delta);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xc0, (byte)0x6e});

        // Error condition
        offset = new CornerOffset(new byte[]{(byte)0x80, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToSmall()
    {
        new CornerOffset(-0.0751);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToBig()
    {
        new CornerOffset(0.0751);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new CornerOffset(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
