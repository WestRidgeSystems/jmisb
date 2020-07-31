package org.jmisb.api.klv.st0601;

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
    private double windspeed;
    private static double MIN_VALUE = 0;
    private static double MAX_VALUE = 100;
    private static double RANGE = 100;
    private static double MAXINT = 255.0; // 2^8 - 1

    /**
     * Create from value.
     *
     * @param speed Wind speed in meters/second. Legal values are in [0, 100].
     */
    public WindSpeed(double speed) {
        if (speed > MAX_VALUE || speed < MIN_VALUE) {
            throw new IllegalArgumentException("Wind speed must be in range [0,100]");
        }
        windspeed = speed;
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
        windspeed = ((intVal / MAXINT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the wind speed.
     *
     * @return The wind speed in meters/second
     */
    public double getMetersPerSecond() {
        return windspeed;
    }

    @Override
    public byte[] getBytes() {
        short intVal = (short) Math.round(((windspeed - MIN_VALUE) / RANGE) * MAXINT);
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1fm/s", windspeed);
    }

    @Override
    public String getDisplayName() {
        return "Wind Speed";
    }
}
