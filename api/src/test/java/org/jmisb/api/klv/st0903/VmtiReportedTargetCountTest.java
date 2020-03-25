package org.jmisb.api.klv.st0903;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for numTargetsReported (ST0903 Tag 6).
 */
public class VmtiReportedTargetCountTest
{
    @Test
    public void testConstructFromValue()
    {
        VmtiReportedTargetCount count = new VmtiReportedTargetCount(14);
        assertEquals(count.getBytes(), new byte[]{(byte)0xE});
        assertEquals(count.getDisplayName(), "Reported Targets");
        assertEquals(count.getDisplayableValue(), "14");
        assertEquals(count.getValue(), 14);
    }
    
    @Test
    public void testConstructFromValueMin()
    {
        VmtiReportedTargetCount count = new VmtiReportedTargetCount(0);
        assertEquals(count.getBytes(), new byte[]{(byte)0x00});
        assertEquals(count.getDisplayName(),  "Reported Targets");
        assertEquals(count.getDisplayableValue(), "0");
        assertEquals(count.getValue(), 0);
    }

    @Test
    public void testConstructFromValueMax()
    {
        VmtiReportedTargetCount count = new VmtiReportedTargetCount(16777215);
        assertEquals(count.getBytes(), new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF});
        assertEquals(count.getDisplayName(), "Reported Targets");
        assertEquals(count.getDisplayableValue(), "16777215");
        assertEquals(count.getValue(), 16777215);
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        VmtiReportedTargetCount count = new VmtiReportedTargetCount(new byte[]{(byte)0x0E});
        assertEquals(count.getBytes(), new byte[]{(byte)0x0E});
        assertEquals(count.getDisplayName(), "Reported Targets");
        assertEquals(count.getDisplayableValue(), "14");
        assertEquals(count.getValue(), 14);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiLocalSet.createValue(VmtiMetadataKey.NumberOfReportedTargets, new byte[]{(byte)0x0E});
        assertTrue(value instanceof VmtiReportedTargetCount);
        VmtiReportedTargetCount count = (VmtiReportedTargetCount)value;
        assertEquals(count.getBytes(), new byte[]{(byte)0x0E});
        assertEquals(count.getDisplayName(), "Reported Targets");
        assertEquals(count.getDisplayableValue(), "14");
        assertEquals(count.getValue(), 14);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new FrameHeight(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new FrameHeight(16777216);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new FrameHeight(new byte[]{0x01, 0x02, 0x03, 0x04});
    }
}
