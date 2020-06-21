package org.jmisb.api.klv.st0903.vmask;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for the Pixel Number / Run Pair DTO. */
public class PixelRunPairTest {
    @Test
    public void checkPixelSet() {
        PixelRunPair uut = new PixelRunPair(1L, 2);
        assertEquals(uut.getPixelNumber(), 1L);
        uut.setPixelNumber(3L);
        assertEquals(uut.getPixelNumber(), 3L);
    }

    @Test
    public void setRunSet() {
        PixelRunPair uut = new PixelRunPair(1L, 2);
        assertEquals(uut.getRun(), 2);
        uut.setRun(4);
        assertEquals(uut.getRun(), 4);
    }
}
