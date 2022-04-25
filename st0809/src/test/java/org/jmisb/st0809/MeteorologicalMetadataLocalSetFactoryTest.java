package org.jmisb.st0809;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for the ST 0809 Meteorological Metadata Local Set Factory. */
public class MeteorologicalMetadataLocalSetFactoryTest {

    @Test
    public void parseTags() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02
                };
        MeteorologicalMetadataLocalSetFactory factory = new MeteorologicalMetadataLocalSetFactory();
        MeteorologicalMetadataLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(
                localSet.getUniversalLabel(),
                MeteorologicalMetadataLocalSet.MeteorologicalMetadataLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 2);
    }
}
