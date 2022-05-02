package org.jmisb.st1601;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Geo-Registration Local Set Second Image Name. (ST 1601 Geo-Registration Local Set Tag 6).
 *
 * <p>From ST 1601:
 *
 * <blockquote>
 *
 * The Second Image Name item uniquely identifies the second Image used in the geo-registration
 * process.
 *
 * </blockquote>
 *
 * <p>This item is optional within the Geo-Registration Local Set.
 */
public class SecondImageName implements IGeoRegistrationValue {
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param name the second image name
     */
    public SecondImageName(String name) {
        this.value = new Utf8String(name);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes second image name, UTF-8 encoded string.
     */
    public SecondImageName(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a String.
     *
     * @return the second image name
     */
    public String getValue() {
        return value.getValue();
    }

    @Override
    public byte[] getBytes() {
        return value.getBytes();
    }

    @Override
    public String getDisplayableValue() {
        return value.getValue();
    }

    @Override
    public final String getDisplayName() {
        return "Second Image Name";
    }
}
