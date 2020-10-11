package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ST 1303 Array Processing Algorithm enumeration. */
public class ArrayProcessingAlgorithmTest {

    public ArrayProcessingAlgorithmTest() {}

    @Test
    public void checkValue() {
        assertEquals(ArrayProcessingAlgorithm.ST1201, ArrayProcessingAlgorithm.getValue(2));
    }

    @Test
    public void checkValueUnknown() {
        assertEquals(ArrayProcessingAlgorithm.Unused, ArrayProcessingAlgorithm.getValue(17));
    }

    @Test
    public void checkValueUnused() {
        assertEquals(ArrayProcessingAlgorithm.Unused, ArrayProcessingAlgorithm.getValue(0));
    }
}
