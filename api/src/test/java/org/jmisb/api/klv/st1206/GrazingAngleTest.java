package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Grazing Angle (ST1206 Tag 1) */
public class GrazingAngleTest {
    @Test
    public void testConstructFromValue() {
        GrazingAngle uut = new GrazingAngle(6.25);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        GrazingAngle uut = new GrazingAngle(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.GrazingAngle, new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof GrazingAngle);
        GrazingAngle uut = (GrazingAngle) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Grazing Angle");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new GrazingAngle(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new GrazingAngle(90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new GrazingAngle(new byte[] {0x01, 0x02, 0x03});
    }
}
