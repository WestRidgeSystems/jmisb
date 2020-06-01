package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VTarget Pack
 */
public class VTargetPackTest extends LoggerChecks
{
    public VTargetPackTest()
    {
        super(VTargetPack.class);
    }

    @Test
    public void testFactoryUnknown() throws KlvParseException
    {
        verifyNoLoggerMessages();
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.Undefined, new byte[]{(byte)0x2a, (byte)0x94});
        verifySingleLoggerMessage("Unrecognized VTarget tag: Undefined");
        assertNull(value);
    }

    @Test
    public void parseAlgorithmId() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x01, // Target Id
            0x16, 0x03, 0x01, 0x03, (byte)0x98 // Tag 22 - Algorithm Id.
        };
        VTargetPack localSet = new VTargetPack(bytes, 0, bytes.length);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        assertTrue(localSet.getTags().contains(VTargetMetadataKey.AlgorithmId));
        IVmtiMetadataValue v = localSet.getField(VTargetMetadataKey.AlgorithmId);
        assertEquals(v.getDisplayName(), "Algorithm Id");
        assertEquals(v.getDisplayableValue(), "66456");
        assertTrue(v instanceof AlgorithmId);
        AlgorithmId id = (AlgorithmId) localSet.getField(VTargetMetadataKey.AlgorithmId);
        assertEquals(id.getValue(), 66456);
        assertEquals(id.getBytes(), new byte[] {0x01, 0x03, (byte)0x98});
    }

    @Test
    public void parseUnknown() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x01, // Target Id
            0x37, 0x02, 0x01, 0x03, // No such tag
            0x16, 0x03, 0x01, 0x03, (byte)0x98 // Tag 22 - Algorithm Id.
        };
        verifyNoLoggerMessages();
        VTargetPack localSet = new VTargetPack(bytes, 0, bytes.length);
        verifySingleLoggerMessage("Unknown VMTI VTarget Metadata tag: 55");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
    }
}
