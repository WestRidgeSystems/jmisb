package org.jmisb.api.klv.st0601;

/** Represents a UAS Datalink value that is not interpreted by the library. */
public class OpaqueValue implements IUasDatalinkValue {
    private final byte[] bytes;
    /** The display name (label) used for Opaque Values. */
    public static final String DISPLAY_NAME = "Opaque Value";

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array
     */
    public OpaqueValue(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    @Override
    public byte[] getBytes() {
        return bytes.clone();
    }

    @Override
    public String getDisplayableValue() {
        return "N/A";
    }

    @Override
    public final String getDisplayName() {
        return DISPLAY_NAME;
    }
}
