package org.jmisb.api.klv.eg0104;

import static org.testng.Assert.*;

import org.jmisb.api.klv.KlvConstants;
import org.testng.annotations.Test;

public class PredatorMetadataKeyTest {
    @Test
    public void checkUnknownKey() {
        assertEquals(
                PredatorMetadataKey.getKey(KlvConstants.GeneralizedTransformationUl),
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
