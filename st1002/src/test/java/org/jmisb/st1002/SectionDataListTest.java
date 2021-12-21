package org.jmisb.st1002;

import static org.testng.Assert.*;

import java.util.Set;
import org.jmisb.api.klv.IKlvKey;
import org.testng.annotations.Test;

/** Unit tests for SectionDataList. */
public class SectionDataListTest {

    @Test
    public void checkDisplayName() {
        SectionDataList uut = new SectionDataList();
        assertEquals(uut.getDisplayName(), "Section Data");
    }

    @Test
    public void checkDisplayableValue() {
        SectionDataList uut = new SectionDataList();
        assertEquals(uut.getDisplayableValue(), "[VLPs]");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void checkBadGetBytesOperation() {
        SectionDataList uut = new SectionDataList();
        uut.getBytes();
    }

    @Test
    public void checkWithNoElements() {
        SectionDataList uut = new SectionDataList();
        assertTrue(uut.getPacks().isEmpty());
        assertTrue(uut.getIdentifiers().isEmpty());
        SectionDataIdentifierKey someKeyNotPresent = new SectionDataIdentifierKey(1, 1);
        assertNull(uut.getField(someKeyNotPresent));
    }

    @Test
    public void checkWithTwoSectionDataElements() {
        SectionDataList uut = new SectionDataList();
        SectionData sectionData1 = new SectionData(1, 1, new double[][] {}, null);
        uut.add(sectionData1);
        SectionData sectionData2 = new SectionData(1, 2, new double[][] {}, null);
        uut.add(sectionData2);
        assertFalse(uut.getPacks().isEmpty());
        Set<? extends IKlvKey> identifiers = uut.getIdentifiers();
        assertEquals(identifiers.size(), 2);
        int totalYnumbers = 0;
        for (var key : identifiers) {
            var field = uut.getField(key);
            assertTrue(field instanceof SectionData);
            SectionData sectionData = (SectionData) field;
            assertEquals(sectionData.getSectionNumberX(), 1);
            totalYnumbers += sectionData.getSectionNumberY();
        }
        assertEquals(totalYnumbers, 3);
        SectionDataIdentifierKey someKeyNotPresent = new SectionDataIdentifierKey(2, 1);
        assertNull(uut.getField(someKeyNotPresent));
    }
}
