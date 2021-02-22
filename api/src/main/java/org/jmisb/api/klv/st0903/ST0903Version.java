package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * VMTI LS Version Number (ST 0903 VMTI LS Item 4 and VTrack LS Item 11).
 *
 * <p>Version number of the VMTI standards document used to encode the VMTI metadata.
 *
 * <p>Notifies downstream clients of the VMTI or VTrack LS version used to encode the VMTI metadata.
 *
 * <p>0 is pre-release, initial release (EG0903), or test data. 1...65535 corresponds to document
 * revisions 1 through 65535.
 *
 * <p>The significant step is from 3 to 4, which changed floating point encoding.
 */
public class ST0903Version implements IVmtiMetadataValue, IVTrackMetadataValue {
    private int version;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 65535;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link VmtiMetadataConstants#ST_VERSION_NUMBER}.
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
