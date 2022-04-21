package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Correspondence Points Elevation. */
public class CorrespondencePointsElevationTest {

    private final double[] points = new double[] {1500.0, 1501.0, 1500.0, 1499.0};

    private final byte[] bytes =
            new byte[] {
                0x01,
                0x04,
                0x03,
                0x02,
                (byte) 0xc0,
                (byte) 0x8c,
                0x20,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x40,
                (byte) 0xe3,
                (byte) 0x88,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x04,
                (byte) 0xb0,
                0x00,
                0x04,
                (byte) 0xb0,
                (byte) 0x80,
                0x04,
                (byte) 0xb0,
                0x00,
                0x04,
                (byte) 0xaf,
                (byte) 0x80
            };

    @Test
    public void testConstructFromValue() {
        CorrespondencePointsElevation uut = new CorrespondencePointsElevation(points);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Elevation");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Elevation]");
        assertEquals(uut.getElevations(), points);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        CorrespondencePointsElevation uut = new CorrespondencePointsElevation(bytes);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Elevation");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Elevation]");
        assertEquals(uut.getElevations(), points, 0.001);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBad() throws KlvParseException {
        new CorrespondencePointsElevation(new byte[] {0x00});
    }

    @Test
    public void testToBytesBad() throws KlvParseException {
        CorrespondencePointsElevation uut = new CorrespondencePointsElevation(new double[] {});
        assertEquals(uut.getBytes(), new byte[0]);
    }
}
