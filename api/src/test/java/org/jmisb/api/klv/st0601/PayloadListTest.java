package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.dto.Payload;
import org.jmisb.api.klv.st0601.dto.PayloadKey;
import org.testng.annotations.Test;

public class PayloadListTest {
    private final byte[] st_example_bytes =
            new byte[] {
                (byte) 0x03, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte) 0x0F, (byte) 0x56,
                        (byte) 0x49, (byte) 0x53, (byte) 0x20, (byte) 0x4E, (byte) 0x6F,
                        (byte) 0x73, (byte) 0x65, (byte) 0x20, (byte) 0x43, (byte) 0x61,
                (byte) 0x6D, (byte) 0x65, (byte) 0x72, (byte) 0x61, (byte) 0x15, (byte) 0x01,
                        (byte) 0x00, (byte) 0x12, (byte) 0x41, (byte) 0x43, (byte) 0x4D,
                        (byte) 0x45, (byte) 0x20, (byte) 0x56, (byte) 0x49, (byte) 0x53,
                (byte) 0x20, (byte) 0x4D, (byte) 0x6F, (byte) 0x64, (byte) 0x65, (byte) 0x6C,
                        (byte) 0x20, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x14,
                        (byte) 0x02, (byte) 0x00, (byte) 0x11, (byte) 0x41, (byte) 0x43,
                (byte) 0x4D, (byte) 0x45, (byte) 0x20, (byte) 0x49, (byte) 0x52, (byte) 0x20,
                        (byte) 0x4D, (byte) 0x6F, (byte) 0x64, (byte) 0x65, (byte) 0x6C,
                        (byte) 0x20, (byte) 0x34, (byte) 0x35, (byte) 0x36
            };

    @Test
    public void testConstructFromValue() {
        List<Payload> payloads = new ArrayList<>();
        Payload payload0 = new Payload(0, 0, "VIS Nose Camera");
        payloads.add(payload0);
        Payload payload1 = new Payload(1, 0, "ACME VIS Model 123");
        payloads.add(payload1);
        Payload payload2 = new Payload(2, 0, "ACME IR Model 456");
        payloads.add(payload2);
        PayloadList list = new PayloadList(payloads);
        checkPayloadList(list);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        PayloadList list = new PayloadList(st_example_bytes);
        checkPayloadList(list);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PayloadList, st_example_bytes);
        assertTrue(v instanceof PayloadList);
        PayloadList list = (PayloadList) v;
        checkPayloadList(list);
    }

    private void checkPayloadList(PayloadList list) {
        List<Payload> payloads = list.getPayloadList();
        Payload payload0 = payloads.get(0);
        assertEquals(payload0.getIdentifier(), 0);
        assertEquals(payload0.getType(), 0);
        assertEquals(payload0.getName(), "VIS Nose Camera");
        Payload payload1 = payloads.get(1);
        assertEquals(payload1.getIdentifier(), 1);
        assertEquals(payload1.getType(), 0);
        assertEquals(payload1.getName(), "ACME VIS Model 123");
        Payload payload2 = payloads.get(2);
        assertEquals(payload2.getIdentifier(), 2);
        assertEquals(payload2.getType(), 0);
        assertEquals(payload2.getName(), "ACME IR Model 456");
        assertEquals(payloads.size(), 3);
        assertEquals(list.getBytes(), st_example_bytes);
        assertEquals(list.getDisplayableValue(), "[Payloads]");
        assertEquals(list.getDisplayName(), "Payload List");
        assertEquals(list.getIdentifiers().size(), 3);
        assertTrue(list.getIdentifiers().contains(new PayloadIdentifierKey(0)));
        assertTrue(list.getIdentifiers().contains(new PayloadIdentifierKey(1)));
        assertTrue(list.getIdentifiers().contains(new PayloadIdentifierKey(2)));
        assertEquals(list.getField(new PayloadIdentifierKey(0)).getDisplayableValue(), "Payload 0");
        assertEquals(list.getField(new PayloadIdentifierKey(1)).getDisplayableValue(), "Payload 1");
        assertEquals(list.getField(new PayloadIdentifierKey(2)).getDisplayableValue(), "Payload 2");
        assertNull(list.getField(new PayloadIdentifierKey(3)));
        assertEquals(
                ((Payload) list.getField(new PayloadIdentifierKey(1)))
                        .getField(PayloadKey.PayloadName)
                        .getDisplayableValue(),
                "ACME VIS Model 123");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        new PayloadList(new byte[] {0x6f, 0x4f, 0x5c, 0x05, 0x65, 0x32, 0x2e, (byte) 0xf4});
    }
}
