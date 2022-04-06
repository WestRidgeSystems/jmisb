package org.jmisb.st0601;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Abstract base class for longitude values in ST 0601.
 *
 * <p>Used by items: 14, 24, 41, 68, 83, 85, 87, 89.
 *
 * <blockquote>
 *
 * Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000.
 *
 * <p>Resolution: ~84 nano degrees.
 *
 * </blockquote>
 */
public abstract class UasDatalinkLongitude implements IUasDatalinkValue {
    private final double degrees;
    private static final byte[] invalidBytes =
            new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static final double FLOAT_RANGE = 180.0;
    private static final double MAX_INT = 2147483647.0;
    /**
     * Approximate encoding error in this value.
     *
     * <p>This is based on the resolution / 2 and should be considered a precision. It is unlikely
     * to represent actual accuracy.
     */
    public static final double DELTA = 42e-9; // +/- 42 nano degrees

    private final String displayName;

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     * @param displayName human readable (display) name for this type - see static values
     */
    public UasDatalinkLongitude(double degrees, String displayName) {
        this.displayName = displayName;
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -180 || degrees > 180)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte int
     * @param displayName human readable (display) name for this type - see static values
     */
    public UasDatalinkLongitude(byte[] bytes, String displayName) {
        this.displayName = displayName;
        if (bytes.length != 4) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 4-byte int");
        }

        if (Arrays.equals(bytes, invalidBytes)) {
            degrees = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / MAX_INT) * FLOAT_RANGE;
        }
    }

    /**
     * Get the longitude in degrees.
     *
     * @return Degrees in range [-180,180], or Double.POSITIVE_INFINITY if error condition was
     *     specified.
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public byte[] getBytes() {
        if (degrees == Double.POSITIVE_INFINITY) {
            return invalidBytes.clone();
        }

        int intVal = (int) Math.round((degrees / FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }
}
