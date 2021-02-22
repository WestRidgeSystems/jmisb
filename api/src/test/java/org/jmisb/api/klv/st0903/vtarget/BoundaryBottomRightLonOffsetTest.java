package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Boundary Bottom Right Longitude Offset (Tag 16) */
public class BoundaryBottomRightLonOffsetTest {

    public BoundaryBottomRightLonOffsetTest() {}

    @Test
    public void testConstructFromValue() {
        BoundaryBottomRightLonOffset offset = new BoundaryBottomRightLonOffset(10.0);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() {
        BoundaryBottomRightLonOffset offset =
                new BoundaryBottomRightLonOffset(
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingIMAP() {
        BoundaryBottomRightLonOffset offset =
                new BoundaryBottomRightLonOffset(
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67}, EncodingMode.IMAPB);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingLegacy() {
        // ST0903.3 Section 7.11.17
        BoundaryBottomRightLonOffset offset =
                new BoundaryBottomRightLonOffset(
                        new byte[] {(byte) 0x42, (byte) 0xAA, (byte) 0xAA}, EncodingMode.LEGACY);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x66});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0, 0.003);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.BoundaryBottomRightLonOffset,
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertTrue(value instanceof BoundaryBottomRightLonOffset);
        BoundaryBottomRightLonOffset offset = (BoundaryBottomRightLonOffset) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactoryExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.BoundaryBottomRightLonOffset,
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof BoundaryBottomRightLonOffset);
        BoundaryBottomRightLonOffset offset = (BoundaryBottomRightLonOffset) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactoryExplicitEncodingLegacy() throws KlvParseException {
        // ST0903.3 Section 7.11.17
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.BoundaryBottomRightLonOffset,
                        new byte[] {(byte) 0x42, (byte) 0xAA, (byte) 0xAA},
                        EncodingMode.LEGACY);
        assertTrue(value instanceof BoundaryBottomRightLonOffset);
        BoundaryBottomRightLonOffset offset = (BoundaryBottomRightLonOffset) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x66});
        assertEquals(offset.getDisplayName(), "Boundary Bottom Right Lon Offset");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0, 0.003);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new BoundaryBottomRightLonOffset(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new BoundaryBottomRightLonOffset(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new BoundaryBottomRightLonOffset(new byte[] {0x01, 0x02, 0x03, 0x04}, EncodingMode.IMAPB);
    }
}
