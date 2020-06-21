package org.jmisb.api.klv.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class VideoDateRateTest {
    @Test
    public void testConstructFromValue() {
        // Min
        VideoDataRate rate = new VideoDataRate(0);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});

        // Max
        rate = new VideoDataRate(4294967295L);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        rate = new VideoDataRate(159);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});

        assertEquals(rate.getDisplayName(), "Video Data Rate");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        VideoDataRate rate =
                new VideoDataRate(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(rate.getRate(), 0);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(rate.getDisplayableValue(), "0");

        // Max
        rate = new VideoDataRate(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        assertEquals(rate.getRate(), 4294967295L);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        assertEquals(rate.getDisplayableValue(), "4294967295");

        rate = new VideoDataRate(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(rate.getRate(), 159);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(rate.getDisplayableValue(), "159");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.VideoDataRate, bytes);
        assertTrue(v instanceof VideoDataRate);
        VideoDataRate rate = (VideoDataRate) v;
        assertEquals(rate.getRate(), 0);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(rate.getDisplayableValue(), "0");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f};
        v = RvtLocalSet.createValue(RvtMetadataKey.VideoDataRate, bytes);
        assertTrue(v instanceof VideoDataRate);
        rate = (VideoDataRate) v;
        assertEquals(rate.getRate(), 159);
        assertEquals(
                rate.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9f});
        assertEquals(rate.getDisplayableValue(), "159");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new VideoDataRate(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new VideoDataRate(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new VideoDataRate(new byte[] {0x01, 0x02, 0x03});
    }
}
