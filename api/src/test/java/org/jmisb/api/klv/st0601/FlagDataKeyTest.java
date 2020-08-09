package org.jmisb.api.klv.st0601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for FlagDataKey enumeration. */
public class FlagDataKeyTest {

    @Test
    public void checkEnumeration() {
        assertEquals(FlagDataKey.LaserRange.getIdentifier(), 0);

        assertEquals(FlagDataKey.ImageInvalid.getIdentifier(), 5);

        assertEquals(FlagDataKey.IR_Polarity.getIdentifier(), 2);
    }
}
