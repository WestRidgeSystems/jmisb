package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnBoardMiStoragePercentFullTest
{
    @Test
    public void testConstructFromValue()
    {
        // From ST:
        OnBoardMiStoragePercentFull percent = new OnBoardMiStoragePercentFull(72.0);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x48, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "72.0%");
        Assert.assertEquals(percent.getDisplayName(), "On-board MI Storage Percent Full");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // From ST:
        OnBoardMiStoragePercentFull percent = new OnBoardMiStoragePercentFull(new byte[]{(byte)0x48, (byte)0x00});
        Assert.assertEquals(percent.getPercentage(), 72.0, 0.01);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x48, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "72.0%");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OnBoardMiStoragePercentFull, bytes);
        Assert.assertTrue(v instanceof OnBoardMiStoragePercentFull);
        OnBoardMiStoragePercentFull percent = (OnBoardMiStoragePercentFull)v;
        Assert.assertEquals(percent.getPercentage(), 72.0, 0.01);
        Assert.assertEquals(percent.getBytes(), new byte[]{(byte)0x48, (byte)0x00});
        Assert.assertEquals(percent.getDisplayableValue(), "72.0%");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new OnBoardMiStoragePercentFull(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new OnBoardMiStoragePercentFull(100.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong()
    {
        new OnBoardMiStoragePercentFull(new byte[]{0x01, 0x02, 0x03, 0x04});
    }
}
