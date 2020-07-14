package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Abstract base class for pressure values in ST 0601.
 *
 * <p>Used by items: 37, 49, 53.
 *
 * <blockquote>
 *
 * Map 0..(2^16-1) to 0..5000 millibars.
 *
 * <p>Resolution: ~0.08 millibar
 *
 * </blockquote>
 */
public abstract class UasPressureMillibars implements IUasDatalinkValue {
    private double pressure;
    private static double RANGE = 5000.0;
    private static double MAXINT = 65535.0; // 2^16 - 1

    /**
     * Create from value
     *
     * @param pressureMillibars pressure in millibars
     */
    public UasPressureMillibars(double pressureMillibars) {
        if (pressureMillibars < 0 || pressureMillibars > RANGE) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0,5000]");
        }
        pressure = pressureMillibars;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded byte array
     */
    public UasPressureMillibars(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        pressure = (intVal / MAXINT) * RANGE;
    }

    /**
     * Get the value in millibars
     *
     * @return pressure, in millibars
     */
    public double getMillibars() {
        return pressure;
    }

    @Override
    public byte[] getBytes() {
        int intVal = (int) Math.round((pressure / RANGE) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2fmB", pressure);
    }
}
