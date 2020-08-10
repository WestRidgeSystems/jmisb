package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for TargetIdentifierKey. */
public class TargetIdentifierKeyTest {

    public TargetIdentifierKeyTest() {}

    @Test
    public void checkDisplayName() {
        TargetIdentifierKey targetIdentifierKey = new TargetIdentifierKey(2);
        assertEquals(targetIdentifierKey.getIdentifier(), 2);
    }

    @Test
    public void checkToString() {
        TargetIdentifierKey targetIdentifierKey = new TargetIdentifierKey(2);
        assertEquals(targetIdentifierKey.toString(), "Target 2");
    }

    @Test
    public void checkHash() {
        TargetIdentifierKey targetIdentifierKey = new TargetIdentifierKey(2);
        assertEquals(targetIdentifierKey.hashCode(), 2);
    }

    @Test
    public void checkEquals() {
        TargetIdentifierKey targetIdentifierKey = new TargetIdentifierKey(2);
        assertFalse(targetIdentifierKey.equals(null));
        assertFalse(targetIdentifierKey.equals(new TargetIdentifierKey(3)));
        assertFalse(targetIdentifierKey.equals(new Integer(2)));
        assertTrue(targetIdentifierKey.equals(targetIdentifierKey));
    }
}
