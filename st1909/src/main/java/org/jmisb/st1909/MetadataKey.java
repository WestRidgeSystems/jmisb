package org.jmisb.st1909;

/**
 * The valid keys for a metadata item.
 *
 * <p>This is not intended to be a normal KLV key, but rather the key in a key:value Map sense.
 */
public enum MetadataKey {
    /** Platform name. */
    PlatformName("Platform Name"),
    /** Platform Latitude. */
    PlatformLatitude("Platform Latitude"),
    /** Platform Longitude. */
    PlatformLongitude("Platform Longitude"),
    /** Platform Altitude. */
    PlatformAltitude("Platform Altitude"),
    /** Target Latitude. */
    TargetLatitude("Target Latitude"),
    /** Target Longitude. */
    TargetLongitude("Target Longitude"),
    /** Target Elevation. */
    TargetElevation("Target Elevation"),
    /** Vertical Field of View (FOV). */
    VerticalFOV("Vertical FOV"),
    /** Horizontal Field of View (FOV). */
    HorizontalFOV("Horizontal FOV"),
    /** Target Width. */
    TargetWidth("Target Width"),
    /** Slant Range. */
    SlantRange("Slant Range"),
    /** Metadata Timestamp. */
    MetadataTimestamp("Metadata Timestamp"),
    /** Laser PRF Code. */
    LaserPrfCode("Laser PRF Code"),
    /** Main Sensor Name. */
    MainSensorName("Main Sensor Name"),
    /** Azimuth Angle. */
    AzAngle("Azimuth Angle"),
    /** Elevation Angle. */
    ElAngle("Elevation Angle"),
    /** Laser Sensor Name. */
    LaserSensorName("Laser Sensor Name"),
    /** Laser Sensor Status. */
    LaserSensorStatus("Laser Sensor Status"),
    /**
     * Classification and Releasability Line 1.
     *
     * <p>This is the upper line.
     */
    ClassificationAndReleasabilityLine1("Classification and Releasability Group Line 1"),
    /**
     * Classification and Releasability Line 2.
     *
     * <p>This is the lower line.
     */
    ClassificationAndReleasabilityLine2("Classification and Releasability Group Line 2"),
    /** True North Angle. */
    NorthAngle("True North Angle");

    private final String text;

    /**
     * Constructor.
     *
     * @param text the human-readable name for the key.
     */
    MetadataKey(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
