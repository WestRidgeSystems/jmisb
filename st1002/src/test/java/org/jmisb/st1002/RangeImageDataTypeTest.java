package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for RangeImageDataType. */
public class RangeImageDataTypeTest {

    @Test
    public void checkPerspective() {
        RangeImageryDataType uut = RangeImageryDataType.PERSPECTIVE;
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "Perspective Range Image");
    }

    @Test
    public void checkDepth() {
        RangeImageryDataType uut = RangeImageryDataType.DEPTH;
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Depth Range Image");
    }

    @Test
    public void checkLookupPerspective() {
        RangeImageryDataType uut = RangeImageryDataType.lookup(0);
        assertEquals(uut.getEncodedValue(), 0);
        assertEquals(uut.getTextDescription(), "Perspective Range Image");
    }

    @Test
    public void checkLookupDepth() {
        RangeImageryDataType uut = RangeImageryDataType.lookup(1);
        assertEquals(uut.getEncodedValue(), 1);
        assertEquals(uut.getTextDescription(), "Depth Range Image");
    }

    @Test
    public void checkLookupUnknownValue() {
        RangeImageryDataType uut = RangeImageryDataType.lookup(2);
        assertEquals(uut, RangeImageryDataType.UNKNOWN);
        assertEquals(uut.getEncodedValue(), -1);
        assertEquals(uut.getTextDescription(), "Unknown");
    }
}
