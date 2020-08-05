package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.*;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Target Intensity (ST0903 VTarget Tag 9). */
public class TargetIntensityTest {
    @Test
    public void testConstructFromValue() {
        TargetIntensity intensity = new TargetIntensity(13140);
        assertEquals(intensity.getBytes(), new byte[] {(byte) 0x33, (byte) 0x54});
        assertEquals(intensity.getDisplayName(), "Target Intensity");
        assertEquals(intensity.getDisplayableValue(), "13140");
        assertEquals(intensity.getValue(), 13140);
    }

    @Test
    public void testConstructFromValueMin() {
        TargetIntensity intensity = new TargetIntensity(0);
        assertEquals(intensity.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(intensity.getDisplayName(), "Target Intensity");
        assertEquals(intensity.getDisplayableValue(), "0");
        assertEquals(intensity.getValue(), 0);
    }

    @Test
    public void testConstructFromValueMax() {
        TargetIntensity intensity = new TargetIntensity(16777215);
        assertEquals(intensity.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        assertEquals(intensity.getDisplayName(), "Target Intensity");
        assertEquals(intensity.getDisplayableValue(), "16777215");
        assertEquals(intensity.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetIntensity intensity = new TargetIntensity(new byte[] {(byte) 0x33, (byte) 0x54});
        assertEquals(intensity.getBytes(), new byte[] {(byte) 0x33, (byte) 0x54});
        assertEquals(intensity.getDisplayName(), "Target Intensity");
        assertEquals(intensity.getDisplayableValue(), "13140");
        assertEquals(intensity.getValue(), 13140);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetIntensity,
                        new byte[] {(byte) 0x33, (byte) 0x54},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetIntensity);
        TargetIntensity intensity = (TargetIntensity) value;
        assertEquals(intensity.getBytes(), new byte[] {(byte) 0x33, (byte) 0x54});
        assertEquals(intensity.getDisplayName(), "Target Intensity");
        assertEquals(intensity.getDisplayableValue(), "13140");
        assertEquals(intensity.getValue(), 13140);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetIntensity(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetIntensity(16777216);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetIntensity(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}
