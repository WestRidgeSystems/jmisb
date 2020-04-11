package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vtracker.VTrackerLS;

/**
 * VTracker tracking information (ST0903 VTarget Pack Tag 104).
 * <p>
 * From ST0903:
 * <blockquote>
 * Contains spatial and temporal information ancillary to VChip, VObject, and
 * VFeature to assist in tracking the target. Such information allows Motion
 * Imagery tracking algorithms to produce better tracks from the VMTI target
 * information. Note: In general, use the VTrack (no “er”) LS over the VTracker
 * LS for the representation of target tracks.
 * </blockquote>
 */
public class VTracker implements IVmtiMetadataValue
{
    private final VTrackerLS value;

    /**
     * Create from value.
     *
     * @param vtracker the VTracker Local Set.
     */
    public VTracker(VTrackerLS vtracker)
    {
        value = vtracker;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VTracker LS
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VTracker(byte[] bytes) throws KlvParseException
    {
        value = new VTrackerLS(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        return value.getBytes();
    }

    @Override
    public String getDisplayableValue()
    {
        return "[VTracker]";
    }

    @Override
    public final String getDisplayName()
    {
        return "VTracker";
    }

    /**
     * Get the VTrackerLS.
     *
     * @return the tracker local set.
     */
    public VTrackerLS getTracker()
    {
        return value;
    }
}
