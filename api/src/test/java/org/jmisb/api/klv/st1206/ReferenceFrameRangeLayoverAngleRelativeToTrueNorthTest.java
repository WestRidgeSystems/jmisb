package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Reference Frame Range Layover Angle Relative to True North (ST1206 Tag 13) */
public class ReferenceFrameRangeLayoverAngleRelativeToTrueNorthTest {
    @Test
    public void testConstructFromValue() {
        ReferenceFrameRangeLayoverAngleRelativeToTrueNorth uut =
                new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(25.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(), "Reference Frame Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ReferenceFrameRangeLayoverAngleRelativeToTrueNorth uut =
                new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(), "Reference Frame Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ReferenceFrameRangeLayoverAngleRelativeToTrueNorth,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof ReferenceFrameRangeLayoverAngleRelativeToTrueNorth);
        ReferenceFrameRangeLayoverAngleRelativeToTrueNorth uut =
                (ReferenceFrameRangeLayoverAngleRelativeToTrueNorth) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(), "Reference Frame Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(new byte[] {0x01, 0x02, 0x03});
    }
}
