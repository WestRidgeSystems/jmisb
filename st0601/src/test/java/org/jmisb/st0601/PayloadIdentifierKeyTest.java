package org.jmisb.st0601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for PayloadIdentifierKey. */
public class PayloadIdentifierKeyTest {

    public PayloadIdentifierKeyTest() {}

    @Test
    public void checkDisplayName() {
        PayloadIdentifierKey payloadIdentifierKey = new PayloadIdentifierKey(2);
        assertEquals(payloadIdentifierKey.getIdentifier(), 2);
    }

    @Test
    public void checkToString() {
        PayloadIdentifierKey payloadIdentifierKey = new PayloadIdentifierKey(2);
        assertEquals(payloadIdentifierKey.toString(), "Payload 2");
    }

    @Test
    public void checkHash() {
        PayloadIdentifierKey payloadIdentifierKey = new PayloadIdentifierKey(2);
        assertEquals(payloadIdentifierKey.hashCode(), 2);
    }

    @Test
    public void checkCompareTo() {
        PayloadIdentifierKey payloadIdentifierKey = new PayloadIdentifierKey(2);
        PayloadIdentifierKey payloadIdentifierKeyHigher = new PayloadIdentifierKey(4);
        assertTrue(payloadIdentifierKey.compareTo(payloadIdentifierKeyHigher) < 0);
    }

    @Test
    public void checkEquals() {
        PayloadIdentifierKey payloadIdentifierKey = new PayloadIdentifierKey(2);
        assertFalse(payloadIdentifierKey.equals(null));
        assertFalse(payloadIdentifierKey.equals(new PayloadIdentifierKey(3)));
        assertFalse(payloadIdentifierKey.equals(2));
        assertTrue(payloadIdentifierKey.equals(payloadIdentifierKey));
    }
}
