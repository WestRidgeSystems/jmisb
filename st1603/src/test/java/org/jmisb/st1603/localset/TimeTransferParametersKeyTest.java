package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.TimeTransferParametersKey;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for {@link org.jmisb.api.klv.st1603.localset.TimeTransferParametersKey}. */
public class TimeTransferParametersKeyTest {

    @Test
    public void check1() {
        TimeTransferParametersKey uut = TimeTransferParametersKey.ReferenceSource;
        assertEquals(uut.getIdentifier(), 1);
        assertEquals(uut, TimeTransferParametersKey.ReferenceSource);
        assertNotEquals(uut, TimeTransferParametersKey.CorrectionMethod);
        assertNotEquals(uut, TimeTransferParametersKey.TimeTransferMethod);
    }

    @Test
    public void check2() {
        TimeTransferParametersKey uut = TimeTransferParametersKey.CorrectionMethod;
        assertEquals(uut.getIdentifier(), 2);
        assertEquals(uut, TimeTransferParametersKey.CorrectionMethod);
        assertNotEquals(uut, TimeTransferParametersKey.TimeTransferMethod);
        assertNotEquals(uut, TimeTransferParametersKey.ReferenceSource);
    }

    @Test
    public void check3() {
        TimeTransferParametersKey uut = TimeTransferParametersKey.TimeTransferMethod;
        assertEquals(uut.getIdentifier(), 3);
        assertEquals(uut, TimeTransferParametersKey.TimeTransferMethod);
        assertNotEquals(uut, TimeTransferParametersKey.ReferenceSource);
        assertNotEquals(uut, TimeTransferParametersKey.CorrectionMethod);
    }
}
