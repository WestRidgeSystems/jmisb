package org.jmisb.api.klv.st0602;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.KlvConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AnnotationMetadataUniversalSetTest {
    private AnnotationMetadataUniversalSet minimalUniversalSet;

    @BeforeTest
    public void createSet() {
        SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> values = new TreeMap<>();
        values.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.DELETE));
        values.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(3));
        minimalUniversalSet = new AnnotationMetadataUniversalSet(values);
    }

    @Test
    void testUniversalLabel() {
        // Check that the correct universal label is applied
        Assert.assertEquals(
                minimalUniversalSet.getUniversalLabel(), KlvConstants.AnnotationUniversalSetUl);
    }

    @Test
    public void testDisplayHeader() {
        Assert.assertEquals(minimalUniversalSet.displayHeader(), "ST 0602 (universal)");
    }

    @Test
    public void testFrameMinimal() {
        byte[] bytes = minimalUniversalSet.getAnnotationUniversalSetBytes();
        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, 0, 16), KlvConstants.AnnotationUniversalSetUl.getBytes());

        // Check that the length field was encoded correctly
        BerField lengthField = BerDecoder.decode(bytes, 16, false);
        Assert.assertEquals(bytes.length, 16 + lengthField.getLength() + lengthField.getValue());
        // Check that the first key corresponds to the item identifier
        int offset = 16 + lengthField.getLength();
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + 16),
                AnnotationMetadataConstants.locallyUniqueIdentifierUl.getBytes());

        // Check that the value equals 3
        offset += 16;
        lengthField = BerDecoder.decode(bytes, offset, false);
        Assert.assertEquals(4, lengthField.getValue());
        offset += lengthField.getLength();
        Assert.assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + lengthField.getValue()),
                new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test
    public void parseBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0e,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x27,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x31
                };
        AnnotationMetadataUniversalSet universalSet = new AnnotationMetadataUniversalSet(bytes);
        Assert.assertEquals(universalSet.displayHeader(), "ST 0602 (universal)");
        Assert.assertEquals(
                universalSet.getUniversalLabel(), KlvConstants.AnnotationUniversalSetUl);
        Assert.assertEquals(universalSet.getIdentifiers().size(), 2);
        Assert.assertTrue(
                universalSet.getIdentifiers().contains(AnnotationMetadataKey.EventIndication));
        Assert.assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayName(),
                "Event Indication");
        Assert.assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayableValue(),
                "NEW");
        Assert.assertTrue(
                universalSet
                        .getIdentifiers()
                        .contains(AnnotationMetadataKey.LocallyUniqueIdentifier));
        Assert.assertEquals(
                universalSet
                        .getField(AnnotationMetadataKey.LocallyUniqueIdentifier)
                        .getDisplayName(),
                "Locally Unique Identifier");
        Assert.assertEquals(
                universalSet
                        .getField(AnnotationMetadataKey.LocallyUniqueIdentifier)
                        .getDisplayableValue(),
                "3");
    }
}
