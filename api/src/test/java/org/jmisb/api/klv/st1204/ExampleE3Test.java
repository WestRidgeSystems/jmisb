package org.jmisb.api.klv.st1204;

import java.util.UUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.*;

/**
 * Example E3: Foundational ID with physical Sensor ID and managed Platform ID
 */
public class ExampleE3Test {

    public ExampleE3Test() {
    }

    @Test
    public void checkBuildFromString() {
        /*
        0168:F354-666E-D552-4C0D-9168-A745-4CEB-A073/840A-4799-BBC0-4BD5-97A7-56F6-4092-AF7B:AA
        Usage Byte = 68 = 0 11 01 0 0 0 = (3=PHYSICAL,1=MANAGED,0=None,0=None)
        Version (1)
        KLV: 060E2B3401010101 0E01040503000000 220168F354666ED5 524C0D9168A7454C EBA073840A4799BB C04BD597A756F640 92AF7B
        Text: 0168:F354-666E-D552-4C0D-9168-A745-4CEB-A073/840A-4799-BBC0-4BD5-97A7-56F6-4092-AF7B:AA
         */
        CoreIdentifier coreIdentifier = CoreIdentifier.fromString("0168:F354-666E-D552-4C0D-9168-A745-4CEB-A073/840A-4799-BBC0-4BD5-97A7-56F6-4092-AF7B:AA");
        verifyCoreIdentifierE3(coreIdentifier);
    }
    
    @Test
    public void checkBuildFromRawBytes() {
        byte[] bytes = new byte[] {(byte)0x01, (byte)0x68, (byte)0xF3, (byte)0x54, (byte)0x66, (byte)0x6E, (byte)0xD5, (byte)0x52, (byte)0x4C, (byte)0x0D, (byte)0x91, (byte)0x68, (byte)0xA7, (byte)0x45, (byte)0x4C, (byte)0xEB, (byte)0xA0, (byte)0x73, (byte)0x84, (byte)0x0A, (byte)0x47, (byte)0x99, (byte)0xBB, (byte)0xC0, (byte)0x4B, (byte)0xD5, (byte)0x97, (byte)0xA7, (byte)0x56, (byte)0xF6, (byte)0x40, (byte)0x92, (byte)0xAF, (byte)0x7B};
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(bytes);
        verifyCoreIdentifierE3(coreIdentifier);
    }

    private void verifyCoreIdentifierE3(CoreIdentifier coreIdentifier) {
        assertEquals(coreIdentifier.getVersion(), 1);
        assertEquals(coreIdentifier.getSensorIdType(), IdType.Physical);
        assertEquals(coreIdentifier.getPlatformIdType(), IdType.Managed);
        UUID expectedSensorUUID = UUID.fromString("F354666E-D552-4C0D-9168-A7454CEBA073");
        assertEquals(coreIdentifier.getSensorUUID(), expectedSensorUUID);
        UUID expectedPlatformUUID = UUID.fromString("840A4799-BBC0-4BD5-97A7-56F64092AF7B");
        assertEquals(coreIdentifier.getPlatformUUID(), expectedPlatformUUID);
        assertEquals(coreIdentifier.getWindowUUID(), null);
        assertEquals(coreIdentifier.getMinorUUID(), null);
        assertTrue(coreIdentifier.hasValidCheckValue());
        byte[] expectedBytes = new byte[] {(byte)0x01, (byte)0x68, (byte)0xF3, (byte)0x54, (byte)0x66, (byte)0x6E, (byte)0xD5, (byte)0x52, (byte)0x4C, (byte)0x0D, (byte)0x91, (byte)0x68, (byte)0xA7, (byte)0x45, (byte)0x4C, (byte)0xEB, (byte)0xA0, (byte)0x73, (byte)0x84, (byte)0x0A, (byte)0x47, (byte)0x99, (byte)0xBB, (byte)0xC0, (byte)0x4B, (byte)0xD5, (byte)0x97, (byte)0xA7, (byte)0x56, (byte)0xF6, (byte)0x40, (byte)0x92, (byte)0xAF, (byte)0x7B};
        assertEquals(coreIdentifier.getRawBytesRepresentation(), expectedBytes);
        String expectedText = "0168:F354-666E-D552-4C0D-9168-A745-4CEB-A073/840A-4799-BBC0-4BD5-97A7-56F6-4092-AF7B:AA";
        assertEquals(coreIdentifier.getTextRepresentation(), expectedText);
    }
}
