package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Number of Track Points (ST0903 VTracker LS Tag 8).
 * <p>
 * From ST0903:
 * <blockquote>
 * The number of coordinates of type Location that describe the
 * trackHistorySeries of VMTI detections for the target. Strictly speaking,
 * Number of Track Points is unnecessary, since the value is derivable from the
 * Length information associated with VTracker Tag 9 trackHistorySeries. If
 * specified, the number of track points needs to be at least 1, else no
 * trackHistorySeries would exist.
 * <p>
 * Valid Values: The set of all integers from 1 to 65,536 inclusive
 * </blockquote>
 */
public class NumTrackPoints implements IVmtiMetadataValue
{
    private int value;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 65535;

    /**
     * Create from value.
     *
     * @param value The number of track points.
     */
    public NumTrackPoints(int value)
    {
        if (value < MIN_VALUE || value > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [1,65535]");
        }
        this.value = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Byte array of variable length, maximum 2
     */
    public NumTrackPoints(byte[] bytes)
    {
        switch (bytes.length)
        {
            case 1:
                value = PrimitiveConverter.toUint8(bytes);
                break;
            case 2:
                value = PrimitiveConverter.toUint16(bytes);
                break;
            default:
                throw new IllegalArgumentException(this.getDisplayName() + " encoding is one or two byte unsigned integer");
        }
    }

    /**
     * Get the number of track points.
     *
     * @return The number of track points.
     */
    public int getNumberOfTrackPoints()
    {
        return value;
    }

    @Override
    public byte[] getBytes()
    {
        if (value < 256)
        {
            return PrimitiveConverter.uint8ToBytes((short)value);
        }
        return PrimitiveConverter.uint16ToBytes(value);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + value;
    }

    @Override
    public final String getDisplayName()
    {
        return "Num Track Points";
    }
}
