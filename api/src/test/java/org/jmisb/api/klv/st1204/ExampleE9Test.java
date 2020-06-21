package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** ST1204 Example E9: Foundational ID with Window ID */
public class ExampleE9Test {

    public ExampleE9Test() {}

    @Test
    public void checkBuildFromString() {
        /*
        0104:C2A7-D724-96F7-47DB-A23D-A297-3007-5876:55
        Option Byte = 04 = 0 00 00 1 0 0 = (0=None,0=None,1=Included,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 120104C2A7D72496 F747DBA23DA29730 075876
        Text: 0104:C2A7-D724-96F7-47DB-A23D-A297-3007-5876:55
         */
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString("0104:C2A7-D724-96F7-47DB-A23D-A297-3007-5876:55");
        verifyCoreIdentifierE9(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0xC2,
                    (byte) 0xA7,
                    (byte) 0xD7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xF7,
                    (byte) 0x47,
                    (byte) 0xDB,
                    (byte) 0xA2,
                    (byte) 0x3D,
                    (byte) 0xA2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58,
                    (byte) 0x76
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE9(coreIdentifier);
    }

    private void verifyCoreIdentifierE9(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.None);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.None);
        assertEquals(coreIdentifier.getSensorUUID(), null);
        assertEquals(coreIdentifier.getPlatformUUID(), null);
        assertEquals(
                coreIdentifier.getWindowUUID(),
                UUID.fromString("C2A7D724-96F7-47DB-A23D-A29730075876"));
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0xC2,
                    (byte) 0xA7,
                    (byte) 0xD7,
                    (byte) 0x24,
                    (byte) 0x96,
                    (byte) 0xF7,
                    (byte) 0x47,
                    (byte) 0xDB,
                    (byte) 0xA2,
                    (byte) 0x3D,
                    (byte) 0xA2,
                    (byte) 0x97,
                    (byte) 0x30,
                    (byte) 0x07,
                    (byte) 0x58,
                    (byte) 0x76
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0104:C2A7-D724-96F7-47DB-A23D-A297-3007-5876:55";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
