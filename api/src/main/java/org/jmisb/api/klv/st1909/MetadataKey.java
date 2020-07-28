package org.jmisb.api.klv.st1909;

/**
 * The valid keys for a metadata item.
 *
 * <p>This is not intended to be a normal KLV key, but rather the key in a key:value Map sense.
 */
public enum MetadataKey {
    PlatformName("Platform Name"),
    PlatformLatitude("Platform Latitude"),
    PlatformLongitude("Platform Longitude"),
    PlatformAltitude("Platform Altitude"),
    TargetLatitude("Target Latitude"),
    TargetLongitude("Target Longitude"),
    TargetElevation("Target Elevation"),
    VerticalFOV("Vertical FOV"),
    HorizontalFOV("Horizontal FOV"),
    TargetWidth("Target Width"),
    SlantRange("Slant Range"),
    MetadataTimestamp("Metadata Timestamp"),
    LaserPrfCode("Laser PRF Code"),
    MainSensorName("Main Sensor Name"),
    AzAngle("Azimuth Angle"),
    ElAngle("Elevation Angle"),
    LaserSensorName("Laser Sensor Name"),
    LaserSensorStatus("Laser Sensor Status"),
    ClassificationAndReleasabilityLine1("Classification and Releasability Group Line 1"),
    ClassificationAndReleasabilityLine2("Classification and Releasability Group Line 2"),
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
