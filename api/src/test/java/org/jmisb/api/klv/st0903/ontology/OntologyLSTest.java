package org.jmisb.api.klv.st0903.ontology;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 Ontology LS.
 */
public class OntologyLSTest
{
    @Test
    public void parseTag3() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[] {
            0x03, 71,
            0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
            0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
            0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
            0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
            0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
            0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
            0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
            0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
            0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C
        };
        OntologyLS ontologyLS = new OntologyLS(bytes);
        assertNotNull(ontologyLS);
        assertEquals(ontologyLS.getTags().size(), 1);
        checkOntologyExample(ontologyLS);
    }

    @Test
    public void parseTag4() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{0x04, 0x08, 0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D};
        OntologyLS ontologyLS = new OntologyLS(bytes);
        assertNotNull(ontologyLS);
        assertEquals(ontologyLS.getTags().size(), 1);
        checkOntologyClassExample(ontologyLS);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x05, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x03, 71,
            0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
            0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
            0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
            0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
            0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
            0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
            0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
            0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
            0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
            0x04, 0x08,
            0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D};
        OntologyLS ontologyLS = new OntologyLS(bytes);
        assertNotNull(ontologyLS);
        assertEquals(ontologyLS.getTags().size(), 2);
        checkOntologyClassExample(ontologyLS);
        checkOntologyExample(ontologyLS);
    }

    public void checkOntologyExample(OntologyLS ontologyLS) throws URISyntaxException
    {
        final String stringVal = "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl";
        assertTrue(ontologyLS.getTags().contains(OntologyMetadataKey.ontology));
        IVmtiMetadataValue v = ontologyLS.getField(OntologyMetadataKey.ontology);
        assertEquals(v.getDisplayName(), "Ontology");
        assertEquals(v.getDisplayName(), VmtiUri.ONTOLOGY);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri) ontologyLS.getField(OntologyMetadataKey.ontology);
        assertEquals(uri.getUri().toString(), stringVal);
    }

    public void checkOntologyClassExample(OntologyLS ontologyLS) throws URISyntaxException
    {
        final String stringVal = "Mushroom";
        assertTrue(ontologyLS.getTags().contains(OntologyMetadataKey.ontologyClass));
        IVmtiMetadataValue v = ontologyLS.getField(OntologyMetadataKey.ontologyClass);
        assertEquals(v.getDisplayName(), "Ontology Class");
        assertEquals(v.getDisplayName(), VmtiTextString.ONTOLOGY_CLASS);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertEquals(v.getBytes(), new byte[]{0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D});
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString text = (VmtiTextString) ontologyLS.getField(OntologyMetadataKey.ontologyClass);
        assertEquals(text.getValue(), stringVal);
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        IVmtiMetadataValue value = OntologyLS.createValue(OntologyMetadataKey.Undefined, bytes);
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException, URISyntaxException, IOException
    {
        Map<OntologyMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue ontology = new VmtiUri(VmtiUri.ONTOLOGY, "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl");
        values.put(OntologyMetadataKey.ontology, ontology);
        IVmtiMetadataValue ontologyClass = new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, "Mushroom");
        values.put(OntologyMetadataKey.ontologyClass, ontologyClass);
        OntologyLS ontologyLS = new OntologyLS(values);
        assertNotNull(ontologyLS);
        assertEquals(ontologyLS.getTags().size(), 2);
        checkOntologyExample(ontologyLS);
        checkOntologyClassExample(ontologyLS);
        byte[] expectedBytes = new byte[]{
            0x03, 71,
            0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
            0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
            0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
            0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
            0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
            0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
            0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
            0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
            0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
            0x04, 0x08,
            0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D};
        assertEquals(ontologyLS.getBytes(), expectedBytes);
    }
}
