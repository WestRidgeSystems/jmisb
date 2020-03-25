package org.jmisb.api.klv.st0903.vobject;

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
 * Tests for the ST0903 VObject LS.
 */
public class VObjectLSTest
{
    private final byte[] ontologyBytes = new byte[]
    {0x01, 71,
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

    final byte[] ontologyClassBytes = new byte[]{
        0x02, 8,
        0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D
    };

    private final byte[] mergedBytes = new byte[]{
        0x01, 71,
        0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
        0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
        0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
        0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
        0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
        0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
        0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
        0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
        0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
        0x02, 8,
        0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D
    };

    @Test
    public void parseTag1() throws KlvParseException, URISyntaxException
    {
        VObjectLS vObjectLS = new VObjectLS(ontologyBytes);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkOntologyExample(vObjectLS);
    }

    @Test
    public void parseTag2() throws KlvParseException, URISyntaxException
    {
        VObjectLS vObjectLS = new VObjectLS(ontologyClassBytes);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkOntologyClassExample(vObjectLS);
    }

    @Test
    public void parseMerged() throws KlvParseException, URISyntaxException
    {
        VObjectLS vObjectLS = new VObjectLS(mergedBytes);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 2);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x05, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x01, 71, // Tag 1 + Length
            0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
            0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
            0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
            0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
            0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
            0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
            0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
            0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
            0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
            0x02, 8, // Tag 2 + length
            0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D};
        VObjectLS vObjectLS = new VObjectLS(bytes);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 2);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
    }

    private void checkOntologyExample(VObjectLS vObjectLS) throws URISyntaxException
    {
        final String stringVal = "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl";
        assertTrue(vObjectLS.getTags().contains(VObjectMetadataKey.ontology));
        IVmtiMetadataValue v = vObjectLS.getField(VObjectMetadataKey.ontology);
        assertEquals(v.getDisplayName(), "Ontology");
        assertEquals(v.getDisplayName(), VmtiUri.ONTOLOGY);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri) vObjectLS.getField(VObjectMetadataKey.ontology);
        assertEquals(uri.getUri().toString(), stringVal);
    }

    private void checkOntologyClassExample(VObjectLS vObjectLS)
    {
        assertTrue(vObjectLS.getTags().contains(VObjectMetadataKey.ontologyClass));
        IVmtiMetadataValue v = vObjectLS.getField(VObjectMetadataKey.ontologyClass);
        assertEquals(v.getDisplayName(), "Ontology Class");
        assertEquals(v.getDisplayName(), VmtiTextString.ONTOLOGY_CLASS);
        assertEquals(v.getDisplayableValue(), "Mushroom");
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString textString = (VmtiTextString) vObjectLS.getField(VObjectMetadataKey.ontologyClass);
        assertEquals(textString.getValue(), "Mushroom");
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        IVmtiMetadataValue value = VObjectLS.createValue(VObjectMetadataKey.Undefined, bytes);
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException, URISyntaxException
    {
        Map<VObjectMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue ontologyValue = VObjectLS.createValue(VObjectMetadataKey.ontology, new byte[]{
            0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
            0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
            0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
            0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
            0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
            0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
            0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
            0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
            0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C});
        values.put(VObjectMetadataKey.ontology, ontologyValue);
        IVmtiMetadataValue ontologyClassValue = VObjectLS.createValue(VObjectMetadataKey.ontologyClass, new byte[]{0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D});
        values.put(VObjectMetadataKey.ontologyClass, ontologyClassValue);
        VObjectLS vObjectLS = new VObjectLS(values);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 2);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
    }
}
