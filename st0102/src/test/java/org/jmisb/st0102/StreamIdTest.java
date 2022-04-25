package org.jmisb.st0102;

import static org.testng.Assert.*;

import org.jmisb.st0102.localset.LocalSetFactory;
import org.testng.annotations.Test;

public class StreamIdTest {

    @Test
    public void testConstructFromEncoded() {
        byte[] bytes = new byte[] {(byte) 0x08};
        StreamId uut = new StreamId(bytes);
        assertNotNull(uut);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getStreamIdentifier(), 8);
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getDisplayName(), "Stream Identifier");
    }

    @Test
    public void testConstructByFactory() {
        byte[] bytes = new byte[] {(byte) 0x08};
        ISecurityMetadataValue v = LocalSetFactory.createValue(SecurityMetadataKey.StreamId, bytes);
        assertTrue(v instanceof StreamId);
        StreamId uut = (StreamId) v;
        assertNotNull(uut);
        assertEquals(uut.getBytes(), bytes);
        assertEquals(uut.getStreamIdentifier(), 8);
        assertEquals(uut.getDisplayableValue(), "8");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueTooLong() {
        byte[] bytes2 = new byte[] {(byte) 0x01, (byte) 0x02};
        new StreamId(bytes2);
    }
}
