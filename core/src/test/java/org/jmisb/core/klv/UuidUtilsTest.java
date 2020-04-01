package org.jmisb.core.klv;

import java.util.UUID;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class UuidUtilsTest
{
    @Test
    public void checkConstructor()
    {
        UuidUtils uuidUtils = new UuidUtils();
        assertNotNull(uuidUtils);
    }

    @Test
    public void checkFormatting()
    {
        assertEquals(UuidUtils.formatUUID(UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876")), "C2A7-D724-96F7-47DB-A23D-A297-3007-5876");
    }

    @Test
    public void checkParseUUID()
    {
        assertEquals(UuidUtils.parseUUID("C2A7-D724-96F7-47DB-A23D-A297-3007-5876"), UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876"));
    }

    @Test
    public void checkParseUUIDBadFormat()
    {
        assertNull(UuidUtils.parseUUID("C2A7-D724-96F7-47DB-A23D-A297-30075876"));
        assertNull(UuidUtils.parseUUID(""));
    }

    @Test
    public void checkUUIDtoByteArray()
    {
        UUID uuid = UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876");
        byte[] ba = UuidUtils.uuidToArray(uuid);
        byte[] expectedBytes = new byte[] {(byte)0xc2, (byte)0xa7, (byte)0xd7, (byte)0x24, (byte)0x96, (byte)0xf7, (byte)0x47, (byte)0xdb, (byte)0xa2, (byte)0x3d, (byte)0xa2, (byte)0x97, (byte)0x30, (byte)0x07, (byte)0x58, (byte)0x76};
        assertEquals(ba, expectedBytes);
    }

    @Test
    public void checkByteArrayToUUID()
    {
        byte[] bytes = new byte[] {(byte)0xc2, (byte)0xa7, (byte)0xd7, (byte)0x24, (byte)0x96, (byte)0xf7, (byte)0x47, (byte)0xdb, (byte)0xa2, (byte)0x3d, (byte)0xa2, (byte)0x97, (byte)0x30, (byte)0x07, (byte)0x58, (byte)0x76};
        UUID uuid = UuidUtils.arrayToUuid(bytes, 0);
        UUID expectedUuid = UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876");
        assertEquals(uuid, expectedUuid);
    }
}
