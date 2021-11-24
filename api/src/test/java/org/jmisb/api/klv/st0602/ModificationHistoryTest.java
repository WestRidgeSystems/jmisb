package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Media Description implementation. */
public class ModificationHistoryTest {

    public ModificationHistoryTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        ModificationHistory uut = new ModificationHistory("Brad H.");
        assertEquals(uut.getDisplayName(), "Modification History");
        assertEquals(uut.getDisplayableValue(), "Brad H.");
        assertEquals(uut.getBytes(), new byte[] {0x42, 0x72, 0x61, 0x64, 0x20, 0x48, 0x2e});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        ModificationHistory uut =
                new ModificationHistory(new byte[] {0x42, 0x72, 0x61, 0x64, 0x20, 0x48, 0x2e});
        assertEquals(uut.getDisplayName(), "Modification History");
        assertEquals(uut.getDisplayableValue(), "Brad H.");
        assertEquals(uut.getBytes(), new byte[] {0x42, 0x72, 0x61, 0x64, 0x20, 0x48, 0x2e});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooLong() throws KlvParseException {
        new ModificationHistory(
                "This text is 128 bytes......................................................................................................long");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionTooLongBytes() throws KlvParseException {
        byte[] bytes = new byte[128];
        Arrays.fill(bytes, (byte) 0x51);
        new ModificationHistory(bytes);
    }

    @Test
    public void checkConstructionJustOKBytes() throws KlvParseException {
        byte[] bytes = new byte[127];
        Arrays.fill(bytes, (byte) 0x51);
        ModificationHistory uut = new ModificationHistory(bytes);
        assertEquals(
                uut.getDisplayableValue(),
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }
}
