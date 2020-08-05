package org.jmisb.api.klv.st0903.vtracker;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Confidence (VTracker LS Tag 7) */
public class ConfidenceTest {

    @Test
    public void testConstructFromValue() {
        TrackConfidence confidence = new TrackConfidence((short) 50);
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x32});
        assertEquals(confidence.getDisplayName(), "Track Confidence");
        assertEquals(confidence.getDisplayableValue(), "50%");
        assertEquals(confidence.getConfidence(), 50);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TrackConfidence confidence = new TrackConfidence(new byte[] {(byte) 0x32});
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x32});
        assertEquals(confidence.getDisplayName(), "Track Confidence");
        assertEquals(confidence.getDisplayableValue(), "50%");
        assertEquals(confidence.getConfidence(), 50);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.confidence,
                        new byte[] {(byte) 0x32},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TrackConfidence);
        TrackConfidence confidence = (TrackConfidence) value;
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x32});
        assertEquals(confidence.getDisplayName(), "Track Confidence");
        assertEquals(confidence.getDisplayableValue(), "50%");
        assertEquals(confidence.getConfidence(), 50);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TrackConfidence((short) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TrackConfidence((short) 101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TrackConfidence(new byte[] {0x01, 0x02});
    }
}
