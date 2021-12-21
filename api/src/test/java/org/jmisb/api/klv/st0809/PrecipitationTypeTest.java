package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for PrecipitationType. */
public class PrecipitationTypeTest {

    @Test
    public void fromValue() {
        PrecipitationType uut = new PrecipitationType("rain");
        assertEquals(uut.getValue(), "rain");
        assertEquals(uut.getDisplayName(), "Precipitation Type");
        assertEquals(uut.getDisplayableValue(), "rain");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6e});
    }

    @Test
    public void fromBytes() {
        PrecipitationType uut =
                new PrecipitationType(
                        new byte[] {(byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6e});
        assertEquals(uut.getValue(), "rain");
        assertEquals(uut.getDisplayName(), "Precipitation Type");
        assertEquals(uut.getDisplayableValue(), "rain");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6e});
    }

    @Test
    public void fromFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.PrecipitationType,
                        new byte[] {(byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6e});
        assertTrue(v instanceof PrecipitationType);
        PrecipitationType uut = (PrecipitationType) v;
        assertEquals(uut.getValue(), "rain");
        assertEquals(uut.getDisplayName(), "Precipitation Type");
        assertEquals(uut.getDisplayableValue(), "rain");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x72, (byte) 0x61, (byte) 0x69, (byte) 0x6e});
    }
}
