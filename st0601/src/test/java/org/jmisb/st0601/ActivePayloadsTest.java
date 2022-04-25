package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class ActivePayloadsTest {
    private final byte[] ST_EXAMPLE_BYTES = new byte[] {(byte) 0x0b};

    @Test
    public void testConstructFromValue() {
        List<Integer> values = new ArrayList<>();
        values.add(0);
        values.add(1);
        values.add(3);
        ActivePayloads activePayloads = new ActivePayloads(values);
        checkValuesForExample(activePayloads);
    }

    @Test
    public void testConstructFromEncoded() {
        ActivePayloads activePayloads = new ActivePayloads(ST_EXAMPLE_BYTES);
        checkValuesForExample(activePayloads);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ActivePayloads, ST_EXAMPLE_BYTES);
        assertTrue(v instanceof ActivePayloads);
        ActivePayloads activePayloads = (ActivePayloads) v;
        checkValuesForExample(activePayloads);
    }

    private void checkValuesForExample(ActivePayloads activePayloads) {
        assertEquals(activePayloads.getBytes(), ST_EXAMPLE_BYTES);
        assertEquals(activePayloads.getDisplayableValue(), "0,1,3");
        assertTrue(activePayloads.payloadIsActive(0));
        assertTrue(activePayloads.payloadIsActive(1));
        assertFalse(activePayloads.payloadIsActive(2));
        assertTrue(activePayloads.payloadIsActive(3));
        assertFalse(activePayloads.payloadIsActive(4));
        assertFalse(activePayloads.payloadIsActive(7));
        assertFalse(activePayloads.payloadIsActive(8));
        assertFalse(activePayloads.payloadIsActive(200002));
        List<Integer> identifiers = activePayloads.getIdentifiers();
        assertEquals(identifiers.size(), 3);
        assertTrue(identifiers.contains(0));
        assertTrue(identifiers.contains(1));
        assertTrue(identifiers.contains(3));
        assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncodedHighBit() {
        ActivePayloads activePayloads = new ActivePayloads(new byte[] {(byte) 0x80});
        assertEquals(activePayloads.getBytes(), new byte[] {(byte) 0x80});
        assertEquals(activePayloads.getDisplayableValue(), "7");
        assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncoded0() {
        ActivePayloads activePayloads = new ActivePayloads(new byte[] {(byte) 0x00});
        assertEquals(activePayloads.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(activePayloads.getDisplayableValue(), "");
        assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncodedTwoBytes() {
        ActivePayloads activePayloads = new ActivePayloads(new byte[] {(byte) 0x80, (byte) 0x0b});
        assertEquals(activePayloads.getBytes(), new byte[] {(byte) 0x80, (byte) 0x0b});
        assertEquals(activePayloads.getDisplayableValue(), "0,1,3,15");
        assertTrue(activePayloads.payloadIsActive(0));
        assertTrue(activePayloads.payloadIsActive(1));
        assertFalse(activePayloads.payloadIsActive(2));
        assertTrue(activePayloads.payloadIsActive(3));
        assertFalse(activePayloads.payloadIsActive(4));
        assertFalse(activePayloads.payloadIsActive(14));
        assertTrue(activePayloads.payloadIsActive(15));
        assertFalse(activePayloads.payloadIsActive(16));
        List<Integer> identifiers = activePayloads.getIdentifiers();
        assertEquals(identifiers.size(), 4);
        assertTrue(identifiers.contains(0));
        assertTrue(identifiers.contains(1));
        assertTrue(identifiers.contains(3));
        assertTrue(identifiers.contains(15));
        assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testModifiers() {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        ActivePayloads activePayloads = new ActivePayloads(values);
        assertTrue(activePayloads.payloadIsActive(1));
        assertFalse(activePayloads.payloadIsActive(0));
        activePayloads.setPayloadActive(4);
        assertTrue(activePayloads.payloadIsActive(4));
        assertTrue(activePayloads.payloadIsActive(1));
        assertFalse(activePayloads.payloadIsActive(0));
        activePayloads.setPayloadActive(1);
        assertTrue(activePayloads.payloadIsActive(4));
        assertTrue(activePayloads.payloadIsActive(1));
        assertFalse(activePayloads.payloadIsActive(0));
        activePayloads.setPayloadActive(0);
        assertTrue(activePayloads.payloadIsActive(4));
        assertTrue(activePayloads.payloadIsActive(1));
        assertTrue(activePayloads.payloadIsActive(0));
        activePayloads.setPayloadInactive(4);
        assertFalse(activePayloads.payloadIsActive(4));
        assertTrue(activePayloads.payloadIsActive(1));
        assertTrue(activePayloads.payloadIsActive(0));
        activePayloads.setPayloadInactive(1);
        assertFalse(activePayloads.payloadIsActive(4));
        assertFalse(activePayloads.payloadIsActive(1));
        assertTrue(activePayloads.payloadIsActive(0));
    }
}
