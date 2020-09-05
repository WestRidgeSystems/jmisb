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
                    (byte) 0x99,
                    0x4d
                };
        MIMD mimd = new MIMD(bytes);
        assertEquals(mimd.displayHeader(), "MIMD");
        assertEquals(mimd.getUniversalLabel(), KlvConstants.MIMDLocalSetUl);
        assertEquals(mimd.getIdentifiers().size(), 1);
        assertTrue(mimd.getIdentifiers().contains(MIMDMetadataKey.version));
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayName(), "Version");
        assertEquals(mimd.getField(MIMDMetadataKey.version).getDisplayableValue(), "1");
    }
}
