package org.jmisb.st0602;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Locally Unique Identifier implementation. */
public class LocallyUniqueIdentifierTest {

    public LocallyUniqueIdentifierTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        LocallyUniqueIdentifier uut = new LocallyUniqueIdentifier(3);
        assertEquals(uut.getDisplayName(), "Locally Unique Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        LocallyUniqueIdentifier uut =
                new LocallyUniqueIdentifier(new byte[] {0x00, 0x00, 0x00, 0x03});
        assertEquals(uut.getDisplayName(), "Locally Unique Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new LocallyUniqueIdentifier(new byte[] {0x00, 0x00, 0x02});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new LocallyUniqueIdentifier(new byte[] {0x00, 0x00, 0x00, 0x00, 0x04});
    }

    @Test
    public void checkConstruction0() throws KlvParseException {
        LocallyUniqueIdentifier uut = new LocallyUniqueIdentifier(0);
        assertEquals(uut.getDisplayName(), "Locally Unique Identifier");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionNeg() throws KlvParseException {
        new LocallyUniqueIdentifier(-1);
    }

    @Test
    public void checkConstructionMax() throws KlvParseException {
        LocallyUniqueIdentifier uut = new LocallyUniqueIdentifier(4294967295l);
        assertEquals(uut.getDisplayName(), "Locally Unique Identifier");
        assertEquals(uut.getDisplayableValue(), "4294967295");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooLarge() throws KlvParseException {
        new LocallyUniqueIdentifier(4294967296l);
    }
}
