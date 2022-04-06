package org.jmisb.st0903;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 0903 tags - description and numbers.
 *
 * <p>Note that ST0903.5 removes UL keys.
 */
public enum VmtiMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Detects errors within a standalone VMTI LS. */
    Checksum(1),
    /** Microsecond count from Epoch of 1970; See MISP Time System - MISB ST 0603. */
    PrecisionTimeStamp(2),
    /** Name and/or description of the VMTI system. */
    SystemName(3),
    /** Version number of the VMTI LS used to generate the VMTI metadata. */
    VersionNumber(4),
    /** Total number of targets detected in a frame. 0 represents no targets detected. */
    TotalTargetsInFrame(5),
    /** Number of targets reported following a culling process. */
    NumberOfReportedTargets(6),
    /** Frame number identifying detected targets. Use Precision Time Stamp when available. */
    FrameNumber(7),
    /** Width of the Motion Imagery frame in pixels. */
    FrameWidth(8),
    /** Height of the Motion Imagery frame in pixels. */
    FrameHeight(9),
    /** String of VMTI source sensor. E.g. 'EO Nose', 'EO Zoom (DLTV)'. */
    SourceSensor(10),
    /** Horizontal field of view of imaging sensor input to VMTI process. */
    HorizontalFieldOfView(11),
    /** Vertical field of view of imaging sensor input to VMTI process. */
    VerticalFieldOfView(12),
    /**
     * A Motion Imagery Identification System (MIIS) Core Identifier conformant with MISB ST 1204.
     */
    MiisId(13),
    /** VTarget Packs ordered as a Series. */
    VTargetSeries(101),
    /** Series of one or more Algorithm LS. */
    AlgorithmSeries(102),
    /** Series of one or more Ontology LS. */
    OntologySeries(103);

    private final int tag;

    private static final Map<Integer, VmtiMetadataKey> tagTable = new HashMap<>();

    static {
        for (VmtiMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    /**
     * Constructor.
     *
     * <p>Internal use only.
     *
     * @param tag the tag value to initialise the enumeration value.
     */
    VmtiMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with the metadata key.
     *
     * <p>This is the value used in the Key part of the KLV encoding.
     *
     * @return metadata key tag value as an integer
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up a metadata key from its tag value.
     *
     * @param tag the tag value (Key part of KLV encoding).
     * @return the corresponding metadata key.
     */
    public static VmtiMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
