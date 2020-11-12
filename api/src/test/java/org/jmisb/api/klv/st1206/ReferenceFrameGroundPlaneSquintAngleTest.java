package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Reference Frame Ground Plane Squint Angle (ST1206 Tag 25) */
public class ReferenceFrameGroundPlaneSquintAngleTest {
    @Test
    public void testConstructFromValue() {
        ReferenceFrameGroundPlaneSquintAngle uut = new ReferenceFrameGroundPlaneSquintAngle(38.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Reference Frame Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ReferenceFrameGroundPlaneSquintAngle uut =
                new ReferenceFrameGroundPlaneSquintAngle(new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Reference Frame Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ReferenceFrameGroundPlaneSquintAngle,
                        new byte[] {(byte) 0x40, (byte) 0x00});
        assertTrue(value instanceof ReferenceFrameGroundPlaneSquintAngle);
        ReferenceFrameGroundPlaneSquintAngle uut = (ReferenceFrameGroundPlaneSquintAngle) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x40, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Reference Frame Ground Plane Squint Angle");
        assertEquals(uut.getDisplayableValue(), "38.0\u00B0");
        assertEquals(uut.getAngle(), 38.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ReferenceFrameGroundPlaneSquintAngle(-90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ReferenceFrameGroundPlaneSquintAngle(90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ReferenceFrameGroundPlaneSquintAngle(new byte[] {0x01, 0x02, 0x03});
    }
}
