package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for OzoneConcentration. */
public class OzoneConcentrationTest {

    @Test
    public void fromValue() {
        OzoneConcentration uut = new OzoneConcentration(0.02f);
        assertEquals(uut.getConcentration(), 0.02f);
        assertEquals(uut.getDisplayName(), "Ozone Concentration");
        assertEquals(uut.getDisplayableValue(), "0.020 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xa3, (byte) 0xd7, (byte) 0x0a});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        OzoneConcentration uut =
                new OzoneConcentration(
                        new byte[] {(byte) 0x3c, (byte) 0xa3, (byte) 0xd7, (byte) 0x0a});
        assertEquals(uut.getConcentration(), 0.02f);
        assertEquals(uut.getDisplayName(), "Ozone Concentration");
        assertEquals(uut.getDisplayableValue(), "0.020 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xa3, (byte) 0xd7, (byte) 0x0a});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.OzoneConcentration,
                        new byte[] {(byte) 0x3c, (byte) 0xa3, (byte) 0xd7, (byte) 0x0a});
        assertTrue(v instanceof OzoneConcentration);
        OzoneConcentration uut = (OzoneConcentration) v;
        assertEquals(uut.getConcentration(), 0.02f);
        assertEquals(uut.getDisplayName(), "Ozone Concentration");
        assertEquals(uut.getDisplayableValue(), "0.020 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3c, (byte) 0xa3, (byte) 0xd7, (byte) 0x0a});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new OzoneConcentration(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new OzoneConcentration(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new OzoneConcentration(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
