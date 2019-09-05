package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OnBoardMiStorageCapacityTest
{
    @Test
    public void testMinMax()
    {
        OnBoardMiStorageCapacity capacity = new OnBoardMiStorageCapacity(0);
        byte[] bytes = capacity.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x00});
        Assert.assertEquals(capacity.getGigabytes(), 0);

        capacity = new OnBoardMiStorageCapacity(4294967295L);
        bytes = capacity.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(capacity.getGigabytes(), 4294967295L);

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getGigabytes(), 0L);
        Assert.assertEquals(capacity.getBytes(), new byte[]{(byte)0x00});

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        capacity = new OnBoardMiStorageCapacity(bytes);
        Assert.assertEquals(capacity.getGigabytes(), 4294967295L);
        Assert.assertEquals(capacity.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMin()
    {
        new OnBoardMiStorageCapacity(-1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMax()
    {
        new OnBoardMiStorageCapacity(4294967296L);
    }

    @Test
    public void stExample()
    {
        long value = 10_000L;
        byte[] origBytes = new byte[]{(byte)0x27, (byte)0x10};

        OnBoardMiStorageCapacity capacity = new OnBoardMiStorageCapacity(origBytes);
        Assert.assertEquals(capacity.getGigabytes(), value);
        Assert.assertEquals(capacity.getBytes(), origBytes);

        capacity = new OnBoardMiStorageCapacity(value);
        Assert.assertEquals(capacity.getGigabytes(), value);
        Assert.assertEquals(capacity.getBytes(), origBytes);
    }
}
