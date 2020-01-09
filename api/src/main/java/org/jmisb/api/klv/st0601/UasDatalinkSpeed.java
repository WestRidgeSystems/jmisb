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
 * UAS Datalink Speed (used by ST 0601 tag 8, 9 and 56)
 * <p>
 * Map 0..(2^8-1) to 0..255 meters/second.
 * <p>
 * Resolution: 1 metre/second.
 */
public class UasDatalinkSpeed implements IUasDatalinkValue {

    private int speed;
    private static double MIN_VALUE = 0;
    private static double MAX_VALUE = 255;

    /**
     * Create from value
     *
     * @param speed speed in meters/second. Legal values are in [0, 255].
     */
    public UasDatalinkSpeed(int speed) {
        if (speed > MAX_VALUE || speed < MIN_VALUE)
        {
            throw new IllegalArgumentException("Speed must be in range [0, 255]");
        }
        this.speed = speed;
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 1
     */
    public UasDatalinkSpeed(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Speed encoding is a 1-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint8(bytes);
        speed = intVal;
    }

    /**
     * Get the speed
     * @return The speed in meters/second
     */
    public int getMetersPerSecond()
    {
        return speed;
    }

    @Override
    public byte[] getBytes() {
        short intVal = (short)speed;
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dm/s", speed);
    }

}
