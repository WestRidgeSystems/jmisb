package org.jmisb.api.klv.eg0104;

import org.jmisb.api.klv.KlvConstants;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class PredatorMetadataKeyTest
{
    @Test
    public void checkUnknownKey()
    {
        assertEquals(PredatorMetadataKey.getKey(KlvConstants.VmtiLocalSetUl), PredatorMetadataKey.Undefined);
    }
    
    @Test
    public void checkGetter()
    {
        assertEquals(PredatorMetadataKey.AngleToNorth.getUl(), PredatorMetadataConstants.ANGLE_TO_NORTH);
    }
}
