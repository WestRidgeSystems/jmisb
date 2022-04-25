package org.jmisb.api.klv;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for BerField. */
public class BerFieldTest {

    @Test
    public void checkSetGet() {
        BerField ber = new BerField(1, 4);
        assertEquals(ber.getLength(), 1);
        assertEquals(ber.getValue(), 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNegative() {
        new BerField(1, -2);
    }
}
