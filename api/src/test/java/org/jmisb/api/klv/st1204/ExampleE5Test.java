package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.*;

/**
 * Example E5: Foundational ID with Sensor ID and Window ID
 */
public class ExampleE5Test {

    public ExampleE5Test() {
    }

    @Test
    public void checkBuildFromString() {
        /*
        0144:340F-463B-AEC2-4F8C-BD45-92AE-DE80-E1C6/F0AF-8673-3A17-424C-B060-3EE4-A86B-38F9:D9
        Usage Byte = 44 = 0 10 00 1 0 0 = (2=VIRTUAL,0=None,1=Included,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 220144340F463BAE C24F8CBD4592AEDE 80E1C6F0AF86733A 17424CB0603EE4A8 6B38F9
        Text: 0144:340F-463B-AEC2-4F8C-BD45-92AE-DE80-E1C6/F0AF-8673-3A17-424C-B060-3EE4-A86B-38F9:D9
         */
        CoreIdentifier coreIdentifier = CoreIdentifier.fromString("0144:340F-463B-AEC2-4F8C-BD45-92AE-DE80-E1C6/F0AF-8673-3A17-424C-B060-3EE4-A86B-38F9:D9");
        verifyCoreIdentifierE5(coreIdentifier);
    }
    
    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes = new byte[] {(byte)0x01, (byte)0x44, (byte)0x34, (byte)0x0F, (byte)0x46, (byte)0x3B, (byte)0xAE, (byte)0xC2, (byte)0x4F, (byte)0x8C, (byte)0xBD, (byte)0x45, (byte)0x92, (byte)0xAE, (byte)0xDE, (byte)0x80, (byte)0xE1, (byte)0xC6, (byte)0xF0, (byte)0xAF, (byte)0x86, (byte)0x73, (byte)0x3A, (byte)0x17, (byte)0x42, (byte)0x4C, (byte)0xB0, (byte)0x60, (byte)0x3E, (byte)0xE4, (byte)0xA8, (byte)0x6B, (byte)0x38, (byte)0xF9};
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE5(coreIdentifier);
    }

    private void verifyCoreIdentifierE5(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.None);
        UUID expectedSensorUUID = UUID.fromString("340F463B-AEC2-4F8C-BD45-92AEDE80E1C6");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        assertEquals(coreIdentifier.getPlatformUUID(), null);
        assertEquals(coreIdentifier.getWindowUUID(), UUID.fromString("F0AF8673-3A17-424C-B060-3EE4A86B38F9"));
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes = new byte[] {(byte)0x01, (byte)0x44, (byte)0x34, (byte)0x0F, (byte)0x46, (byte)0x3B, (byte)0xAE, (byte)0xC2, (byte)0x4F, (byte)0x8C, (byte)0xBD, (byte)0x45, (byte)0x92, (byte)0xAE, (byte)0xDE, (byte)0x80, (byte)0xE1, (byte)0xC6, (byte)0xF0, (byte)0xAF, (byte)0x86, (byte)0x73, (byte)0x3A, (byte)0x17, (byte)0x42, (byte)0x4C, (byte)0xB0, (byte)0x60, (byte)0x3E, (byte)0xE4, (byte)0xA8, (byte)0x6B, (byte)0x38, (byte)0xF9};
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0144:340F-463B-AEC2-4F8C-BD45-92AE-DE80-E1C6/F0AF-8673-3A17-424C-B060-3EE4-A86B-38F9:D9";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
