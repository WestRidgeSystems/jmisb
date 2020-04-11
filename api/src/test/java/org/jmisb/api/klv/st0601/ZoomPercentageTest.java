package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZoomPercentageTest
{
    @Test
    public void testConstructFromValue()
    {
        // From ST:
        ZoomPercentage percent = new ZoomPercentage(55.0);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x37, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "55.0%");
        Assert.assertEquals(percent.getDisplayName(), "Zoom Percentage");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // From ST:
        ZoomPercentage percent = new ZoomPercentage(new byte[]{(byte)0x37, (byte)0x00});
        Assert.assertEquals(percent.getPercentage(), 55.0, 0.01);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x37, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "55.0%");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x37, (byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.ZoomPercentage, bytes);
        Assert.assertTrue(v instanceof ZoomPercentage);
        ZoomPercentage percent = (ZoomPercentage)v;
        Assert.assertEquals(percent.getPercentage(), 55.0, 0.01);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x37, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "55.0%");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new ZoomPercentage(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new ZoomPercentage(100.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong()
    {
        new ZoomPercentage(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
