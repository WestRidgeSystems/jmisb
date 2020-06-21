package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** Example E4: Foundational ID with physical Sensor ID and physical Platform ID */
public class ExampleE4Test {

    public ExampleE4Test() {}

    @Test
    public void checkBuildFromString() {
        /*
        0178:865E-FD9C-EF8A-41C3-8244-B885-AFCC-40BF/ED8A-9AB8-72E2-4165-9979-7E5A-F54A-5B9A:25
        Usage Byte = 78 = 0 11 11 0 0 0 = (3=PHYSICAL,3=PHYSICAL,0=None,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 220178865EFD9CEF 8A41C38244B885AF CC40BFED8A9AB872 E2416599797E5AF5 4A5B9A
        Text: 0178:865E-FD9C-EF8A-41C3-8244-B885-AFCC-40BF/ED8A-9AB8-72E2-4165-9979-7E5A-F54A-5B9A:25
         */
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0178:865E-FD9C-EF8A-41C3-8244-B885-AFCC-40BF/ED8A-9AB8-72E2-4165-9979-7E5A-F54A-5B9A:25");
        verifyCoreIdentifierE4(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x78,
                    (byte) 0x86,
                    (byte) 0x5E,
                    (byte) 0xFD,
                    (byte) 0x9C,
                    (byte) 0xEF,
                    (byte) 0x8A,
                    (byte) 0x41,
                    (byte) 0xC3,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0xB8,
                    (byte) 0x85,
                    (byte) 0xAF,
                    (byte) 0xCC,
                    (byte) 0x40,
                    (byte) 0xBF,
                    (byte) 0xED,
                    (byte) 0x8A,
                    (byte) 0x9A,
                    (byte) 0xB8,
                    (byte) 0x72,
                    (byte) 0xE2,
                    (byte) 0x41,
                    (byte) 0x65,
                    (byte) 0x99,
                    (byte) 0x79,
                    (byte) 0x7E,
                    (byte) 0x5A,
                    (byte) 0xF5,
                    (byte) 0x4A,
                    (byte) 0x5B,
                    (byte) 0x9A
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE4(coreIdentifier);
    }

    private void verifyCoreIdentifierE4(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Physical);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Physical);
        UUID expectedSensorUUID = UUID.fromString("865EFD9C-EF8A-41C3-8244-B885AFCC40BF");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        UUID expectedPlatformUUID = UUID.fromString("ED8A9AB8-72E2-4165-9979-7E5AF54A5B9A");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x78,
                    (byte) 0x86,
                    (byte) 0x5E,
                    (byte) 0xFD,
                    (byte) 0x9C,
                    (byte) 0xEF,
                    (byte) 0x8A,
                    (byte) 0x41,
                    (byte) 0xC3,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0xB8,
                    (byte) 0x85,
                    (byte) 0xAF,
                    (byte) 0xCC,
                    (byte) 0x40,
                    (byte) 0xBF,
                    (byte) 0xED,
                    (byte) 0x8A,
                    (byte) 0x9A,
                    (byte) 0xB8,
                    (byte) 0x72,
                    (byte) 0xE2,
                    (byte) 0x41,
                    (byte) 0x65,
                    (byte) 0x99,
                    (byte) 0x79,
                    (byte) 0x7E,
                    (byte) 0x5A,
                    (byte) 0xF5,
                    (byte) 0x4A,
                    (byte) 0x5B,
                    (byte) 0x9A
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText =
                "0178:865E-FD9C-EF8A-41C3-8244-B885-AFCC-40BF/ED8A-9AB8-72E2-4165-9979-7E5A-F54A-5B9A:25";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
