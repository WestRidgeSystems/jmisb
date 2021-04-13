package org.jmisb.api.klv.st1902;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for MimdIdReference */
public class MimdIdReferenceTest {

    public MimdIdReferenceTest() {}

    @Test
    public void fromValue() {
        MimdIdReference uut = new MimdIdReference(8, 0, "Composite Security", "Security");
        assertEquals(uut.getDisplayName(), "Composite Security");
        assertEquals(uut.getDisplayableValue(), "REF<Security>(8, 0)");
        assertEquals(uut.getGroupId(), 0);
        assertEquals(uut.getSerialNumber(), 8);
        assertEquals(uut.getBytes(), new byte[] {0x08});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        MimdIdReference uut =
                new MimdIdReference(new byte[] {0x08}, 0, 1, "CompositeSomething", "Timer");
        assertEquals(uut.getDisplayName(), "CompositeSomething");
        assertEquals(uut.getDisplayableValue(), "REF<Timer>(8, 0)");
        assertEquals(uut.getGroupId(), 0);
        assertEquals(uut.getSerialNumber(), 8);
        assertEquals(uut.getBytes(), new byte[] {0x08});
    }

    @Test
    public void fromBytesWithGroup() throws KlvParseException {
        MimdIdReference uut =
                new MimdIdReference(new byte[] {0x08, 0x04}, 0, 2, "Composite Something", "Stage");
        assertEquals(uut.getDisplayName(), "Composite Something");
        assertEquals(uut.getDisplayableValue(), "REF<Stage>(8, 4)");
        assertEquals(uut.getGroupId(), 4);
        assertEquals(uut.getSerialNumber(), 8);
        assertEquals(uut.getBytes(), new byte[] {0x08, 0x04});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesWithGroupBadFormat() throws KlvParseException {
        new MimdIdReference(new byte[] {0x08, (byte) 0x84}, 0, 2, "X", "Y");
    }
}
