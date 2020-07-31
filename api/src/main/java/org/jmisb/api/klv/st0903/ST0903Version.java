package org.jmisb.api.klv.st0903;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * VMTI LS Version Number (ST 0903 VMTI LS Tag 4).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Version number of the VMTI LS document used to generate the VMTI metadata.
 *
 * <p>Notified downstream clients of the LS version used to encode the VMTI metadata.
 *
 * <p>0 is pre-release, initial release (ST0903), or test data. 1...65535 corresponds to document
 * revisions 1 through 65535.
 *
 * </blockquote>
 */
public class ST0903Version implements IVmtiMetadataValue {
    private int version;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 65535;

    /**
     * Create from value.
     *
     * @param version The version number
     */
    public ST0903Version(int version) {
        if (version < MIN_VALUE || version > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of variable length, maximum 2
     */
    public ST0903Version(byte[] bytes) {
        switch (bytes.length) {
            case 1:
                version = PrimitiveConverter.toUint8(bytes);
                break;
            case 2:
                version = PrimitiveConverter.toUint16(bytes);
                break;
            default:
                throw new IllegalArgumentException(
                        this.getDisplayName() + " encoding is one or two byte unsigned integer");
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
        if (version < 256) {
            return PrimitiveConverter.uint8ToBytes((short) version);
        }
        return PrimitiveConverter.uint16ToBytes(version);
    }

    @Override
    public String getDisplayableValue() {
        return "ST0903." + version;
    }

    @Override
    public final String getDisplayName() {
        return "Version Number";
    }
}
