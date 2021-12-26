package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ReferenceSource enumeration. */
public class ReferenceSourceTest {
    @Test
    public void check0() {
        ReferenceSource uut = ReferenceSource.Unknown;
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(uut.getDisplayableValue(), "Reference Source status is unknown");
        assertEquals(uut.getValue(), 0);
        assertEquals(uut.getMeaning(), "Reference Source status is unknown");
        assertEquals(uut, ReferenceSource.Unknown);
    }

    @Test
    public void check1() {
        ReferenceSource uut = ReferenceSource.NotSynchronized;
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(
                uut.getDisplayableValue(),
                "Reference Source is not synchronized to an atomic source");
        assertEquals(uut.getValue(), 1);
        assertEquals(uut.getMeaning(), "Reference Source is not synchronized to an atomic source");
        assertEquals(uut, ReferenceSource.NotSynchronized);
    }

    @Test
    public void check2() {
        ReferenceSource uut = ReferenceSource.Synchronized;
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(
                uut.getDisplayableValue(), "Reference Source is synchronized to an atomic source");
        assertEquals(uut.getValue(), 2);
        assertEquals(uut.getMeaning(), "Reference Source is synchronized to an atomic source");
        assertEquals(uut, ReferenceSource.Synchronized);
    }

    @Test
    public void check3() {
        ReferenceSource uut = ReferenceSource.Reserved;
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use");
        assertEquals(uut.getValue(), 3);
        assertEquals(uut.getMeaning(), "Reserved for future use");
        assertEquals(uut, ReferenceSource.Reserved);
    }

    @Test
    public void checkLookup2() {
        ReferenceSource uut = ReferenceSource.lookupValue(2);
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(
                uut.getDisplayableValue(), "Reference Source is synchronized to an atomic source");
        assertEquals(uut.getValue(), 2);
        assertEquals(uut.getMeaning(), "Reference Source is synchronized to an atomic source");
        assertEquals(uut, ReferenceSource.Synchronized);
    }

    @Test
    public void checkUnknownLookup() {
        ReferenceSource uut = ReferenceSource.lookupValue(4);
        assertEquals(uut.getDisplayName(), "Reference Source");
        assertEquals(uut.getDisplayableValue(), "Reference Source status is unknown");
        assertEquals(uut.getValue(), 0);
        assertEquals(uut.getMeaning(), "Reference Source status is unknown");
        assertEquals(uut, ReferenceSource.Unknown);
    }
}
