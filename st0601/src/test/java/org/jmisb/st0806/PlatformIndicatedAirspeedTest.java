package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class PlatformIndicatedAirspeedTest {
    @Test
    public void testConstructFromValue() {
        // Min
        RvtPlatformIndicatedAirspeed speed = new RvtPlatformIndicatedAirspeed(0);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});

        // Max
        speed = new RvtPlatformIndicatedAirspeed(65535);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});

        speed = new RvtPlatformIndicatedAirspeed(159);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});

        assertEquals(speed.getDisplayName(), "Platform Indicated Airspeed (IAS)");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        RvtPlatformIndicatedAirspeed speed =
                new RvtPlatformIndicatedAirspeed(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(speed.getMetersPerSecond(), 0.0);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(speed.getDisplayableValue(), "0m/s");

        // Max
        speed = new RvtPlatformIndicatedAirspeed(new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(speed.getMetersPerSecond(), 65535);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(speed.getDisplayableValue(), "65535m/s");

        speed = new RvtPlatformIndicatedAirspeed(new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(speed.getMetersPerSecond(), 159);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IRvtMetadataValue v =
                RvtLocalSet.createValue(RvtMetadataKey.PlatformIndicatedAirspeed, bytes);
        assertTrue(v instanceof RvtPlatformIndicatedAirspeed);
        RvtPlatformIndicatedAirspeed speed = (RvtPlatformIndicatedAirspeed) v;
        assertEquals(speed.getMetersPerSecond(), 0);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(speed.getDisplayableValue(), "0m/s");

        bytes = new byte[] {(byte) 0x00, (byte) 0x9f};
        v = RvtLocalSet.createValue(RvtMetadataKey.PlatformIndicatedAirspeed, bytes);
        assertTrue(v instanceof RvtPlatformIndicatedAirspeed);
        speed = (RvtPlatformIndicatedAirspeed) v;
        assertEquals(speed.getMetersPerSecond(), 159);
        assertEquals(speed.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(speed.getDisplayableValue(), "159m/s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RvtPlatformIndicatedAirspeed(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RvtPlatformIndicatedAirspeed(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RvtPlatformIndicatedAirspeed(new byte[] {0x00});
    }
}
