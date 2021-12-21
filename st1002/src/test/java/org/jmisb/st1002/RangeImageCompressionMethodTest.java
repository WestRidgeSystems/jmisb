package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for RangeImageCompressionMethod. */
public class RangeImageCompressionMethodTest {

    @Test
    public void checkNoCompression() {
        RangeImageCompressionMethod uut = RangeImageCompressionMethod.NO_COMPRESSION;
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "No Compression");
    }

    @Test
    public void checkPlanarFit() {
        RangeImageCompressionMethod uut = RangeImageCompressionMethod.PLANAR_FIT;
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Planar Fit");
    }

    @Test
    public void checkLookupNoCompression() {
        RangeImageCompressionMethod uut = RangeImageCompressionMethod.lookup(0);
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "No Compression");
    }

    @Test
    public void checkLookupPlanar() {
        RangeImageCompressionMethod uut = RangeImageCompressionMethod.lookup(1);
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Planar Fit");
    }

    @Test
    public void checkLookupUnknownValue() {
        RangeImageCompressionMethod uut = RangeImageCompressionMethod.lookup(2);
        assertEquals(uut, RangeImageCompressionMethod.UNKNOWN);
        assertEquals(uut.getEncodedValue(), -1);
        assertEquals(uut.getTextDescription(), "Unknown");
    }
}
