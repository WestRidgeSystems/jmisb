package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformTrueAirspeedTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        PlatformTrueAirspeed speed = new PlatformTrueAirspeed(0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});

        // Max
        speed = new PlatformTrueAirspeed(255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});

        // From ST:
        speed = new PlatformTrueAirspeed(147);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x93});

        Assert.assertEquals(speed.getDisplayName(), "Platform True Airspeed");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        PlatformTrueAirspeed speed = new PlatformTrueAirspeed(new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals("0m/s", speed.getDisplayableValue());

        // Max
        speed = new PlatformTrueAirspeed(new byte[]{(byte)0xff});
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals("255m/s", speed.getDisplayableValue());

        // From ST:
        speed = new PlatformTrueAirspeed(new byte[]{(byte)0x93});
        Assert.assertEquals(speed.getMetersPerSecond(), 147);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x93});
        Assert.assertEquals("147m/s", speed.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformTrueAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformTrueAirspeed);
        PlatformTrueAirspeed speed = (PlatformTrueAirspeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals("0m/s", speed.getDisplayableValue());

        bytes = new byte[]{(byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformTrueAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformTrueAirspeed);
        speed = (PlatformTrueAirspeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals("255m/s", speed.getDisplayableValue());

        bytes = new byte[]{(byte)0x93};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformTrueAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformTrueAirspeed);
        speed = (PlatformTrueAirspeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 147);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x93});
        Assert.assertEquals("147m/s", speed.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PlatformTrueAirspeed(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PlatformTrueAirspeed(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PlatformTrueAirspeed(new byte[]{0x00, 0x00});
    }
}
