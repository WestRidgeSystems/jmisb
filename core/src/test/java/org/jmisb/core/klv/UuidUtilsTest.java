package org.jmisb.core.klv;

import static org.testng.Assert.*;

import java.util.UUID;
import org.testng.annotations.Test;

public class UuidUtilsTest {
    @Test
    public void checkConstructor() {
        UuidUtils uuidUtils = new UuidUtils();
        assertNotNull(uuidUtils);
    }

    @Test
    public void checkFormatting() {
        assertEquals(
                UuidUtils.formatUUID(UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876")),
                "C2A7-D724-96F7-47DB-A23D-A297-3007-5876");
    }

    @Test
    public void checkParseUUID() {
        assertEquals(
                UuidUtils.parseUUID("C2A7-D724-96F7-47DB-A23D-A297-3007-5876"),
                UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876"));
    }

    @Test
    public void checkParseUUIDBadFormat() {
        assertNull(UuidUtils.parseUUID("C2A7-D724-96F7-47DB-A23D-A297-30075876"));
        assertNull(UuidUtils.parseUUID(""));
    }

    @Test
    public void checkUUIDtoByteArray() {
        UUID uuid = UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876");
        byte[] ba = UuidUtils.uuidToArray(uuid);
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0xc2,
                    (byte) 0xa7,
                    (byte) 0xd7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xf7,
                    (byte) 0x47,
                    (byte) 0xdb,
                    (byte) 0xa2,
                    (byte) 0x3d,
                    (byte) 0xa2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58,
                    (byte) 0x76
                };
        assertEquals(ba, expectedBytes);
    }

    @Test
    public void checkUUIDtoLongArray() {
        UUID uuid = UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876");
        long[] la = UuidUtils.uuidToLongArray(uuid);
        long[] expectedLongs =
                new long[] {
                    (long) 0xc2,
                    (long) 0xa7,
                    (long) 0xd7,
                    (long) 0x24,
                    (long) 0x96,
                    (long) 0xf7,
                    (long) 0x47,
                    (long) 0xdb,
                    (long) 0xa2,
                    (long) 0x3d,
                    (long) 0xa2,
                    (long) 0x97,
                    (long) 0x30,
                    (long) 0x07,
                    (long) 0x58,
                    (long) 0x76
                };
        assertEquals(la, expectedLongs);
    }

    @Test
    public void checkByteArrayToUUID() {
        byte[] bytes =
                new byte[] {
                    (byte) 0xc2,
                    (byte) 0xa7,
                    (byte) 0xd7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xf7,
                    (byte) 0x47,
                    (byte) 0xdb,
                    (byte) 0xa2,
                    (byte) 0x3d,
                    (byte) 0xa2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58,
                    (byte) 0x76
                };
        UUID uuid = UuidUtils.arrayToUuid(bytes, 0);
        UUID expectedUuid = UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876");
        assertEquals(uuid, expectedUuid);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkByteArrayBadLengthToUUID() {
        byte[] bytes =
                new byte[] {
                    (byte) 0xc2,
                    (byte) 0xa7,
                    (byte) 0xd7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xf7,
                    (byte) 0x47,
                    (byte) 0xdb,
                    (byte) 0xa2,
                    (byte) 0x3d,
                    (byte) 0xa2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58
                };
        UuidUtils.arrayToUuid(bytes, 0);
    }

    @Test
    public void checkUUIDStringtoByteArray() {
        byte[] ba = UuidUtils.uuidStringToByteArray("C2A7D724-96F7-47DB-A23D-A29730075876");
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x43,
                    (byte) 0x32,
                    (byte) 0x41,
                    (byte) 0x37,
                    (byte) 0x44,
                    (byte) 0x37,
                    (byte) 0x32,
                    (byte) 0x34,
                    (byte) 0x39,
                    (byte) 0x36,
                    (byte) 0x46,
                    (byte) 0x37,
                    (byte) 0x34,
                    (byte) 0x37,
                    (byte) 0x44,
                    (byte) 0x42,
                    (byte) 0x41,
                    (byte) 0x32,
                    (byte) 0x33,
                    (byte) 0x44,
                    (byte) 0x41,
                    (byte) 0x32,
                    (byte) 0x39,
                    (byte) 0x37,
                    (byte) 0x33,
                    (byte) 0x30,
                    (byte) 0x30,
                    (byte) 0x37,
                    (byte) 0x35,
                    (byte) 0x38,
                    (byte) 0x37,
                    (byte) 0x36
                };
        assertEquals(ba, expectedBytes);
    }

    @Test
    public void checkUUIDlowerCaseStringtoByteArray() {
        byte[] ba = UuidUtils.uuidStringToByteArray("c2a7d724-96f7-47db-a23d-a29730075876");
        // for (int i = 0; i < ba.length; i++)
        // {
        //    System.out.print(String.format("(byte)0x%02x, ", ba[i]));
        // }
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x43,
                    (byte) 0x32,
                    (byte) 0x41,
                    (byte) 0x37,
                    (byte) 0x44,
                    (byte) 0x37,
                    (byte) 0x32,
                    (byte) 0x34,
                    (byte) 0x39,
                    (byte) 0x36,
                    (byte) 0x46,
                    (byte) 0x37,
                    (byte) 0x34,
                    (byte) 0x37,
                    (byte) 0x44,
                    (byte) 0x42,
                    (byte) 0x41,
                    (byte) 0x32,
                    (byte) 0x33,
                    (byte) 0x44,
                    (byte) 0x41,
                    (byte) 0x32,
                    (byte) 0x39,
                    (byte) 0x37,
                    (byte) 0x33,
                    (byte) 0x30,
                    (byte) 0x30,
                    (byte) 0x37,
                    (byte) 0x35,
                    (byte) 0x38,
                    (byte) 0x37,
                    (byte) 0x36
                };
        assertEquals(ba, expectedBytes);
    }

    @Test
    public void checkByteArrayToUUID5() {
        byte[] bytes =
                new byte[] {
                    (byte) 0xc2,
                    (byte) 0xa7,
                    (byte) 0xd7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xf7,
                    (byte) 0x47,
                    (byte) 0xdb,
                    (byte) 0xa2,
                    (byte) 0x3d,
                    (byte) 0xa2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58,
                    (byte) 0x76
                };
        UUID uuid = UuidUtils.convertHashOutputToVersion5UUID(bytes);
        UUID expectedUuid = UUID.fromString("C2A7D724-96F7-57DB-A23D-A29730075876");
        assertEquals(uuid, expectedUuid);
    }
}
