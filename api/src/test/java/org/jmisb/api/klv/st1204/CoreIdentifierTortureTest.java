package org.jmisb.api.klv.st1204;

import static org.testng.Assert.*;

import java.util.UUID;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;
import uk.org.lidalia.slf4jtest.LoggingEvent;

public class CoreIdentifierTortureTest extends LoggerChecks {

    public CoreIdentifierTortureTest() {
        super(CoreIdentifier.class);
    }

    @Test
    public void badCheckDigitsC7() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:C7");
        verifyCoreIdentifierE1BadCheck(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    @Test
    public void badCheckDigitsD8() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863:D7");
        verifyCoreIdentifierE1BadCheck(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    private void verifyCoreIdentifierE1BadCheck(CoreIdentifier coreIdentifier) {
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
        assertFalse(coreIdentifier.hasValidCheckValue());
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
        this.verifyNoLoggerMessages();
    }

    @Test
    public void noCheckDigits() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863");
        verifyCoreIdentifierE1BadCheck(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    @Test
    public void badLengthVerUsage() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "015:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863");
        assertNull(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    @Test
    public void badNumberOfParts() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB/5E71-B0DC-20FE-4920-8216-26D6-4F61-D863");
        assertNull(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    @Test
    public void badNumberOfIds() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromString(
                        "0154:C7D1-6253-98A2-41C2-BA6E-90F8-FCC7-3914/E047-AB3E-81BE-41ED-9664-09B0-2F44-5FAB:C8");
        assertNull(coreIdentifier);
        this.verifyNoLoggerMessages();
    }

    @Test
    public void badByteLengthHeader() {
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(new byte[] {0x01});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Core Identifier usage");
        LOGGER.clear();
    }

    @Test
    public void badByteLengthSensorId() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromBytes(
                        new byte[] {0x01, 0x40, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Sensor UUID");
        LOGGER.clear();
    }

    @Test
    public void badByteLengthPlatformId() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromBytes(
                        new byte[] {0x01, 0x10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Platform UUID");
        LOGGER.clear();
    }

    @Test
    public void badByteLengthWindowId() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromBytes(
                        new byte[] {0x01, 0x04, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Window UUID");
        LOGGER.clear();
    }

    @Test
    public void badByteLengthMinorId() {
        CoreIdentifier coreIdentifier =
                CoreIdentifier.fromBytes(
                        new byte[] {0x01, 0x02, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Minor UUID");
        LOGGER.clear();
    }

    @Test
    public void badByteLengthUsage() {
        CoreIdentifier coreIdentifier = CoreIdentifier.fromBytes(new byte[] {(byte) 0x81, 0x00});
        assertNull(coreIdentifier);
        assertEquals(LOGGER.getLoggingEvents().size(), 1);
        LoggingEvent event = LOGGER.getLoggingEvents().get(0);
        assertEquals(event.getMessage(), "Insufficient bytes to read MIIS Core Identifier usage");
        LOGGER.clear();
    }
}
