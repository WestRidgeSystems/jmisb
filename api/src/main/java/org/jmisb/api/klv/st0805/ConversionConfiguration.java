package org.jmisb.api.klv.st0805;

/**
 * Configuration options for KLV to CoT conversion.
 *
 * <p>This is intended to be set up, and be enduring over multiple CoT conversions.
 */
public class ConversionConfiguration {

    private static final long SECONDS_TO_MICROSECONDS = 1_000_000;

    /**
     * Platform type.
     *
     * <p>Defaults to atom-friendly-Air AOB(a-f-A).
     */
    private String platformType = "a-f-A";

    /**
     * Stale time in microseconds.
     *
     * <p>defaults to 5 seconds.
     */
    private long stalePeriod = 5 * SECONDS_TO_MICROSECONDS;

    /**
     * Override value for platform UID.
     *
     * <p>This takes precedence over the uid that would be derived from the Platform Designation and
     * Mission ID values in the message.
     */
    private String platformUidOverride;

    /**
     * Fallback value for platform UID.
     *
     * <p>This is used if the Platform Designation and Mission ID values in the message are not
     * valid.
     */
    private String platformUidFallback = "jmisb";

    /**
     * Override value for sensor UID.
     *
     * <p>This takes precedence over the uid that would be derived from the Platform Designation,
     * Mission ID and Image Sensor Source values in the message.
     */
    private String sensorUidOverride;

    /**
     * Fallback value for sensor UID suffix.
     *
     * <p>This provides a fallback suffix for the sensor UID construction. It does not provide the
     * whole UID, but instead will be added to the platform UID fallback if the image source sensor
     * value is not available.
     */
    private String sensorSuffixFallback = "UNKNOWN";

    /**
     * Get the CoT platform type.
     *
     * @return the platform type as a valid CoT platform type.
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * Set the CoT platform type.
     *
     * <p>Examples are "a-f-A-M-F" for atom-friendly-Air AOB-Military-Fixed Wing, or "a-f-A-M-F-Q"
     * for a UAV variant of that. Substitute "H" for "F" to represent rotary wing. See the CoT
     * "event.xml" list for options, of which there are many.
     *
     * @param platformType the platform type as String.
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * Get the stale time.
     *
     * <p>This is the time after the valid time at which the message is no longer valid.
     *
     * @return stale time in microseconds
     */
    public long getStalePeriod() {
        return stalePeriod;
    }

    /**
     * Set the stale time.
     *
     * <p>This is the time after the valid time at which the message is no longer valid.
     *
     * @param stalePeriod the stale period in microseconds.
     */
    public void setStalePeriod(long stalePeriod) {
        this.stalePeriod = stalePeriod;
    }

    /**
     * Get an override value for the Platform {@code uid} string.
     *
     * @return the uid string, or null if the uid should not be overridden.
     */
    public String getPlatformUidOverride() {
        return platformUidOverride;
    }

    /**
     * Set the override value for the Platform {@code uid}.
     *
     * @param uid the platform uid.
     */
    public void setPlatformUidOverride(String uid) {
        this.platformUidOverride = uid;
    }

    /**
     * Get an override value for the Sensor {@code uid} string.
     *
     * @return the uid string, or null if the uid should not be overridden.
     */
    public String getSensorUidOverride() {
        return sensorUidOverride;
    }

    /**
     * Set the override value for the Sensor {@code uid}.
     *
     * @param uid the platform uid.
     */
    public void setSensorUidOverride(String uid) {
        this.sensorUidOverride = uid;
    }

    /**
     * Get a fallback value for the Platform uid string.
     *
     * @return the uid string.
     */
    public String getPlatformUidFallback() {
        return platformUidFallback;
    }

    /**
     * Set the fallback value for the Platform {@code uid}.
     *
     * @param uid the platform uid, not null.
     */
    public void setPlatformUidFallback(String uid) {
        if (uid != null) {
            this.platformUidFallback = uid;
        }
    }

    /**
     * Get the fallback value for sensor UID suffix.
     *
     * <p>This provides a fallback suffix for the sensor UID construction. It does not provide the
     * whole UID, but instead will be added to the platform UID fallback if the image source sensor
     * value is not available.
     *
     * @return the fallback suffix as a string
     */
    public String getSensorSuffixFallback() {
        return sensorSuffixFallback;
    }

    /**
     * Set the fallback value for sensor UID suffix.
     *
     * <p>This provides a fallback suffix for the sensor UID construction. It does not provide the
     * whole UID, but instead will be added to the platform UID fallback if the image source sensor
     * value is not available.
     *
     * @param suffix the fallback sensor identifier
     */
    public void setSensorSuffixFallback(String suffix) {
        this.sensorSuffixFallback = suffix;
    }
}
