package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class OpaqueValueTest {

    @Test
    public void testConstructFromEncoded() {
        OpaqueValue opaqueValue = new OpaqueValue(new byte[] {0x01, 0x02});
        assertEquals(opaqueValue.getDisplayName(), "Opaque Value");
        assertEquals(opaqueValue.getDisplayableValue(), "N/A");
        assertEquals(opaqueValue.getBytes(), new byte[] {0x01, 0x02});
    }
}
