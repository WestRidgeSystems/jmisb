package org.jmisb.api.klv.st0603;

/**
 * ST0603 Time Status.
 * <p>
 * From ST0603.5 section 7.4:
 * </p>
 * <blockquote>
 * <p>
 * Systems producing a timestamp representing Absolute Time for Motion Imagery
 * and metadata will have differing requirements for the accuracy and precision
 * of the timestamp. System designers need to specify both the precision and the
 * accuracy of the timestamp, so that users of the data understand what can be
 * expected in data analysis.
 * </p>
 * <p>
 * The purpose of the Time Status is twofold:
 * </p>
 * <p>
 * 1. Provide information on the reference clock used to produce a timestamp,
 * and
 * </p>
 * <p>
 * 2. Provide a bit-efficient timestamp qualifier for use within Motion Imagery.
 * </p>
 * <p>
 * There are cases where a suitable reference for a timestamp is not available,
 * or the synchronization to a reference may be temporarily lost. The Time
 * Status provides end-user information regarding the reference for the
 * timestamp in these instances.
 * </p>
 * </blockquote>
 * <p>
 * The Time Status is a set of three flags. The reverse flag depends on the
 * discontinuity flag, since it describes the direction of the discontinuity
 * (jump).
 */
public class TimeStatus
{
    private boolean lockUnknown;
    private boolean discontinuity;
    private boolean reverse;

    private static final byte LOCK_UNKNOWN_BITMASK = (byte)(0b1 << 7);
    private static final byte DISCONTINUITY_BITMASK = (byte)(0b1 << 6);
    private static final byte REVERSE_BITMASK = (byte)(0b1 << 5);

    /**
     * Constructor.
     *
     * This will create a "non-locked, forward linear" status.
     */
    public TimeStatus()
    {
        lockUnknown = true;
        discontinuity = false;
        reverse = false;
    }

    /**
     * Create from encoded byte.
     *
     * @param encoded Encoded byte
     */
    public TimeStatus(byte encoded)
    {
        lockUnknown = ((encoded & LOCK_UNKNOWN_BITMASK) == LOCK_UNKNOWN_BITMASK);
        discontinuity = ((encoded & DISCONTINUITY_BITMASK) == DISCONTINUITY_BITMASK);
        if (discontinuity)
        {
            reverse = ((encoded & REVERSE_BITMASK) == REVERSE_BITMASK);
        }
        else
        {
            reverse = false;
        }
    }

    /**
     * Whether the time is locked or not.
     * <p>
     * Locked means that the internal clock is locked to absolute time reference.
     * Not locked also covers the "lock status is unknown" case.
     * </p>
     * @return true if the time is locked, false if the time is not locked.
     */
    public boolean isLocked()
    {
        return !lockUnknown;
    }

    /**
     * Set whether the time is locked or not.
     * <p>
     * Locked means that the internal clock is locked to absolute time reference.
     * Not locked also covers the "lock status is unknown" case.
     * </p>
     * @param locked true if the time is locked, false if the time is not locked.
     */
    public void setLocked(boolean locked)
    {
        this.lockUnknown = !locked;
    }

    /**
     * Whether the time is discontinuous.
     * <p>
     * A discontinuity means that time has not incremented forward in a linear
     * fashion. That is, a forward non-linear or reverse jump as a by-product of
     * relocking the clock to a reference, or other correction.
     * </p>
     * @return true if there is a discontinuity, or false if time is
     * incrementing in a normal linear fashion.
     */
    public boolean isDiscontinuity()
    {
        return discontinuity;
    }

    /**
     * Set whether the time is discontinuous.
     * <p>
     * A discontinuity means that time has not incremented forward in a linear
     * fashion. That is, a forward non-linear or reverse jump as a by-product of
     * relocking the clock to a reference, or other correction.
     * </p>
     *
     * @param discontinuity true if there is a discontinuity, or false if time
     * is incrementing in a normal linear fashion.
     * @param reverse true if the discontinuity is a reverse jump, or false if
     * the discontinuity is a forward jump.
     */
    public void setDiscontinuity(final boolean discontinuity, final boolean reverse)
    {
        this.discontinuity = discontinuity;
        if (discontinuity)
        {
            this.reverse = reverse;
        }
        else
        {
            this.reverse = false;
        }
    }

    /**
     * Whether the discontinuity is in the reverse direction.
     * <p>
     * Only meaningful if there is a discontinuity (see isDiscontinuity()).
     * </p>
     *
     * @return true if the discontinuity is reverse direction (step back),
     * otherwise false.
     */
    public boolean isReverse()
    {
        return reverse;
    }

    /**
     * Get the encoded value for this TimeStatus.
     * <p>
     * The encoded value is a set of bit flags in a byte. See ST0603.5 Table 3.
     * @return the encoded value, as a single byte.
     */
    byte getEncodedValue()
    {
        byte value = 0b00011111;
        if (discontinuity)
        {
            value |= DISCONTINUITY_BITMASK;
            if (reverse)
            {
                value |= REVERSE_BITMASK;
            }
        }
        if (lockUnknown)
        {
            value |= LOCK_UNKNOWN_BITMASK;
        }
        return value;
    }
}
