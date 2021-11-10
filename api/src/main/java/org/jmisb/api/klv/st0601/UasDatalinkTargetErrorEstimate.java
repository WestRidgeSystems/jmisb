package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Error Estimate (used by ST 0601 items 45 and 46).
 *
 * <blockquote>
 *
 * From ST:
 *
 * <p>Map 0..(2^16-1) to 0..4095 meters
 *
 * <p>Resolution: ~0.0625 meters
 *
 * </blockquote>
 */
public abstract class UasDatalinkTargetErrorEstimate implements IUasDatalinkValue {
    protected static final double FLOAT_RANGE = 4095;
    protected static final double INT_RANGE = 65535.0; // (2^16) - 1
    protected double meters;

    /**
     * Create from value.
     *
     * @param meters The value in meters
     */
    public UasDatalinkTargetErrorEstimate(double meters) {
        if ((meters < 0) || (meters > 4095.0)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0,4095]");
        }

        this.meters = meters;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public UasDatalinkTargetErrorEstimate(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.meters = (intVal / INT_RANGE) * FLOAT_RANGE;
    }

    /**
     * Get the value in meters.
     *
     * @return The error value in meters
     */
    public double getMetres() {
        return meters;
    }

    @Override
    public byte[] getBytes() {
        int val = (int) Math.round((meters / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.uint16ToBytes(val);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4fm", meters);
    }
}
