package org.jmisb.api.klv.st0903.vtarget;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vobject.VObjectLS;
import org.jmisb.api.klv.st0903.vobject.VObjectMetadataKey;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VObjectSeries Series (ST0903 VTarget Tag 106).
 */
public class VObjectSeriesTest
{
    final byte[] bytesOneObject = new byte[] {
        83, // combined length 
        0x01, 71, // Tag 1 and length
        0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
        0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
        0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
        0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
        0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
        0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
        0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
        0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
        0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
        0x02, 8, // Tag 2 and length
        0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D
    };

    final byte[] bytesTwoObjects = new byte[]
    {
        83, // composite length for first ontology
        0x01, 71, // Tag 1 and length
        0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F,
        0x72, 0x61, 0x77, 0x2E, 0x67, 0x69, 0x74, 0x68,
        0x75, 0x62, 0x75, 0x73, 0x65, 0x72, 0x63, 0x6F,
        0x6E, 0x74, 0x65, 0x6E, 0x74, 0x2E, 0x63, 0x6F,
        0x6D, 0x2F, 0x6F, 0x77, 0x6C, 0x63, 0x73, 0x2F,
        0x70, 0x69, 0x7A, 0x7A, 0x61, 0x2D, 0x6F, 0x6E,
        0x74, 0x6F, 0x6C, 0x6F, 0x67, 0x79, 0x2F, 0x6D,
        0x61, 0x73, 0x74, 0x65, 0x72, 0x2F, 0x70, 0x69,
        0x7A, 0x7A, 0x61, 0x2E, 0x6F, 0x77, 0x6C,
        0x02, 8, // Tag 2 and length
        0x4D, 0x75, 0x73, 0x68, 0x72, 0x6F, 0x6F, 0x6D,
        6, // composite length for second ontology
        0x02, 4, // Tag 2 and length
        0x74, 0x65, 0x73, 0x74
    };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException
    {
        VObjectSeries objectSeries = new VObjectSeries(bytesOneObject, EnumSet.noneOf(ParseOptions.class));
        assertEquals(objectSeries.getBytes().length, bytesOneObject.length);
        assertEquals(objectSeries.getDisplayName(), "Ontologies");
        assertEquals(objectSeries.getDisplayableValue(), "[VObject Series]");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VObjectSeries , bytesOneObject);
        assertTrue(value instanceof VObjectSeries );
        VObjectSeries objectSeries = (VObjectSeries)value;
        assertEquals(objectSeries.getBytes(), bytesOneObject);
        assertEquals(objectSeries.getDisplayName(), "Ontologies");
        assertEquals(objectSeries.getDisplayableValue(), "[VObject Series]");
        assertEquals(objectSeries.getVObjects().size(), 1);
        assertEquals(objectSeries.getVObjects().get(0).getTags().size(), 2);
    }

    @Test
    public void constructFromValue() throws KlvParseException, URISyntaxException
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
        List<VObjectLS> localSets = new ArrayList<>();
        localSets.add(vObjectLS);
        VObjectSeries vObjectSeries = new VObjectSeries(localSets);
        assertNotNull(vObjectSeries);
        assertEquals(vObjectSeries.getVObjects().size(), 1);
        assertEquals(vObjectSeries.getVObjects().get(0).getTags().size(), 2);
    }

    @Test
    public void testFactoryEncodedBytesTwoObjects() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VObjectSeries , bytesTwoObjects);
        assertTrue(value instanceof VObjectSeries );
        VObjectSeries objectSeries = (VObjectSeries)value;
        assertEquals(objectSeries.getBytes(), bytesTwoObjects);
        assertEquals(objectSeries.getDisplayName(), "Ontologies");
        assertEquals(objectSeries.getDisplayableValue(), "[VObject Series]");
        assertEquals(objectSeries.getVObjects().size(), 2);
        assertEquals(objectSeries.getVObjects().get(0).getTags().size(), 2);
        assertEquals(objectSeries.getVObjects().get(1).getTags().size(), 1);
    }
}
