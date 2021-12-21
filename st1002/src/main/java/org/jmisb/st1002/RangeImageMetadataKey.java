package org.jmisb.st1002;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1002 tags - description and numbers.
 *
 * <p>These tags are used to identify each element in the local set.
 */
public enum RangeImageMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Range Image Precision Time Stamp. */
    PrecisionTimeStamp(1),
    /** Document Version. */
    DocumentVersion(11),
    /** Range Image Enumerations. */
    RangeImageEnumerations(12),
    /** Single Point Range Measurement (SPRM). */
    SinglePointRangeMeasurement(13),
    /**
     * Single Point Range Measurement (SPRM) Uncertainty.
     *
     * <p>Range measurement uncertainty.
     */
    SinglePointRangeMeasurementUncertainty(14),
    /**
     * Single Point Range Measurement (SPRM) Row Coordinate.
     *
     * <p>Measured Row Coordinate for Range.
     */
    SinglePointRangeMeasurementRowCoordinate(15),
    /**
     * Single Point Range Measurement (SPRM) Column Coordinate.
     *
     * <p>Measured Column Coordinate for Range.
     */
    SinglePointRangeMeasurementColumnCoordinate(16),
    /** Number of Sections in X. */
    NumberOfSectionsInX(17),
    /** Number of Sections in Y. */
    NumberOfSectionsInY(18),
    /** Generalized Transformation Local Set. */
    GeneralizedTransformationLocalSet(19),
    /** Section Data Variable Length Pack. */
    SectionDataVLP(20),
    /** CRC-16-CCITT. */
    CRC16CCITT(21);

    private final int tag;

    private static final Map<Integer, RangeImageMetadataKey> tagTable = new HashMap<>();

    static {
        for (RangeImageMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private RangeImageMetadataKey(int tag) {
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
    public static RangeImageMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
