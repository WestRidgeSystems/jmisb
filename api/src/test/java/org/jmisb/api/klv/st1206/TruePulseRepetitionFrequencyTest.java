package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for True Pulse Repetition Frequency (ST 1206 Item 18). */
public class TruePulseRepetitionFrequencyTest {
    @Test
    public void testConstructFromValue() {
        TruePulseRepetitionFrequency uut = new TruePulseRepetitionFrequency(50000.0);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x1a, (byte) 0x80, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "True Pulse Repetition Frequency");
        assertEquals(uut.getDisplayableValue(), "50000.0Hz");
        assertEquals(uut.getPulseRepetitionFrequency(), 50000.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TruePulseRepetitionFrequency uut =
                new TruePulseRepetitionFrequency(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x40, (byte) 0x00});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "True Pulse Repetition Frequency");
        assertEquals(uut.getDisplayableValue(), "40.0Hz");
        assertEquals(uut.getPulseRepetitionFrequency(), 40.00, 0.001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.TruePulseRepetitionFrequency,
                        new byte[] {(byte) 0x01, (byte) 0x03, (byte) 0xa0, (byte) 0x00});
        assertTrue(value instanceof TruePulseRepetitionFrequency);
        TruePulseRepetitionFrequency uut = (TruePulseRepetitionFrequency) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x03, (byte) 0xa0, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "True Pulse Repetition Frequency");
        assertEquals(uut.getDisplayableValue(), "8308.0Hz");
        assertEquals(uut.getPulseRepetitionFrequency(), 8308.0, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TruePulseRepetitionFrequency(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TruePulseRepetitionFrequency(1000000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TruePulseRepetitionFrequency(new byte[] {0x01, 0x02, 0x03});
    }
}
