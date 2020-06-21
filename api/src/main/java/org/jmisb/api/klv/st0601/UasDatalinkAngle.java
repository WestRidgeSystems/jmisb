package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Generic angle (used by ST 0601 tag 6, 50 and 52)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Map (-2^15-1)..(2^15-1) to +/-20. Use -2^15 as an "out of range" indicator. -2^15 = 0x8000.
 *
 * <p>Resolution: ~610 micro degrees
 *
 * </blockquote>
 */
public abstract class UasDatalinkAngle implements IUasDatalinkValue {
    static final byte[] invalidBytes = new byte[] {(byte) 0x80, (byte) 0x00};
    protected static final double FLOAT_RANGE = 40.0;
    protected static final double INT_RANGE = 65534.0; // 2^15-1
    protected double degrees;

    /**
     * Create from value
     *
     * @param degrees The value in degrees, or {@code Double.POSITIVE_INFINITY} to represent an
     *     error condition
     */
    public UasDatalinkAngle(double degrees) {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -20 || degrees > 20)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-20,20]");
        }

        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public UasDatalinkAngle(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 2-byte signed int");
        }

        if (Arrays.equals(bytes, invalidBytes)) {
            degrees = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt16(bytes);
            this.degrees = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in degrees
     *
     * @return The value in degrees, or {@code Double.POSITIVE_INFINITY} to indicate an error
     *     condition
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        if (degrees == Double.POSITIVE_INFINITY) {
            return invalidBytes.clone();
        }
        short shortVal = (short) Math.round((degrees / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }
}
