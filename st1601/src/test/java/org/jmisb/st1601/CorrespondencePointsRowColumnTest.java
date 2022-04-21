package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1601 Correspondence Points Row/Column. */
public class CorrespondencePointsRowColumnTest {
    private long[][] points =
            new long[][] {
                {133, 128, 97, 69},
                {31, 91, 122, 129},
                {89, 82, 52, 27},
                {125, 176, 204, 210}
            };
    private byte[] bytes =
            new byte[] {
                0x02,
                0x04,
                0x04,
                0x01,
                0x01,
                (byte) 0x85,
                (byte) 0x80,
                0x61,
                0x45,
                0x1f,
                0x5b,
                0x7a,
                (byte) 0x81,
                0x59,
                0x52,
                0x34,
                0x1b,
                0x7d,
                (byte) 0xb0,
                (byte) 0xcc,
                (byte) 0xd2
            };

    @Test
    public void testConstructFromValue() {
        CorrespondencePointsRowColumn uut = new CorrespondencePointsRowColumn(points);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Row/Column");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Row/Column]");
        assertEquals(uut.getCorrespondencePoints(), points);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        CorrespondencePointsRowColumn uut = new CorrespondencePointsRowColumn(bytes);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getDisplayName(), "Correspondence Points – Row/Column");
        assertEquals(uut.getDisplayableValue(), "[Correspondence Points – Row/Column]");
        assertEquals(uut.getCorrespondencePoints(), points);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBad() throws KlvParseException {
        new CorrespondencePointsRowColumn(new byte[] {0x00});
    }

    @Test
    public void testToBytesBad() throws KlvParseException {
        CorrespondencePointsRowColumn uut = new CorrespondencePointsRowColumn(new long[][] {{}});
        assertEquals(uut.getBytes(), new byte[0]);
    }
}
