package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Meteorological Metadata Version (ST 0809 Local Set Item 2).
 *
 * <p>Version number of the standards document used to encode the Meteorological metadata.
 */
public class ST0809Version implements IMeteorologicalMetadataValue {
    private final int version;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 65535;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link
     * MeteorologicalMetadataConstants#ST_VERSION_NUMBER}.
     *
     * @param version The version number
     */
    public ST0809Version(int version) {
        if (version < MIN_VALUE || version > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public ST0809Version(byte[] bytes) throws KlvParseException {
        if (bytes.length != 2) {
            throw new KlvParseException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        version = PrimitiveConverter.toUint16(bytes);
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
        return PrimitiveConverter.uint16ToBytes(version);
    }

    @Override
    public String getDisplayableValue() {
        return "ST 0809." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Version";
    }
}
