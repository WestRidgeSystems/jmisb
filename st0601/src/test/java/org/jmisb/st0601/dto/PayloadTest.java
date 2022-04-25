package org.jmisb.st0601.dto;

import static org.testng.Assert.*;

import org.jmisb.st0601.CountryCodeKey;
import org.testng.annotations.Test;

/** Tests for Payload DTO */
public class PayloadTest {

    @Test
    public void hashTest() {
        Payload payload = makePayload();
        assertEquals(payload.hashCode(), 0x193ceaf2);
        payload.setIdentifier(4);
        assertEquals(payload.hashCode(), 0x193d5935);
    }

    @Test
    public void equalsSameObject() {
        Payload payload = makePayload();
        assertTrue(payload.equals(payload));
    }

    @Test
    public void equalsSameValues() {
        Payload payload1 = makePayload();
        Payload payload2 = makePayload();
        assertTrue(payload1.equals(payload2));
        assertTrue(payload2.equals(payload1));
        assertTrue(payload1 != payload2);
    }

    @Test
    public void equalsNull() {
        Payload payload = makePayload();
        assertFalse(payload.equals(null));
    }

    @Test
    public void equalsDifferentClass() {
        Payload payload = makePayload();
        assertFalse(payload.equals(new String("blah")));
    }

    protected Payload makePayload() {
        Payload payload = new Payload(1, 4, "Test Sensor");
        return payload;
    }

    @Test
    public void equalsDifferentId() {
        Payload payload1 = makePayload();
        Payload payload2 = makePayload();
        assertTrue(payload1.equals(payload2));
        payload2.setIdentifier(5);
        assertFalse(payload1.equals(payload2));
    }

    @Test
    public void equalsDifferentType() {
        Payload payload1 = makePayload();
        Payload payload2 = makePayload();
        assertTrue(payload1.equals(payload2));
        payload2.setType(1);
        assertFalse(payload1.equals(payload2));
    }

    @Test
    public void equalsDifferentName() {
        Payload payload1 = makePayload();
        Payload payload2 = makePayload();
        assertTrue(payload1.equals(payload2));
        payload2.setName("Another Sensor");
        assertFalse(payload1.equals(payload2));
    }

    @Test
    public void equalsOperations() {
        Payload payload1 = makePayload();
        Payload payload2 = makePayload();
        assertEquals(payload1, payload2);
        payload2.setName("Sensor");
        assertNotEquals(payload1, payload2);
    }

    @Test
    public void displayName() {
        Payload payload1 = makePayload();
        assertEquals(payload1.getDisplayName(), "Payload");
    }

    @Test
    public void displayableValue() {
        Payload payload1 = makePayload();
        assertEquals(payload1.getDisplayableValue(), "Payload 1");
    }

    @Test
    public void identifiers() {
        Payload payload1 = makePayload();
        assertEquals(payload1.getIdentifiers().size(), 3);
        assertTrue(payload1.getIdentifiers().contains(PayloadKey.Identifier));
        assertTrue(payload1.getIdentifiers().contains(PayloadKey.PayloadType));
        assertTrue(payload1.getIdentifiers().contains(PayloadKey.PayloadName));
    }

    @Test
    public void fields() {
        Payload payload1 = makePayload();
        assertEquals(payload1.getField(PayloadKey.Identifier).getDisplayableValue(), "1");
        assertEquals(payload1.getField(PayloadKey.PayloadType).getDisplayableValue(), "4");
        assertEquals(
                payload1.getField(PayloadKey.PayloadName).getDisplayableValue(), "Test Sensor");
        assertNull(payload1.getField(PayloadKey.unknown));
    }

    @Test
    public void invalidIdentifier() {
        Payload payload1 = makePayload();
        assertNull(payload1.getField(CountryCodeKey.CodingMethod));
    }
}
