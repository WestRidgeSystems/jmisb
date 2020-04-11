package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavsatsInViewTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        NavsatsInView count = new NavsatsInView(0);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x00});

        // Max
        count = new NavsatsInView(255);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0xff});

        // From ST:
        count = new NavsatsInView(7);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x07});

        Assert.assertEquals(count.getDisplayName(), "NAVSATs In View");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        NavsatsInView count = new NavsatsInView(new byte[]{(byte)0x00});
        Assert.assertEquals(count.getNavsatsInView(), 0);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals("0", count.getDisplayableValue());

        // Max
        count = new NavsatsInView(new byte[]{(byte)0xff});
        Assert.assertEquals(count.getNavsatsInView(), 255);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals("255", count.getDisplayableValue());

        // From ST:
        count = new NavsatsInView(new byte[]{(byte)0x07});
        Assert.assertEquals(count.getNavsatsInView(), 7);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x07});
        Assert.assertEquals("7", count.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.NumberNavsatsInView, bytes);
        Assert.assertTrue(v instanceof NavsatsInView);
        NavsatsInView count = (NavsatsInView)v;
        Assert.assertEquals(count.getNavsatsInView(), 0);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals("0", count.getDisplayableValue());

        bytes = new byte[]{(byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.NumberNavsatsInView, bytes);
        Assert.assertTrue(v instanceof NavsatsInView);
        count = (NavsatsInView)v;
        Assert.assertEquals(count.getNavsatsInView(), 255);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals("255", count.getDisplayableValue());

        bytes = new byte[]{(byte)0x07};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.NumberNavsatsInView, bytes);
        Assert.assertTrue(v instanceof NavsatsInView);
        count = (NavsatsInView)v;
        Assert.assertEquals(count.getNavsatsInView(), 7);
        Assert.assertEquals(count.getBytes(), new byte[]{(byte)0x07});
        Assert.assertEquals("7", count.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new NavsatsInView(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new NavsatsInView(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new NavsatsInView(new byte[]{0x00, 0x00});
    }
}
