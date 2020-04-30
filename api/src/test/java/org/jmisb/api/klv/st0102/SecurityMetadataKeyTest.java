package org.jmisb.api.klv.st0102;

import org.jmisb.api.klv.UniversalLabel;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for SecurityMetadataKey enumeration.
 */
public class SecurityMetadataKeyTest
{
    @Test
    public void getKeyFromUl()
    {
        SecurityMetadataKey key = SecurityMetadataKey.getKey(SecurityMetadataConstants.caveatsUl);
        assertEquals(key, SecurityMetadataKey.Caveats);
    }

    @Test
    public void getKeyFromUlUnknown()
    {
        byte[] bytes = new byte[]{0x06, 0x0e, 0x2b, 0x34,
                0x01, 0x00, 0x00, 0x00,
                0x01, 0x00, 0x05, 0x3a,
                0x01, 0x00, 0x00, 0x00};

        UniversalLabel label = new UniversalLabel(bytes);
        SecurityMetadataKey key = SecurityMetadataKey.getKey(label);
        assertEquals(key, SecurityMetadataKey.Undefined);
    }
}
