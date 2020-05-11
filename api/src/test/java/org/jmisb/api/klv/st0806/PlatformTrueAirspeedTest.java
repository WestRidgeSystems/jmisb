package org.jmisb.api.klv.st0806;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformTrueAirspeedTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        RvtPlatformTrueAirspeed speed = new RvtPlatformTrueAirspeed(0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // Max
        speed = new RvtPlatformTrueAirspeed(65535);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff, (byte)0xff});

        speed = new RvtPlatformTrueAirspeed(159);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});

        Assert.assertEquals(speed.getDisplayName(), "Platform True Airspeed (TAS)");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        RvtPlatformTrueAirspeed speed = new RvtPlatformTrueAirspeed(new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        // Max
        speed = new RvtPlatformTrueAirspeed(new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(speed.getMetersPerSecond(), 65535);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "65535m/s");

        speed = new RvtPlatformTrueAirspeed(new byte[]{(byte)0x00, (byte)0x9f});
        Assert.assertEquals(speed.getMetersPerSecond(), 159);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});
        Assert.assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x00};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.PlatformTrueAirspeed, bytes);
        Assert.assertTrue(v instanceof RvtPlatformTrueAirspeed);
        RvtPlatformTrueAirspeed speed = (RvtPlatformTrueAirspeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 0);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        bytes = new byte[]{(byte)0x00, (byte)0x9f};
        v = RvtLocalSet.createValue(RvtMetadataKey.PlatformTrueAirspeed, bytes);
        Assert.assertTrue(v instanceof RvtPlatformTrueAirspeed);
        speed = (RvtPlatformTrueAirspeed)v;
        Assert.assertEquals(speed.getMetersPerSecond(), 159);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});
        Assert.assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new RvtPlatformTrueAirspeed(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new RvtPlatformTrueAirspeed(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new RvtPlatformTrueAirspeed(new byte[]{0x00});
    }
}
