package org.jmisb.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared Range - used by ST 0601 items 21 (Slant Range) and 57 (Ground Range).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Map 0..(2^32-1) to 0..5000000 meters.
 *
 * <p>Resolution: ~1.2 milli meters.
 *
 * </blockquote>
 */
public abstract class UasRange implements IUasDatalinkValue {

    private static final double MIN_VAL = 0.0;
    private static final double MAX_VAL = 5000000.0;
    private static final double MAX_INT = 4294967295.0; // 2^32-1
    /**
     * Approximate encoding error in this value.
     *
     * <p>This is based on the resolution / 2 and should be considered a precision. It is unlikely
     * to represent actual accuracy.
     */
    public static final double DELTA = 0.6e-3; // +/- 0.6 mm

    private double meters;

    /**
     * Create from value.
     *
     * @param meters Range in meters, in [0,5000000]
     */
    public UasRange(double meters) {
        if (meters < MIN_VAL || meters > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [0,5000000]");
        }
        this.meters = meters;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Range, encoded as a 4-byte unsigned int
     */
    public UasRange(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 4-byte unsigned int");
        }
        long longVal = PrimitiveConverter.toUint32(bytes);
        this.meters = (longVal / MAX_INT) * MAX_VAL;
    }

    /**
     * Get the value in meters.
     *
     * @return Meters in range [0,5000000]
     */
    public double getMeters() {
        return meters;
    }

    @Override
    public byte[] getBytes() {
        long longVal = Math.round((meters / MAX_VAL) * MAX_INT);
        return PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3fm", meters);
    }
}
