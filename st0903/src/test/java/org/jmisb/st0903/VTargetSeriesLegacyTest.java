package org.jmisb.st0903;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.vtarget.CentroidPixelColumn;
import org.jmisb.st0903.vtarget.CentroidPixelRow;
import org.jmisb.st0903.vtarget.TargetHAE;
import org.jmisb.st0903.vtarget.TargetPriority;
import org.jmisb.st0903.vtarget.VTargetMetadataKey;
import org.jmisb.st0903.vtarget.VTargetPack;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Tests for VTargetSeries (Tag 101) */
public class VTargetSeriesLegacyTest {
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
                (byte) 0x8C,
                (byte) 0x38, // Target HAE Value (two bytes, legacy encoding)
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
    public void setUpMethod() throws Exception {
        vTargetSeriesFromBytes = new VTargetSeries(twoVTargetsBytes, EncodingMode.LEGACY);

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
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.VTargetSeries, twoVTargetsBytes, EncodingMode.LEGACY);
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
}
