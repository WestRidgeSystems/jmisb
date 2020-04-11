package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformGroundSpeedTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        PlatformGroundSpeed speed = new PlatformGroundSpeed(0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});

        // Max
        speed = new PlatformGroundSpeed(255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});

        // From ST:
        speed = new PlatformGroundSpeed(140);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x8c});

        Assert.assertEquals(speed.getDisplayName(), "Platform Ground Speed");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        PlatformGroundSpeed speed = new PlatformGroundSpeed(new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        // Max
        speed = new PlatformGroundSpeed(new byte[]{(byte)0xff});
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "255m/s");

        // From ST:
        speed = new PlatformGroundSpeed(new byte[]{(byte)0x8c});
        Assert.assertEquals(speed.getMetersPerSecond(), 140);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x8c});
        Assert.assertEquals(speed.getDisplayableValue(), "140m/s");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformGroundSpeed, bytes);
        Assert.assertTrue(v instanceof PlatformGroundSpeed);
        PlatformGroundSpeed speed = (PlatformGroundSpeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        bytes = new byte[]{(byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformGroundSpeed, bytes);
        Assert.assertTrue(v instanceof PlatformGroundSpeed);
        speed = (PlatformGroundSpeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "255m/s");

        bytes = new byte[]{(byte)0x8c};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformGroundSpeed, bytes);
        Assert.assertTrue(v instanceof PlatformGroundSpeed);
        speed = (PlatformGroundSpeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 140);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x8c});
        Assert.assertEquals(speed.getDisplayableValue(), "140m/s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PlatformGroundSpeed(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PlatformGroundSpeed(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PlatformGroundSpeed(new byte[]{0x00, 0x00});
    }
}
