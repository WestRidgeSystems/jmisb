package org.jmisb.st1202;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ST 1202 Document Version (ST 1202 Generalized Transformation Local Set Tag 10).
 *
 * <p>The version number is the same of the minor version number of the standard document. For
 * example, with MISB ST 1202.1, the version number value is {@code 1} and with ST 1202.2, the
 * version number value is {@code 2}.
 *
 * <p>This implementation assumes the document version is UINT8 encoded - the standard states
 * BER-OID encoded, but also states 1 byte, and a valid range of 0 to 255. That is inconsistent,
 * although it makes no practical difference for feasible version numbers. MISB have indicated that
 * a future update (assuming there ever is one) to ST 1202 would change this to UINT8.
 */
public class ST1202DocumentVersion implements IGeneralizedTransformationMetadataValue {

    private final int version;

    /**
     * The currently supported revision is 1202.2.
     *
     * <p>This may be useful in the constructor.
     */
    public static final short ST_VERSION_NUMBER = 2;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link #ST_VERSION_NUMBER}.
     *
     * @param versionNumber The version number
     */
    public ST1202DocumentVersion(int versionNumber) {
        if (versionNumber < 0) {
            throw new IllegalArgumentException("ST 1202 Document Version cannot be negative");
        }
        this.version = versionNumber;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing UINT8 formatted version number
     */
    public ST1202DocumentVersion(byte[] bytes) {
        this.version = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the document version number.
     *
     * @return The version number
     */
    public int getVersion() {
        return version;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes((short) version);
    }

    @Override
    public String getDisplayName() {
        return "Document Version";
    }

    @Override
    public String getDisplayableValue() {
        if (version == 0) {
            return "ST 1202";
        } else {
            return "ST 1202." + version;
        }
    }
}
