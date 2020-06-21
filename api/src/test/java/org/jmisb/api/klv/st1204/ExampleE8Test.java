package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** Example E8: Foundational ID with Platform ID */
public class ExampleE8Test {

    public ExampleE8Test() {}

    @Test
    public void checkBuildFromString() {
        /*
        0110:1AB8-231E-17E8-4748-A133-CE93-89A7-A060:25
        Usage Byte = 10 = 0 00 10 0 0 0 = (0=None,2=VIRTUAL,0=None,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 1201101AB8231E17 E84748A133CE9389 A7A060
        Text: 0110:1AB8-231E-17E8-4748-A133-CE93-89A7-A060:25
         */
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString("0110:1AB8-231E-17E8-4748-A133-CE93-89A7-A060:25");
        verifyCoreIdentifierE8(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x1A,
                    (byte) 0xB8,
                    (byte) 0x23,
                    (byte) 0x1E,
                    (byte) 0x17,
                    (byte) 0xE8,
                    (byte) 0x47,
                    (byte) 0x48,
                    (byte) 0xA1,
                    (byte) 0x33,
                    (byte) 0xCE,
                    (byte) 0x93,
                    (byte) 0x89,
                    (byte) 0xA7,
                    (byte) 0xA0,
                    (byte) 0x60
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE8(coreIdentifier);
    }

    private void verifyCoreIdentifierE8(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.None);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getSensorUUID(), null);
        UUID expectedPlatformUUID = UUID.fromString("1AB8231E-17E8-4748-A133-CE9389A7A060");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x1A,
                    (byte) 0xB8,
                    (byte) 0x23,
                    (byte) 0x1E,
                    (byte) 0x17,
                    (byte) 0xE8,
                    (byte) 0x47,
                    (byte) 0x48,
                    (byte) 0xA1,
                    (byte) 0x33,
                    (byte) 0xCE,
                    (byte) 0x93,
                    (byte) 0x89,
                    (byte) 0xA7,
                    (byte) 0xA0,
                    (byte) 0x60
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0110:1AB8-231E-17E8-4748-A133-CE93-89A7-A060:25";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
