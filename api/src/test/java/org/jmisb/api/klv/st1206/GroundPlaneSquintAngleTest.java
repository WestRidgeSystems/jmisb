package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Ground Plane Squint Angle (ST1206 Tag 2) */
public class GroundPlaneSquintAngleTest {
    @Test
    public void testConstructFromValue() {
        GroundPlaneSquintAngle uut = new GroundPlaneSquintAngle(38.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        GroundPlaneSquintAngle uut =
                new GroundPlaneSquintAngle(new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.GroundPlaneSquintAngle,
                        new byte[] {(byte) 0x40, (byte) 0x00});
        assertTrue(value instanceof GroundPlaneSquintAngle);
        GroundPlaneSquintAngle uut = (GroundPlaneSquintAngle) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new GroundPlaneSquintAngle(-90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new GroundPlaneSquintAngle(90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new GroundPlaneSquintAngle(new byte[] {0x01, 0x02, 0x03});
    }
}
