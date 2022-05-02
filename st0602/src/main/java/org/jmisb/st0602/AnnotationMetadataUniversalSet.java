package org.jmisb.st0602;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.UdsField;
import org.jmisb.api.klv.UdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Annotation Metadata Universal Set message packet defined by ST 0602. */
public class AnnotationMetadataUniversalSet implements IMisbMessage {

    /** Universal label for Annotation Universal Metadata Set. */
    public static final UniversalLabel AnnotationUniversalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x01, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x01, 0x00, 0x00, 0x00
                    });

    private static Logger logger = LoggerFactory.getLogger(AnnotationMetadataUniversalSet.class);
    private Map<AnnotationMetadataKey, IAnnotationMetadataValue> map = new HashMap<>();

    /**
     * Create the message from the given key/value pairs.
     *
     * @param values Key/value pairs to be included in the message
     */
    public AnnotationMetadataUniversalSet(
            Map<AnnotationMetadataKey, IAnnotationMetadataValue> values) {
        this.map = values;
    }

    /**
     * Create an Annotation Metadata Universal Set message by parsing the given byte array.
     *
     * @param bytes Byte array containing an Annotation Metadata Universal Set message
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
                if (key != AnnotationMetadataKey.Undefined) {
                    IAnnotationMetadataValue value =
                            UniversalSetFactory.createValue(
                                    AnnotationMetadataKey.getKey(field.getKey()), field.getValue());
                    setField(key, value);
                }
            } catch (IllegalArgumentException ex) {
                InvalidDataHandler.getInstance()
                        .handleInvalidFieldEncoding(logger, ex.getMessage());
            }
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return AnnotationUniversalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException("ST 0602 universal set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(new AnnotationByteOrderMessage().frameMessage(false));
        arrayBuilder.append(getActiveLinesPerFrameBytes());
        arrayBuilder.append(getActiveSamplesPerLineBytes());
        arrayBuilder.append(getAnnotationUniversalSetBytes());

        return arrayBuilder.toBytes();
    }

    private byte[] getActiveLinesPerFrameBytes() {
        IAnnotationMetadataValue lines =
                map.getOrDefault(AnnotationMetadataKey.ActiveLinesPerFrame, null);
        if ((lines != null) && (lines instanceof ActiveLinesPerFrame)) {
            ActiveLinesPerFrame activeLinesPerFrame = (ActiveLinesPerFrame) lines;
            ActiveLinesPerFrameMessage message =
                    new ActiveLinesPerFrameMessage(activeLinesPerFrame);
            return message.frameMessage(false);
        } else {
            return new byte[] {};
        }
    }

    private byte[] getActiveSamplesPerLineBytes() {
        IAnnotationMetadataValue samples =
                map.getOrDefault(AnnotationMetadataKey.ActiveSamplesPerLine, null);
        if ((samples != null) && (samples instanceof ActiveSamplesPerLine)) {
            ActiveSamplesPerLine activeSamplesPerLine = (ActiveSamplesPerLine) samples;
            ActiveSamplesPerLineMessage message =
                    new ActiveSamplesPerLineMessage(activeSamplesPerLine);
            return message.frameMessage(false);
        } else {
            return new byte[] {};
        }
    }

    /**
     * Get the data for just the Annotation Universal Metadata Set part of the total message.
     *
     * <p>This is the same as {@link #frameMessage(boolean)} but without the byte order and image
     * size prefix items. It saves a small amount of data usage in the case where the prefix items
     * have been sent in the previous quarter second.
     *
     * @return byte array containing KLV serialised form of the Annotation Universal Metadata Set.
     */
    public byte[] getAnnotationUniversalSetBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();

        // Add all values from map
        for (Map.Entry<AnnotationMetadataKey, IAnnotationMetadataValue> entry : map.entrySet()) {
            AnnotationMetadataKey key = entry.getKey();
            if (key.equals(AnnotationMetadataKey.ByteOrder)) {
                continue;
            }
            if (key.equals(AnnotationMetadataKey.ActiveLinesPerFrame)) {
                continue;
            }
            if (key.equals(AnnotationMetadataKey.ActiveSamplesPerLine)) {
                continue;
            }
            IAnnotationMetadataValue value = entry.getValue();
            byte[] bytes = value.getBytes();
            if (bytes != null && bytes.length > 0) {
                arrayBuilder.append(key.getUl());
                arrayBuilder.appendAsBerLength(bytes.length);
                arrayBuilder.append(bytes.clone());
            }
        }
        arrayBuilder.prependLength();
        arrayBuilder.prepend(AnnotationUniversalSetUl);
        return arrayBuilder.toBytes();
    }

    @Override
    public String displayHeader() {
        if (map.containsKey(AnnotationMetadataKey.LocallyUniqueIdentifier)) {
            return "ST 0602, ID: "
                    + map.get(AnnotationMetadataKey.LocallyUniqueIdentifier).getDisplayableValue();
        } else {
            return "ST 0602, ID: Unknown";
        }
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

    /**
     * Get the metadata keys that are set on this Set.
     *
     * @return Set of metadata keys.
     */
    public Set<AnnotationMetadataKey> getMetadataKeys() {
        return map.keySet();
    }

    /**
     * Merge the metadata from another set into this set.
     *
     * <p>Existing items will be overwritten, and new items will be added.
     *
     * @param set the set to merge from.
     */
    public void mergeAndUpdate(AnnotationMetadataUniversalSet set) {
        for (AnnotationMetadataKey entry : set.getMetadataKeys()) {
            IAnnotationMetadataValue value = set.getField(entry);
            setField(entry, value);
        }
    }
}
