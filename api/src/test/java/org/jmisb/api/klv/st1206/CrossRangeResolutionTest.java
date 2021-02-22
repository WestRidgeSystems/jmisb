package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Cross Range Resolution (ST1206 Tag 5). */
public class CrossRangeResolutionTest {
    @Test
    public void testConstructFromValue() {
        CrossRangeResolution uut = new CrossRangeResolution(3.4);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x1b, (byte) 0x33});
        assertEquals(uut.getDisplayName(), "Cross Range Resolution");
        assertEquals(uut.getDisplayableValue(), "3.40m");
        assertEquals(uut.getResolution(), 3.400, 0.001);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        CrossRangeResolution uut =
                new CrossRangeResolution(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Cross Range Resolution");
        assertEquals(uut.getDisplayableValue(), "32.39m");
        assertEquals(uut.getResolution(), 32.391, 0.001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.CrossRangeResolution,
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertTrue(value instanceof CrossRangeResolution);
        CrossRangeResolution uut = (CrossRangeResolution) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Cross Range Resolution");
        assertEquals(uut.getDisplayableValue(), "0.39m");
        assertEquals(uut.getResolution(), 0.390, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new CrossRangeResolution(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new CrossRangeResolution(1000000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new CrossRangeResolution(new byte[] {0x01, 0x02, 0x03});
    }
}
