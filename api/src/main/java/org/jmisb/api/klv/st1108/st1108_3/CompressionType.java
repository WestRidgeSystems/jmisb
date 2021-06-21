package org.jmisb.api.klv.st1108.st1108_3;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Video Compression Type.
 *
 * <p>The Compression Type mandatory item is a MISP-approved type such as H.264, H.265 etc.
 * specified using an unsigned 8-bit integer enumeration. For no compression (i.e., uncompressed)
 * specify a value of zero (0).
 */
public enum CompressionType implements IInterpretabilityQualityMetadataValue {

    /**
     * Undefined or Reserved.
     *
     * <p>This is not a valid compression type value, and should not be created.
     */
    Undefined(-1),
    /** Uncompressed. */
    Uncompressed(0),
    /** H.262 (MPEG-2) Compression. */
    H262(1),
    /** H.264 (AVC) Compression. */
    H264(2),
    /** H.265 (HEVC) Compression. */
    H265(3),
    /** JPEG 2000 Compression. */
    JPEG2000(4);

    static CompressionType fromBytes(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException("Compression Type encoding is a one-byte unsigned int");
        }
        return lookup.getOrDefault(bytes[0], Undefined);
    }

    private static final Map<Byte, CompressionType> lookup = new HashMap<>();

    static {
        for (CompressionType key : values()) {
            lookup.put(key.value, key);
        }
    }

    private byte value;

    @Override
    public String getDisplayName() {
        return "Compression Type";
    }

    @Override
    public String getDisplayableValue() {
        switch (this) {
            case Uncompressed:
                return "Uncompressed";
            case H262:
                return "H.262 (MPEG-2)";
            case H264:
                return "H.264 (AVC)";
            case H265:
                return "H.265 (HEVC)";
            case JPEG2000:
                return "JPEG2000";
            default:
                return "Unknown or Reserved";
        }
    }

    private CompressionType(int value) {
        this.value = (byte) value;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.CompressionType.getIdentifier());
        byte[] valueBytes = new byte[] {(byte) value};
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
