package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Range Direction Angle Relative to True North (ST1206 Tag 11) */
public class RangeDirectionAngleRelativeToTrueNorthTest {
    @Test
    public void testConstructFromValue() {
        RangeDirectionAngleRelativeToTrueNorth uut =
                new RangeDirectionAngleRelativeToTrueNorth(25.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        RangeDirectionAngleRelativeToTrueNorth uut =
                new RangeDirectionAngleRelativeToTrueNorth(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.RangeDirectionAngleRelativeToTrueNorth,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof RangeDirectionAngleRelativeToTrueNorth);
        RangeDirectionAngleRelativeToTrueNorth uut = (RangeDirectionAngleRelativeToTrueNorth) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Direction Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RangeDirectionAngleRelativeToTrueNorth(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RangeDirectionAngleRelativeToTrueNorth(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RangeDirectionAngleRelativeToTrueNorth(new byte[] {0x01, 0x02, 0x03});
    }
}
