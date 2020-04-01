package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmLS;
import org.jmisb.api.klv.st0903.algorithm.AlgorithmMetadataKey;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for AlgorithmSeries (Tag 102)
 */
public class AlgorithmSeriesTest
{
    private AlgorithmSeries algorithmSeriesFromBytes;
    private AlgorithmSeries algorithmSeriesFromAlgorithms;

    private final byte[] twoAlgorithmsBytes = new byte[]
    {
        37, // Length of Algorithm entry 1
        0x02, 0x14, 0x6B, 0x36, 0x5F, 0x79, 0x6F, 0x6C, 0x6F, 0x5F, 0x39, 0x30, 0x30, 0x30, 0x5F, 0x74, 0x72, 0x61, 0x63, 0x6B, 0x65, 0x72,
        0x03, 0x04, 0x32, 0x2E, 0x36, 0x61,
        0x04, 0x07, 0x6B, 0x61, 0x6C, 0x6D, 0x61, 0x6E, 0x6E,
        0x09, // Length of Algorithm entry 2
        0x02, 0x07, 0x6f, 0x70, 0x65, 0x6e, 0x63, 0x76, 0x32
    };

    @BeforeMethod
    public void setUpMethod() throws Exception
    {
        algorithmSeriesFromBytes = new AlgorithmSeries(twoAlgorithmsBytes);

        List<AlgorithmLS> targetPacks = new ArrayList<>();

        SortedMap<AlgorithmMetadataKey, IVmtiMetadataValue> targetPack1Values = new TreeMap<>();
        VmtiTextString algo1Name = new VmtiTextString(VmtiTextString.ALGORITHM_NAME, "k6_yolo_9000_tracker");
        targetPack1Values.put(AlgorithmMetadataKey.name, algo1Name);
        VmtiTextString algo1Version = new VmtiTextString(VmtiTextString.ALGORITHM_VERSION, "2.6a");
        targetPack1Values.put(AlgorithmMetadataKey.version, algo1Version);
        VmtiTextString algo1Class = new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, "kalmann");
        targetPack1Values.put(AlgorithmMetadataKey.algorithmClass, algo1Class);
        AlgorithmLS algorithmPack1 = new AlgorithmLS(targetPack1Values);
        targetPacks.add(algorithmPack1);

        SortedMap<AlgorithmMetadataKey, IVmtiMetadataValue> targetPack2Values = new TreeMap<>();
        VmtiTextString algo2Name = new VmtiTextString(VmtiTextString.ALGORITHM_NAME, "opencv2");
        targetPack2Values.put(AlgorithmMetadataKey.name, algo2Name);
        AlgorithmLS algorithmPack2 = new AlgorithmLS(targetPack2Values);
        targetPacks.add(algorithmPack2);

        algorithmSeriesFromAlgorithms = new AlgorithmSeries(targetPacks);
    }

    /**
     * Check build
     */
    @Test
    public void testParsingFromValues()
    {
        assertNotNull(algorithmSeriesFromAlgorithms);
        List<AlgorithmLS> algorithms = algorithmSeriesFromAlgorithms.getAlgorithms();
        assertEquals(algorithms.size(), 2);
        AlgorithmLS algo1 = algorithms.get(0);
        assertEquals(algo1.getTags().size(), 3);
        AlgorithmLS algo2 = algorithms.get(1);
        assertEquals(algo2.getTags().size(), 1);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VmtiLocalSet.createValue(VmtiMetadataKey.AlgorithmSeries, twoAlgorithmsBytes);
        assertNotNull(value);
        assertTrue(value instanceof AlgorithmSeries);
        AlgorithmSeries algorithmSeries = (AlgorithmSeries)value;
        List<AlgorithmLS> algorithms = algorithmSeries.getAlgorithms();
        assertEquals(algorithms.size(), 2);
        AlgorithmLS algo1 = algorithms.get(0);
        assertEquals(algo1.getTags().size(), 3);
        AlgorithmLS algo2 = algorithms.get(1);
        assertEquals(algo2.getTags().size(), 1);
    }

    /**
     * Check parsing
     */
    @Test
    public void testParsingFromBytes()
    {
        assertNotNull(algorithmSeriesFromBytes);
        List<AlgorithmLS> algorithms = algorithmSeriesFromBytes.getAlgorithms();
        assertEquals(algorithms.size(), 2);
        AlgorithmLS algorithm1 = algorithms.get(0);
        assertEquals(algorithm1.getTags().size(), 3);
        AlgorithmLS algorithm2 = algorithms.get(1);
        assertEquals(algorithm2.getTags().size(), 1);
    }

    /**
     * Test of getBytes method, of class AlgorithmSeries.
     */
    @Test
    public void testGetBytesFromSeriesFromBytes()
    {
        assertEquals(algorithmSeriesFromBytes.getBytes(), twoAlgorithmsBytes);
    }

    @Test
    public void testGetBytesFromSeriesFromAlgorithms()
    {
        assertEquals(algorithmSeriesFromAlgorithms.getBytes(), twoAlgorithmsBytes);
    }

    /**
     * Test of getDisplayableValue method, of class AlgorithmSeries.
     */
    @Test
    public void testGetDisplayableValue()
    {
        assertEquals(algorithmSeriesFromBytes.getDisplayableValue(), "[Algorithms]");
    }

    /**
     * Test of getDisplayName method, of class AlgorithmSeries.
     */
    @Test
    public void testGetDisplayName()
    {
        assertEquals(algorithmSeriesFromBytes.getDisplayName(), "Algorithm Series");
    }

}
