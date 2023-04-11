package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st1202.GeneralizedTransformationLocalSet;
import org.jmisb.st1202.GeneralizedTransformationParametersKey;
import org.jmisb.st1202.IGeneralizedTransformationMetadataValue;
import org.jmisb.st1202.ST1202DocumentVersion;
import org.testng.annotations.Test;

/** Tests for ST 1002 Generalized Transformation (ST 1002 Tag 19). */
public class GeneralizedTransformationTest {

    @Test
    public void testConstructFromValueMap() throws KlvParseException {
        Map<GeneralizedTransformationParametersKey, IGeneralizedTransformationMetadataValue>
                values = new HashMap<>();
        values.put(
                GeneralizedTransformationParametersKey.DocumentVersion,
                new ST1202DocumentVersion(2));
        GeneralizedTransformationLocalSet localSet = new GeneralizedTransformationLocalSet(values);

        GeneralizedTransformation uut = new GeneralizedTransformation(localSet);
        assertEquals(uut.getBytes(), new byte[] {0x0a, 0x01, 0x02});
        assertEquals(uut.getDisplayName(), "Generalized Transformation Local Set");
        assertEquals(uut.getDisplayableValue(), "ST 1202 Generalized Transformation Local Set");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayName(),
                "Document Version");
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        GeneralizedTransformation uut =
                new GeneralizedTransformation(new byte[] {0x0a, 0x01, 0x02});
        assertEquals(uut.getBytes(), new byte[] {0x0a, 0x01, 0x02});
        assertEquals(uut.getDisplayName(), "Generalized Transformation Local Set");
        assertEquals(uut.getDisplayableValue(), "ST 1202 Generalized Transformation Local Set");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayName(),
                "Document Version");
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRangeImageMetadataValue value =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.GeneralizedTransformationLocalSet,
                        new byte[] {0x0a, 0x01, 0x02});
        assertTrue(value instanceof GeneralizedTransformation);
        GeneralizedTransformation uut = (GeneralizedTransformation) value;
        assertEquals(uut.getBytes(), new byte[] {0x0a, 0x01, 0x02});
        assertEquals(uut.getDisplayName(), "Generalized Transformation Local Set");
        assertEquals(uut.getDisplayableValue(), "ST 1202 Generalized Transformation Local Set");
        assertTrue(
                uut.getIdentifiers()
                        .contains(GeneralizedTransformationParametersKey.DocumentVersion));
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayName(),
                "Document Version");
        assertEquals(
                uut.getField(GeneralizedTransformationParametersKey.DocumentVersion)
                        .getDisplayableValue(),
                "ST 1202.2");
    }
}
