package org.jmisb.api.klv.st1108.st1108_3;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Document Version Number.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Document Version item indicates the numeric version of this document. For example, ST 1108.3
 * is version 3, ST 1108.4 is version 4.
 *
 * </blockquote>
 *
 * Note that ST 1108.2 and earlier versions (EG 1108 and EG 1108.1) did not include this
 * information. That is, there is no Document Version or equivalent tag until ST 1108.3.
 */
public class DocumentVersion implements IInterpretabilityQualityMetadataValue {
    private int version;
    private static final int MIN_VALUE = 3;
    private static final int MAX_VALUE = 255;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * @param version The version number
     */
    public DocumentVersion(int version) {
        if (version < MIN_VALUE || version > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [3,255]");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public DocumentVersion(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        version = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the version number.
     *
     * @return The version number
     */
    public int getVersion() {
        return version;
    }

    @Override
    public String getDisplayableValue() {
        return "ST 1108." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Document Version";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.DocumentVersion.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) version);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
