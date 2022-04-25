package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** ST1204 Example E7: Foundational ID with Platform ID and Window ID */
public class ExampleE7Test {

    /*
    0114:3D50-2DB6-4A93-44C3-B56E-94AD-7C4E-E476/45E2-8FAF-C2D3-4A6E-815B-5FE6-B0A9-6ABD:F1
    Usage Byte = 14 = 0 00 10 1 0 0 = (0=None,2=VIRTUAL,1=Included,0=None)
    Version (1)
    KLV: 060E2B3401010101 0E01040503000000 2201143D502DB64A 9344C3B56E94AD7C 4EE47645E28FAFC2 D34A6E815B5FE6B0 A96ABD
    Text: 0114:3D50-2DB6-4A93-44C3-B56E-94AD-7C4E-E476/45E2-8FAF-C2D3-4A6E-815B-5FE6-B0A9-6ABD:F1
     */

    public ExampleE7Test() {}

    @Test
    public void checkBuildFromString() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0114:3D50-2DB6-4A93-44C3-B56E-94AD-7C4E-E476/45E2-8FAF-C2D3-4A6E-815B-5FE6-B0A9-6ABD:F1");
        verifyCoreIdentifierE7(coreIdentifier);
        CoreIdentifier copy = new CoreIdentifier(coreIdentifier);
        verifyCoreIdentifierE7(copy);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x14,
                    (byte) 0x3D,
                    (byte) 0x50,
                    (byte) 0x2D,
                    (byte) 0xB6,
                    (byte) 0x4A,
                    (byte) 0x93,
                    (byte) 0x44,
                    (byte) 0xC3,
                    (byte) 0xB5,
                    (byte) 0x6E,
                    (byte) 0x94,
                    (byte) 0xAD,
                    (byte) 0x7C,
                    (byte) 0x4E,
                    (byte) 0xE4,
                    (byte) 0x76,
                    (byte) 0x45,
                    (byte) 0xE2,
                    (byte) 0x8F,
                    (byte) 0xAF,
                    (byte) 0xC2,
                    (byte) 0xD3,
                    (byte) 0x4A,
                    (byte) 0x6E,
                    (byte) 0x81,
                    (byte) 0x5B,
                    (byte) 0x5F,
                    (byte) 0xE6,
                    (byte) 0xB0,
                    (byte) 0xA9,
                    (byte) 0x6A,
                    (byte) 0xBD
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE7(coreIdentifier);
    }

    private void verifyCoreIdentifierE7(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.None);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getSensorUUID(), null);
        UUID expectedPlatformUUID = UUID.fromString("3D502DB6-4A93-44C3-B56E-94AD7C4EE476");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        assertEquals(
                coreIdentifier.getWindowUUID(),
                UUID.fromString("45E28FAF-C2D3-4A6E-815B-5FE6B0A96ABD"));
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x14,
                    (byte) 0x3D,
                    (byte) 0x50,
                    (byte) 0x2D,
                    (byte) 0xB6,
                    (byte) 0x4A,
                    (byte) 0x93,
                    (byte) 0x44,
                    (byte) 0xC3,
                    (byte) 0xB5,
                    (byte) 0x6E,
                    (byte) 0x94,
                    (byte) 0xAD,
                    (byte) 0x7C,
                    (byte) 0x4E,
                    (byte) 0xE4,
                    (byte) 0x76,
                    (byte) 0x45,
                    (byte) 0xE2,
                    (byte) 0x8F,
                    (byte) 0xAF,
                    (byte) 0xC2,
                    (byte) 0xD3,
                    (byte) 0x4A,
                    (byte) 0x6E,
                    (byte) 0x81,
                    (byte) 0x5B,
                    (byte) 0x5F,
                    (byte) 0xE6,
                    (byte) 0xB0,
                    (byte) 0xA9,
                    (byte) 0x6A,
                    (byte) 0xBD
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText =
                "0114:3D50-2DB6-4A93-44C3-B56E-94AD-7C4E-E476/45E2-8FAF-C2D3-4A6E-815B-5FE6-B0A9-6ABD:F1";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
