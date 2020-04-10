package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Leap Seconds (ST 0601 tag 136).
 * <p>
 * From ST:
 * <blockquote>
 * Number of leap seconds to adjust Precision Time Stamp (Tag 2) to UTC.
 * <p>
 * KLV format: int, Min: -(2^31), Max: (2^31)-1.
 * <p>
 * Length: variable, Max Length: 4, Required Length: N/A.
 * <p>
 * Resolution: 1 second.
 * <p>
 * Add this value to Precision Time Stamp (Tag 2) to convert to UTC.
 * <p>
 * When adjusting Precision Time Stamp to UTC multiply this leap second value by
 * 1,000,000 to convert it to microseconds.
 * <p>
 * See handbook for more details on Leap Seconds and the MISP Time System.
 * <p>
 * See "Packet Timestamp" section for more information on the use of this item.
 * </blockquote>
 */
public class LeapSeconds implements IUasDatalinkValue
{
    private int seconds;
    private static long MIN_VAL = -2147483648; // -2^31
    private static long MAX_VAL = 2147483647; // 2^31-1
    private static int MAX_BYTES = 4;

    /**
     * Create from value
     * @param seconds time in seconds. Legal values are in [-2^31,2^31-1].
     */
    public LeapSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array, maximum four bytes
     */
    public LeapSeconds(byte[] bytes)
    {
        if (bytes.length > MAX_BYTES)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " field length must be 1 - 4 bytes");
        }
        this.seconds = PrimitiveConverter.toInt32(bytes);
    }

    /**
     * Get the number of leap seconds
     * @return The leap second offset, in seconds
     */
    public int getSeconds()
    {
        return seconds;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.int32ToVariableBytes(this.seconds);
    }

    @Override
    public String getDisplayableValue()
    {
        return seconds + "s";
    }

    @Override
    public final String getDisplayName()
    {
        return "Leap Seconds";
    }
}
