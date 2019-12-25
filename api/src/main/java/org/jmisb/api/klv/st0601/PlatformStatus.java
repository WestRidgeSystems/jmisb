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

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Platform Status (ST 0601 tag 125)
 * <p>
 * From ST:
 * <blockquote>
 * Enumeration of operational modes of the platform (e.g., in-route, RTB)
 * <p>
 * Map 0..12 to enumeration value
 * </blockquote>
 */
public class PlatformStatus extends UasEnumeration {

    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Active"},
        {1, "Pre-flight"},
        {2, "Pre-flight-taxiing"},
        {3, "Run-up"},
        {4, "Take-off"},
        {5, "Ingress"},
        {6, "Manual operation"},
        {7, "Automated-orbit"},
        {8, "Transitioning"},
        {9, "Egress"},
        {10, "Landing"},
        // TODO: typo in source
        {11, "Landed-taxiing"},
        {12, "Landed-Parked"}
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param status The value of the platform status enumeration.
     */
    public PlatformStatus(byte status) {
        super(status);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public PlatformStatus(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the platform status
     *
     * @return The value as an enumeration
     */
    public byte getPlatformStatus() {
        return value;
    }

    @Override
    public Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Platform Status";
    }

}
