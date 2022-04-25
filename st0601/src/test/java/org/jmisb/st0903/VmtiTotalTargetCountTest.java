package org.jmisb.st0903;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for totalNumTargetsDetected (ST0903 Tag 5). */
public class VmtiTotalTargetCountTest {
    @Test
    public void testConstructFromValue() {
        VmtiTotalTargetCount count = new VmtiTotalTargetCount(28);
        assertEquals(count.getBytes(), new byte[] {(byte) 0x1C});
        assertEquals(count.getDisplayName(), "Targets In Frame");
        assertEquals(count.getDisplayableValue(), "28");
        assertEquals(count.getValue(), 28);
    }

    @Test
    public void testConstructFromValueMin() {
        VmtiTotalTargetCount count = new VmtiTotalTargetCount(0);
        assertEquals(count.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(count.getDisplayName(), "Targets In Frame");
        assertEquals(count.getDisplayableValue(), "0");
        assertEquals(count.getValue(), 0);
    }

    @Test
    public void testConstructFromValueMax() {
        VmtiTotalTargetCount count = new VmtiTotalTargetCount(16777215);
        assertEquals(count.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        assertEquals(count.getDisplayName(), "Targets In Frame");
        assertEquals(count.getDisplayableValue(), "16777215");
        assertEquals(count.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        VmtiTotalTargetCount count = new VmtiTotalTargetCount(new byte[] {(byte) 0x1C});
        assertEquals(count.getBytes(), new byte[] {(byte) 0x1C});
        assertEquals(count.getDisplayName(), "Targets In Frame");
        assertEquals(count.getDisplayableValue(), "28");
        assertEquals(count.getValue(), 28);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.TotalTargetsInFrame,
                        new byte[] {(byte) 0x1C},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof VmtiTotalTargetCount);
        VmtiTotalTargetCount count = (VmtiTotalTargetCount) value;
        assertEquals(count.getBytes(), new byte[] {(byte) 0x1C});
        assertEquals(count.getDisplayName(), "Targets In Frame");
        assertEquals(count.getDisplayableValue(), "28");
        assertEquals(count.getValue(), 28);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new VmtiTotalTargetCount(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new VmtiTotalTargetCount(16777216);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new VmtiTotalTargetCount(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}
