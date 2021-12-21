package org.jmisb.st1002;

import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;

/**
 * Version Number (ST 1002 Range Image Local Set Tag 11).
 *
 * <p>The version number is the same of the minor version number of the standard document. For
 * example, with MISB ST 1002.1, the version number value is {@code 1} and with ST 1002.2, the
 * version number value is {@code 2}.
 */
public class ST1002VersionNumber implements IRangeImageMetadataValue {

    private final int version;

    /**
     * The currently supported revision is 1002.2.
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
    public ST1002VersionNumber(int versionNumber) {
        if (versionNumber < 0) {
            throw new IllegalArgumentException("ST 1002 Version Number cannot be negative");
        }
        this.version = versionNumber;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing BER-OID formatted version number
     */
    public ST1002VersionNumber(byte[] bytes) {
        BerField berField = BerDecoder.decode(bytes, 0, true);
        this.version = berField.getValue();
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
        return BerEncoder.encode(version, Ber.OID);
    }

    @Override
    public String getDisplayName() {
        return "Version Number";
    }

    @Override
    public String getDisplayableValue() {
        if (version == 0) {
            return "ST 1002";
        } else {
            return "ST 1002." + version;
        }
    }
}
