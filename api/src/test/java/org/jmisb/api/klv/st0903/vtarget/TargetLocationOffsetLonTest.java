package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Target Location Offset Longitude (Tag 11) */
public class TargetLocationOffsetLonTest {

    public TargetLocationOffsetLonTest() {}

    @Test
    public void testConstructFromValue() {
        TargetLocationOffsetLon offset = new TargetLocationOffsetLon(10.0);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingIMAP() {
        TargetLocationOffsetLon offset =
                new TargetLocationOffsetLon(
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67}, EncodingMode.IMAPB);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingLegacy() {
        TargetLocationOffsetLon offset =
                new TargetLocationOffsetLon(
                        new byte[] {(byte) 0x42, (byte) 0xAA, (byte) 0xAA}, EncodingMode.LEGACY);
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x66});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0, 0.000003);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocationOffsetLon,
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertTrue(value instanceof TargetLocationOffsetLon);
        TargetLocationOffsetLon offset = (TargetLocationOffsetLon) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactoryExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocationOffsetLon,
                        new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetLocationOffsetLon);
        TargetLocationOffsetLon offset = (TargetLocationOffsetLon) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x67});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0);
    }

    @Test
    public void testFactoryExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocationOffsetLon,
                        new byte[] {(byte) 0x42, (byte) 0xAA, (byte) 0xAA},
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetLocationOffsetLon);
        TargetLocationOffsetLon offset = (TargetLocationOffsetLon) value;
        assertEquals(offset.getBytes(), new byte[] {(byte) 0x3A, (byte) 0x66, (byte) 0x66});
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "10.00000\u00B0");
        assertEquals(offset.getValue(), 10.0, 0.000003);
    }

    @Test
    public void testFactoryExplicitEncodingLegacyErrorIndicator() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocationOffsetLon,
                        new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00},
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetLocationOffsetLon);
        TargetLocationOffsetLon offset = (TargetLocationOffsetLon) value;
        assertEquals(offset.getDisplayName(), "Target Location Offset Longitude");
        assertEquals(offset.getDisplayableValue(), "NaN\u00B0");
        assertEquals(offset.getValue(), Double.NaN);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetLocationOffsetLon(-19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetLocationOffsetLon(19.200001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetLocationOffsetLon(new byte[] {0x01, 0x02, 0x03, 0x04}, EncodingMode.IMAPB);
    }
}
