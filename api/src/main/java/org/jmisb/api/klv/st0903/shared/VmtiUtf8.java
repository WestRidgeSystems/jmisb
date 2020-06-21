package org.jmisb.api.klv.st0903.shared;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/** Base class for URI and plain text string implementations. */
public abstract class VmtiUtf8 implements IVmtiMetadataValue {
    protected final String displayName;
    protected final String stringValue;

    /**
     * Create from value
     *
     * @param name The display name for the string
     * @param value The string value
     */
    public VmtiUtf8(String name, String value) {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param name The display name for the string
     * @param bytes Encoded byte array
     */
    public VmtiUtf8(String name, byte[] bytes) {
        this.displayName = name;
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the value
     *
     * @return The string value
     */
    public String getValue() {
        return stringValue;
    }

    @Override
    public byte[] getBytes() {
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue() {
        return stringValue;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
