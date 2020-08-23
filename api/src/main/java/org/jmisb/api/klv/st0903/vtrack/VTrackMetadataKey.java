package org.jmisb.api.klv.st0903.vtrack;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 0903 VTrack tags - description and numbers.
 *
 * <p>Note that ST0903.5 removes UL keys.
 */
public enum VTrackMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /**
     * Checksum value.
     *
     * <p>Detects errors within a standalone VTrack Local Set.
     *
     * <p>Unlike VMTI, VTrack is typically standalone.
     */
    Checksum(1),
    /**
     * Track Time Stamp.
     *
     * <p>Indicates the time of the track report. By contrast, Track End Time (Item 6) indicates the
     * time of the most recent observation of the entity.
     *
     * <p>Microsecond count from Epoch of 1970; See MISP Time System - MISB ST 0603.
     */
    TrackTimeStamp(2),
    TrackId(3),
    TrackStatus(4),
    TrackStartTime(5),
    TrackEndTime(6),
    TrackBoundarySeries(7),
    TrackAlgorithm(8),
    TrackConfidence(9),
    SystemName(10),
    VersionNumber(11),
    /** String of VMTI source sensor. E.g. 'EO Nose', 'EO Zoom (DLTV)'. */
    SourceSensor(12),
    NumTrackPoints(13),
    /**
     * VTrackItem Packs ordered as a Series.
     *
     * <p>These are track metadata values, each of which is a VTrackItem.
     */
    VTrackItemSeries(101),
    /** Series of one or more Ontology Local Sets. */
    OntologySeries(103);

    private int tag;

    private static final Map<Integer, VTrackMetadataKey> tagTable = new HashMap<>();

    static {
        for (VTrackMetadataKey key : values()) {
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
    private VTrackMetadataKey(int tag) {
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
    public static VTrackMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}
