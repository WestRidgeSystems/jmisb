/*
 * The MIT License
 *
 * Copyright 2022 West Ridge Systems.
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
package org.jmisb.st1603.nanopack;

import org.jmisb.st1603.nanopack.NanoTimeTransferPackKey;
import org.jmisb.st1603.nanopack.NanoTimeTransferPack;
import org.jmisb.st1603.nanopack.NanoTimeTransferPackFactory;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0603.NanoPrecisionTimeStamp;
import org.jmisb.st1603.localset.TimeTransferKey;
import org.jmisb.st1603.localset.TimeTransferLocalSet;
import org.testng.annotations.Test;

/** Unit tests for NanoTimeTransferPackFactory. */
public class NanoTimeTransferPackFactoryTest {

    @Test
    public void checkFactory() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x02,
                    0x09,
                    0x00,
                    0x00,
                    0x00,
                    0x0B,
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F,
                    0x15,
                    0x01,
                    0x01,
                    0x02
                };
        NanoTimeTransferPackFactory factory = new NanoTimeTransferPackFactory();
        NanoTimeTransferPack pack = factory.create(bytes);
        assertNotNull(pack);
        assertEquals(pack.displayHeader(), "Nano Time Transfer Pack");
        assertEquals(pack.getIdentifiers().size(), 2);
        assertTrue(pack.getIdentifiers().contains(NanoTimeTransferPackKey.NanoPrecisionTimeStamp));
        assertTrue(
                pack.getIdentifiers().contains(NanoTimeTransferPackKey.TimeTransferLocalSetValue));
        assertTrue(
                pack.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp)
                        instanceof NanoPrecisionTimeStamp);
        assertEquals(
                pack.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp).getDisplayableValue(),
                "1641720845123456789 ns");
        assertTrue(
                pack.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        instanceof TimeTransferLocalSet);
        assertEquals(
                pack.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        .getDisplayableValue(),
                "Time Transfer");
        INestedKlvValue localSetAsNested =
                (INestedKlvValue) pack.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue);
        assertEquals(localSetAsNested.getIdentifiers().size(), 1);
        assertTrue(localSetAsNested.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(pack.getTimeStamp().getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(pack.getTimeTransferLocalSet().getDisplayableValue(), "Time Transfer");
        assertEquals(pack.frameMessage(false), bytes);
        assertEquals(
                pack.frameMessage(true),
                new byte[] {
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F,
                    0x15,
                    0x01,
                    0x01,
                    0x02
                });
    }
}
