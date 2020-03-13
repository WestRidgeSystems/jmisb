package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests for Target Centroid (Tag 1)
 */
public class TargetCentroidTest {
    
    public TargetCentroidTest() {
    }

    @Ignore
    @Test
    public void testConstructFromValue()
    {
        TargetCentroid systemName = new TargetCentroid(409600);
        assertEquals(systemName.getBytes(), new byte[]{(byte)0x06, (byte)0x40, (byte)0x00});
        assertEquals(systemName.getDisplayName(), "Target Centroid");
        assertEquals(systemName.getDisplayableValue(), "409600");
        assertEquals(systemName.getPixelNumber(), 409600L);
    }
}
