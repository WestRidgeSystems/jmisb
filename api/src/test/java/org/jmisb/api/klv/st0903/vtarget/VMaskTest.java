package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vmask.BitMaskSeries;
import org.jmisb.api.klv.st0903.vmask.PixelPolygon;
import org.jmisb.api.klv.st0903.vmask.PixelRunPair;
import org.jmisb.api.klv.st0903.vmask.VMaskLS;
import org.jmisb.api.klv.st0903.vmask.VMaskLSTest;
import org.jmisb.api.klv.st0903.vmask.VMaskMetadataKey;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VMask (Tag 101).
 */
public class VMaskTest {

    @Test
    public void testConstructFromValue()
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
        VMask mask = new VMask(localSet);
        assertEquals(mask.getBytes(), new byte[]{
            0x01,
            0x09,
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02,
            0x0C,
            0x03, 0x01, 0x4A, 0x02,
            0x03, 0x01, 0x59, 0x04,
            0x03, 0x01, 0x6A, 0x02
        });
        assertEquals(mask.getDisplayName(), "Target Pixel Mask");
        assertEquals(mask.getDisplayableValue(), "[VMask]");
        VMaskLS feature = mask.getFeature();
        VMaskLSTest.checkPolygonExample(feature);
        VMaskLSTest.checkBitmaskExample(feature);
    }

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException
    {
        VMask mask = new VMask(new byte[]{
            0x01, // Tag 1
            0x09, // Length
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02, // Tag
            0x0C, // Length
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        });
        assertEquals(mask.getBytes(), new byte[]{
            0x01,
            0x09,
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02,
            0x0C,
            0x03, 0x01, 0x4A, 0x02,
            0x03, 0x01, 0x59, 0x04,
            0x03, 0x01, 0x6A, 0x02
        });
        assertEquals(mask.getDisplayName(), "Target Pixel Mask");
        assertEquals(mask.getDisplayableValue(), "[VMask]");
        assertEquals(mask.getDisplayName(), "Target Pixel Mask");
        assertEquals(mask.getDisplayableValue(), "[VMask]");
        VMaskLS feature = mask.getFeature();
        VMaskLSTest.checkPolygonExample(feature);
        VMaskLSTest.checkBitmaskExample(feature);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VMask, new byte[]{
            0x01, // Tag 1
            0x09, // Length
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02, // Tag
            0x0C, // Length
            0x03, 0x01, 0x4A, 0x02, // (74, 2)
            0x03, 0x01, 0x59, 0x04, // (89, 4)
            0x03, 0x01, 0x6A, 0x02  // (106, 2)
        });
        assertTrue(value instanceof VMask);
        VMask mask = (VMask)value;
        assertEquals(mask.getBytes(), new byte[]{
            0x01,
            0x09,
            0x02, 0x39, (byte)0xAA,
            0x02, 0x39, (byte)0xBF,
            0x02, 0x3B, 0x0B,
            0x02,
            0x0C,
            0x03, 0x01, 0x4A, 0x02,
            0x03, 0x01, 0x59, 0x04,
            0x03, 0x01, 0x6A, 0x02
        });
        assertEquals(mask.getDisplayName(), "Target Pixel Mask");
        assertEquals(mask.getDisplayableValue(), "[VMask]");
        assertEquals(mask.getDisplayName(), "Target Pixel Mask");
        assertEquals(mask.getDisplayableValue(), "[VMask]");
        VMaskLS feature = mask.getFeature();
        VMaskLSTest.checkPolygonExample(feature);
        VMaskLSTest.checkBitmaskExample(feature);
    }
}
