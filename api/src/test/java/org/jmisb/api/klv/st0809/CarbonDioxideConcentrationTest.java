package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CarbonDioxideConcentration. */
public class CarbonDioxideConcentrationTest {

    @Test
    public void fromValue() {
        CarbonDioxideConcentration uut = new CarbonDioxideConcentration(460.0f);
        assertEquals(uut.getConcentration(), 460.0f);
        assertEquals(uut.getDisplayName(), "Carbon Dioxide Concentration");
        assertEquals(uut.getDisplayableValue(), "460.00 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x43, (byte) 0xe6, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        CarbonDioxideConcentration uut =
                new CarbonDioxideConcentration(
                        new byte[] {(byte) 0x43, (byte) 0xe6, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getConcentration(), 460.0f);
        assertEquals(uut.getDisplayName(), "Carbon Dioxide Concentration");
        assertEquals(uut.getDisplayableValue(), "460.00 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x43, (byte) 0xe6, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CarbonDioxideConcentration,
                        new byte[] {(byte) 0x43, (byte) 0xe6, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof CarbonDioxideConcentration);
        CarbonDioxideConcentration uut = (CarbonDioxideConcentration) v;
        assertEquals(uut.getConcentration(), 460.0f);
        assertEquals(uut.getDisplayName(), "Carbon Dioxide Concentration");
        assertEquals(uut.getDisplayableValue(), "460.00 ppm");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x43, (byte) 0xe6, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new CarbonDioxideConcentration(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new CarbonDioxideConcentration(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new CarbonDioxideConcentration(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
