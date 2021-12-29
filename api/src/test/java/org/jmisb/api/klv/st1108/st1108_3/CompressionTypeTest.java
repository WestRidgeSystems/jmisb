package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for CompressionType. */
public class CompressionTypeTest {

    public CompressionTypeTest() {}

    @Test
    public void uncompressed() {
        CompressionType uut = CompressionType.Uncompressed;
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "Uncompressed");
    }

    @Test
    public void h262() {
        CompressionType uut = CompressionType.H262;
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "H.262 (MPEG-2)");
    }

    @Test
    public void h264() {
        CompressionType uut = CompressionType.H264;
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "H.264 (AVC)");
    }

    @Test
    public void h265() {
        CompressionType uut = CompressionType.H265;
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "H.265 (HEVC)");
    }

    @Test
    public void jpeg2000() throws KlvParseException {
        CompressionType uut = CompressionType.fromBytes(new byte[] {0x04});
        assertEquals(uut, CompressionType.JPEG2000);
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "JPEG2000");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badLength() throws KlvParseException {
        CompressionType.fromBytes(new byte[] {0x00, 0x01});
    }

    @Test
    public void undefined() throws KlvParseException {
        CompressionType uut = CompressionType.fromBytes(new byte[] {0x7F});
        assertEquals(uut, CompressionType.Undefined);
        assertEquals(uut.getDisplayName(), "Compression Type");
        assertEquals(uut.getDisplayableValue(), "Unknown or Reserved");
    }
}
