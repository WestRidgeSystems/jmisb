package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Correspondence Points Latitude / Longitude. */
public class CorrespondencePointsLatLonTest {

    private final double[][] points =
            new double[][] {
                {32.98416, 32.98417, 32.98418, 32.98419},
                {48.08388, 48.08389, 48.08390, 48.08391}
            };
    private final byte[] bytes =
            new byte[] {
                0x02,
                0x02,
                0x04,
                0x04,
                0x02,
                (byte) 0xc0,
                0x66,
                (byte) 0x80,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x40,
                0x66,
                (byte) 0x80,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x35,
                0x3e,
                (byte) 0xfc,
                0x7a,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xa4,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xce,
                0x35,
                0x3e,
                (byte) 0xfc,
                (byte) 0xf8,
                0x39,
                0x05,
                0x5e,
                0x4a,
                0x39,
                0x05,
                0x5e,
                0x74,
                0x39,
                0x05,
                0x5e,
                (byte) 0x9e,
                0x39,
                0x05,
                0x5e,
                (byte) 0xc8
            };

    @Test
    public void testConstructFromValue() {
        CorrespondencePointsLatLon uut = new CorrespondencePointsLatLon(points);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Latitude/Longitude");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Latitude/Longitude]");
        assertEquals(uut.getCorrespondencePoints(), points);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        CorrespondencePointsLatLon uut = new CorrespondencePointsLatLon(bytes);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Latitude/Longitude");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Latitude/Longitude]");
        assertEquals(uut.getCorrespondencePoints().length, points.length);
        for (int r = 0; r < uut.getCorrespondencePoints().length; r++) {
            assertEquals(uut.getCorrespondencePoints()[r], points[r], 0.00001);
        }
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBad() throws KlvParseException {
        new CorrespondencePointsLatLon(new byte[] {0x00});
    }

    @Test
    public void testToBytesBad() throws KlvParseException {
        CorrespondencePointsLatLon uut = new CorrespondencePointsLatLon(new double[][] {{}});
        assertEquals(uut.getBytes(), new byte[0]);
    }
}
