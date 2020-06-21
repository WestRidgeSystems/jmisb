package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sensor Relative Roll (ST 0601 tag 20)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Relative roll angle of sensor to aircraft platform. Twisting angle of camera about lens axis. Top
 * of image is zero degrees. Positive angles are clockwise when looking from behind camera.
 *
 * <p>Map 0..(2^32-1) to 0..360
 *
 * <p>Resolution ~84 nano degrees
 *
 * </blockquote>
 */
public class SensorRelativeRoll implements IUasDatalinkValue {
    private double degrees;
    private static double RANGE = 360.0;
    private static double MAXINT = 4294967295.0;

    /**
     * Create from value
     *
     * @param degrees Sensor Relative Roll, in degrees [0,360]
     */
    public SensorRelativeRoll(double degrees) {
        if (degrees < 0 || degrees > 360) {
            throw new IllegalArgumentException(
                    "Sensor Relative Roll angle must be in range [0,360]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Sensor Relative Roll, encoded as a 4-byte unsigned int
     */
    public SensorRelativeRoll(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    "Sensor Relative Roll encoding is a 4-byte unsigned int");
        }
        long longVal = PrimitiveConverter.toUint32(bytes);
        this.degrees = (longVal / MAXINT) * RANGE;
    }

    /**
     * Get the value in degrees
     *
     * @return Degrees in range [0,360]
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        long longVal = Math.round((degrees / RANGE) * MAXINT);
        return PrimitiveConverter.uint32ToBytes(longVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public String getDisplayName() {
        return "Sensor Relative Roll";
    }
}
