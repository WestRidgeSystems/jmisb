package org.jmisb.api.klv.st1205;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 1205 tags - description and numbers. */
public enum CalibrationPackMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Time Stamp of Last Frame in Sequence. */
    TimeStampOfLastFrameInSequence(1),
    /** Sequence Duration (in Frames). */
    SequenceDuration(2),
    /** Time Stamp of Calibration Pack Creation. */
    TimeStampOfCalibrationPackCreation(3),
    /** Calibration Sequence Identifier. */
    CalibrationSequenceIdentifier(4);

    private final int tag;

    private static final Map<Integer, CalibrationPackMetadataKey> tagTable = new HashMap<>();

    static {
        for (CalibrationPackMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    CalibrationPackMetadataKey(int tag) {
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
     * @return corresponding metadata key
     */
    public static CalibrationPackMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
