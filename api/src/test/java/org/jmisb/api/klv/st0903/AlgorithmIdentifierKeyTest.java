package org.jmisb.api.klv.st0903;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for AlgorithmIdentifierKey. */
public class AlgorithmIdentifierKeyTest {

    public AlgorithmIdentifierKeyTest() {}

    @Test
    public void checkDisplayName() {
        AlgorithmIdentifierKey algorithmIdentifierKey = new AlgorithmIdentifierKey(2);
        assertEquals(algorithmIdentifierKey.getIdentifier(), 2);
    }

    @Test
    public void checkToString() {
        AlgorithmIdentifierKey algorithmIdentifierKey = new AlgorithmIdentifierKey(2);
        assertEquals(algorithmIdentifierKey.toString(), "Algorithm 2");
    }

    @Test
    public void checkHash() {
        AlgorithmIdentifierKey algorithmIdentifierKey = new AlgorithmIdentifierKey(2);
        assertEquals(algorithmIdentifierKey.hashCode(), 2);
    }

    @Test
    public void checkCompareTo() {
        AlgorithmIdentifierKey algorithmIdentifierKey = new AlgorithmIdentifierKey(2);
        AlgorithmIdentifierKey algorithmIdentifierKeyHigher = new AlgorithmIdentifierKey(4);
        assertTrue(algorithmIdentifierKey.compareTo(algorithmIdentifierKeyHigher) < 0);
    }

    @Test
    public void checkEquals() {
        AlgorithmIdentifierKey algorithmIdentifierKey = new AlgorithmIdentifierKey(2);
        assertFalse(algorithmIdentifierKey.equals(null));
        assertFalse(algorithmIdentifierKey.equals(new AlgorithmIdentifierKey(3)));
        assertFalse(algorithmIdentifierKey.equals(new Integer(2)));
        assertTrue(algorithmIdentifierKey.equals(algorithmIdentifierKey));
    }
}
