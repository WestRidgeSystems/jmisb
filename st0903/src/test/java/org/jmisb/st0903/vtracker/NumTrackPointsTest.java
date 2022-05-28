package org.jmisb.st0903.vtracker;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Number of Track Points (VTracker LS Tag 8) */
public class NumTrackPointsTest {

    @Test
    public void testConstructFromValue() {
        NumTrackPoints num = new NumTrackPoints((short) 27);
        assertEquals(num.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(num.getDisplayName(), "Num Track Points");
        assertEquals(num.getDisplayableValue(), "27");
        assertEquals(num.getNumberOfTrackPoints(), 27);
    }

    @Test
    public void testConstructFromValue2() {
        NumTrackPoints num = new NumTrackPoints((short) 539);
        assertEquals(num.getBytes(), new byte[] {(byte) 0x02, (byte) 0x1B});
        assertEquals(num.getDisplayName(), "Num Track Points");
        assertEquals(num.getDisplayableValue(), "539");
        assertEquals(num.getNumberOfTrackPoints(), 539);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        NumTrackPoints num = new NumTrackPoints(new byte[] {(byte) 0x1B});
        assertEquals(num.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(num.getDisplayName(), "Num Track Points");
        assertEquals(num.getDisplayableValue(), "27");
        assertEquals(num.getNumberOfTrackPoints(), 27);
    }

    @Test
    public void testConstructFromEncodedBytes2() {
        NumTrackPoints num = new NumTrackPoints(new byte[] {(byte) 0x02, (byte) 0x1B});
        assertEquals(num.getBytes(), new byte[] {(byte) 0x02, (byte) 0x1B});
        assertEquals(num.getDisplayName(), "Num Track Points");
        assertEquals(num.getDisplayableValue(), "539");
        assertEquals(num.getNumberOfTrackPoints(), 539);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.numTrackPoints,
                        new byte[] {(byte) 0x1B},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof NumTrackPoints);
        NumTrackPoints num = (NumTrackPoints) value;
        assertEquals(num.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(num.getDisplayName(), "Num Track Points");
        assertEquals(num.getDisplayableValue(), "27");
        assertEquals(num.getNumberOfTrackPoints(), 27);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new NumTrackPoints((short) 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new NumTrackPoints(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new NumTrackPoints(new byte[] {0x01, 0x02, 0x03});
    }
}
