package org.jmisb.api.klv.st1108.st1108_2;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1108.2 tags - description and numbers.
 *
 * <p>Later versions of ST 1108 use different tags, and this enumeration is not applicable to
 * messages using that version of the local set.
 */
public enum LegacyIQMetadataKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It is used to provide a mapping for "any other value".
     */
    Undefined(0),
    MostRecentFrameTime(1),
    VideoInterpretability(2),
    VideoQuality(3),
    InterpretabilityQualityMethod(4),
    PSNRCoefficientIdentifier(5),
    QualityCoefficientIdentifier(6),
    RatingDuration(7),
    MIQPakInsertionTime(8),
    ChipLocationSizeBitDepth(9),
    ChipYvaluesUncompressed(10),
    ChipYvaluesPNG(11),
    ChipEdgeIntensity(12),
    ChipFrequencyRatio(13),
    ChipPSNR(14);

    private int tag;

    private static final Map<Integer, LegacyIQMetadataKey> tagTable = new HashMap<>();

    static {
        for (LegacyIQMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    LegacyIQMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key, or Undefined if the key is not known / valid.
     */
    public static LegacyIQMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
