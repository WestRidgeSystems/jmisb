package org.jmisb.api.klv.st0601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for FlagDataKey enumeration. */
public class FlagDataKeyTest {

    @Test
    public void checkEnumeration() {
        assertEquals(FlagDataKey.LaserRange.getTag(), 0);

        assertEquals(FlagDataKey.ImageInvalid.getTag(), 5);

        assertEquals(FlagDataKey.IR_Polarity.getTag(), 2);
    }
}
