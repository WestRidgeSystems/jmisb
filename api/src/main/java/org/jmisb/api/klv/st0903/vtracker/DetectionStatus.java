package org.jmisb.api.klv.st0903.vtracker;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiEnumeration;

/**
 * Detection State (ST 0903 VTracker LS Tag 2)
 * <p>
 * From ST0903:
 * <blockquote>
 * An enumerated data value.
 * <p>
 * Inactive: The VMTI detections for the entity have ended. The entity may have
 * merged with one or more other entities; have split into two or more new
 * entities; or have ceased to exist because no VMTI detection can be correlated
 * with it.
 * <p>
 * Active: Detections for the entity established or updated based on associated
 * VMTI report or prediction. An entity can resume this state by transition from
 * Stopped or Dropped to “moving” when a VMTI detection (or a prediction) with a
 * new position has become associated with it.
 * <p>
 * Dropped: The entity could not be correlated with any VMTI detection for an
 * interval of time exceeding a specified threshold. An entity can remain in a
 * Dropped or “lost” condition for an indeterminate period if there is some
 * likelihood it may resume (Active) again. Eventually, it may become Inactive.
 * <p>
 * Stopped: The entity has either become stationary or was always in a fixed
 * location.
 * </blockquote>
 */
public class DetectionStatus extends VmtiEnumeration implements IVmtiMetadataValue
{
    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Inactive"},
        {1, "Active"},
        {2, "Dropped"},
        {3, "Stopped"}
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param state The value of the detection state enumeration.
     */
    public DetectionStatus(byte state)
    {
        super(state);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public DetectionStatus(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public Map<Integer, String> getDisplayValues()
    {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName()
    {
        return "Detection Status";
    }

    /**
     * Get the detection status.
     *
     * @return The value as an enumeration
     */
    public byte getDetectionStatus()
    {
        return value;
    }
}
