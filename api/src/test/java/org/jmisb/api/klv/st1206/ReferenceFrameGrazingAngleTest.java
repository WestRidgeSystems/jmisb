package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Reference Frame Grazing Angle (ST1206 Tag 24) */
public class ReferenceFrameGrazingAngleTest {
    @Test
    public void testConstructFromValue() {
        ReferenceFrameGrazingAngle uut = new ReferenceFrameGrazingAngle(6.25);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Reference Frame Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ReferenceFrameGrazingAngle uut =
                new ReferenceFrameGrazingAngle(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Reference Frame Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ReferenceFrameGrazingAngle,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof ReferenceFrameGrazingAngle);
        ReferenceFrameGrazingAngle uut = (ReferenceFrameGrazingAngle) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Reference Frame Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ReferenceFrameGrazingAngle(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ReferenceFrameGrazingAngle(90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ReferenceFrameGrazingAngle(new byte[] {0x01, 0x02, 0x03});
    }
}
