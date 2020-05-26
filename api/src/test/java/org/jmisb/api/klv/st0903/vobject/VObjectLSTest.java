package org.jmisb.api.klv.st0903.vobject;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VObject LS.
 */
public class VObjectLSTest extends LoggerChecks
{
    public VObjectLSTest()
    {
        super(VObjectLS.class);
    }

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

    final byte[] ontologyIdBytes = new byte[]{
        0x03, 2,
        0x01, 0x02
    };

    final byte[] confidenceBytes = new byte[]{
        0x04, 2,
        0x30, 0x00
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
        0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D,
        0x03, 2,
        0x01, 0x02,
        0x04, 2,
        0x30, 0x00
    };

    @Test
    public void parseTag1() throws KlvParseException, URISyntaxException
    {
        VObjectLS vObjectLS = new VObjectLS(ontologyBytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkOntologyExample(vObjectLS);
    }

    @Test
    public void parseTag2() throws KlvParseException
    {
        VObjectLS vObjectLS = new VObjectLS(ontologyClassBytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkOntologyClassExample(vObjectLS);
    }

    @Test
    public void parseTag3() throws KlvParseException
    {
        VObjectLS vObjectLS = new VObjectLS(ontologyIdBytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkOntologyIdExample(vObjectLS);
    }

    @Test
    public void parseTag4() throws KlvParseException
    {
        VObjectLS vObjectLS = new VObjectLS(confidenceBytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 1);
        checkConfidenceExample(vObjectLS);
    }

    @Test
    public void parseMerged() throws KlvParseException, URISyntaxException
    {
        VObjectLS vObjectLS = new VObjectLS(mergedBytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 4);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
        checkOntologyIdExample(vObjectLS);
        checkConfidenceExample(vObjectLS);
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
            0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D,
            0x03, 2, // Tag 3 + length
            0x01, 0x02
        };
        verifyNoLoggerMessages();
        VObjectLS vObjectLS = new VObjectLS(bytes, EnumSet.noneOf(ParseOptions.class));
        verifySingleLoggerMessage("Unknown VMTI VObject Metadata tag: 5");
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 3);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
        checkOntologyIdExample(vObjectLS);
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

    private void checkOntologyIdExample(VObjectLS vObjectLS)
    {
        assertTrue(vObjectLS.getTags().contains(VObjectMetadataKey.ontologyId));
        IVmtiMetadataValue v = vObjectLS.getField(VObjectMetadataKey.ontologyId);
        assertEquals(v.getDisplayName(), "Ontology Id");
        assertEquals(v.getDisplayableValue(), "258");
        assertTrue(v instanceof OntologyId);
        OntologyId id = (OntologyId) vObjectLS.getField(VObjectMetadataKey.ontologyId);
        assertEquals(id.getValue(), 258);
    }

    private void checkConfidenceExample(VObjectLS vObjectLS)
    {
        assertTrue(vObjectLS.getTags().contains(VObjectMetadataKey.confidence));
        IVmtiMetadataValue v = vObjectLS.getField(VObjectMetadataKey.confidence);
        assertEquals(v.getDisplayName(), "Confidence");
        assertEquals(v.getDisplayableValue(), "48.0%");
        assertTrue(v instanceof Confidence2);
        Confidence2 confidence = (Confidence2) vObjectLS.getField(VObjectMetadataKey.confidence);
        assertEquals(confidence.getConfidence(), 48.0, 0.001);
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        verifyNoLoggerMessages();
        IVmtiMetadataValue value = VObjectLS.createValue(VObjectMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized VObject tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException, URISyntaxException
    {
        Map<VObjectMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        values.put(VObjectMetadataKey.ontology, new VmtiUri(VmtiUri.ONTOLOGY, "https://raw.githubusercontent.com/owlcs/pizza-ontology/master/pizza.owl"));
        values.put(VObjectMetadataKey.ontologyClass, new VmtiTextString(VmtiTextString.ONTOLOGY_CLASS, "Mushroom"));
        values.put(VObjectMetadataKey.ontologyId, new OntologyId(258));
        values.put(VObjectMetadataKey.confidence, new Confidence2(48.0));
        VObjectLS vObjectLS = new VObjectLS(values);
        assertNotNull(vObjectLS);
        assertEquals(vObjectLS.getTags().size(), 4);
        checkOntologyExample(vObjectLS);
        checkOntologyClassExample(vObjectLS);
        checkOntologyIdExample(vObjectLS);
        checkConfidenceExample(vObjectLS);
        assertEquals(vObjectLS.getBytes(), mergedBytes);
    }
}
