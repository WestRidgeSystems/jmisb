package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;

/**
 * Security Local Set (ST 0601 Item 48).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Local set Item to include the ST 0102 Local Set Security Metadata items within ST 0601. Use the
 * MISB ST 0102 Local Set items within the MISB ST 0601 item 48.
 *
 * <p>The length field is the size of all MISB ST 0102 metadata items to be packaged within item 48.
 *
 * </blockquote>
 */
public class NestedSecurityMetadata implements IUasDatalinkValue {
    private SecurityMetadataLocalSet localSet;

    /**
     * Wrap an existing {@link SecurityMetadataLocalSet}
     *
     * @param localSet Existing SecurityMetadataLocalSet object
     */
    public NestedSecurityMetadata(SecurityMetadataLocalSet localSet) {
        this.localSet = localSet;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded bytes representing a nested ST 0102 local set
     * @throws KlvParseException if a parsing error occurs
     */
    public NestedSecurityMetadata(byte[] bytes) throws KlvParseException {
        this.localSet = new SecurityMetadataLocalSet(bytes, false);
    }

    /**
     * Get the wrapped {@link SecurityMetadataLocalSet} object
     *
     * @return The wrapped object
     */
    public SecurityMetadataLocalSet getLocalSet() {
        return localSet;
    }

    @Override
    public byte[] getBytes() {
        return localSet.frameMessage(true);
    }

    @Override
    public String getDisplayableValue() {
        return "[Security metadata]";
    }

    @Override
    public String getDisplayName() {
        return "Security";
    }
}
