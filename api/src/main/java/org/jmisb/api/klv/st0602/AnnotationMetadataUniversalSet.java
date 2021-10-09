package org.jmisb.api.klv.st0602;

import static org.jmisb.core.klv.ArrayUtils.arrayFromChunks;

import java.util.*;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Annotation Metadata Universal Set message packet defined by ST 0602. */
public class AnnotationMetadataUniversalSet implements IMisbMessage {

    private static Logger logger = LoggerFactory.getLogger(AnnotationMetadataUniversalSet.class);
    private SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Key/value pairs to be included in the message
     */
    public AnnotationMetadataUniversalSet(
            SortedMap<AnnotationMetadataKey, IAnnotationMetadataValue> values) {
        this.map = values;
    }

    /**
     * Create a Security Metadata Universal Set message by parsing the given byte array.
     *
     * @param bytes Byte array containing a Security Metadata Universal Set message
     * @throws KlvParseException if a parsing error occurs
     */
    public AnnotationMetadataUniversalSet(byte[] bytes) throws KlvParseException {
        // Parse the length field
        BerField lengthField = BerDecoder.decode(bytes, UniversalLabel.LENGTH, false);
        int lengthLength = lengthField.getLength();
        int offset = UniversalLabel.LENGTH + lengthLength;
        int valueLength = lengthField.getValue();
        List<UdsField> fields = UdsParser.parseFields(bytes, offset, valueLength);
        for (UdsField field : fields) {
            try {
                AnnotationMetadataKey key = AnnotationMetadataKey.getKey(field.getKey());
                IAnnotationMetadataValue value =
                        UniversalSetFactory.createValue(
                                AnnotationMetadataKey.getKey(field.getKey()), field.getValue());
                setField(key, value);
            } catch (IllegalArgumentException ex) {
                InvalidDataHandler.getInstance()
                        .handleInvalidFieldEncoding(logger, ex.getMessage());
            }
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.AnnotationUniversalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 0602 universal set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(getByteOrderBytes());
        arrayBuilder.append(getActiveLinesPerFrameBytes());
        arrayBuilder.append(getActiveSamplesPerLineBytes());
        arrayBuilder.append(getAnnotationUniversalSetBytes());

        return arrayBuilder.toBytes();
    }

    private byte[] getByteOrderBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        byte[] byteOrderValue = new byte[] {0x4D, 0x4D};
        arrayBuilder.append(AnnotationMetadataConstants.byteOrderUl);
        arrayBuilder.appendAsBerLength(byteOrderValue.length);
        arrayBuilder.append(byteOrderValue);
        return arrayBuilder.toBytes();
    }

    private byte[] getActiveLinesPerFrameBytes() {
        IAnnotationMetadataValue lines =
                map.getOrDefault(AnnotationMetadataKey.LinesPerFrame, null);
        if (lines != null) {
            byte[] linesBytes = lines.getBytes();
            ArrayBuilder arrayBuilder = new ArrayBuilder();
            arrayBuilder.append(AnnotationMetadataConstants.activeLinesPerFrameUl);
            arrayBuilder.appendAsBerLength(linesBytes.length);
            arrayBuilder.append(linesBytes);
            return arrayBuilder.toBytes();
        } else {
            return new byte[] {};
        }
    }

    private byte[] getActiveSamplesPerLineBytes() {
        IAnnotationMetadataValue samples =
                map.getOrDefault(AnnotationMetadataKey.SamplesPerFrame, null);
        if (samples != null) {
            byte[] samplesBytes = samples.getBytes();
            ArrayBuilder arrayBuilder = new ArrayBuilder();
            arrayBuilder.append(AnnotationMetadataConstants.activeSamplesPerLineUl);
            arrayBuilder.appendAsBerLength(samplesBytes.length);
            arrayBuilder.append(samplesBytes);
            return arrayBuilder.toBytes();
        } else {
            return new byte[] {};
        }
    }

    byte[] getAnnotationUniversalSetBytes() {
        // List representing all keys and values as primitive byte arrays. Avoids boxing/unboxing
        // individual bytes for efficiency.
        List<byte[]> chunks = new ArrayList<>();

        // Add all values from map
        for (Map.Entry<AnnotationMetadataKey, IAnnotationMetadataValue> entry : map.entrySet()) {
            AnnotationMetadataKey key = entry.getKey();
            if (key.equals(AnnotationMetadataKey.ByteOrder)) {
                continue;
            }
            if (key.equals(AnnotationMetadataKey.LinesPerFrame)) {
                continue;
            }
            if (key.equals(AnnotationMetadataKey.SamplesPerFrame)) {
                continue;
            }
            IAnnotationMetadataValue value = entry.getValue();
            byte[] bytes = value.getBytes();
            if (bytes != null && bytes.length > 0) {
                chunks.add(key.getUl().getBytes());
                chunks.add(BerEncoder.encode(bytes.length));
                chunks.add(bytes.clone());
            }
        }

        // Figure out value length
        int valueLength = 0;
        for (byte[] chunk : chunks) {
            valueLength += chunk.length;
        }

        // Determine total length
        int totalLength;
        // Prepend length field into front of the list
        byte[] lengthField = BerEncoder.encode(valueLength);
        chunks.add(0, lengthField);

        // Prepend key field (UL) into front of the list
        chunks.add(0, KlvConstants.AnnotationUniversalSetUl.getBytes());

        // Compute full message length
        totalLength = UniversalLabel.LENGTH + lengthField.length + valueLength;

        // Allocate array and write all chunks
        return arrayFromChunks(chunks, totalLength);
    }

    @Override
    public String displayHeader() {
        return "ST 0602 (universal)";
    }

    /**
     * Set a message field.
     *
     * @param key The key
     * @param value The value
     */
    public void setField(AnnotationMetadataKey key, IAnnotationMetadataValue value) {
        map.put(key, value);
    }

    @Override
    public IAnnotationMetadataValue getField(IKlvKey key) {
        return this.getField((AnnotationMetadataKey) key);
    }

    /**
     * Get a message field.
     *
     * @param key The key
     * @return The value
     */
    public IAnnotationMetadataValue getField(AnnotationMetadataKey key) {
        return map.get(key);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }
}
