package org.jmisb.api.klv.st1205;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class SequenceDurationTest {
    @Test
    public void testConstructFromValue() {
        // Min
        SequenceDuration duration = new SequenceDuration(0);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});

        // Max
        duration = new SequenceDuration(65535);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});

        duration = new SequenceDuration(159);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});

        assertEquals(duration.getDisplayName(), "Sequence Duration");
        assertEquals(duration.getDurationInFrames(), 159);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        // Min
        SequenceDuration duration = new SequenceDuration(new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(duration.getDurationInFrames(), 0);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(duration.getDisplayableValue(), "0");

        // Max
        duration = new SequenceDuration(new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(duration.getDurationInFrames(), 65535);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        assertEquals(duration.getDisplayableValue(), "65535");

        duration = new SequenceDuration(new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(duration.getDurationInFrames(), 159);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(duration.getDisplayableValue(), "159");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        ICalibrationMetadataValue v =
                CalibrationLocalSet.createValue(CalibrationPackMetadataKey.SequenceDuration, bytes);
        assertTrue(v instanceof SequenceDuration);
        SequenceDuration duration = (SequenceDuration) v;
        assertEquals(duration.getDurationInFrames(), 0);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        assertEquals(duration.getDisplayableValue(), "0");

        bytes = new byte[] {(byte) 0x00, (byte) 0x9f};
        v = CalibrationLocalSet.createValue(CalibrationPackMetadataKey.SequenceDuration, bytes);
        assertTrue(v instanceof SequenceDuration);
        duration = (SequenceDuration) v;
        assertEquals(duration.getDurationInFrames(), 159);
        assertEquals(duration.getBytes(), new byte[] {(byte) 0x00, (byte) 0x9f});
        assertEquals(duration.getDisplayableValue(), "159");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SequenceDuration(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SequenceDuration(65536);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLength() throws KlvParseException {
        new SequenceDuration(new byte[] {0x00});
    }
}
