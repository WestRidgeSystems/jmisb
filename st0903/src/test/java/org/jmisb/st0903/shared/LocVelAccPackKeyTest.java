package org.jmisb.st0903.shared;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for LocVelAccPackKey. */
public class LocVelAccPackKeyTest {

    public LocVelAccPackKeyTest() {}

    @Test
    public void checkUnknown() {
        LocVelAccPackKey key = LocVelAccPackKey.unknown;
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void checkEast() {
        LocVelAccPackKey key = LocVelAccPackKey.east;
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void checkRhoNorthUp() {
        LocVelAccPackKey key = LocVelAccPackKey.rhoNorthUp;
        assertEquals(key.getIdentifier(), 9);
    }
}
