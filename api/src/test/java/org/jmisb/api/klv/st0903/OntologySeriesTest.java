package org.jmisb.api.klv.st0903;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.ontology.OntologyLS;
import org.jmisb.api.klv.st0903.ontology.OntologyMetadataKey;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Tests for OntologySeries (Tag 103) */
public class OntologySeriesTest {
    private OntologySeries ontologySeriesFromBytes;
    private OntologySeries ontologySeriesFromOntologys;

    private final byte[] twoOntologysBytes =
            new byte[] {
                83, // Length of Ontology entry 1
                0x03, 71, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x72, 0x61, 0x77, 0x2E,
                0x67, 0x69, 0x74, 0x68, 0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F, 0x6E, 0x74,
                0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
                0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E, 0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79,
                0x2F, 0x6D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2E,
                0x6F, 0x77, 0x6C, 0x04, 0x08, 0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D,
                10, // Length of Ontology entry 2
                0x04, 0x08, 0x41, 0x6d, 0x65, 0x72, 0x69, 0x63, 0x61, 0x6e
            };

    @BeforeMethod
    public void setUpMethod() throws Exception {
        ontologySeriesFromBytes = new OntologySeries(twoOntologysBytes);

        List<OntologyLS> ontologies = new ArrayList<>();

        SortedMap<OntologyMetadataKey, IVmtiMetadataValue> localSet1Values = new TreeMap<>();
        VmtiUri ontology =
                new VmtiUri(
                        VmtiUri.ONTOLOGY,
                        "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        localSet1Values.put(OntologyMetadataKey.ontology, ontology);
        VmtiTextString class1 = new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, "Mushroom");
        localSet1Values.put(OntologyMetadataKey.ontologyClass, class1);
        OntologyLS ontologyLS1 = new OntologyLS(localSet1Values);
        ontologies.add(ontologyLS1);

        SortedMap<OntologyMetadataKey, IVmtiMetadataValue> localSet2Values = new TreeMap<>();
        VmtiTextString class2 = new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, "American");
        localSet2Values.put(OntologyMetadataKey.ontologyClass, class2);
        OntologyLS ontologyPack2 = new OntologyLS(localSet2Values);
        ontologies.add(ontologyPack2);
        ontologySeriesFromOntologys = new OntologySeries(ontologies);
    }

    /** Check build */
    @Test
    public void testParsingFromValues() {
        assertNotNull(ontologySeriesFromOntologys);
        List<OntologyLS> ontologys = ontologySeriesFromOntologys.getOntologies();
        assertEquals(ontologys.size(), 2);
        OntologyLS algo1 = ontologys.get(0);
        assertEquals(algo1.getTags().size(), 2);
        OntologyLS algo2 = ontologys.get(1);
        assertEquals(algo2.getTags().size(), 1);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(VmtiMetadataKey.OntologySeries, twoOntologysBytes);
        assertNotNull(value);
        assertTrue(value instanceof OntologySeries);
        OntologySeries ontologySeries = (OntologySeries) value;
        List<OntologyLS> ontologys = ontologySeries.getOntologies();
        assertEquals(ontologys.size(), 2);
        OntologyLS algo1 = ontologys.get(0);
        assertEquals(algo1.getTags().size(), 2);
        OntologyLS algo2 = ontologys.get(1);
        assertEquals(algo2.getTags().size(), 1);
    }

    /** Check parsing */
    @Test
    public void testParsingFromBytes() {
        assertNotNull(ontologySeriesFromBytes);
        List<OntologyLS> ontologys = ontologySeriesFromBytes.getOntologies();
        assertEquals(ontologys.size(), 2);
        OntologyLS ontology1 = ontologys.get(0);
        assertEquals(ontology1.getTags().size(), 2);
        OntologyLS ontology2 = ontologys.get(1);
        assertEquals(ontology2.getTags().size(), 1);
    }

    /** Test of getBytes method, of class OntologySeries. */
    @Test
    public void testGetBytesFromSeriesFromBytes() {
        assertEquals(ontologySeriesFromBytes.getBytes(), twoOntologysBytes);
    }

    @Test
    public void testGetBytesFromSeriesFromOntologys() {
        assertEquals(ontologySeriesFromOntologys.getBytes(), twoOntologysBytes);
    }

    /** Test of getDisplayableValue method, of class OntologySeries. */
    @Test
    public void testGetDisplayableValue() {
        assertEquals(ontologySeriesFromBytes.getDisplayableValue(), "[Ontologies]");
    }

    /** Test of getDisplayName method, of class OntologySeries. */
    @Test
    public void testGetDisplayName() {
        assertEquals(ontologySeriesFromBytes.getDisplayName(), "Ontology Series");
    }
}
