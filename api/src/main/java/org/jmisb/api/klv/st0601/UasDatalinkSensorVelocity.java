package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

import java.util.Arrays;

/**
 * Abstract base class for Sensor Velocity (used by ST 0601 tag 79 and 80)
 * <p>
 * From ST:
 * <blockquote>
 * <p>
 * Map (-2^15-1)..(2^15-1) to +/-327 m/sec. Use -2^15 as an "out of range" indicator.
 * -2^15 = 0x8000.
 * <p>
 * Resolution: ~1 cm/sec
 * </blockquote>
 */
abstract public class UasDatalinkSensorVelocity implements IUasDatalinkValue
{
    private double velocity;
    private static byte[] invalidBytes = new byte[]{(byte)0x80, (byte)0x00};
    private static double FLOAT_RANGE = 654.0;
    private static double INT_RANGE = 65534.0; // 2^15-1

    /**
     * Create from value
     * @param velocity The value in m/sec, or {@code Double.POSITIVE_INFINITY} to represent an error condition
     */
    public UasDatalinkSensorVelocity(double velocity)
    {
        if (velocity != Double.POSITIVE_INFINITY && (velocity < -327.0 || velocity > 327.0))
        {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-327,327]");
        }

        this.velocity = velocity;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 2
     */
    public UasDatalinkSensorVelocity(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 2-byte signed int");
        }

        if (Arrays.equals(bytes, invalidBytes))
        {
            velocity = Double.POSITIVE_INFINITY;
        }
        else
        {
            int intVal = PrimitiveConverter.toInt16(bytes);
            this.velocity = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the velocity
     * @return The value in m/sec, or {@code Double.POSITIVE_INFINITY} to indicate an error condition
     */
    public double getVelocity()
    {
        return velocity;
    }

    @Override
    public byte[] getBytes()
    {
        if (velocity == Double.POSITIVE_INFINITY)
        {
            return invalidBytes;
        }

        short shortVal = (short) Math.round((velocity / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.2fm/s", velocity);
    }
}
