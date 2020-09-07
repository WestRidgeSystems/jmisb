package org.jmisb.api.klv.st190x;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st1903.MIMD;
import org.jmisb.api.klv.st1903.MIMDMetadataKey;
import org.testng.annotations.Test;

/** Basic parse tests for MIMD. */
public class MimdParseTest {

    public MimdParseTest() {}

    @Test
    public void parseVersionNumber() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x05,
                    0x03,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x05,
                    0x21,
                    0x01,
                    0x31,
                    (byte) 0xE7,
                    (byte) 0x92
                };
        MIMD mimd = new MIMD(bytes);
        assertEquals(mimd.displayHeader(), "MIMD");
        assertEquals(mimd.getUniversalLabel(), KlvConstants.MIMDLocalSetUl);
        assertEquals(mimd.getIdentifiers().size(), 1);
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.version));
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayName(), "Version");
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayableValue(), "1");
    }

    @Test
    public void parseMissionSupportProfile() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x05,
                    0x03,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    // Length
                    3 + 17 + 2,
                    // version
                    0x21,
                    0x01,
                    0x31,
                    // LIST<Timers>
                    0x26,
                    0x0E,
                    // Timer 0
                    0x0D,
                    // Timer 0 nanoPrecisionTimestamp
                    0x21,
                    0x08,
                    0x16,
                    0x2C,
                    (byte) 0x86,
                    0x31,
                    0x7D,
                    0x78,
                    0x15,
                    (byte) 0xF9,
                    // Timer 0 utcLeapSeconds
                    0x22,
                    0x01,
                    0x24,
                    // Check value
                    (byte) 0xD8,
                    (byte) 0x62
                };
        MIMD mimd = new MIMD(bytes);
        assertEquals(mimd.displayHeader(), "MIMD");
        assertEquals(mimd.getUniversalLabel(), KlvConstants.MIMDLocalSetUl);
        assertEquals(mimd.getIdentifiers().size(), 2);
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.version));
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayName(), "Version");
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayableValue(), "1");
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.timers));
        assertEquals(mimd.getField(MIMDMetadataKey.timers).getDisplayName(), "Timers");
        assertEquals(mimd.getField(MIMDMetadataKey.timers).getDisplayableValue(), "[Timers]");
    }
}
