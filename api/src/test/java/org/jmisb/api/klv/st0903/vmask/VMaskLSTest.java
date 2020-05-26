package org.jmisb.api.klv.st0903.vmask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.ParseOptions;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VMask LS.
 */
public class VMaskLSTest extends LoggerChecks
{
    public VMaskLSTest()
    {
        super(VMaskLS.class);
    }

    @Test
    public void parseTag1() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x01, // Tag 1
            0x09, // Length
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B
        };
        VMaskLS localSet = new VMaskLS(bytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        checkPolygonExample(localSet);
    }

    @Test
    public void parseTag2() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x02, // Tag
            0x0C, // Length
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        };
        VMaskLS localSet = new VMaskLS(bytes, EnumSet.noneOf(ParseOptions.class));
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        assertEquals(localSet.getBytes(), bytes);
        checkBitmaskExample(localSet);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x03, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x01, // Tag 1
            0x09, // Length
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02, // Tag 2
            0x0C, // Length of bitmask series value
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        };
        verifyNoLoggerMessages();
        VMaskLS localSet = new VMaskLS(bytes, EnumSet.noneOf(ParseOptions.class));
        this.verifySingleLoggerMessage("Unknown VMTI VMask Metadata tag: 3");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkPolygonExample(localSet);
        checkBitmaskExample(localSet);
    }

    public static void checkPolygonExample(VMaskLS localSet)
    {
        assertTrue(localSet.getTags().contains(VMaskMetadataKey.polygon));
        IVmtiMetadataValue v = localSet.getField(VMaskMetadataKey.polygon);
        assertEquals(v.getDisplayName(), "Polygon");
        assertEquals(v.getDisplayableValue(), "[Pixel Numbers]");
        assertTrue(v instanceof PixelPolygon);
        PixelPolygon boundarySeries = (PixelPolygon) localSet.getField(VMaskMetadataKey.polygon);
        assertEquals(boundarySeries.getPolygon().size(), 3);
        assertEquals((long)boundarySeries.getPolygon().get(0), 14762L);
        assertEquals((long)boundarySeries.getPolygon().get(1), 14783L);
        assertEquals((long)boundarySeries.getPolygon().get(2), 15115L);
    }

    public static void checkBitmaskExample(VMaskLS localSet)
    {
        assertTrue(localSet.getTags().contains(VMaskMetadataKey.bitMaskSeries));
        IVmtiMetadataValue v = localSet.getField(VMaskMetadataKey.bitMaskSeries);
        assertTrue(v instanceof BitMaskSeries);
        BitMaskSeries series = (BitMaskSeries) localSet.getField(VMaskMetadataKey.bitMaskSeries);
        assertEquals(series.getRuns().size(), 3);
        assertEquals(series.getRuns().get(0).getPixelNumber(), 74L);
        assertEquals(series.getRuns().get(0).getRun(), 2);
        assertEquals(series.getRuns().get(1).getPixelNumber(), 89L);
        assertEquals(series.getRuns().get(1).getRun(), 4);
        assertEquals(series.getRuns().get(2).getPixelNumber(), 106L);
        assertEquals(series.getRuns().get(2).getRun(), 2);
        assertEquals(v.getDisplayName(), "BitMask");
        assertEquals(v.getDisplayableValue(), "[Pixel / Run Pairs]");
        assertEquals(v.getBytes(), new byte[]{
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        });
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        verifyNoLoggerMessages();
        IVmtiMetadataValue value = VMaskLS.createValue(VMaskMetadataKey.Undefined, bytes);
        this.verifySingleLoggerMessage("Unrecognized VMask tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException
    {
        Map<VMaskMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        List<Long> points = new ArrayList<>();
        points.add(14762L);
        points.add(14783L);
        points.add(15115L);
        PixelPolygon boundarySeries = new PixelPolygon(points);
        values.put(VMaskMetadataKey.polygon, boundarySeries);
        List<PixelRunPair> runs = new ArrayList<>();
        runs.add(new PixelRunPair(74L, 2));
        runs.add(new PixelRunPair(89L, 4));
        runs.add(new PixelRunPair(106L, 2));
        BitMaskSeries bitmask = new BitMaskSeries(runs);values.put(VMaskMetadataKey.bitMaskSeries, bitmask);
        VMaskLS localSet = new VMaskLS(values);
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 2);
        checkPolygonExample(localSet);
        checkBitmaskExample(localSet);
        byte[] expectedBytes = new byte[]{
            0x01, // Tag 1
            0x09, // Length
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02, // Tag 2
            0x0C, // Length of bitmask series value
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        };
        assertEquals(localSet.getBytes(), expectedBytes);
    }
}
