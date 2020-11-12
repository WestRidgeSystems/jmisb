package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Reference Frame Range Direction Angle Relative to True North (ST1206 Tag 26) */
public class ReferenceFrameRangeDirectionAngleRelativeToTrueNorthTest {
    @Test
    public void testConstructFromValue() {
        ReferenceFrameRangeDirectionAngleRelativeToTrueNorth uut =
                new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(25.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(),
                "Reference Frame Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ReferenceFrameRangeDirectionAngleRelativeToTrueNorth uut =
                new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(),
                "Reference Frame Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ReferenceFrameRangeDirectionAngleRelativeToTrueNorth,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof ReferenceFrameRangeDirectionAngleRelativeToTrueNorth);
        ReferenceFrameRangeDirectionAngleRelativeToTrueNorth uut =
                (ReferenceFrameRangeDirectionAngleRelativeToTrueNorth) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(
                uut.getDisplayName(),
                "Reference Frame Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(new byte[] {0x01, 0x02, 0x03});
    }
}
