package org.jmisb.st0806.userdefined;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class RvtNumericIdTest {
    @Test
    public void testConstructFromValue() {
        RvtNumericId number = new RvtNumericId(0, 1);
        assertEquals(number.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(number.getDataType(), 0);
        assertEquals(number.getId(), 1);
        assertEquals(number.getDisplayName(), "Numeric ID");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IRvtUserDefinedMetadataValue v =
                RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.NumericId, bytes);
        assertTrue(v instanceof RvtNumericId);
        RvtNumericId number = (RvtNumericId) v;
        assertEquals(number.getDataType(), 0);
        assertEquals(number.getId(), 0);
        assertEquals(number.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(number.getDisplayableValue(), "String: 0");

        bytes = new byte[] {(byte) 0xFF};
        v = RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.NumericId, bytes);
        assertTrue(v instanceof RvtNumericId);
        number = (RvtNumericId) v;
        assertEquals(number.getDataType(), 3);
        assertEquals(number.getId(), 63);
        assertEquals(number.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(number.getDisplayableValue(), "Experimental: 63");
        bytes = new byte[] {(byte) 0x5F};
        v = RvtUserDefinedLocalSet.createValue(RvtUserDefinedMetadataKey.NumericId, bytes);
        assertTrue(v instanceof RvtNumericId);
        number = (RvtNumericId) v;
        assertEquals(number.getDataType(), 1);
        assertEquals(number.getId(), 31);
        assertEquals(number.getBytes(), new byte[] {(byte) 0x5F});
        assertEquals(number.getDisplayableValue(), "Signed Integer: 31");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDataTypeTooSmall() {
        new RvtNumericId(-1, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDataTypeTooBig() {
        new RvtNumericId(4, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIdTooSmall() {
        new RvtNumericId(0, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIdTooBig() {
        new RvtNumericId(0, 64);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RvtNumericId(new byte[] {0x01, 0x02});
    }

    private class DubiousClassJustForTestCoverage extends RvtNumericId {
        DubiousClassJustForTestCoverage() {
            super(0, 0);
            this.number = 0x102;
        }
    };

    @Test
    public void forcedDataTypeTestForCoverage() {
        RvtNumericId id = new DubiousClassJustForTestCoverage();
        assertEquals(id.getDisplayableValue(), "Unknown: 2");
    }
}
