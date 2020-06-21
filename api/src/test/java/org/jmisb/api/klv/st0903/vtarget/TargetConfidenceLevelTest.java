package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for Target Confidence Level (Tag 5) */
public class TargetConfidenceLevelTest {

    public TargetConfidenceLevelTest() {}

    @Test
    public void testConstructFromValue() {
        TargetConfidenceLevel confidence = new TargetConfidenceLevel((short) 80);
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x50});
        assertEquals(confidence.getDisplayName(), "Target Confidence");
        assertEquals(confidence.getDisplayableValue(), "80%");
        assertEquals(confidence.getConfidence(), 80);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetConfidenceLevel confidence = new TargetConfidenceLevel(new byte[] {(byte) 0x50});
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x50});
        assertEquals(confidence.getDisplayName(), "Target Confidence");
        assertEquals(confidence.getDisplayableValue(), "80%");
        assertEquals(confidence.getConfidence(), 80);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetConfidenceLevel, new byte[] {(byte) 0x50});
        assertTrue(value instanceof TargetConfidenceLevel);
        TargetConfidenceLevel confidence = (TargetConfidenceLevel) value;
        assertEquals(confidence.getBytes(), new byte[] {(byte) 0x50});
        assertEquals(confidence.getDisplayName(), "Target Confidence");
        assertEquals(confidence.getDisplayableValue(), "80%");
        assertEquals(confidence.getConfidence(), 80);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetConfidenceLevel((short) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetConfidenceLevel((short) 101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetConfidenceLevel(new byte[] {0x01, 0x02});
    }
}
