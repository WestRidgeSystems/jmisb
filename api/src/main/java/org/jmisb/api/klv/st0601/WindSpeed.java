/*
 * The MIT License
 *
 * Copyright 2019 West Ridge Systems.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Wind Speed (ST 0601 tag 36)
 * <p>
 * From ST:
 * <blockquote>
 * Wind speed at aircraft location.
 * <p>
 * Map 0..(2^8-1) to 0..100 meters/second.
 * <p>
 * Resolution: ~0.4 meters/second.
 * </blockquote>
 */
public class WindSpeed implements IUasDatalinkValue {

    private double windspeed;
    private static double MIN_VALUE = 0;
    private static double MAX_VALUE = 100;
    private static double RANGE = 100;
    private static double MAXINT = 255.0; // 2^8 - 1

    /**
     * Create from value
     *
     * @param speed Wind speed in meters/second. Legal values are in [0, 100].
     */
    public WindSpeed(double speed) {
        if (speed > MAX_VALUE || speed < MIN_VALUE)
        {
            throw new IllegalArgumentException("Wind speed must be in range [0,100]");
        }
        windspeed = speed;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 1
     */
    public WindSpeed(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Wind speed encoding is a 1-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint8(bytes);
        windspeed = ((intVal / MAXINT) * RANGE) + MIN_VALUE;
    }

    /**
     * Get the wind speed
     * @return The wind speed in meters/second
     */
    public double getMetersPerSecond()
    {
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

}
