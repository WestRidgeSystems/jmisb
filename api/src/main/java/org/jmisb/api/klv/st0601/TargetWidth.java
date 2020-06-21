package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Width (ST 0601 tag 22)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Target Width within sensor field of view.
 *
 * <p>Map 0..(2^16-1) to 0..10000 meters.
 *
 * <p>Resolution: ~0.16 meters
 *
 * </blockquote>
 */
public class TargetWidth implements IUasDatalinkValue {
    private double meters;
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 10000.0;
    private static double MAXINT = 65535.0; // 2^16 - 1
    public static double DELTA = 0.08; // +/- .08 meters

    /**
     * Create from value
     *
     * @param meters Target width, in meters
     */
    public TargetWidth(double meters) {
        if (meters < MIN_VAL || meters > MAX_VAL) {
            throw new IllegalArgumentException("Target Width must be in range [0,10000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded byte array
     */
    public TargetWidth(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException("Target Width encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.meters = (intVal / MAXINT) * MAX_VAL;
    }

    /**
     * Get the value in meters
     *
     * @return Target width in meters
     */
    public double getMeters() {
        return meters;
    }

    @Override
    public byte[] getBytes() {
        int intVal = (int) Math.round((meters / MAX_VAL) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2fm", meters);
    }

    @Override
    public String getDisplayName() {
        return "Target Width";
    }
}
