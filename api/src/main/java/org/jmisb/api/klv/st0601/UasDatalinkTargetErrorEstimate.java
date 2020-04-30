package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Error Estimate (used by ST 0601 tag 45 and 46)
 * <blockquote>
 * From ST:
 * <p>
 * Map 0..(2^16-1) to 0..4095 metres
 * <p>
 * Resolution: ~0.0625 metres
 * </blockquote>
 */
public abstract class UasDatalinkTargetErrorEstimate implements IUasDatalinkValue
{
    protected static final double FLOAT_RANGE = 4095;
    protected static final double INT_RANGE = 65535.0; // (2^16) - 1
    protected double metres;

    /**
     * Create from value
     *
     * @param metres The value in metres
     */
    public UasDatalinkTargetErrorEstimate(double metres)
    {
        if ((metres < 0) || (metres > 4095.0))
        {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0,4095]");
        }

        this.metres = metres;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public UasDatalinkTargetErrorEstimate(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.metres = (intVal / INT_RANGE) * FLOAT_RANGE;
    }

    /**
     * Get the value in metres
     *
     * @return The error value in metres
     */
    public double getMetres()
    {
        return metres;
    }

    @Override
    public byte[] getBytes()
    {
        int val = (int)Math.round((metres / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.uint16ToBytes(val);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.4fm", metres);
    }
}
