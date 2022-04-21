package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Geo-Registration Local Set Document Version Number (ST 1601 Geo-Registration Local Set Tag 1).
 *
 * <p>From ST 1601:
 *
 * <blockquote>
 *
 * The Document Version item identifies the version of MISB ST 1601 used in the implementation.
 *
 * </blockquote>
 *
 * <p>The value is set to the minor version of the document; for example, ST 1601.1 would have a
 * value of 1.
 *
 * <p>This item is mandatory within the Geo-Registration Local Set.
 */
public class ST1601DocumentVersion implements IGeoRegistrationValue {
    private final int version;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param version The version number
     */
    public ST1601DocumentVersion(int version) {
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
    public ST1601DocumentVersion(byte[] bytes) throws KlvParseException {
        try {
            version = PrimitiveConverter.variableBytesToUint16(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1601 Document Version: " + ex.getMessage());
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
        return "ST 1601." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Document Version";
    }
}
