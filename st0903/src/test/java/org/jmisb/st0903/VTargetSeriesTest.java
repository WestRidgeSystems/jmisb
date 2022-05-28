package org.jmisb.st0903;

import static org.jmisb.st0903.VmtiMetadataKey.VTargetSeries;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.vtarget.CentroidPixelColumn;
import org.jmisb.st0903.vtarget.CentroidPixelRow;
import org.jmisb.st0903.vtarget.TargetHAE;
import org.jmisb.st0903.vtarget.TargetIdentifierKey;
import org.jmisb.st0903.vtarget.TargetPriority;
import org.jmisb.st0903.vtarget.VTargetMetadataKey;
import org.jmisb.st0903.vtarget.VTargetPack;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Tests for VTargetSeries (Tag 101) */
public class VTargetSeriesTest {
    private VTargetSeries vTargetSeriesFromBytes;
    private VTargetSeries vTargetSeriesFromVTargets;

    private final byte[] twoVTargetsBytes =
            new byte[] {
                4, // Length of VTarget entry 1
                0x01, // Target ID 1
                0x04, // Target Priority Tag
                0x01, // Target Priority Length
                0x1B, // Target Priority Value
                13, // Length of VTarget entry 2
                0x02, // Target ID 2
                0x0C, // Target HAE Tag
                0x02, // Target HAE Length
                0x2a,
                (byte) 0x94, // Target HAE Value
                0x13, // Centroid Pix Row Tag
                0x02, // Centroid Pix Row Length
                0x03,
                0x68, // Centroid Pix Row Value
                0x14, // Centroid Pix Column Tag
                0x02, // Centroid Pix Column Length
                0x04,
                0x71 // Centroid Pix Column Value
            };

    @BeforeMethod
    @SuppressWarnings("deprecation")
    public void setUpMethod() throws Exception {
        vTargetSeriesFromBytes = new VTargetSeries(twoVTargetsBytes);

        List<VTargetPack> targetPacks = new ArrayList<>();

        SortedMap<VTargetMetadataKey, IVmtiMetadataValue> targetPack1Values = new TreeMap<>();
        TargetPriority targetPriorityValue = new TargetPriority((short) 27);
        targetPack1Values.put(VTargetMetadataKey.TargetPriority, targetPriorityValue);
        VTargetPack vtargetPack1 = new VTargetPack(1, targetPack1Values);
        targetPacks.add(vtargetPack1);

        SortedMap<VTargetMetadataKey, IVmtiMetadataValue> targetPack2Values = new TreeMap<>();
        TargetHAE targetHaeValue = new TargetHAE(10000.0);
        targetPack2Values.put(VTargetMetadataKey.TargetHAE, targetHaeValue);
        CentroidPixelRow rowValue = new CentroidPixelRow(872);
        targetPack2Values.put(VTargetMetadataKey.CentroidPixRow, rowValue);
        CentroidPixelColumn columnValue = new CentroidPixelColumn(1137);
        targetPack2Values.put(VTargetMetadataKey.CentroidPixColumn, columnValue);
        VTargetPack vtargetPack2 = new VTargetPack(2, targetPack2Values);
        targetPacks.add(vtargetPack2);

        vTargetSeriesFromVTargets = new VTargetSeries(targetPacks);
    }

    /** Check build */
    @Test
    public void testParsingFromValues() {
        assertNotNull(vTargetSeriesFromVTargets);
        List<VTargetPack> vtargets = vTargetSeriesFromVTargets.getVTargets();
        assertEquals(vtargets.size(), 2);
        VTargetPack vtarget1 = vtargets.get(0);
        assertEquals(vtarget1.getTargetIdentifier(), 1);
        VTargetPack vtarget2 = vtargets.get(1);
        assertEquals(vtarget2.getTargetIdentifier(), 2);
        assertEquals(vTargetSeriesFromVTargets.getIdentifiers().size(), 2);
        assertNotNull(vTargetSeriesFromVTargets.getField(new TargetIdentifierKey(1)));
        IKlvValue value1FromFields =
                (vTargetSeriesFromVTargets.getField(new TargetIdentifierKey(1)));
        assertTrue(value1FromFields instanceof VTargetPack);
        VTargetPack vtargetPack1FromFields = (VTargetPack) value1FromFields;
        assertEquals(vtargetPack1FromFields.getTargetIdentifier(), 1);
    }

