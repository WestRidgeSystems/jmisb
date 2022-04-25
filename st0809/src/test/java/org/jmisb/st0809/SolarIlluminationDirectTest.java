package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SolarIlluminationDirect. */
public class SolarIlluminationDirectTest {

    @Test
    public void fromValue() {
        SolarIlluminationDirect uut = new SolarIlluminationDirect(60.0f);
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        SolarIlluminationDirect uut =
                new SolarIlluminationDirect(
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.SolarIlluminationDirect,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof SolarIlluminationDirect);
        SolarIlluminationDirect uut = (SolarIlluminationDirect) v;
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Solar Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new SolarIlluminationDirect(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new SolarIlluminationDirect(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new SolarIlluminationDirect(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
