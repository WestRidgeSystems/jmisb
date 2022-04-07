package org.jmisb.st0102;

import static org.testng.Assert.*;

import org.jmisb.st0102.localset.LocalSetFactory;
import org.testng.annotations.Test;

public class TransportStreamIdTest {

    @Test
    public void testConstructFromEncoded() {
        byte[] bytes = new byte[] {(byte) 0x03, (byte) 0x4f};
        TransportStreamId uut = new TransportStreamId(bytes);
        assertNotNull(uut);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getTransportStreamIdentifier(), 847);
        assertEquals(uut.getDisplayableValue(), "847");
        assertEquals(uut.getDisplayName(), "Transport Stream Identifier");
    }

    @Test
    public void testConstructByFactory() {
        byte[] bytes = new byte[] {(byte) 0x03, (byte) 0x4f};
        ISecurityMetadataValue v =
                LocalSetFactory.createValue(SecurityMetadataKey.TransportStreamId, bytes);
        assertTrue(v instanceof TransportStreamId);
        TransportStreamId uut = (TransportStreamId) v;
        assertNotNull(uut);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getTransportStreamIdentifier(), 847);
        assertEquals(uut.getDisplayableValue(), "847");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueTooLong() {
        byte[] bytes3 = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x03};
        new TransportStreamId(bytes3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueTooShort() {
        byte[] bytes1 = new byte[] {(byte) 0x01};
        new TransportStreamId(bytes1);
    }
}
