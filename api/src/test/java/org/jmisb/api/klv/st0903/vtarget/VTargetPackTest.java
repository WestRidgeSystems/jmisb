package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VTarget Pack
 */
public class VTargetPackTest
{
    @Test
    public void testFactoryUnknown() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.Undefined, new byte[]{(byte)0x2a, (byte)0x94});
        assertNull(value);
    }
}
