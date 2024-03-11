package org.jmisb.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sensor Relative Azimuth (ST 0601 Item 18).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Relative rotation angle of sensor to platform longitudinal axis. Rotation angle between platform
 * longitudinal axis and camera pointing direction as seen from above the platform.
 *
 * <p>Map 0..(2^32-1) to 0..360
 *
 * <p>Resolution ~84 nano degrees
 *
 * </blockquote>
 */
public class SensorRelativeAzimuth implements IUasDatalinkValue {
    private final double degrees;
    private static final double RANGE = 360.0;
    private static final double MAX_INT = 4294967295.0; // 2^32-1

    /**
     * Create from value.
     *
     * @param degrees Sensor Relative Azimuth, in degrees [0,360]
     */
    public SensorRelativeAzimuth(double degrees) {
        if (degrees < 0 || degrees > 360) {
            throw new IllegalArgumentException(
                    "Sensor Relative Azimuth angle must be in range [0,360]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Sensor Relative Azimuth, encoded as a 4-byte unsigned int
     */
    public SensorRelativeAzimuth(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    "Sensor Relative Azimuth encoding is a 4-byte unsigned int");
        }
        long longVal = PrimitiveConverter.toUint32(bytes);
        this.degrees = (longVal / MAX_INT) * RANGE;
    }

    /**
     * Get the value in degrees.
     *
     * @return Degrees in range [0,360]
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        long longVal = Math.round((degrees / RANGE) * MAX_INT);
        return PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public String getDisplayName() {
        return "Sensor Relative Azimuth";
    }
}