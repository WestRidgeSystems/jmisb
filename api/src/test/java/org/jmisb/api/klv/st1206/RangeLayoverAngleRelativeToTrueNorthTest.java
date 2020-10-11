package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Range Layover Angle Relative to True North (ST1206 Tag 13) */
public class RangeLayoverAngleRelativeToTrueNorthTest {
    @Test
    public void testConstructFromValue() {
        RangeLayoverAngleRelativeToTrueNorth uut = new RangeLayoverAngleRelativeToTrueNorth(25.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        RangeLayoverAngleRelativeToTrueNorth uut =
                new RangeLayoverAngleRelativeToTrueNorth(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.RangeLayoverAngleRelativeToTrueNorth,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof RangeLayoverAngleRelativeToTrueNorth);
        RangeLayoverAngleRelativeToTrueNorth uut = (RangeLayoverAngleRelativeToTrueNorth) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Range Layover Angle Relative to True North");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RangeLayoverAngleRelativeToTrueNorth(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RangeLayoverAngleRelativeToTrueNorth(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RangeLayoverAngleRelativeToTrueNorth(new byte[] {0x01, 0x02, 0x03});
    }
}
