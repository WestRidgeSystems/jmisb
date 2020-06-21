package org.jmisb.api.klv.st0806.userdefined;

/**
 * User Data for use in a RVT User Defined Data local set.
 *
 * <p>Required when using User Defined Local Set.
 */
public class RvtUserData implements IRvtUserDefinedMetadataValue {
    private final byte[] data;

    /**
     * Create from encoded bytes.
     *
     * <p>This is also the only way to construct "from value", given the open-ended nature of this
     * data type.
     *
     * @param bytes Encoded byte array
     */
    public RvtUserData(byte[] bytes) {
        this.data = bytes.clone();
    }

    @Override
    public byte[] getBytes() {
        return this.data.clone();
    }

    @Override
    public String getDisplayableValue() {
        return "[User Data]";
    }

    @Override
    public String getDisplayName() {
        return "User Data";
    }
}
