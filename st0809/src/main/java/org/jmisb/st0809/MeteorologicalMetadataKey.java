package org.jmisb.st0809;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 0809 tags - description and numbers. */
public enum MeteorologicalMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /**
     * Precision Time Stamp.
     *
     * <p>Meteorological Metadata Local Set Tag 1. This tag must appear first in every
     * Meteorological Metadata Local Set.
     */
    PrecisionTimeStamp(1),
    /**
     * Version number for the Meteorological Metadata Local Set.
     *
     * <p>Meteorological Metadata Local Set Tag 2. This tag must appear second in every
     * Meteorological Metadata Local Set.
     */
    Version(2),
    /**
     * Reporting ID.
     *
     * <p>Meteorological Metadata Local Set Tag 3.
     */
    ReportingID(3),
    /**
     * Device Latitude.
     *
     * <p>Meteorological Metadata Local Set Tag 4.
     */
    DeviceLatitude(4),
    /**
     * Device Longitude.
     *
     * <p>Meteorological Metadata Local Set Tag 5.
     */
    DeviceLongitude(5),
    /**
     * Barometric Pressure.
     *
     * <p>Meteorological Metadata Local Set Tag 6.
     */
    BarometricPressure(6),
    /**
     * Black Globe Temperature.
     *
     * <p>Meteorological Metadata Local Set Tag 7.
     */
    BlackGlobeTemperature(7),
    /**
     * Web Bulb Temperature Heat Index.
     *
     * <p>Meteorological Metadata Local Set Tag 8.
     */
    WetBulbTemperatureHeatIndex(8),
    /**
     * Cloud Cover Percentage.
     *
     * <p>Meteorological Metadata Local Set Tag 9.
     */
    CloudCoverPercentage(9),
    /**
     * Cloud Sky Cover Type.
     *
     * <p>Meteorological Metadata Local Set Tag 10.
     */
    CloudSkyCoverType(10),
    /**
     * Cloud Base Height.
     *
     * <p>Meteorological Metadata Local Set Tag 11.
     */
    CloudBaseHeight(11),
    /**
     * Cloud Top Height.
     *
     * <p>Meteorological Metadata Local Set Tag 12.
     */
    CloudTopHeight(12),
    /**
     * Cloud Thickness.
     *
     * <p>Meteorological Metadata Local Set Tag 13.
     */
    CloudThickness(13),
    /**
     * Carbon Dioxide Concentration.
     *
     * <p>Meteorological Metadata Local Set Tag 14.
     */
    CarbonDioxideConcentration(14),
    /**
     * Ozone Concentration.
     *
     * <p>Meteorological Metadata Local Set Tag 15.
     */
    OzoneConcentration(15),
    /**
     * Temperature.
     *
     * <p>Meteorological Metadata Local Set Tag 16.
     */
    Temperature(16),
    /**
     * Dry Bulb Temperature.
     *
     * <p>Meteorological Metadata Local Set Tag 17.
     */
    DryBulbTemperature(17),
    /**
     * Wet Bulb Temperature.
     *
     * <p>Meteorological Metadata Local Set Tag 18.
     */
    WetBulbTemperature(18),
    /**
     * Relative Humidity.
     *
     * <p>Meteorological Metadata Local Set Tag 19.
     */
    RelativeHumidity(19),
    /**
     * Dew Point.
     *
     * <p>Meteorological Metadata Local Set Tag 20.
     */
    DewPoint(20),
    /**
     * Temperature Lapse Rate.
     *
     * <p>Meteorological Metadata Local Set Tag 21.
     */
    TemperatureLapseRate(21),
    /**
     * Lighting Conditions.
     *
     * <p>Meteorological Metadata Local Set Tag 22.
     */
    LightingConditions(22),
    /**
     * Ambient Light Color Temperature.
     *
     * <p>Meteorological Metadata Local Set Tag 23.
     */
    AmbientLightColorTemperature(23),
    /**
     * Visibility Conditions.
     *
     * <p>Meteorological Metadata Local Set Tag 24.
     */
    VisibilityConditions(24),
    /**
     * Visibility.
     *
     * <p>Meteorological Metadata Local Set Tag 25.
     */
    Visibility(25),
    /**
     * Solar Illumination Diffuse.
     *
     * <p>Meteorological Metadata Local Set Tag 26.
     */
    SolarIlluminationDiffuse(26),
    /**
     * Solar Illumination Direct.
     *
     * <p>Meteorological Metadata Local Set Tag 27.
     */
    SolarIlluminationDirect(27),
    /**
     * Total Illumination Direct.
     *
     * <p>Meteorological Metadata Local Set Tag 28.
     */
    TotalIlluminationDirect(28),
    /**
     * Fog Presence.
     *
     * <p>Meteorological Metadata Local Set Tag 29.
     */
    FogPresence(29),
    /**
     * Fog Thickness.
     *
     * <p>Meteorological Metadata Local Set Tag 30.
     */
    FogThickness(30),
    /**
     * Fog Cover Percentage.
     *
     * <p>Meteorological Metadata Local Set Tag 31.
     */
    FogCoverPercentage(31),
    /**
     * Fog Extinction Coefficient.
     *
     * <p>Meteorological Metadata Local Set Tag 32.
     */
    FogExtinctionCoefficient(32),
    /**
     * Precipitation Type.
     *
     * <p>Meteorological Metadata Local Set Tag 33.
     */
    PrecipitationType(33),
    /**
     * Precipitation Rate.
     *
     * <p>Meteorological Metadata Local Set Tag 34.
     */
    PrecipitationRate(34),
    /**
     * R Naught (r<sub>0</sub>).
     *
     * <p>Meteorological Metadata Local Set Tag 35.
     */
    RNaught(35),
    /**
     * Theta Naught (θ<sub>0</sub>).
     *
     * <p>Meteorological Metadata Local Set Tag 36.
     */
    ThetaNaught(36),
    /**
     * Greenwood Frequency.
     *
     * <p>Meteorological Metadata Local Set Tag 37.
     */
    GreenwoodFrequency(37),
    /**
     * Rytov Parameter.
     *
     * <p>Meteorological Metadata Local Set Tag 38.
     */
    RytovParameter(38),
    /**
     * Tyler Tracking Frequency.
     *
     * <p>Meteorological Metadata Local Set Tag 39.
     */
    TylerTrackingFrequency(39);

    private final int tag;

    private static final Map<Integer, MeteorologicalMetadataKey> tagTable = new HashMap<>();

    static {
        for (MeteorologicalMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    MeteorologicalMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static MeteorologicalMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
