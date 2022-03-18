package org.jmisb.st0602;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Active Lines Per Frame implementation. */
public class ActiveLinesPerFrameTest {

    public ActiveLinesPerFrameTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        ActiveLinesPerFrame uut = new ActiveLinesPerFrame(1024);
        assertEquals(uut.getDisplayName(), "Active Lines Per Frame");
        assertEquals(uut.getDisplayableValue(), "1024");
        assertEquals(uut.getNumber(), 1024);
        assertEquals(uut.getBytes(), new byte[] {0x04, 0x00});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        ActiveLinesPerFrame uut = new ActiveLinesPerFrame(new byte[] {0x04, 0x00});
        assertEquals(uut.getDisplayName(), "Active Lines Per Frame");
        assertEquals(uut.getDisplayableValue(), "1024");
        assertEquals(uut.getNumber(), 1024);
        assertEquals(uut.getBytes(), new byte[] {0x04, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new ActiveLinesPerFrame(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new ActiveLinesPerFrame(new byte[] {0x00, 0x01, 0x02});
    }

    @Test
    public void checkConstructionMin() throws KlvParseException {
        ActiveLinesPerFrame uut = new ActiveLinesPerFrame(0);
        assertEquals(uut.getDisplayName(), "Active Lines Per Frame");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getNumber(), 0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
    }

    @Test
    public void checkConstructionMax() throws KlvParseException {
        ActiveLinesPerFrame uut = new ActiveLinesPerFrame(65535);
        assertEquals(uut.getDisplayName(), "Active Lines Per Frame");
        assertEquals(uut.getDisplayableValue(), "65535");
        assertEquals(uut.getNumber(), 65535);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooLarge() throws KlvParseException {
        new ActiveLinesPerFrame(65536);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooSmall() throws KlvParseException {
        new ActiveLinesPerFrame(-1);
    }
}
