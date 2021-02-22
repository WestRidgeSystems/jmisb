package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** Example E2: Foundational ID with SensorID and PlatformID */
public class ExampleE2Test {

    public ExampleE2Test() {}

    @Test
    public void checkBuildFromString() {
        /*
        0150:08CE-252E-D0FA-49E3-B1A0-65E6-1D57-20DC/EAF1-8A27-A086-4019-A586-EAAF-9715-7BFA:DA
        UsageByte = 50 = 0 10 10 0 0 0 = (2=VIRTUAL,2=VIRTUAL,0=None,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 22015008CE252ED0 FA49E3B1A065E61D 5720DCEAF18A27A0 864019A586EAAF97 157BFA
        Text: 0150:08CE-252E-D0FA-49E3-B1A0-65E6-1D57-20DC/EAF1-8A27-A086-4019-A586-EAAF-9715-7BFA:DA
         */
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0150:08CE-252E-D0FA-49E3-B1A0-65E6-1D57-20DC/EAF1-8A27-A086-4019-A586-EAAF-9715-7BFA:DA");
        verifyCoreIdentifierE2(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x50,
                    (byte) 0x08,
                    (byte) 0xCE,
                    (byte) 0x25,
                    (byte) 0x2E,
                    (byte) 0xD0,
                    (byte) 0xFA,
                    (byte) 0x49,
                    (byte) 0xE3,
                    (byte) 0xB1,
                    (byte) 0xA0,
                    (byte) 0x65,
                    (byte) 0xE6,
                    (byte) 0x1D,
                    (byte) 0x57,
                    (byte) 0x20,
                    (byte) 0xDC,
                    (byte) 0xEA,
                    (byte) 0xF1,
                    (byte) 0x8A,
                    (byte) 0x27,
                    (byte) 0xA0,
                    (byte) 0x86,
                    (byte) 0x40,
                    (byte) 0x19,
                    (byte) 0xA5,
                    (byte) 0x86,
                    (byte) 0xEA,
                    (byte) 0xAF,
                    (byte) 0x97,
                    (byte) 0x15,
                    (byte) 0x7B,
                    (byte) 0xFA
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE2(coreIdentifier);
    }

    private void verifyCoreIdentifierE2(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Virtual);
        UUID expectedSensorUUID = UUID.fromString("08CE252E-D0FA-49E3-B1A0-65E61D5720DC");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        UUID expectedPlatformUUID = UUID.fromString("EAF18A27-A086-4019-A586-EAAF97157BFA");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x50,
                    (byte) 0x08,
                    (byte) 0xCE,
                    (byte) 0x25,
                    (byte) 0x2E,
                    (byte) 0xD0,
                    (byte) 0xFA,
                    (byte) 0x49,
                    (byte) 0xE3,
                    (byte) 0xB1,
                    (byte) 0xA0,
                    (byte) 0x65,
                    (byte) 0xE6,
                    (byte) 0x1D,
                    (byte) 0x57,
                    (byte) 0x20,
                    (byte) 0xDC,
                    (byte) 0xEA,
                    (byte) 0xF1,
                    (byte) 0x8A,
                    (byte) 0x27,
                    (byte) 0xA0,
                    (byte) 0x86,
                    (byte) 0x40,
                    (byte) 0x19,
                    (byte) 0xA5,
                    (byte) 0x86,
                    (byte) 0xEA,
                    (byte) 0xAF,
                    (byte) 0x97,
                    (byte) 0x15,
                    (byte) 0x7B,
                    (byte) 0xFA
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText =
                "0150:08CE-252E-D0FA-49E3-B1A0-65E6-1D57-20DC/EAF1-8A27-A086-4019-A586-EAAF-9715-7BFA:DA";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
