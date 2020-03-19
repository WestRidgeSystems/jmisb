package org.jmisb.api.klv.st0903.vtracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Metadata tag numbers for VTracker local set.
 */
public enum VTrackerMetadataKey
{
    /**
     * Unknown key. This should not be created.
     */
    Undefined(0),
    /**
     * A unique identifier (UUID) for the track.
     */
    trackId(1),
    /**
     * Enumeration indicating the current state of VMTI detections for a given
     * entity (Inactive, Active, Dropped, Stopped).
     */
    detectionStatus(2),
    /**
     * Start time for the first observation of the entity.
     */
    startTime(3),
    /**
     * End time for the most recent observation of the entity.
     */
    endTime(4),
    /**
     * Set of vertices of type Location that specify a minimum bounding area or
     * volume, which encloses the full extent of VMTI detections for the entity.
     */
    boundarySeries(5),
    /**
     * Name or description of the algorithm or method used to create or maintain
     * object movement reports or intervening predictions of such movement.
     */
    algorithm(6),
    /**
     * An estimation of the certainty or correctness of VMTI movement
     * detections. Larger values indicate greater confidence.
     */
    confidence(7),
    /**
     * Number of coordinates of type Location that describe the history of VMTI
     * detections described by trackHistorySeries.
     */
    numTrackPoints(8),
    /**
     * Points of type Location that represent the locations of VMTI detections.
     */
    trackHistorySeries(9),
    /**
     * Velocity of the entity at the time of last observation.
     */
    velocity(10),
    /**
     * Acceleration of the entity at the time of last observation.
     */
    acceleration(11),
    /**
     * Identifier indicating which algorithm in the Algorithm Series tracked
     * this target.
     */
    algorithmId(12);

   
    private int tag;

    private static final Map<Integer, VTrackerMetadataKey> tagTable = new HashMap<>();

    static
    {
        for (VTrackerMetadataKey key : values())
        {
            tagTable.put(key.tag, key);
        }
    }

    VTrackerMetadataKey(int tag)
    {
        this.tag = tag;
    }

    /**
     * Get the tag number associated with this VTracker tag.
     * @return
     */
    public int getTag()
    {
        return tag;
    }

    /**
     * Look up a VTracker tag by enum value.
     *
     * @param tag the tag number.
     * @return the corresponding VTracker tag.
     */
    public static VTrackerMetadataKey getKey(int tag)
    {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }

}
