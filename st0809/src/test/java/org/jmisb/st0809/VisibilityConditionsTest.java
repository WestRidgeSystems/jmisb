package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for VisibilityConditions. */
public class VisibilityConditionsTest {

    @Test
    public void fromValue() {
        VisibilityConditions uut = new VisibilityConditions("clear");
        assertEquals(uut.getValue(), "clear");
        assertEquals(uut.getDisplayName(), "Visibility Conditions");
        assertEquals(uut.getDisplayableValue(), "clear");
        assertEquals(
                uut.getBytes(),
                new byte[] {(byte) 0x63, (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x72});
    }

    @Test
    public void fromBytes() {
        VisibilityConditions uut =
                new VisibilityConditions(
                        new byte[] {
                            (byte) 0x63, (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x72
                        });
        assertEquals(uut.getValue(), "clear");
        assertEquals(uut.getDisplayName(), "Visibility Conditions");
        assertEquals(uut.getDisplayableValue(), "clear");
        assertEquals(
                uut.getBytes(),
                new byte[] {(byte) 0x63, (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x72});
    }

    @Test
    public void fromFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.VisibilityConditions,
                        new byte[] {
                            (byte) 0x63, (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x72
                        });
        assertTrue(v instanceof VisibilityConditions);
        VisibilityConditions uut = (VisibilityConditions) v;
        assertEquals(uut.getValue(), "clear");
        assertEquals(uut.getDisplayName(), "Visibility Conditions");
        assertEquals(uut.getDisplayableValue(), "clear");
        assertEquals(
                uut.getBytes(),
                new byte[] {(byte) 0x63, (byte) 0x6c, (byte) 0x65, (byte) 0x61, (byte) 0x72});
    }
}
