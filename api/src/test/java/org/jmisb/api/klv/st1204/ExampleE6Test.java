package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.*;

/**
 * Example E6: Foundational ID with Sensor ID
 */
public class ExampleE6Test {

    public ExampleE6Test() {
    }

    @Test
    public void checkBuildFromString() {
        /*
        0140:BC76-CFEF-0BEE-41A4-9618-EB4B-010D-2F08:B6
        Usage Byte = 40 = 0 10 00 0 0 0 = (2=VIRTUAL,0=None,0=None,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 120140BC76CFEF0B EE41A49618EB4B01 0D2F08
        Text: 0140:BC76-CFEF-0BEE-41A4-9618-EB4B-010D-2F08:B6
         */
        CoreIdentifier coreIdentifier = CoreIdentifier.fromString("0140:BC76-CFEF-0BEE-41A4-9618-EB4B-010D-2F08:B6");
        verifyCoreIdentifierE6(coreIdentifier);
    }
    
    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes = new byte[] {(byte)0x01, (byte)0x40, (byte)0xBC, (byte)0x76, (byte)0xCF, (byte)0xEF, (byte)0x0B, (byte)0xEE, (byte)0x41, (byte)0xA4, (byte)0x96, (byte)0x18, (byte)0xEB, (byte)0x4B, (byte)0x01, (byte)0x0D, (byte)0x2F, (byte)0x08};
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE6(coreIdentifier);
    }
    
    private void verifyCoreIdentifierE6(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Virtual);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.None);
        UUID expectedSensorUUID = UUID.fromString("BC76CFEF-0BEE-41A4-9618-EB4B010D2F08");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        assertEquals(coreIdentifier.getPlatformUUID(), null);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes = new byte[] {(byte)0x01, (byte)0x40, (byte)0xBC, (byte)0x76, (byte)0xCF, (byte)0xEF, (byte)0x0B, (byte)0xEE, (byte)0x41, (byte)0xA4, (byte)0x96, (byte)0x18, (byte)0xEB, (byte)0x4B, (byte)0x01, (byte)0x0D, (byte)0x2F, (byte)0x08};
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0140:BC76-CFEF-0BEE-41A4-9618-EB4B-010D-2F08:B6";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
