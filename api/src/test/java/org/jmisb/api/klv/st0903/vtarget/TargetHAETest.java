package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
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
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() {
        TargetHAE hae = new TargetHAE(new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingIMAP() {
        TargetHAE hae = new TargetHAE(new byte[] {(byte) 0x2a, (byte) 0x94}, EncodingMode.IMAPB);
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingLegacy() {
        // ST0903.3 Section 7.11.13
        TargetHAE hae = new TargetHAE(new byte[] {(byte) 0x8C, (byte) 0x38}, EncodingMode.LEGACY);
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x93});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000, 0.3);
    }

    @Test
    @SuppressWarnings("deprecation")
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

    @Test
    public void testFactoryExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHAE,
                        new byte[] {(byte) 0x2a, (byte) 0x94},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetHAE);
        TargetHAE hae = (TargetHAE) value;
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x94});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000);
    }

    @Test
    public void testFactoryExplicitEncodingLegacy() throws KlvParseException {
        // ST0903.3 Section 7.11.13
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHAE,
                        new byte[] {(byte) 0x8C, (byte) 0x38},
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetHAE);
        TargetHAE hae = (TargetHAE) value;
        assertEquals(hae.getBytes(), new byte[] {(byte) 0x2a, (byte) 0x93});
        assertEquals(hae.getDisplayName(), "Target HAE");
        assertEquals(hae.getDisplayableValue(), "10000.0");
        assertEquals(hae.getHAE(), 10000, 0.3);
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
        new TargetHAE(new byte[] {0x01, 0x02, 0x03}, EncodingMode.IMAPB);
    }
}
