package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Time Airborne (ST 0601 tag 110).
 * <p>
 * From ST:
 * <blockquote>
 * Number of seconds aircraft has been airborne.
 * <p>
 * KLV format: uint, Min: 0, Max: (2^32)-1.
 * <p>
 * Length: variable, Max Length: 4, Required Length: N/A.
 * <p>
 * Resolution: 1 second.
 * <p>
 * This item is related to the "Take-Off Time" (Tag 131). Suggest using "Time
 * airborne" (Tag 110) or "Take-Off Time" (Tag 131) but not both in the same
 * MISB ST 0601 Local Set.
 * <p>
 * Time Airborne is a continual count of the number of seconds since the
 * aircraft took off from the ground (or ship). The Take-Off time (Tag 131) is
 * the timestamp indicating when the aircraft became airborne. The Time Airborne
 * and Take-Off Time are related mathematically using the Precision Time Stamp
 * (Tag 2), so the Local Set needs only one of these items to compute the other.
 * </blockquote>
 */
public class TimeAirborne implements IUasDatalinkValue
{
    private long seconds;
    private static long MIN_VAL = 0;
    private static long MAX_VAL = 4294967295L; // 2^32-1
    private static int MAX_BYTES = 4;

    /**
     * Create from value
     * @param seconds time in seconds. Legal values are in [0,2^31-1].
     */
    public TimeAirborne(long seconds)
    {
        if (seconds > MAX_VAL || seconds < MIN_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,2^32-1]");
        }
        this.seconds = seconds;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array, maximum four bytes
     */
    public TimeAirborne(byte[] bytes)
    {
        if (bytes.length > MAX_BYTES)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " field length must be 1 - 4 bytes");
        }
        this.seconds = PrimitiveConverter.variableBytesToUint32(bytes);
    }

    /**
     * Get the time airborne
     * @return The time airborne, in seconds
     */
    public long getSeconds()
    {
        return seconds;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint32ToVariableBytes(this.seconds);
    }

    @Override
    public String getDisplayableValue()
    {
        return seconds + "s";
    }

    @Override
    public final String getDisplayName()
    {
        return "Time Airborne";
    }
}
