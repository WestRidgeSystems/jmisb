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

/**
 * Platform Indicated Airspeed (ST 0601 tag 9)
 * <p>
 * From ST:
 * <blockquote>
 * Indicated airspeed (IAS) of platform. Derived from Pitot tube and static pressure sensors.
 * <p>
 * Map 0..(2^8-1) to 0..255 meters/second.
 * <p>
 * Resolution: 1 metre/second.
 * </blockquote>
 */
public class PlatformIndicatedAirspeed extends UasDatalinkSpeed implements IUasDatalinkValue {

    /**
     * Create from value
     *
     * @param speed Air speed in meters/second. Legal values are in [0, 255].
     */
    public PlatformIndicatedAirspeed(int speed) {
        super(speed);
    }

    /**
     * Create from encoded bytes
     * @param bytes The byte array of length 1
     */
    public PlatformIndicatedAirspeed(byte[] bytes)
    {
        super(bytes);
    }
}
