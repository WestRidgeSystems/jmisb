package org.jmisb.api.klv.st1301;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * MIIS Local Set Version (ST 1301 Local Set Item 3).
 *
 * <p>Version number of the MIIS Local Set standards document (ST 1301) used to encode the local
 * set.
 *
 * <p>As an example, ST 1301.2 has the version {@code 2}, which would be BER-OID encoded as {@code
 * 0x02}.
 */
public class ST1301Version implements IMiisMetadataValue {
    private static final int MIN_VALUE = 0;
    private final int version;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link MiisMetadataConstants#ST_VERSION_NUMBER}.
     *
     * @param version The version number
     */
    public ST1301Version(int version) {
        if (version < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of variable length, BER-OID encoded
     * @throws KlvParseException if {@code bytes} does not have consistent length with the encoded
     *     value
     */
    public ST1301Version(byte[] bytes) throws KlvParseException {
        BerField ber;
        try {
            ber = BerDecoder.decode(bytes, 0, true);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
        if (ber.getLength() != bytes.length) {
            throw new KlvParseException(
                    String.format(
                            "Version length inconsistent - provided %d bytes, but encoding was %d",
                            bytes.length, ber.getLength()));
        }
        this.version = ber.getValue();
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
        return BerEncoder.encode(this.version, Ber.OID);
    }

    @Override
    public String getDisplayableValue() {
        return "ST 1301." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Version";
    }
}
