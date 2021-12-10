package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Active Samples Per Line implementation. */
public class ActiveSamplesPerLineTest {

    public ActiveSamplesPerLineTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        ActiveSamplesPerLine uut = new ActiveSamplesPerLine(1280);
        assertEquals(uut.getDisplayName(), "Active Samples Per Line");
        assertEquals(uut.getDisplayableValue(), "1280");
        assertEquals(uut.getNumber(), 1280);
        assertEquals(uut.getBytes(), new byte[] {0x05, 0x00});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        ActiveSamplesPerLine uut = new ActiveSamplesPerLine(new byte[] {0x05, 0x00});
        assertEquals(uut.getDisplayName(), "Active Samples Per Line");
        assertEquals(uut.getDisplayableValue(), "1280");
        assertEquals(uut.getNumber(), 1280);
        assertEquals(uut.getBytes(), new byte[] {0x05, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new ActiveSamplesPerLine(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new ActiveSamplesPerLine(new byte[] {0x00, 0x01, 0x02});
    }

    @Test
    public void checkConstructionMin() throws KlvParseException {
        ActiveSamplesPerLine uut = new ActiveSamplesPerLine(0);
        assertEquals(uut.getDisplayName(), "Active Samples Per Line");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getNumber(), 0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
    }

    @Test
    public void checkConstructionMax() throws KlvParseException {
        ActiveSamplesPerLine uut = new ActiveSamplesPerLine(65535);
        assertEquals(uut.getDisplayName(), "Active Samples Per Line");
        assertEquals(uut.getDisplayableValue(), "65535");
        assertEquals(uut.getNumber(), 65535);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooLarge() throws KlvParseException {
        new ActiveSamplesPerLine(65536);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooSmall() throws KlvParseException {
        new ActiveSamplesPerLine(-1);
    }
}
