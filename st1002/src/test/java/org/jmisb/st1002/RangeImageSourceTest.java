package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for RangeImageSource. */
public class RangeImageSourceTest {

    @Test
    public void checkComputationallyExtracted() {
        RangeImageSource uut = RangeImageSource.COMPUTATIONALLY_EXTRACTED;
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "Computationally Extracted");
    }

    @Test
    public void checkRangeSensor() {
        RangeImageSource uut = RangeImageSource.RANGE_SENSOR;
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Range Sensor");
    }

    @Test
    public void checkLookupComputationallyExtracted() {
        RangeImageSource uut = RangeImageSource.lookup(0);
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "Computationally Extracted");
    }

    @Test
    public void checkLookupRangeSensor() {
        RangeImageSource uut = RangeImageSource.lookup(1);
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Range Sensor");
    }

    @Test
    public void checkLookupUnknownValue() {
        RangeImageSource uut = RangeImageSource.lookup(2);
        assertEquals(uut, RangeImageSource.UNKNOWN);
        assertEquals(uut.getEncodedValue(), -1);
        assertEquals(uut.getTextDescription(), "Unknown");
    }
}
