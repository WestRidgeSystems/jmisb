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
 * OperationalMode (ST 0601 tag 77)
 * <p>
 * From ST:
 * <blockquote>
 * Indicates the mode of operations of the event portrayed in Motion Imagery.
 * <p>
 * Map 0..5 to enumeration value
 * </blockquote>
 */
public class OperationalMode extends UasEnumeration {

    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Other"},
        {1, "Operational"},
        {2, "Training"},
        {3, "Exercise"},
        {4, "Maintenance"},
        {5, "Test"}
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param mode The value of the operational mode enumeration.
     */
    public OperationalMode(byte mode) {
        super(mode);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public OperationalMode(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the operational mode
     *
     * @return The value as an enumeration
     */
    public byte getOperationalMode() {
        return value;
    }

    @Override
    public Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Operational Mode";
    }

}
