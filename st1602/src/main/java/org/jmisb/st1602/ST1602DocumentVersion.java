package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Composite Imaging Local Set Document Version Number (ST 1602 Composite Imaging Local Set Tag 2).
 *
 * <p>From ST 1602:
 *
 * <blockquote>
 *
 * The Document Version (Tag 2) identifies the version of ST 1602 used in the implementation.
 *
 * </blockquote>
 *
 * <p>The value is set to the minor version of the document; for example, ST 1602.1 would have a
 * value of 1.
 *
 * <p>This item is mandatory within the Composite Imaging Local Set.
 */
public class ST1602DocumentVersion implements ICompositeImagingValue {
    private final int version;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param version The version number
     */
    public ST1602DocumentVersion(int version) {
        if (version < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes BER-OID encoding of the unsigned integer value
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public ST1602DocumentVersion(byte[] bytes) throws KlvParseException {
        try {
            BerField berField = BerDecoder.decode(bytes, 0, true);
            this.version = berField.getValue();
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise ST 1602 Document Version: " + ex.getMessage());
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
        return BerEncoder.encode(this.version, Ber.OID);
    }

    @Override
    public String getDisplayableValue() {
        return "ST 1602." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Document Version";
    }
}
