package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.annotations.Test;

/** Tests for Target History (Tag 6) */
public class TargetHistoryTest {

    public TargetHistoryTest() {}

    @Test
    public void testConstructFromValue() {
        TargetHistory history = new TargetHistory(2765);
        assertEquals(history.getBytes(), new byte[] {(byte) 0x0A, (byte) 0xCD});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "2765");
        assertEquals(history.getTargetHistory(), 2765);
    }

    @Test
    public void testConstructFromValue0() {
        TargetHistory history = new TargetHistory(0);
        assertEquals(history.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "0");
        assertEquals(history.getTargetHistory(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetHistory history = new TargetHistory(new byte[] {(byte) 0x0A, (byte) 0xCD});
        assertEquals(history.getBytes(), new byte[] {(byte) 0x0A, (byte) 0xCD});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "2765");
        assertEquals(history.getTargetHistory(), 2765);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHistory,
                        new byte[] {(byte) 0x0A, (byte) 0xCD},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetHistory);
        TargetHistory history = (TargetHistory) value;
        assertEquals(history.getBytes(), new byte[] {(byte) 0x0A, (byte) 0xCD});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "2765");
        assertEquals(history.getTargetHistory(), 2765);
    }

    @Test
    public void testFactory0() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHistory,
                        new byte[] {(byte) 0x00},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetHistory);
        TargetHistory history = (TargetHistory) value;
        assertEquals(history.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "0");
        assertEquals(history.getTargetHistory(), 0);
    }

    @Test
    public void testFactoryMax() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetHistory,
                        new byte[] {(byte) 0xFF, (byte) 0xFF},
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetHistory);
        TargetHistory history = (TargetHistory) value;
        assertEquals(history.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(history.getDisplayName(), "Target History");
        assertEquals(history.getDisplayableValue(), "65535");
        assertEquals(history.getTargetHistory(), 65535);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetHistory(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetHistory(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetHistory(new byte[] {0x01, 0x02, 0x03});
    }
}
