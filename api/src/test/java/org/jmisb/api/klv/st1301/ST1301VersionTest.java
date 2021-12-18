/*
 * The MIT License
 *
 * Copyright 2021 West Ridge Systems.
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
package org.jmisb.api.klv.st1301;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST1301Version. */
public class ST1301VersionTest {

    public ST1301VersionTest() {}

    @Test
    public void checkFromValue() {
        ST1301Version uut = new ST1301Version(2);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 1301.2");
        assertEquals(uut.getBytes(), new byte[] {0x02});
        assertEquals(uut.getVersion(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkFromValueNegative() {
        new ST1301Version(-1);
    }

    @Test
    public void checkFromBytes() throws KlvParseException {
        ST1301Version uut = new ST1301Version(new byte[] {0x02});
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 1301.2");
        assertEquals(uut.getBytes(), new byte[] {0x02});
        assertEquals(uut.getVersion(), 2);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesBadLength() throws KlvParseException {
        new ST1301Version(new byte[] {0x02, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesBadBerOID() throws KlvParseException {
        new ST1301Version(new byte[] {(byte) 0x80});
    }

    @Test
    public void checkFromValue128() {
        ST1301Version uut = new ST1301Version(128);
        assertEquals(uut.getDisplayName(), "Version");
        assertEquals(uut.getDisplayableValue(), "ST 1301.128");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x81, 0x00});
        assertEquals(uut.getVersion(), 128);
    }
}
