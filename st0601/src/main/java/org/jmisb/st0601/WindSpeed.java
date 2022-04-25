package org.jmisb.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Wind Speed (ST 0601 Item 36).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Wind speed at aircraft location.
 *
 * <p>Map 0..(2^8-1) to 0..100 meters/second.
 *
 * <p>Resolution: ~0.4 meters/second.
 *
 * </blockquote>
 */
public class WindSpeed implements IUasDatalinkValue {
    private final double windSpeed;
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 100;
    private static final double RANGE = 100;
    private static final double MAX_INT = 255.0; // 2^8 - 1

    /**
     * Create from value.
     *
     * @param speed Wind speed in meters/second. Legal values are in [0, 100].
     */
    public WindSpeed(double speed) {
        if (speed > MAX_VALUE || speed < MIN_VALUE) {
            throw new IllegalArgumentException("Wind speed must be in range [0,100]");
        }
        windSpeed = speed;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public WindSpeed(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Wind speed encoding is a 1-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint8(bytes);
        windSpeed = ((intVal / MAX_INT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the wind speed.
     *
     * @return The wind speed in meters/second
     */
    public double getMetersPerSecond() {
        return windSpeed;
    }

    @Override
    public byte[] getBytes() {
        short intVal = (short) Math.round(((windSpeed - MIN_VALUE) / RANGE) * MAX_INT);
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1fm/s", windSpeed);
    }

    @Override
    public String getDisplayName() {
        return "Wind Speed";
    }
}
