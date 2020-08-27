package org.jmisb.api.klv.st0601.dto;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for PayloadKey. */
public class PayloadKeyTest {

    public PayloadKeyTest() {}

    @Test
    public void checkUnknown() {
        PayloadKey key = PayloadKey.unknown;
        assertEquals(key.getIdentifier(), 0);
    }

    @Test
    public void checkIdentifier() {
        PayloadKey key = PayloadKey.Identifier;
        assertEquals(key.getIdentifier(), 1);
    }

    @Test
    public void checkName() {
        PayloadKey key = PayloadKey.PayloadName;
        assertEquals(key.getIdentifier(), 3);
    }

    @Test
    public void checkType() {
        PayloadKey key = PayloadKey.PayloadType;
        assertEquals(key.getIdentifier(), 2);
    }
}
