package org.jmisb.st1601;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1601 tags - description and numbers.
 *
 * <p>This enumeration maps the Geo-Registration Local Set tag values to a name, and names to tag
 * values. It conceptually corresponds to the first two columns in ST 1601.1 Table 1.
 */
public enum GeoRegistrationKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It does not correspond to any known ST 1601 tag / key.
     */
    Undefined(0),
    /**
     * Document Version.
     *
     * <p>ST 1601 Local Set Tag 1.
     */
    DocumentVersion(1),
    /**
     * Algorithm Name.
     *
     * <p>ST 1601 Local Set Tag 2.
     */
    AlgorithmName(2),
    /**
     * Algorithm Version.
     *
     * <p>ST 1601 Local Set Tag 3.
     */
    AlgorithmVersion(3),
    /**
     * Correspondence Points - Row / Column.
     *
     * <p>ST 1601 Local Set Tag 4.
     */
    CorrespondencePointsRowColumn(4),
    /**
     * Correspondence Points - Latitude / Longitude.
     *
     * <p>ST 1601 Local Set Tag 5.
     */
    CorrespondencePointsLatLon(5),
    /**
     * Second Image Name.
     *
     * <p>ST 1601 Local Set Tag 6.
     */
    SecondImageName(6),
    /**
     * Algorithm Configuration Identifier.
     *
     * <p>ST 1601 Local Set Tag 7.
     */
    AlgorithmConfigurationIdentifier(7),
    /**
     * Correspondence Points - Elevation.
     *
     * <p>ST 1601 Local Set Tag 8.
     */
    CorrespondencePointsElevation(8),
    /**
     * Correspondence Points - Row / Column Standard Deviation and Correlation Coefficients.
     *
     * <p>ST 1601 Local Set Tag 9.
     */
    CorrespondencePointsRowColumnSDCC(9),
    /**
     * Correspondence Points - Latitude / Longitude / Elevation Standard Deviation and Correlation
     * Coefficients.
     *
     * <p>ST 1601 Local Set Tag 10.
     */
    CorrespondencePointsLatLonElevSDCC(10);

    private final int tag;

    private static final Map<Integer, GeoRegistrationKey> tagTable = new HashMap<>();

    static {
        for (GeoRegistrationKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private GeoRegistrationKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the local set identifier
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding local set key
     */
    public static GeoRegistrationKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
