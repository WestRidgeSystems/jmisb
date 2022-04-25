package org.jmisb.st0102;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ItemDesignatorIdTest {

    @Test
    public void testConstructFromEncoded() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09,
                    (byte) 0x0A,
                    (byte) 0x0B,
                    (byte) 0x0C,
                    (byte) 0x0D,
                    (byte) 0x0E,
                    (byte) 0x0F
                };
        ItemDesignatorId uut = new ItemDesignatorId(bytes);
        Assert.assertNotNull(uut);
        Assert.assertEquals(uut.getBytes(), bytes);
        Assert.assertEquals(uut.getItemDesignatorId(), bytes);
        Assert.assertEquals(
                uut.getDisplayableValue(),
                "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]");
        Assert.assertEquals(uut.getDisplayName(), "Item Designator Id");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueTooShort() {
        byte[] bytes15 =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09,
                    (byte) 0x0A,
                    (byte) 0x0B,
                    (byte) 0x0C,
                    (byte) 0x0D,
                    (byte) 0x0E
                };
        new ItemDesignatorId(bytes15);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueTooLong() {
        byte[] bytes17 =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09,
                    (byte) 0x0A,
                    (byte) 0x0B,
                    (byte) 0x0C,
                    (byte) 0x0D,
                    (byte) 0x0E,
                    (byte) 0x0F,
                    (byte) 0x10
                };
        new ItemDesignatorId(bytes17);
    }
}
