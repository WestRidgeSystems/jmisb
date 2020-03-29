package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for FPA Index (Tag 21)
 */
public class FpaIndexTest {

    public FpaIndexTest() {
    }

    @Test
    public void testConstructFromValue()
    {
        FpaIndex fpaIndex = new FpaIndex(new FpaIndexPack((short)2,(short)3));
        assertEquals(fpaIndex.getBytes(), new byte[]{(byte)0x02, (byte)0x03});
        assertEquals(fpaIndex.getDisplayName(), "FPA Index");
        assertEquals(fpaIndex.getDisplayableValue(), "Row 2, Col 3");
        assertEquals(fpaIndex.getFpaIndexPack().getFpaRow(), (short)2);
        assertEquals(fpaIndex.getFpaIndexPack().getFpaColumn(), (short)3);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        FpaIndex fpaIndex = new FpaIndex(new byte[]{(byte)0x02, (byte)0x03});
        assertEquals(fpaIndex.getBytes(), new byte[]{(byte)0x02, (byte)0x03});
        assertEquals(fpaIndex.getDisplayName(), "FPA Index");
        assertEquals(fpaIndex.getDisplayableValue(), "Row 2, Col 3");
        assertEquals(fpaIndex.getFpaIndexPack().getFpaRow(), (short)2);
        assertEquals(fpaIndex.getFpaIndexPack().getFpaColumn(), (short)3);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.FPAIndex, new byte[]{(byte)0x02, (byte)0x03});
        assertTrue(value instanceof FpaIndex);
        FpaIndex fpaIndex = (FpaIndex)value;
        assertEquals(fpaIndex.getDisplayName(), "FPA Index");
        assertEquals(fpaIndex.getDisplayableValue(), "Row 2, Col 3");
        assertEquals(fpaIndex.getFpaIndexPack().getFpaRow(), (short)2);
        assertEquals(fpaIndex.getFpaIndexPack().getFpaColumn(), (short)3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmallRow()
    {
        new FpaIndex(new FpaIndexPack((short)0, (short)1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmallColumn()
    {
        new FpaIndex(new FpaIndexPack((short)1, (short)0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBigRow()
    {
        new FpaIndex(new FpaIndexPack((short)256, (short)1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBigColumn()
    {
        new FpaIndex(new FpaIndexPack((short)1, (short)256));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new FpaIndex(new byte[]{0x01, 0x02, 0x03});
    }
}
