package org.jmisb.api.klv.st1902;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.st1903.MIMD;
import org.jmisb.api.klv.st1903.MIMDMetadataKey;
import org.jmisb.api.klv.st1903.MIMD_Version;
import org.testng.annotations.Test;

/** Basic parse tests for MIMD. */
public class MimdParseTest {

    public MimdParseTest() {}

    byte[] messageWithVersionBytes =
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
                0x01,
                (byte) 0xd1,
                (byte) 0xc1
            };

    byte[] messageWithVersionBytesBadChecksum1 =
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
                0x01,
                (byte) 0xE8,
                (byte) 0x92
            };

    byte[] messageWithVersionBytesBadChecksum2 =
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
                0x01,
                (byte) 0xE7,
                (byte) 0x91
            };

    byte[] missionSupportProfileBytes =
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
                3 + 16 + 2,
                // version
                0x21,
                0x01,
                0x01,
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
                (byte) 0xfa,
                (byte) 0x61
            };

    @Test
    public void parseVersionNumber() throws KlvParseException {
        MIMD mimd = new MIMD(messageWithVersionBytes);
        checkVersionNumberParse(mimd);
    }

    @Test
    public void parseVersionNumberFactory() throws KlvParseException {
        MimdLocalSetFactory factory = new MimdLocalSetFactory();
        MIMD mimd = factory.create(messageWithVersionBytes);
        checkVersionNumberParse(mimd);
    }

    private void checkVersionNumberParse(MIMD mimd) {
        assertEquals(mimd.displayHeader(), "MIMD");
        assertEquals(mimd.getUniversalLabel(), KlvConstants.MIMDLocalSetUl);
        assertEquals(mimd.getIdentifiers().size(), 1);
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.version));
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayName(), "Version");
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayableValue(), "1");
        assertEquals(mimd.frameMessage(false), messageWithVersionBytes);
        assertEquals(mimd.frameMessage(true), new byte[] {0x21, 0x01, 0x01});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseOverrun() throws KlvParseException {
        new MIMD(
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03, 0x00,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x00, 0x00
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseVersionNumberBadChecksum1() throws KlvParseException {
        new MIMD(messageWithVersionBytesBadChecksum1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseVersionNumberBadChecksum2() throws KlvParseException {
        new MIMD(messageWithVersionBytesBadChecksum2);
    }

    @Test
    public void versionNumberFromValue() throws KlvParseException {
        Map<MIMDMetadataKey, IMimdMetadataValue> values = new HashMap<>();
        values.put(MIMDMetadataKey.version, new MIMD_Version(1));
        MIMD mimd = new MIMD(values);
        checkVersionNumberParse(mimd);
    }

    @Test
    public void parseMissionSupportProfile() throws KlvParseException {
        MIMD mimd = new MIMD(missionSupportProfileBytes);
        assertEquals(mimd.displayHeader(), "MIMD");
        assertEquals(mimd.getUniversalLabel(), KlvConstants.MIMDLocalSetUl);
        assertEquals(mimd.getIdentifiers().size(), 2);
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.version));
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayName(), "Version");
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayableValue(), "1");
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.timers));
        assertEquals(mimd.getField(MIMDMetadataKey.timers).getDisplayName(), "Timers");
        assertEquals(mimd.getField(MIMDMetadataKey.timers).getDisplayableValue(), "LIST[Timer]");
        assertEquals(mimd.frameMessage(false), missionSupportProfileBytes);
    }
}
