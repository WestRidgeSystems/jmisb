package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VTracker LS.
 */
public class VTrackerLSTest
{
    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.Undefined, bytes);
        assertNull(value);
    }

    @Test
    public void parseAlgorithmId() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x0C, 0x01, 0x03};
        VTrackerLS localSet = new VTrackerLS(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        assertTrue(localSet.getTags().contains(VTrackerMetadataKey.algorithmId));
        IVmtiMetadataValue v = localSet.getField(VTrackerMetadataKey.algorithmId);
        assertEquals(v.getDisplayName(), "Algorithm Id");
        assertEquals(v.getDisplayableValue(), "3");
        assertTrue(v instanceof AlgorithmId);
        AlgorithmId id = (AlgorithmId) localSet.getField(VTrackerMetadataKey.algorithmId);
        assertEquals(id.getValue(), 3);
        assertEquals(id.getBytes(), new byte[] {0x03});
    }
}
