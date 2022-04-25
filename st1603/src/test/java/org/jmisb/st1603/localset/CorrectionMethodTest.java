package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.CorrectionMethod;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for CorrectionMethod enumeration. */
public class CorrectionMethodTest {

    @Test
    public void check0() {
        CorrectionMethod uut = CorrectionMethod.Unknown;
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Unknown or No Correction");
        assertEquals(uut.getMeaning(), "Unknown or No Correction");
        assertEquals(uut.getValue(), 0);
    }

    @Test
    public void check1() {
        CorrectionMethod uut = CorrectionMethod.JamCorrection;
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Jam Correction");
        assertEquals(uut.getMeaning(), "Jam Correction");
        assertEquals(uut.getValue(), 1);
    }

    @Test
    public void check2() {
        CorrectionMethod uut = CorrectionMethod.SlewCorrection;
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Slew Correction");
        assertEquals(uut.getMeaning(), "Slew Correction");
        assertEquals(uut.getValue(), 2);
    }

    @Test
    public void check3() {
        CorrectionMethod uut = CorrectionMethod.Reserved;
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Reserved for future use");
        assertEquals(uut.getMeaning(), "Reserved for future use");
        assertEquals(uut.getValue(), 3);
    }

    @Test
    public void checkLookup2() {
        CorrectionMethod uut = CorrectionMethod.lookupValue(2);
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Slew Correction");
        assertEquals(uut.getMeaning(), "Slew Correction");
        assertEquals(uut.getValue(), 2);
    }

    @Test
    public void checkLookupUnknown() {
        CorrectionMethod uut = CorrectionMethod.lookupValue(4);
        assertEquals(uut.getDisplayName(), "Correction Method");
        assertEquals(uut.getDisplayableValue(), "Unknown or No Correction");
        assertEquals(uut.getMeaning(), "Unknown or No Correction");
        assertEquals(uut.getValue(), 0);
    }
}
