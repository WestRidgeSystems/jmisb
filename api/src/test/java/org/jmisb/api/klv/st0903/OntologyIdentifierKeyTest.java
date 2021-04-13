package org.jmisb.api.klv.st0903;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for OntologyIdentifierKey. */
public class OntologyIdentifierKeyTest {

    public OntologyIdentifierKeyTest() {}

    @Test
    public void checkDisplayName() {
        OntologyIdentifierKey ontologyIdentifierKey = new OntologyIdentifierKey(2);
        assertEquals(ontologyIdentifierKey.getIdentifier(), 2);
    }

    @Test
    public void checkToString() {
        OntologyIdentifierKey ontologyIdentifierKey = new OntologyIdentifierKey(2);
        assertEquals(ontologyIdentifierKey.toString(), "Ontology 2");
    }

    @Test
    public void checkHash() {
        OntologyIdentifierKey ontologyIdentifierKey = new OntologyIdentifierKey(2);
        assertEquals(ontologyIdentifierKey.hashCode(), 2);
    }

    @Test
    public void checkCompareTo() {
        OntologyIdentifierKey ontologyIdentifierKey = new OntologyIdentifierKey(2);
        OntologyIdentifierKey ontologyIdentifierKeyHigher = new OntologyIdentifierKey(4);
        assertTrue(ontologyIdentifierKey.compareTo(ontologyIdentifierKeyHigher) < 0);
    }

    @Test
    public void checkEquals() {
        OntologyIdentifierKey ontologyIdentifierKey = new OntologyIdentifierKey(2);
        assertFalse(ontologyIdentifierKey.equals(null));
        assertFalse(ontologyIdentifierKey.equals(new OntologyIdentifierKey(3)));
        assertFalse(ontologyIdentifierKey.equals(2));
        assertTrue(ontologyIdentifierKey.equals(ontologyIdentifierKey));
    }
}
