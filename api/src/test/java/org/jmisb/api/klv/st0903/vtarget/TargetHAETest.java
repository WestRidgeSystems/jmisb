package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for Target HAE (Tag 12) */
public class TargetHAETest {
    @Test
    public void testConstructFromValue() {
        TargetHAE hae = new TargetHAE(10000);
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetHAE hae = new TargetHAE(new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHAE, new byte[] {(byte) 0x2a, (byte) 0x94});
        assertTrue(value instanceof TargetHAE);
        TargetHAE hae = (TargetHAE) value;
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetHAE(-900.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetHAE(19000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetHAE(new byte[] {0x01, 0x02, 0x03});
    }
}
