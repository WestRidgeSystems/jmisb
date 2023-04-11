package org.jmisb.st1002;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for SectionDataIdentifierKey. */
public class SectionDataIdentifierKeyTest {

    @Test
    public void checkDisplayName() {
        SectionDataIdentifierKey uut = new SectionDataIdentifierKey(3, 1);
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void checkSectionNumbers() {
        SectionDataIdentifierKey uut = new SectionDataIdentifierKey(3, 1);
        assertEquals(uut.getSectionX(), 3);
        assertEquals(uut.getSectionY(), 1);
    }

    @Test
    public void checkSectionNumbersY() {
        SectionDataIdentifierKey uut = new SectionDataIdentifierKey(1, 3);
        assertEquals(uut.getSectionX(), 1);
        assertEquals(uut.getSectionY(), 3);
    }

    @Test
    public void checkHash() {
        SectionDataIdentifierKey uut = new SectionDataIdentifierKey(3, 1);
        assertEquals(uut.hashCode(), 2611);
    }

    @Test
    public void checkEquals() {
        SectionDataIdentifierKey uut = new SectionDataIdentifierKey(3, 1);
        assertFalse(uut.equals(null));
        assertTrue(uut.equals(new SectionDataIdentifierKey(3, 1)));
        assertFalse(uut.equals(new SectionDataIdentifierKey(2, 1)));
        assertFalse(uut.equals(new SectionDataIdentifierKey(1, 3)));
        assertFalse(uut.equals(new SectionDataIdentifierKey(3, 3)));
        assertFalse(uut.equals(3));
        assertTrue(uut.equals(uut));
    }

    @Test
    public void checkComparison() {
        SectionDataIdentifierKey uut1 = new SectionDataIdentifierKey(1, 3);
        SectionDataIdentifierKey uut2 = new SectionDataIdentifierKey(1, 2);
        assertEquals(uut1.compareTo(uut2), 1);
        assertEquals(uut2.compareTo(uut1), -1);
        assertEquals(uut1.compareTo(uut1), 0);
    }
}
