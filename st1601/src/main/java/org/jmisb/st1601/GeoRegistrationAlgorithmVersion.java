package org.jmisb.st1601;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Geo-Registration Local Set Algorithm Version. (ST 1601 Geo-Registration Local Set Tag 3).
 *
 * <p>From ST 1601:
 *
 * <blockquote>
 *
 * The Algorithm Version item is an alphanumeric that uniquely identifies the specific version of
 * the Algorithm Name used to geo-register the imagery data.
 *
 * </blockquote>
 *
 * <p>This item is mandatory within the Geo-Registration Local Set.
 */
public class GeoRegistrationAlgorithmVersion implements IGeoRegistrationValue {
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param version the algorithm version
     */
    public GeoRegistrationAlgorithmVersion(String version) {
        this.value = new Utf8String(version);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes algorithm version, UTF-8 encoded string.
     */
    public GeoRegistrationAlgorithmVersion(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a String.
     *
     * @return the algorithm version
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
        return "Algorithm Version";
    }
}
