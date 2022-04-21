package org.jmisb.st1601;

import org.jmisb.api.klv.st0107.Utf8String;

/**
 * Geo-Registration Local Set Algorithm Name. (ST 1601 Geo-Registration Local Set Tag 2).
 *
 * <p>From ST 1601:
 *
 * <blockquote>
 *
 * The Algorithm Name item uniquely identifies the algorithm used to geo-register the imagery data
 * to produce revised sensor model parameter values.
 *
 * </blockquote>
 *
 * <p>This item is mandatory within the Geo-Registration Local Set.
 */
public class GeoRegistrationAlgorithmName implements IGeoRegistrationValue {
    private final Utf8String value;

    /**
     * Create from value.
     *
     * @param name the algorithm name
     */
    public GeoRegistrationAlgorithmName(String name) {
        this.value = new Utf8String(name);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes algorithm name, UTF-8 encoded string.
     */
    public GeoRegistrationAlgorithmName(byte[] bytes) {
        this.value = new Utf8String(bytes);
    }

    /**
     * Get the value as a String.
     *
     * @return the algorithm name
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
        return "Algorithm Name";
    }
}
