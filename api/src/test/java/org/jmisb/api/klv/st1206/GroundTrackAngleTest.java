package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Ground Track Angle (ST1206 Tag 16) */
public class GroundTrackAngleTest {
    @Test
    public void testConstructFromValue() {
        GroundTrackAngle uut = new GroundTrackAngle(12.5);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Ground Track Angle");
        assertEquals(uut.getDisplayableValue(), "12.5\u00B0");
        assertEquals(uut.getAngle(), 12.5, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        GroundTrackAngle uut = new GroundTrackAngle(new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Ground Track Angle");
        assertEquals(uut.getDisplayableValue(), "12.5\u00B0");
        assertEquals(uut.getAngle(), 12.5, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.GroundTrackAngle, new byte[] {(byte) 0x03, (byte) 0x20});
        assertTrue(value instanceof GroundTrackAngle);
        GroundTrackAngle uut = (GroundTrackAngle) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Ground Track Angle");
        assertEquals(uut.getDisplayableValue(), "12.5\u00B0");
        assertEquals(uut.getAngle(), 12.5, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new GroundTrackAngle(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new GroundTrackAngle(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new GroundTrackAngle(new byte[] {0x01, 0x02, 0x03});
    }
}
