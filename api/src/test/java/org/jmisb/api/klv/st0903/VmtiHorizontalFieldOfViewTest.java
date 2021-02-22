package org.jmisb.api.klv.st0903;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for VMTI Horizontal FoV (Tag 11) */
public class VmtiHorizontalFieldOfViewTest {
    @Test
    public void testConstructFromValue() {
        VmtiHorizontalFieldOfView fov = new VmtiHorizontalFieldOfView(12.5);
        assertEquals(fov.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() {
        VmtiHorizontalFieldOfView fov =
                new VmtiHorizontalFieldOfView(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingIMAP() {
        VmtiHorizontalFieldOfView fov =
                new VmtiHorizontalFieldOfView(
                        new byte[] {(byte) 0x06, (byte) 0x40}, EncodingMode.IMAPB);
        assertEquals(fov.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5);
    }

    @Test
    public void testConstructFromEncodedBytesExplicitEncodingLegacy() {
        // ST0903.3 Section 7.10.11
        VmtiHorizontalFieldOfView fov =
                new VmtiHorizontalFieldOfView(
                        new byte[] {(byte) 0x11, (byte) 0xC7}, EncodingMode.LEGACY);
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5, 0.003);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.HorizontalFieldOfView,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof VmtiHorizontalFieldOfView);
        VmtiHorizontalFieldOfView fov = (VmtiHorizontalFieldOfView) value;
        assertEquals(fov.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5);
    }

    @Test
    public void testFactoryExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.HorizontalFieldOfView,
                        new byte[] {(byte) 0x06, (byte) 0x40},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof VmtiHorizontalFieldOfView);
        VmtiHorizontalFieldOfView fov = (VmtiHorizontalFieldOfView) value;
        assertEquals(fov.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5);
    }

    @Test
    public void testFactoryExplicitEncodingLegacy() throws KlvParseException {
        // ST0903.3 Section 7.10.11
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.HorizontalFieldOfView,
                        new byte[] {(byte) 0x11, (byte) 0xC7},
                        EncodingMode.LEGACY);
        assertTrue(value instanceof VmtiHorizontalFieldOfView);
        VmtiHorizontalFieldOfView fov = (VmtiHorizontalFieldOfView) value;
        assertEquals(fov.getDisplayName(), "Horizontal Field of View");
        assertEquals(fov.getDisplayableValue(), "12.5\u00B0");
        assertEquals(fov.getFieldOfView(), 12.5, 0.003);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new VmtiHorizontalFieldOfView(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new VmtiHorizontalFieldOfView(180.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new VmtiHorizontalFieldOfView(new byte[] {0x01, 0x02, 0x03}, EncodingMode.IMAPB);
    }
}
