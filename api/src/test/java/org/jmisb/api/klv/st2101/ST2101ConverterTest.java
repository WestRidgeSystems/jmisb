package org.jmisb.api.klv.st2101;

import static org.testng.Assert.*;

import java.util.UUID;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1204.IdType;
import org.testng.annotations.Test;

/**
 * Unit tests for ST2101Converter.
 *
 * <p>The values in this match the ST 2101 example.
 */
public class ST2101ConverterTest {
    private static final UUID PLATFORM_UUID =
            UUID.fromString("16B74341-0008-41A0-BE36-5B5AB96A3645");
    private static final UUID SENSOR_UUID = UUID.fromString("F592F023-7336-4AF8-AA91-62C00F2EB2DA");
    private static final byte[] ST2101_BYTES =
            new byte[] {
                (byte) 0xa5,
                (byte) 0x50,
                (byte) 0x52,
                (byte) 0xaf,
                (byte) 0x52,
                (byte) 0x16,
                (byte) 0x5f,
                (byte) 0x45,
                (byte) 0xa3,
                (byte) 0x18,
                (byte) 0x1c,
                (byte) 0xfc,
                (byte) 0x7a,
                (byte) 0xbb,
                (byte) 0xc2,
                (byte) 0x67,
                (byte) 0x01,
                (byte) 0x70,
                (byte) 0xF5,
                (byte) 0x92,
                (byte) 0xF0,
                (byte) 0x23,
                (byte) 0x73,
                (byte) 0x36,
                (byte) 0x4A,
                (byte) 0xF8,
                (byte) 0xAA,
                (byte) 0x91,
                (byte) 0x62,
                (byte) 0xC0,
                (byte) 0x0F,
                (byte) 0x2E,
                (byte) 0xB2,
                (byte) 0xDA,
                (byte) 0x16,
                (byte) 0xB7,
                (byte) 0x43,
                (byte) 0x41,
                (byte) 0x00,
                (byte) 0x08,
                (byte) 0x41,
                (byte) 0xA0,
                (byte) 0xBE,
                (byte) 0x36,
                (byte) 0x5B,
                (byte) 0x5A,
                (byte) 0xB9,
                (byte) 0x6A,
                (byte) 0x36,
                (byte) 0x45
            };

    @Test
    public void toBytes() {
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setVersion(1);
        coreIdentifier.setSensorUUID(IdType.Physical, SENSOR_UUID);
        coreIdentifier.setPlatformUUID(IdType.Virtual, PLATFORM_UUID);
        byte[] res = ST2101Converter.coreIdToSideDataBytes(coreIdentifier);
        assertEquals(res, ST2101_BYTES);
    }

    @Test
    public void toCoreId() {
        CoreIdentifier miisCoreIdentifier = ST2101Converter.decodeCoreId(ST2101_BYTES);
        assertNotNull(miisCoreIdentifier);
        assertEquals(miisCoreIdentifier.getVersion(), 1);
        assertEquals(miisCoreIdentifier.getSensorIdType(), IdType.Physical);
        assertEquals(miisCoreIdentifier.getSensorUUID(), SENSOR_UUID);
        assertEquals(miisCoreIdentifier.getPlatformIdType(), IdType.Virtual);
        assertEquals(miisCoreIdentifier.getPlatformUUID(), PLATFORM_UUID);
        assertNull(miisCoreIdentifier.getWindowUUID());
        assertNull(miisCoreIdentifier.getMinorUUID());
    }

    @Test
    public void checkBadLength() {
        CoreIdentifier miisCoreIdentifier =
                ST2101Converter.decodeCoreId(
                        new byte[] {
                            (byte) 0xa5,
                            (byte) 0x50,
                            (byte) 0x52,
                            (byte) 0xaf,
                            (byte) 0x52,
                            (byte) 0x16,
                            (byte) 0x5f,
                            (byte) 0x45,
                            (byte) 0xa3,
                            (byte) 0x18,
                            (byte) 0x1c,
                            (byte) 0xfc,
                            (byte) 0x7a,
                            (byte) 0xbb,
                            (byte) 0xc2,
                            (byte) 0x67,
                            (byte) 0x01,
                            (byte) 0x70
                        });
        assertNull(miisCoreIdentifier);
    }

    @Test
    public void checkBadUUID() {
        CoreIdentifier miisCoreIdentifier =
                ST2101Converter.decodeCoreId(
                        new byte[] {
                            (byte) 0xa5,
                            (byte) 0x50,
                            (byte) 0x52,
                            (byte) 0xaf,
                            (byte) 0x52,
                            (byte) 0x16,
                            (byte) 0x5f,
                            (byte) 0x45,
                            (byte) 0xa3,
                            (byte) 0x18,
                            (byte) 0x1c,
                            (byte) 0xfc,
                            (byte) 0x7a,
                            (byte) 0xbc,
                            (byte) 0xc2,
                            (byte) 0x67,
                            (byte) 0x01,
                            (byte) 0x70,
                            (byte) 0xF5,
                            (byte) 0x92,
                            (byte) 0xF0,
                            (byte) 0x23,
                            (byte) 0x73,
                            (byte) 0x36,
                            (byte) 0x4A,
                            (byte) 0xF8,
                            (byte) 0xAA,
                            (byte) 0x91,
                            (byte) 0x62,
                            (byte) 0xC0,
                            (byte) 0x0F,
                            (byte) 0x2E,
                            (byte) 0xB2,
                            (byte) 0xDA,
                            (byte) 0x16,
                            (byte) 0xB7,
                            (byte) 0x43,
                            (byte) 0x41,
                            (byte) 0x00,
                            (byte) 0x08,
                            (byte) 0x41,
                            (byte) 0xA0,
                            (byte) 0xBE,
                            (byte) 0x36,
                            (byte) 0x5B,
                            (byte) 0x5A,
                            (byte) 0xB9,
                            (byte) 0x6A,
                            (byte) 0x36,
                            (byte) 0x45
                        });
        assertNull(miisCoreIdentifier);
    }
}
