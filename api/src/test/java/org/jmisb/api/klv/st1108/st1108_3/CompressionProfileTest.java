package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for CompressionProfile. */
public class CompressionProfileTest {

    public CompressionProfileTest() {}

    @Test
    public void uncompressed() {
        CompressionProfile uut = CompressionProfile.Uncompressed;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "N/A (uncompressed)");
    }

    @Test
    public void uncompressedFromBytes() throws KlvParseException {
        CompressionProfile uut = CompressionProfile.fromBytes(new byte[] {0x00});
        assertEquals(uut, CompressionProfile.Uncompressed);
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "N/A (uncompressed)");
    }

    @Test
    public void main_h264() {
        CompressionProfile uut = CompressionProfile.MainH264;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Main (H.264)");
    }

    @Test
    public void main_h264FromBytes() throws KlvParseException {
        CompressionProfile uut = CompressionProfile.fromBytes(new byte[] {0x01});
        assertEquals(uut, CompressionProfile.MainH264);
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Main (H.264)");
    }

    @Test
    public void main10_h265() {
        CompressionProfile uut = CompressionProfile.Main10H265;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Main 10 (H.265)");
    }

    @Test
    public void constrainedBaseline() {
        CompressionProfile uut = CompressionProfile.ConstrainedBaselineH264;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Constrained Baseline (H.264)");
    }

    @Test
    public void high_h264() {
        CompressionProfile uut = CompressionProfile.HighH264;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "High (H.264)");
    }

    @Test
    public void main422() {
        CompressionProfile uut = CompressionProfile.Main_4_2_2_12;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Main 4:2:2 12 (H.265)");
    }

    @Test
    public void main444() {
        CompressionProfile uut = CompressionProfile.Main_4_4_4_12;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Main 4:4:4 12 (H.265)");
    }

    @Test
    public void high422() {
        CompressionProfile uut = CompressionProfile.High_4_2_2;
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "High 4:2:2 (H.264)");
    }

    @Test
    public void highPredictive() throws KlvParseException {
        CompressionProfile uut = CompressionProfile.fromBytes(new byte[] {0x08});
        assertEquals(uut, CompressionProfile.High_4_4_4_Predictive_H264);
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "High 4:4:4 Predictive (H.264)");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badLength() throws KlvParseException {
        CompressionProfile.fromBytes(new byte[] {0x00, 0x01});
    }

    @Test
    public void undefined() throws KlvParseException {
        CompressionProfile uut = CompressionProfile.fromBytes(new byte[] {0x7F});
        assertEquals(uut, CompressionProfile.Undefined);
        assertEquals(uut.getDisplayName(), "Compression Profile");
        assertEquals(uut.getDisplayableValue(), "Unknown or Reserved");
    }
}
