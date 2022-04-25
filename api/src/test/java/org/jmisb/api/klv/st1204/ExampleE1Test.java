package org.jmisb.api.klv.st1204;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.UUID;
import org.testng.annotations.*;

/** Example E1: Foundational Core ID with Sensor ID, Platform ID and Window ID */
public class ExampleE1Test {

    public ExampleE1Test() {}

    @Test
    public void checkBuildFromString() {
        /*
        0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8
        UsageByte = 54 = 0 10 10 1 0 0 = (2=VIRTUAL,2=VIRTUAL,1=Included,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 320154C7D1625398 A241C2BA6E90F8FC C73914E047AB3E81 BE41ED966409B02F 445FAB5E71B0DC20 FE4920821626D64F 61D863
        Text: 0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8
         */
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8");
        verifyCoreIdentifierE1(coreIdentifier);
    }

    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x54,
                    (byte) 0xC7,
                    (byte) 0xD1,
                    (byte) 0x62,
                    (byte) 0x53,
                    (byte) 0x98,
                    (byte) 0xA2,
                    (byte) 0x41,
                    (byte) 0xC2,
                    (byte) 0xBA,
                    (byte) 0x6E,
                    (byte) 0x90,
                    (byte) 0xF8,
                    (byte) 0xFC,
                    (byte) 0xC7,
                    (byte) 0x39,
                    (byte) 0x14,
                    (byte) 0xE0,
                    (byte) 0x47,
                    (byte) 0xAB,
                    (byte) 0x3E,
                    (byte) 0x81,
                    (byte) 0xBE,
                    (byte) 0x41,
                    (byte) 0xED,
                    (byte) 0x96,
                    (byte) 0x64,
                    (byte) 0x09,
                    (byte) 0xB0,
                    (byte) 0x2F,
                    (byte) 0x44,
                    (byte) 0x5F,
                    (byte) 0xAB,
                    (byte) 0x5E,
                    (byte) 0x71,
                    (byte) 0xB0,
                    (byte) 0xDC,
                    (byte) 0x20,
                    (byte) 0xFE,
                    (byte) 0x49,
                    (byte) 0x20,
                    (byte) 0x82,
                    (byte) 0x16,
                    (byte) 0x26,
                    (byte) 0xD6,
                    (byte) 0x4F,
                    (byte) 0x61,
                    (byte) 0xD8,
                    (byte) 0x63
                };
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE1(coreIdentifier);
        CoreIdentifier copy = new CoreIdentifier(coreIdentifier);
        verifyCoreIdentifierE1(copy);
    }

    private void verifyCoreIdentifierE1(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Virtual);
        UUID expectedSensorUUID = UUID.fromString("C7D16253-98A2-41C2-BA6E-90F8FCC73914");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        UUID expectedPlatformUUID = UUID.fromString("E047AB3E-81BE-41ED-9664-09B02F445FAB");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        UUID expectedWindowUUID = UUID.fromString("5E71B0DC-20FE-4920-8216-26D64F61D863");
        assertEquals(coreIdentifier.getWindowUUID(), expectedWindowUUID);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x54,
                    (byte) 0xC7,
                    (byte) 0xD1,
                    (byte) 0x62,
                    (byte) 0x53,
                    (byte) 0x98,
                    (byte) 0xA2,
                    (byte) 0x41,
                    (byte) 0xC2,
                    (byte) 0xBA,
                    (byte) 0x6E,
                    (byte) 0x90,
                    (byte) 0xF8,
                    (byte) 0xFC,
                    (byte) 0xC7,
                    (byte) 0x39,
                    (byte) 0x14,
                    (byte) 0xE0,
                    (byte) 0x47,
                    (byte) 0xAB,
                    (byte) 0x3E,
                    (byte) 0x81,
                    (byte) 0xBE,
                    (byte) 0x41,
                    (byte) 0xED,
                    (byte) 0x96,
                    (byte) 0x64,
                    (byte) 0x09,
                    (byte) 0xB0,
                    (byte) 0x2F,
                    (byte) 0x44,
                    (byte) 0x5F,
                    (byte) 0xAB,
                    (byte) 0x5E,
                    (byte) 0x71,
                    (byte) 0xB0,
                    (byte) 0xDC,
                    (byte) 0x20,
                    (byte) 0xFE,
                    (byte) 0x49,
                    (byte) 0x20,
                    (byte) 0x82,
                    (byte) 0x16,
                    (byte) 0x26,
                    (byte) 0xD6,
                    (byte) 0x4F,
                    (byte) 0x61,
                    (byte) 0xD8,
                    (byte) 0x63
                };
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText =
                "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C8";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
