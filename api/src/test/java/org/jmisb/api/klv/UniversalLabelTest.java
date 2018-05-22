package org.jmisb.api.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UniversalLabelTest
{
    @Test
    public void testEquals()
    {
        byte[] oneBytes = new byte[]{0x06, 0x0e, 0x2b, 0x34,
                0x01, 0x00, 0x00, 0x00,
                0x01, 0x00, 0x05, 0x3a,
                0x01, 0x00, 0x00, 0x00};

        byte[] twoBytes = new byte[]{0x06, 0x0e, 0x2b, 0x34,
                0x01, 0x00, 0x00, 0x00,
                0x01, 0x00, 0x05, 0x3a,
                0x01, 0x00, 0x00, 0x00};

        UniversalLabel one = new UniversalLabel(oneBytes);
        UniversalLabel two = new UniversalLabel(twoBytes);

        Assert.assertEquals(one, two); // tests equals() in both directions

        byte[] threeBytes = new byte[]{0x06, 0x0e, 0x2b, 0x34,
                0x01, 0x02, 0x00, 0x00,
                0x01, 0x00, 0x05, 0x3a,
                0x01, 0x00, 0x00, 0x00};
        UniversalLabel three = new UniversalLabel(threeBytes);

        Assert.assertNotEquals(one, three);
        Assert.assertNotEquals(two, three);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooShort()
    {
        byte[] bytes = new byte[]{0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00};
        new UniversalLabel(bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIncorrectHeader()
    {
        byte[] bytes = new byte[]{0x07, 0x0e, 0x2b, 0x34,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00};
        new UniversalLabel(bytes);
    }
}
