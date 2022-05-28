package org.jmisb.st0903vtrack;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for NumTrackPoints class. */
public class NumTrackPointsTest {

    public NumTrackPointsTest() {}

    @Test
    public void checkFromValue() {
        NumTrackPoints numTrackPoints = new NumTrackPoints(384);
        assertEquals(numTrackPoints.getDisplayName(), "Num Track Points");
        assertEquals(numTrackPoints.getDisplayableValue(), "384");
    }

    @Test
    public void checkFromBytes() {
        NumTrackPoints numTrackPoints = new NumTrackPoints(new byte[] {0x01, 0x7E});
        assertEquals(numTrackPoints.getDisplayName(), "Num Track Points");
        assertEquals(numTrackPoints.getDisplayableValue(), "382");
    }
}
