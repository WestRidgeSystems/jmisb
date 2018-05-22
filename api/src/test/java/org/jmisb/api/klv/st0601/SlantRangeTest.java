package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SlantRangeTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        SlantRange range = new SlantRange(0.0);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});

        // Max
        range = new SlantRange(5000000.0);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});

        // From ST:
        // TODO: the ST example appears to have an error, shows encoded value = 0x03 83 09 26 (last byte incorrect)
        range = new SlantRange(68590.98);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0x03, (byte)0x83, (byte)0x09, (byte)0x23});
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        SlantRange range = new SlantRange(new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(range.getMeters(), 0.0);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});

        // Max
        range = new SlantRange(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(range.getMeters(), 5000000.0);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});

        // From ST:
        // TODO: the ST example appears to have an error, shows encoded value = 0x03 83 09 26 (last byte incorrect)
        range = new SlantRange(new byte[]{(byte)0x03, (byte)0x83, (byte)0x09, (byte)0x23});
        Assert.assertEquals(range.getMeters(), 68590.98, SlantRange.DELTA);
        Assert.assertEquals(range.getBytes(), new byte[]{(byte)0x03, (byte)0x83, (byte)0x09, (byte)0x23});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SlantRange(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SlantRange(5000000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new SlantRange(new byte[]{0x00, 0x00});
    }
}
