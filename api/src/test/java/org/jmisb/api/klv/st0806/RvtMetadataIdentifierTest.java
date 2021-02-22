package org.jmisb.api.klv.st0806;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for RvtMetadataIdentifier. */
public class RvtMetadataIdentifierTest {

    public RvtMetadataIdentifierTest() {}

    @Test
    public void checkIdentifierKindPlain() {
        RvtMetadataIdentifier uut = new RvtMetadataIdentifier(RvtMetadataKind.PLAIN, 98);
        assertEquals(uut.getKind(), RvtMetadataKind.PLAIN);
        assertEquals(uut.getKindId(), 98);
        assertEquals(uut.getIdentifier(), 98);
        assertEquals(uut.toString(), "RvtMetadataIdentifier{identifier=98, kind=PLAIN}");
    }

    @Test
    public void checkIdentifierHashCode() {
        RvtMetadataIdentifier uut = new RvtMetadataIdentifier(RvtMetadataKind.PLAIN, 98);
        // We'll take anything - just don't throw
        int a = uut.hashCode();
    }

    @Test
    public void checkIdentifierKindAOI() {
        RvtMetadataIdentifier uut = new RvtMetadataIdentifier(RvtMetadataKind.AOI, 3);
        assertEquals(uut.getKind(), RvtMetadataKind.AOI);
        assertEquals(uut.getKindId(), 3);
        assertEquals(uut.getIdentifier(), 3 + 0x10000);
    }

    @Test
    public void checkIdentifierKindPOI() {
        RvtMetadataIdentifier uut = new RvtMetadataIdentifier(RvtMetadataKind.POI, 332);
        assertEquals(uut.getKind(), RvtMetadataKind.POI);
        assertEquals(uut.getKindId(), 332);
        assertEquals(uut.getIdentifier(), 332 + 0x20000);
        assertTrue(uut.equals(uut));
        assertFalse(uut.equals(null));
        assertFalse(uut.equals("x"));
        assertTrue(uut.equals(new RvtMetadataIdentifier(RvtMetadataKind.POI, 332)));
        assertFalse(uut.equals(new RvtMetadataIdentifier(RvtMetadataKind.AOI, 332)));
        assertFalse(uut.equals(new RvtMetadataIdentifier(RvtMetadataKind.POI, 331)));
    }

    @Test
    public void checkIdentifierKindUserDefined() {
        RvtMetadataIdentifier uut = new RvtMetadataIdentifier(RvtMetadataKind.USER_DEFINED, 8);
        assertEquals(uut.getKind(), RvtMetadataKind.USER_DEFINED);
        assertEquals(uut.getKindId(), 8);
        assertEquals(uut.getIdentifier(), 8 + 0x40000);
    }
}
