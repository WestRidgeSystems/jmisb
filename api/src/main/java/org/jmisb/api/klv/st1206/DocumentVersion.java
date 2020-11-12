package org.jmisb.api.klv.st1206;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * SARMI Document Version (ST 1206 Item 28).
 *
 * <p>The document version identifies the version of MISB ST 1206 used in the implementation.
 *
 * <p>The SARMI Document Version shall be included in all SAR Motion Imagery Metadata Local Set
 * instantiations.
 */
public class DocumentVersion implements ISARMIMetadataValue {
    private int version;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link SARMIMetadataConstants#ST_VERSION_NUMBER}.
     *
     * @param version The version number
     */
    public DocumentVersion(int version) {
        if (version < MIN_VALUE || version > MAX_VALUE) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s value must be in range [%d, %d]",
                            this.getDisplayName(), MIN_VALUE, MAX_VALUE));
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte
     */
    public DocumentVersion(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        version = PrimitiveConverter.toUint8(bytes);
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
    public String getDisplayableValue() {
        return "ST1206." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Document Version";
    }
}
