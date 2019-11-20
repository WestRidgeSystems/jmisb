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

/*
 * Differential Pressure (ST 0601 tag 49)
 * <p>
 * From ST:
 * <blockquote>
 * Differential Pressure. Differential pressure at aircraft location. Measured as
 * the Stagnation/impact/total pressure minus static pressure
 * <p>
 * Map 0..(2^16-1) to 0..5000
 * <p>
 * Resolution: ~0.08 millibar
 * </blockquote>
 */
public class DifferentialPressure extends UasPressureMillibars {

    public DifferentialPressure(double pressureMillibars) {
        super(pressureMillibars);
    }

    public DifferentialPressure(byte[] bytes) {
        super(bytes);
    }
}
