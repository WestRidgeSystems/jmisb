package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for TotalIlluminationDirect. */
public class TotalIlluminationDirectTest {

    @Test
    public void fromValue() {
        TotalIlluminationDirect uut = new TotalIlluminationDirect(60.0f);
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Total Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        TotalIlluminationDirect uut =
                new TotalIlluminationDirect(
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Total Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.TotalIlluminationDirect,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof TotalIlluminationDirect);
        TotalIlluminationDirect uut = (TotalIlluminationDirect) v;
        assertEquals(uut.getIllumination(), 60.0f);
        assertEquals(uut.getDisplayName(), "Total Illumination Direct");
        assertEquals(uut.getDisplayableValue(), "60.000 lx");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new TotalIlluminationDirect(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new TotalIlluminationDirect(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new TotalIlluminationDirect(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
