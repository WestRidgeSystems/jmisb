package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st1204.CoreIdentifier;

/**
 * MIIS Core Identifier (ST0903 VMTI LS Item 13 and VTrackItem Item 19).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Provides a unique identifier for the Motion Imagery stream. Appearance of this item in the
 * metadata stream is desirable but not mandatory. If the VMTI LS is subordinate to a MISB ST 0601
 * LS under Tag 74, a MIIS Core Identifier may already be present in the ST 0601 LS. In this case,
 * omit this item in the VMTI LS.
 *
 * <p>Valid Values: A value conformant with MISB ST 1204.
 *
 * </blockquote>
 */
public class MiisCoreIdentifier implements IVmtiMetadataValue, IVTrackItemMetadataValue {

    private final CoreIdentifier coreIdentifier;

    /**
     * Create from value.
     *
     * @param identifier a valid ST1204 Core Identifier
     */
    public MiisCoreIdentifier(CoreIdentifier identifier) {
        coreIdentifier = identifier;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array containing the raw values.
     */
    public MiisCoreIdentifier(byte[] bytes) {
        coreIdentifier = CoreIdentifier.fromBytes(bytes);
    }

    /**
     * Get the identifier.
     *
     * @return The identifier
     */
    public CoreIdentifier getCoreIdentifier() {
        return coreIdentifier;
    }

    @Override
    public byte[] getBytes() {
        return coreIdentifier.getRawBytesRepresentation();
    }

    @Override
    public String getDisplayableValue() {
        return coreIdentifier.getTextRepresentation();
    }

    @Override
    public String getDisplayName() {
        return "MIIS Core Identifier";
    }
}
