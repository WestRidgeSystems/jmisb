package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformIndicatedAirspeedTest {
    @Test
    public void testConstructFromValue() {
        // Min
        PlatformIndicatedAirspeed speed = new PlatformIndicatedAirspeed(0);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x00});

        // Max
        speed = new PlatformIndicatedAirspeed(255);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        speed = new PlatformIndicatedAirspeed(159);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x9f});

        Assert.assertEquals(speed.getDisplayName(), "Platform Indicated Airspeed");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        PlatformIndicatedAirspeed speed = new PlatformIndicatedAirspeed(new byte[] {(byte) 0x00});
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        // Max
        speed = new PlatformIndicatedAirspeed(new byte[] {(byte) 0xff});
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "255m/s");

        // From ST:
        speed = new PlatformIndicatedAirspeed(new byte[] {(byte) 0x9f});
        Assert.assertEquals(speed.getMetersPerSecond(), 159);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x9f});
        Assert.assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformIndicatedAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformIndicatedAirspeed);
        PlatformIndicatedAirspeed speed = (PlatformIndicatedAirspeed) v;
        Assert.assertEquals(speed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0m/s");

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformIndicatedAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformIndicatedAirspeed);
        speed = (PlatformIndicatedAirspeed) v;
        Assert.assertEquals(speed.getMetersPerSecond(), 255);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "255m/s");

        bytes = new byte[] {(byte) 0x9f};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformIndicatedAirspeed, bytes);
        Assert.assertTrue(v instanceof PlatformIndicatedAirspeed);
        speed = (PlatformIndicatedAirspeed) v;
        Assert.assertEquals(speed.getMetersPerSecond(), 159);
        Assert.assertEquals(speed.getBytes(), new byte[] {(byte) 0x9f});
        Assert.assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformIndicatedAirspeed(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformIndicatedAirspeed(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformIndicatedAirspeed(new byte[] {0x00, 0x00});
    }
}
