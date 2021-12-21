package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for SDCCValueIdentifierKey. */
public class SDCCValueIdentifierKeyTest {

    @Test
    public void checkIdentifier() {
        SDCCValueIdentifierKey uut = new SDCCValueIdentifierKey(3, 1, 4);
        assertEquals(uut.getIdentifier(), 13);
    }

    @Test
    public void checkRowAndColumn() {
        SDCCValueIdentifierKey uut = new SDCCValueIdentifierKey(3, 1, 4);
        assertEquals(uut.getRow(), 3);
        assertEquals(uut.getColumn(), 1);
    }

    @Test
    public void checkHash() {
        SDCCValueIdentifierKey uut = new SDCCValueIdentifierKey(3, 1, 4);
        assertEquals(uut.hashCode(), 22647);
    }

    @Test
    public void checkEquals() {
        SDCCValueIdentifierKey uut = new SDCCValueIdentifierKey(3, 1, 4);
        assertFalse(uut.equals(null));
        assertTrue(uut.equals(new SDCCValueIdentifierKey(3, 1, 4)));
        assertFalse(uut.equals(new SDCCValueIdentifierKey(2, 1, 4)));
        assertFalse(uut.equals(new SDCCValueIdentifierKey(1, 3, 4)));
        assertFalse(uut.equals(new SDCCValueIdentifierKey(3, 3, 4)));
        assertFalse(uut.equals(3));
        assertTrue(uut.equals(uut));
    }

    @Test
    public void checkComparison() {
        SDCCValueIdentifierKey uut1 = new SDCCValueIdentifierKey(1, 3, 4);
        SDCCValueIdentifierKey uut2 = new SDCCValueIdentifierKey(1, 2, 4);
        assertEquals(uut1.compareTo(uut2), 1);
        assertEquals(uut2.compareTo(uut1), -1);
        assertEquals(uut1.compareTo(uut1), 0);
    }
}
