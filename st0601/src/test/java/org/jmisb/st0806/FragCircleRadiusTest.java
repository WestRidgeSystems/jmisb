package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class FragCircleRadiusTest {
    @Test
    public void testConstructFromValue() {
        // Min
        FragCircleRadius radius = new FragCircleRadius(0);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});

        // Max
        radius = new FragCircleRadius(65535);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});

        radius = new FragCircleRadius(159);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});

        assertEquals(radius.getDisplayName(), "Frag Circle Radius");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        FragCircleRadius radius = new FragCircleRadius(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(radius.getRadius(), 0);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(radius.getDisplayableValue(), "0m");

        // Max
        radius = new FragCircleRadius(new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(radius.getRadius(), 65535);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(radius.getDisplayableValue(), "65535m");

        radius = new FragCircleRadius(new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(radius.getRadius(), 159);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(radius.getDisplayableValue(), "159m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.FragCircleRadius, bytes);
        assertTrue(v instanceof FragCircleRadius);
        FragCircleRadius radius = (FragCircleRadius) v;
        assertEquals(radius.getRadius(), 0);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(radius.getDisplayableValue(), "0m");

        bytes = new byte[] {(byte) 0x00, (byte) 0x9f};
        v = RvtLocalSet.createValue(RvtMetadataKey.FragCircleRadius, bytes);
        assertTrue(v instanceof FragCircleRadius);
        radius = (FragCircleRadius) v;
        assertEquals(radius.getRadius(), 159);
        assertEquals(radius.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(radius.getDisplayableValue(), "159m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FragCircleRadius(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FragCircleRadius(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FragCircleRadius(new byte[] {0x00});
    }
}
