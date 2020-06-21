package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for Target Priority (Tag 4) */
public class TargetPriorityTest {

    public TargetPriorityTest() {}

    @Test
    public void testConstructFromValue() {
        TargetPriority priority = new TargetPriority((short) 27);
        assertEquals(priority.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(priority.getDisplayName(), "Target Priority");
        assertEquals(priority.getDisplayableValue(), "27");
        assertEquals(priority.getTargetPriority(), 27);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TargetPriority priority = new TargetPriority(new byte[] {(byte) 0x1B});
        assertEquals(priority.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(priority.getDisplayName(), "Target Priority");
        assertEquals(priority.getDisplayableValue(), "27");
        assertEquals(priority.getTargetPriority(), 27);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetPriority, new byte[] {(byte) 0x1B});
        assertTrue(value instanceof TargetPriority);
        TargetPriority priority = (TargetPriority) value;
        assertEquals(priority.getBytes(), new byte[] {(byte) 0x1B});
        assertEquals(priority.getDisplayName(), "Target Priority");
        assertEquals(priority.getDisplayableValue(), "27");
        assertEquals(priority.getTargetPriority(), 27);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetPriority((short) 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetPriority((short) 256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetPriority(new byte[] {0x01, 0x02});
    }
}
