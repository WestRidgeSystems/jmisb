package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Time Transfer Local Set Document Version Number (ST 1603 Time Transfer Local Set Tag 1).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * The Document Version identifies the version of MISB ST 1603 for the Time Transfer Local Set. The
 * value is set to the minor version of the document; for example, ST 1603.1 would have a value of
 * 1. The Document Version is present in the stream at least once every thirty seconds.
 *
 * </blockquote>
 */
public class ST1603DocumentVersion implements ITimeTransferValue {
    private final int version;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param version The version number
     */
    public ST1603DocumentVersion(int version) {
        if (version < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes,
     *     although we only support 1 or 2).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public ST1603DocumentVersion(byte[] bytes) throws KlvParseException {
        try {
            version = PrimitiveConverter.variableBytesToUint16(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1603 Document Version: " + ex.getMessage());
        }
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
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToVariableBytes(version);
    }

    @Override
    public String getDisplayableValue() {
        return "ST 1603." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Document Version";
    }
}
