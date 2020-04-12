package org.jmisb.api.klv.st0903.ontology;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Unit tests for OntologyMetadataKey.
 */
public class OntologyMetadataKeyTest {

    @Test
    public void Enum0Test()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(0);
        assertEquals(key, OntologyMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void EnumUnknownTest()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(999);
        assertEquals(key, OntologyMetadataKey.Undefined);
        assertEquals(key.getTagCode(), 0);
    }

    @Test
    public void Enum1Test()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(1);
        assertEquals(key, OntologyMetadataKey.id);
        assertEquals(key.getTagCode(), 1);
    }

    @Test
    public void Enum2Test()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(2);
        assertEquals(key, OntologyMetadataKey.parentId);
        assertEquals(key.getTagCode(), 2);
    }

    @Test
    public void Enum3Test()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(3);
        assertEquals(key, OntologyMetadataKey.ontology);
        assertEquals(key.getTagCode(), 3);
    }

    @Test
    public void Enum4Test()
    {
        OntologyMetadataKey key = OntologyMetadataKey.getKey(4);
        assertEquals(key, OntologyMetadataKey.ontologyClass);
        assertEquals(key.getTagCode(), 4);
    }
}
