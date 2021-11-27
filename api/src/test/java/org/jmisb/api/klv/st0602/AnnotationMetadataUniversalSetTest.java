package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.KlvParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AnnotationMetadataUniversalSetTest {
    private AnnotationMetadataUniversalSet minimalUniversalSet;

    @BeforeTest
    public void createSet() throws KlvParseException {
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
        assertEquals(
                minimalUniversalSet.getUniversalLabel(), KlvConstants.AnnotationUniversalSetUl);
    }

    @Test
    public void testDisplayHeader() {
        assertEquals(minimalUniversalSet.displayHeader(), "ST 0602, ID: 3");
    }

    @Test
    public void testFrameMinimal() {
        byte[] bytes = minimalUniversalSet.getAnnotationUniversalSetBytes();
        // Check that the bytes begin with the correct 16-byte UL
        assertEquals(
                Arrays.copyOfRange(bytes, 0, 16), KlvConstants.AnnotationUniversalSetUl.getBytes());

        // Check that the length field was encoded correctly
        BerField lengthField = BerDecoder.decode(bytes, 16, false);
        assertEquals(bytes.length, 16 + lengthField.getLength() + lengthField.getValue());
        // Check that the first key corresponds to the item identifier
        int offset = 16 + lengthField.getLength();
        assertEquals(
                Arrays.copyOfRange(bytes, offset, offset + 16),
                AnnotationMetadataConstants.locallyUniqueIdentifierUl.getBytes());

        // Check that the value equals 3
        offset += 16;
        lengthField = BerDecoder.decode(bytes, offset, false);
        assertEquals(4, lengthField.getValue());
        offset += lengthField.getLength();
        assertEquals(
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
        assertEquals(universalSet.displayHeader(), "ST 0602, ID: 3");
        assertEquals(universalSet.getUniversalLabel(), KlvConstants.AnnotationUniversalSetUl);
        assertEquals(universalSet.getIdentifiers().size(), 2);
        assertEquals(universalSet.getMetadataKeys().size(), 2);
        assertTrue(universalSet.getIdentifiers().contains(AnnotationMetadataKey.EventIndication));
        assertTrue(universalSet.getMetadataKeys().contains(AnnotationMetadataKey.EventIndication));
        assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayName(),
                "Event Indication");
        assertEquals(
                universalSet
                        .getField((IKlvKey) AnnotationMetadataKey.EventIndication)
                        .getDisplayName(),
                "Event Indication");
        assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayableValue(),
                "NEW");
        assertTrue(
                universalSet
                        .getIdentifiers()
                        .contains(AnnotationMetadataKey.LocallyUniqueIdentifier));
        assertTrue(
                universalSet
                        .getMetadataKeys()
                        .contains(AnnotationMetadataKey.LocallyUniqueIdentifier));
        assertEquals(
                universalSet
                        .getField(AnnotationMetadataKey.LocallyUniqueIdentifier)
                        .getDisplayName(),
                "Locally Unique Identifier");
        assertEquals(
                universalSet
                        .getField((IKlvKey) AnnotationMetadataKey.LocallyUniqueIdentifier)
                        .getDisplayName(),
                "Locally Unique Identifier");
        assertEquals(
                universalSet
                        .getField(AnnotationMetadataKey.LocallyUniqueIdentifier)
                        .getDisplayableValue(),
                "3");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void frameMessageNested() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        map.put(AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(1024));
        map.put(AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(1280));
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        set.frameMessage(true);
    }

    @Test
    public void frameDeleteMessage() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        map.put(AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(1024));
        map.put(AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(1280));
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        byte[] bytes = set.frameMessage(false);
        assertEquals(bytes, sampleDeleteMessageBytes());
    }

    @Test
    public void frameDeleteMessageWithEmptyValue() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        map.put(AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(1024));
        map.put(AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(1280));
        map.put(AnnotationMetadataKey.MediaDescription, new MediaDescription(""));
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        byte[] bytes = set.frameMessage(false);
        assertEquals(bytes, sampleDeleteMessageBytes());
    }

    @Test
    public void frameDeleteMessageWithByteOrder() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        map.put(
                AnnotationMetadataKey.ByteOrder,
                new IAnnotationMetadataValue() {
                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x4d, 0x4d};
                    }

                    @Override
                    public String getDisplayName() {
                        return "unused";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "also unused";
                    }
                });
        map.put(AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(1024));
        map.put(AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(1280));
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        byte[] bytes = set.frameMessage(false);
        assertEquals(bytes, sampleDeleteMessageBytes());
    }

    @Test
    public void frameDeleteMessageBadValues() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        // These are deliberately reversed to test the type checking
        map.put(AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveSamplesPerLine(1024));
        map.put(AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveLinesPerFrame(1280));
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        byte[] bytes = set.frameMessage(false);
        assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x4D,
                    (byte) 0x4D,
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
                    (byte) 0x3F,
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
                    (byte) 0x57,
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
                    (byte) 0x34,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0e,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x42,
                    (byte) 0x72,
                    (byte) 0x61,
                    (byte) 0x64,
                    (byte) 0x20,
                    (byte) 0x48,
                    (byte) 0x2e
                });
    }

    @Test
    public void frameDeleteMessageNoSizePrefix() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = sampleDeleteMessageValues();
        AnnotationMetadataUniversalSet set = new AnnotationMetadataUniversalSet(map);
        byte[] bytes = set.frameMessage(false);
        assertEquals(
                bytes,
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x4D,
                    (byte) 0x4D,
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
                    (byte) 0x3F,
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
                    (byte) 0x57,
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
                    (byte) 0x34,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0e,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0x42,
                    (byte) 0x72,
                    (byte) 0x61,
                    (byte) 0x64,
                    (byte) 0x20,
                    (byte) 0x48,
                    (byte) 0x2e
                });
    }

    @Test
    public void parseDeleteMessageWithPrefixMessages() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> expectedDeleteMessageValues =
                sampleDeleteMessageValues();
        expectedDeleteMessageValues.put(
                AnnotationMetadataKey.ActiveLinesPerFrame, new ActiveLinesPerFrame(1024));
        expectedDeleteMessageValues.put(
                AnnotationMetadataKey.ActiveSamplesPerLine, new ActiveSamplesPerLine(1280));
        List<IMisbMessage> messages = KlvParser.parseBytes(sampleDeleteMessageBytes());
        assertEquals(messages.size(), 4);
        assertTrue(messages.get(0) instanceof AnnotationByteOrderMessage);
        assertTrue(messages.get(1) instanceof ActiveLinesPerFrameMessage);
        assertTrue(messages.get(2) instanceof ActiveSamplesPerLineMessage);
        assertTrue(messages.get(3) instanceof AnnotationMetadataUniversalSet);
        AnnotationMetadataUniversalSet annotationMetadataUniversalSet =
                (AnnotationMetadataUniversalSet) messages.get(3);
        assertEquals(annotationMetadataUniversalSet.getIdentifiers().size(), 3);
    }

    @Test
    public void parseDeleteMessage() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> expectedDeleteMessageValues =
                sampleDeleteMessageValues();
        AnnotationMetadataUniversalSetFactory factory = new AnnotationMetadataUniversalSetFactory();
        AnnotationMetadataUniversalSet set =
                factory.create(sampleDeleteMessageBytesUniversalSetOnly());
        assertEquals(set.getMetadataKeys(), expectedDeleteMessageValues.keySet());
    }

    private static byte[] sampleDeleteMessageBytes() {
        return new byte[] {
            (byte) 0x06,
            (byte) 0x0e,
            (byte) 0x2b,
            (byte) 0x34,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x03,
            (byte) 0x01,
            (byte) 0x02,
            (byte) 0x01,
            (byte) 0x02,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x02,
            (byte) 0x4D,
            (byte) 0x4D,
            (byte) 0x06,
            (byte) 0x0e,
            (byte) 0x2b,
            (byte) 0x34,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x04,
            (byte) 0x01,
            (byte) 0x03,
            (byte) 0x02,
            (byte) 0x02,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x02,
            (byte) 0x04,
            (byte) 0x00,
            (byte) 0x06,
            (byte) 0x0e,
            (byte) 0x2b,
            (byte) 0x34,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x04,
            (byte) 0x01,
            (byte) 0x05,
            (byte) 0x01,
            (byte) 0x02,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x02,
            (byte) 0x05,
            (byte) 0x00,
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
            (byte) 0x3F,
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
            (byte) 0x57,
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
            (byte) 0x34,
            (byte) 0x06,
            (byte) 0x0e,
            (byte) 0x2b,
            (byte) 0x34,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x0e,
            (byte) 0x01,
            (byte) 0x02,
            (byte) 0x05,
            (byte) 0x02,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x07,
            (byte) 0x42,
            (byte) 0x72,
            (byte) 0x61,
            (byte) 0x64,
            (byte) 0x20,
            (byte) 0x48,
            (byte) 0x2e
        };
    }

    private static byte[] sampleDeleteMessageBytesUniversalSetOnly() {
        return new byte[] {
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
            (byte) 0x3F,
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
            (byte) 0x57,
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
            (byte) 0x34,
            (byte) 0x06,
            (byte) 0x0e,
            (byte) 0x2b,
            (byte) 0x34,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x01,
            (byte) 0x0e,
            (byte) 0x01,
            (byte) 0x02,
            (byte) 0x05,
            (byte) 0x02,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x07,
            (byte) 0x42,
            (byte) 0x72,
            (byte) 0x61,
            (byte) 0x64,
            (byte) 0x20,
            (byte) 0x48,
            (byte) 0x2e
        };
    }

    private Map<AnnotationMetadataKey, IAnnotationMetadataValue> sampleDeleteMessageValues()
            throws KlvParseException {
        SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> map = new TreeMap<>();
        map.put(AnnotationMetadataKey.LocallyUniqueIdentifier, new LocallyUniqueIdentifier(87));
        map.put(
                AnnotationMetadataKey.EventIndication,
                new EventIndication(EventIndicationKind.DELETE));
        map.put(AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Brad H."));
        return map;
    }

    @Test
    public void checkMergeAndUpdate() throws KlvParseException {
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> mapOfSourceValues = new HashMap<>();
        mapOfSourceValues.put(
                AnnotationMetadataKey.MediaDescription,
                new MediaDescription("Original description"));
        mapOfSourceValues.put(AnnotationMetadataKey.AnnotationSource, new AnnotationSource(3));
        AnnotationMetadataUniversalSet annotations =
                new AnnotationMetadataUniversalSet(mapOfSourceValues);
        Map<AnnotationMetadataKey, IAnnotationMetadataValue> mapOfUpdatedValues = new HashMap<>();
        mapOfUpdatedValues.put(
                AnnotationMetadataKey.MediaDescription,
                new MediaDescription("Updated description"));
        mapOfUpdatedValues.put(
                AnnotationMetadataKey.ModificationHistory, new ModificationHistory("Some author"));
        AnnotationMetadataUniversalSet setToMergeIn =
                new AnnotationMetadataUniversalSet(mapOfUpdatedValues);
        assertEquals(
                annotations.getField(AnnotationMetadataKey.MediaDescription).getDisplayableValue(),
                "Original description");
        annotations.mergeAndUpdate(setToMergeIn);
        assertEquals(
                annotations.getField(AnnotationMetadataKey.MediaDescription).getDisplayableValue(),
                "Updated description");
        assertEquals(
                annotations.getField(AnnotationMetadataKey.AnnotationSource).getDisplayableValue(),
                "Automated from BE/RWAC|Automated from user defined latitude/longitude");
        assertEquals(
                annotations
                        .getField(AnnotationMetadataKey.ModificationHistory)
                        .getDisplayableValue(),
                "Some author");
    }
}
