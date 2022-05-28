package org.jmisb.st0903.vmask;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/** Tests for BitMaskSeries. */
public class BitMaskSeriesTest {
    final byte[] stBytes =
            new byte[] {
                0x03, 0x01, 0x4A, 0x02,
                0x03, 0x01, 0x59, 0x04,
                0x03, 0x01, 0x6A, 0x02
            };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        BitMaskSeries series = new BitMaskSeries(stBytes);
        verifyStExample(series);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value = VMaskLS.createValue(VMaskMetadataKey.bitMaskSeries, stBytes);
        assertTrue(value instanceof BitMaskSeries);
        BitMaskSeries bitmask = (BitMaskSeries) value;
        verifyStExample(bitmask);
    }

    private void verifyStExample(BitMaskSeries series) {
        assertEquals(series.getBytes(), stBytes);
        assertEquals(series.getDisplayName(), "BitMask");
        assertEquals(series.getDisplayableValue(), "[Pixel / Run Pairs]");
        assertEquals(series.getRuns().size(), 3);
        assertEquals(series.getRuns().get(0).getPixelNumber(), 74L);
        assertEquals(series.getRuns().get(0).getRun(), 2);
        assertEquals(series.getRuns().get(1).getPixelNumber(), 89L);
        assertEquals(series.getRuns().get(1).getRun(), 4);
        assertEquals(series.getRuns().get(2).getPixelNumber(), 106L);
        assertEquals(series.getRuns().get(2).getRun(), 2);
    }

    @Test
    public void constructFromValue() throws KlvParseException {
        List<PixelRunPair> runs = new ArrayList<>();
        runs.add(new PixelRunPair(74L, 2));
        runs.add(new PixelRunPair(89L, 4));
        runs.add(new PixelRunPair(106L, 2));
        BitMaskSeries bitmask = new BitMaskSeries(runs);
        verifyStExample(bitmask);
    }

    @Test
    public void constructFromValueMultipleLengths() throws KlvParseException {
        List<PixelRunPair> runs = new ArrayList<>();
        runs.add(new PixelRunPair(1L, 0x01));
        runs.add(new PixelRunPair(255L, 0x01));
        runs.add(new PixelRunPair(256L, 0x01));
        runs.add(new PixelRunPair(65535L, 0x01));
        runs.add(new PixelRunPair(65536L, 0x01));
        runs.add(new PixelRunPair(16777215L, 0x01));
        runs.add(new PixelRunPair(16777216L, 0x01));
        runs.add(new PixelRunPair(4294967295L, 0x01));
        runs.add(new PixelRunPair(4294967296L, 0x01));
        runs.add(new PixelRunPair(1099511627775L, 0x01));
        runs.add(new PixelRunPair(1099511627776L, 0x01));
        runs.add(new PixelRunPair(281474976710655L, 0x01));
        BitMaskSeries bitmask = new BitMaskSeries(runs);
        assertEquals(
                bitmask.getBytes(),
                new byte[] {
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0xFF,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x02,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x03,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0x01,
                    (byte) 0x06,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x06,
                    (byte) 0x04,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0x01,
                    (byte) 0x07,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x07,
                    (byte) 0x05,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x06,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x06,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    (byte) 0x01
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructFromEncodedBytesTooLong() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x03, 0x01, 0x39, 0x03, 0x09, 0x07, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                    0x03, 0x03, 0x01, 0x0B, 0x03
                };
        new BitMaskSeries(bytes);
    }
}