    /** Check parsing */
    @Test
    public void testParsingFromBytes() {
        assertNotNull(vTargetSeriesFromBytes);
        List<VTargetPack> vtargets = vTargetSeriesFromBytes.getVTargets();
        assertEquals(vtargets.size(), 2);
        VTargetPack vtarget1 = vtargets.get(0);
        assertEquals(vtarget1.getTargetIdentifier(), 1);
        VTargetPack vtarget2 = vtargets.get(1);
        assertEquals(vtarget2.getTargetIdentifier(), 2);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(VmtiMetadataKey.VTargetSeries, twoVTargetsBytes);
        assertNotNull(value);
        assertTrue(value instanceof VTargetSeries);
        VTargetSeries targetSeries = (VTargetSeries) value;
        List<VTargetPack> vtargets = targetSeries.getVTargets();
        assertEquals(vtargets.size(), 2);
        VTargetPack vtarget1 = vtargets.get(0);
        assertEquals(vtarget1.getTargetIdentifier(), 1);
        VTargetPack vtarget2 = vtargets.get(1);
        assertEquals(vtarget2.getTargetIdentifier(), 2);
    }

    /** Test of getBytes method, of class VTargetSeries. */
    @Test
    public void testGetBytesFromSeriesFromBytes() {
        assertEquals(vTargetSeriesFromBytes.getBytes(), twoVTargetsBytes);
    }

    @Test
    public void testGetBytesFromSeriesFromVTargets() {
        assertEquals(vTargetSeriesFromVTargets.getBytes(), twoVTargetsBytes);
    }

    /** Test of getDisplayableValue method, of class VTargetSeries. */
    @Test
    public void testGetDisplayableValue() {
        assertEquals(vTargetSeriesFromBytes.getDisplayableValue(), "[Targets]");
    }

    /** Test of getDisplayName method, of class VTargetSeries. */
    @Test
    public void testGetDisplayName() {
        assertEquals(vTargetSeriesFromBytes.getDisplayName(), "Target Series");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBitMaskSeries_fuzz_363_0() throws KlvParseException {
        byte bytes[] =
                new byte[] {
                    0x06,
                    0x26,
                    0x65,
                    0x05,
                    0x02,
                    0x03,
                    0x01,
                    0x05,
                    (byte) 0xce,
                    0x4f,
                    (byte) 0x89,
                    (byte) 0xe1,
                    0x29,
                    (byte) 0xb0,
                    (byte) 0x8c,
                    (byte) 0x84,
                    0x60,
                    0x34,
                    (byte) 0xb9,
                    0x53,
                    (byte) 0x94,
                    0x04
                };
        IVmtiMetadataValue v = VmtiLocalSet.createValue(VTargetSeries, bytes, EncodingMode.IMAPB);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBitMaskSeries_fuzz_363_1() throws KlvParseException {
        byte bytes[] =
                new byte[] {
                    0x06,
                    0x26,
                    0x65,
                    0x05,
                    0x02,
                    0x03,
                    0x01,
                    0x02,
                    (byte) 0xc7,
                    (byte) 0xaa,
                    0x05,
                    (byte) 0xda,
                    0x26,
                    0x0e,
                    0x5c,
                    (byte) 0x90,
                    0x2e,
                    0x79,
                    (byte) 0xb9,
                    0x53,
                    (byte) 0xa4,
                    0x04,
                    0x71,
                    (byte) 0x96,
                    0x44,
                    (byte) 0xc8,
                    (byte) 0xfa,
                    (byte) 0xf3,
                    (byte) 0xc7,
                    (byte) 0x88,
                    0x28,
                    (byte) 0x84,
                    (byte) 0xd2,
                    0x2e,
                    0x02,
                };
        IVmtiMetadataValue v = VmtiLocalSet.createValue(VTargetSeries, bytes, EncodingMode.IMAPB);
    }
}
