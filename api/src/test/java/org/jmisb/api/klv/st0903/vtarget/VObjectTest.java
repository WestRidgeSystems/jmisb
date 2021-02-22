package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.vobject.VObjectLS;
import org.jmisb.api.klv.st0903.vobject.VObjectMetadataKey;
import org.testng.annotations.Test;

/** Tests for VObject (ST0903 Target Tag 102). */
public class VObjectTest {
    private final byte[] ontologyBytes =
            new byte[] {
                0x01, 71, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x72, 0x61, 0x77, 0x2E,
                0x67, 0x69, 0x74, 0x68, 0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F, 0x6E, 0x74,
                0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
                0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E, 0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79,
                0x2F, 0x6D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2E,
                0x6F, 0x77, 0x6C
            };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        VObject vobject = new VObject(ontologyBytes);
        assertEquals(vobject.getBytes(), ontologyBytes);
        assertEquals(vobject.getDisplayName(), "VObject Ontology");
        assertEquals(vobject.getDisplayableValue(), "[VObject]");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.VObject, ontologyBytes, EncodingMode.IMAPB);
        assertTrue(value instanceof VObject);
        VObject vObject = (VObject) value;
        assertEquals(vObject.getBytes(), ontologyBytes);
        assertEquals(vObject.getDisplayName(), "VObject Ontology");
        assertEquals(vObject.getDisplayableValue(), "[VObject]");
        assertEquals(vObject.getVObject().getTags().size(), 1);
    }

    @Test
    public void constructFromValue() throws KlvParseException, URISyntaxException {
        Map<VObjectMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue imageType =
                VObjectLS.createValue(VObjectMetadataKey.ontology, ontologyBytes);
        values.put(VObjectMetadataKey.ontology, imageType);
        final byte[] urlBytes =
                new byte[] {
                    0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67,
                    0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69,
                    0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E,
                    0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67
                };
        IVmtiMetadataValue imageUri =
                VObjectLS.createValue(VObjectMetadataKey.ontologyClass, urlBytes);
        values.put(VObjectMetadataKey.ontologyClass, imageUri);
        VObjectLS vObjectLS = new VObjectLS(values);
        VObject vObject = new VObject(vObjectLS);
        assertNotNull(vObject);
        assertEquals(vObject.getVObject().getTags().size(), 2);
    }
}
