package org.jmisb.api.klv.st1603.nanopack;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/** Unit tests for NanoTimeTransferPackKey. */
public class NanoTimeTransferPackKeyTest {

    @Test
    public void checkNanoTimeStamp() {
        NanoTimeTransferPackKey uut = NanoTimeTransferPackKey.NanoPrecisionTimeStamp;
        assertEquals(uut.getIdentifier(), 1);
    }

    @Test
    public void checkTimeTransferLocalSet() {
        NanoTimeTransferPackKey uut = NanoTimeTransferPackKey.TimeTransferLocalSetValue;
        assertEquals(uut.getIdentifier(), 2);
    }
}