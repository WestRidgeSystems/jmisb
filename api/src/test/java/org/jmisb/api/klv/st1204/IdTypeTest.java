/*
 * The MIT License
 *
 * Copyright 2020 West Ridge Systems.
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
package org.jmisb.api.klv.st1204;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class IdTypeTest {
    
    public IdTypeTest() {
    }

    @Test
    public void checkEnumValue() {
        assertEquals(IdType.None.getValue(), 0);
        assertEquals(IdType.Managed.getValue(), 1);
        assertEquals(IdType.Virtual.getValue(), 2);
        assertEquals(IdType.Physical.getValue(), 3);
    }

    @Test
    public void checkFromValue() {
        assertEquals(IdType.fromValue(0), IdType.None);
        assertEquals(IdType.fromValue(1), IdType.Managed);
        assertEquals(IdType.fromValue(2), IdType.Virtual);
        assertEquals(IdType.fromValue(3), IdType.Physical);
    }
    
    @Test
    public void checkOutOfRangeValue() {
        assertEquals(IdType.fromValue(-1), IdType.None);
        assertEquals(IdType.fromValue(4), IdType.None);
    }
}
