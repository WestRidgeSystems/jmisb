package org.jmisb.st0601;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sensor Relative Elevation (ST 0601 Item 19).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Relative Elevation Angle of sensor to platform longitudinal-transverse plane. Negative angles
 * down.
 *
 * <p>Map -(2^31-1)..(2^31-1) to +/-180. Use -(2^31) as an "error" indicator. -(2^31) = 0x80000000
 *
 * <p>Resolution ~84 nano degrees
 *
 * </blockquote>
 */
public class SensorRelativeElevation implements IUasDatalinkValue {
    private final double degrees;
    private static final byte[] invalidBytes =
            new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static final double FLOAT_RANGE = 180.0;
    private static final double MAX_INT = 2147483647.0;

    /**
     * Create from value.
     *
     * @param degrees Sensor Relative Elevation, in degrees [-180,180], or {@code
     *     Double.POSITIVE_INFINITY} to represent "out of range"
     */
    public SensorRelativeElevation(double degrees) {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -180 || degrees > 180)) {
            throw new IllegalArgumentException(
                    "Sensor Relative Elevation angle must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Sensor Relative Elevation, encoded as a 4-byte int
     */
    public SensorRelativeElevation(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    "Sensor Relative Elevation encoding is a 4-byte int");
        }

        if (Arrays.equals(bytes, invalidBytes)) {
            degrees = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / MAX_INT) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in degrees.
     *
     * @return Degrees in range [-180,180], or Double.POSITIVE_INFINITY if error condition was
     *     specified.
     */
    public double getDegrees() {
        return degrees;
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

    @Override
    public String getDisplayName() {
        return "Sensor Relative Elevation";
    }
}
