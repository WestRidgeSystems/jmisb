package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for LightingConditions. */
public class LightingConditionsTest {

    @Test
    public void fromValue() {
        LightingConditions uut = new LightingConditions("dawn, partly cloudy");
        assertEquals(uut.getValue(), "dawn, partly cloudy");
        assertEquals(uut.getDisplayName(), "Lighting Conditions");
        assertEquals(uut.getDisplayableValue(), "dawn, partly cloudy");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x64, 0x61, 0x77, 0x6e, 0x2c, 0x20, 0x70, 0x61, 0x72, 0x74, 0x6c, 0x79, 0x20,
                    0x63, 0x6c, 0x6f, 0x75, 0x64, 0x79
                });
    }

    @Test
    public void fromBytes() {
        LightingConditions uut =
                new LightingConditions(
                        new byte[] {
                            0x64, 0x61, 0x77, 0x6e, 0x2c, 0x20, 0x70, 0x61, 0x72, 0x74, 0x6c, 0x79,
                            0x20, 0x63, 0x6c, 0x6f, 0x75, 0x64, 0x79
                        });
        assertEquals(uut.getValue(), "dawn, partly cloudy");
        assertEquals(uut.getDisplayName(), "Lighting Conditions");
        assertEquals(uut.getDisplayableValue(), "dawn, partly cloudy");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x64, 0x61, 0x77, 0x6e, 0x2c, 0x20, 0x70, 0x61, 0x72, 0x74, 0x6c, 0x79, 0x20,
                    0x63, 0x6c, 0x6f, 0x75, 0x64, 0x79
                });
    }

    @Test
    public void fromFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.LightingConditions,
                        new byte[] {
                            0x64, 0x61, 0x77, 0x6e, 0x2c, 0x20, 0x70, 0x61, 0x72, 0x74, 0x6c, 0x79,
                            0x20, 0x63, 0x6c, 0x6f, 0x75, 0x64, 0x79
                        });
        assertTrue(v instanceof LightingConditions);
        LightingConditions uut = (LightingConditions) v;
        assertEquals(uut.getValue(), "dawn, partly cloudy");
        assertEquals(uut.getDisplayName(), "Lighting Conditions");
        assertEquals(uut.getDisplayableValue(), "dawn, partly cloudy");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x64, 0x61, 0x77, 0x6e, 0x2c, 0x20, 0x70, 0x61, 0x72, 0x74, 0x6c, 0x79, 0x20,
                    0x63, 0x6c, 0x6f, 0x75, 0x64, 0x79
                });
    }
}
