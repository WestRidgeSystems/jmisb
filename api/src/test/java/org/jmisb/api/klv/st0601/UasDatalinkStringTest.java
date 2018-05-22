package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkStringTest
{
    @Test
    public void testMissionId()
    {
        // Example from Tag 3 Mission ID
        final String stringVal = "MISSION01";
        final byte[] bytes = new byte[]{0x4d, 0x49, 0x53, 0x53, 0x49, 0x4f, 0x4e, 0x30, 0x31};

        UasDatalinkString idFromString = new UasDatalinkString(stringVal);
        UasDatalinkString idFromBytes = new UasDatalinkString(bytes);

        Assert.assertEquals(idFromString.getBytes(), bytes);
        Assert.assertEquals(idFromBytes.getValue(), stringVal);
    }
}
