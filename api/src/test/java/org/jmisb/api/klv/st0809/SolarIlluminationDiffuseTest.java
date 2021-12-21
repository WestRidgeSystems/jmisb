package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SolarIlluminationDiffuse. */
public class SolarIlluminationDiffuseTest {

    @Test
    public void fromValue() {
        SolarIlluminationDiffuse uut = new SolarIlluminationDiffuse(60.0f);
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Diffuse");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        SolarIlluminationDiffuse uut =
                new SolarIlluminationDiffuse(
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Diffuse");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.SolarIlluminationDiffuse,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof SolarIlluminationDiffuse);
        SolarIlluminationDiffuse uut = (SolarIlluminationDiffuse) v;
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Diffuse");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new SolarIlluminationDiffuse(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new SolarIlluminationDiffuse(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new SolarIlluminationDiffuse(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
