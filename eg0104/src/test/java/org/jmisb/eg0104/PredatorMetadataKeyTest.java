package org.jmisb.eg0104;

import static org.testng.Assert.*;

import org.jmisb.api.klv.UniversalLabel;
import org.testng.annotations.Test;

public class PredatorMetadataKeyTest {
    @Test
    public void checkUnknownKey() {
        assertEquals(
                PredatorMetadataKey.getKey(
                        new UniversalLabel(
                                new byte[] {
                                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01,
                                    0x03, 0x05, 0x05, 0x00, 0x00, 0x00
                                })),
                PredatorMetadataKey.Undefined);
    }

    @Test
    public void checkGetter() {
        assertEquals(
                PredatorMetadataKey.AngleToNorth.getUl(), PredatorMetadataConstants.ANGLE_TO_NORTH);
    }

    @Test
    public void checkKey() {
        assertEquals(PredatorMetadataKey.AngleToNorth.getIdentifier(), 118);
    }
}
