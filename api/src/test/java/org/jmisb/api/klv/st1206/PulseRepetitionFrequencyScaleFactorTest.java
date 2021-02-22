package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Pulse Repetition Frequency Scale Factor (ST 1206 Item 19). */
public class PulseRepetitionFrequencyScaleFactorTest {
    @Test
    public void testConstructFromValue() {
        PulseRepetitionFrequencyScaleFactor uut = new PulseRepetitionFrequencyScaleFactor(0.24);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x1e, (byte) 0xb8});
        assertEquals(uut.getDisplayName(), "Pulse Repetition Frequency Scale Factor");
        assertEquals(uut.getDisplayableValue(), "0.24000");
        assertEquals(uut.getScaleFactor(), 0.24000, 0.00001);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        PulseRepetitionFrequencyScaleFactor uut =
                new PulseRepetitionFrequencyScaleFactor(new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Pulse Repetition Frequency Scale Factor");
        assertEquals(uut.getDisplayableValue(), "0.00003");
        assertEquals(uut.getScaleFactor(), 0.00003, 0.00001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.PulseRepetitionFrequencyScaleFactor,
                        new byte[] {(byte) 0x01, (byte) 0x03});
        assertTrue(value instanceof PulseRepetitionFrequencyScaleFactor);
        PulseRepetitionFrequencyScaleFactor uut = (PulseRepetitionFrequencyScaleFactor) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x03});
        assertEquals(uut.getDisplayName(), "Pulse Repetition Frequency Scale Factor");
        assertEquals(uut.getDisplayableValue(), "0.00790");
        assertEquals(uut.getScaleFactor(), 0.0079, 0.00001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PulseRepetitionFrequencyScaleFactor(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PulseRepetitionFrequencyScaleFactor(1.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PulseRepetitionFrequencyScaleFactor(new byte[] {0x01, 0x02, 0x03});
    }
}
