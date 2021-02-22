package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Ground Aperture Angular Extent (ST1206 Tag 15) */
public class GroundApertureAngularExtentTest {
    @Test
    public void testConstructFromValue() {
        GroundApertureAngularExtent uut = new GroundApertureAngularExtent(6.25);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Ground Aperture Angular Extent");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        GroundApertureAngularExtent uut =
                new GroundApertureAngularExtent(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Ground Aperture Angular Extent");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.GroundApertureAngularExtent,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof GroundApertureAngularExtent);
        GroundApertureAngularExtent uut = (GroundApertureAngularExtent) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "Ground Aperture Angular Extent");
        assertEquals(uut.getDisplayableValue(), "6.3\u00B0");
        assertEquals(uut.getAngle(), 6.25, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new GroundApertureAngularExtent(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new GroundApertureAngularExtent(90.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new GroundApertureAngularExtent(new byte[] {0x01, 0x02, 0x03});
    }
}
