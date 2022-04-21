package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st1601.GeoRegistrationAlgorithmName;
import org.jmisb.st1601.GeoRegistrationAlgorithmVersion;
import org.jmisb.st1601.GeoRegistrationKey;
import org.jmisb.st1601.GeoRegistrationLocalSet;
import org.jmisb.st1601.IGeoRegistrationValue;
import org.jmisb.st1601.ST1601DocumentVersion;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedGeoRegistrationLocalSetTest {
    private final byte[] localSetAsByteArray =
            new byte[] {
                0x01, 0x01, 0x01, 0x02, 0x0a, 0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c,
                0x73, 0x03, 0x05, 0x36, 0x2e, 0x37, 0x36, 0x61,
            };

    @Test
    public void testConstructFromLocalSet() {
        NestedGeoRegistrationLocalSet uut = new NestedGeoRegistrationLocalSet(makeLocalSet());
        Assert.assertNotNull(uut);
        checkLocalSetValues(uut);
        assertEquals(uut.getBytes(), localSetAsByteArray);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedGeoRegistrationLocalSet localSetFromBytes =
                new NestedGeoRegistrationLocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(UasDatalinkTag.Georegistration, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedGeoRegistrationLocalSet);
        NestedGeoRegistrationLocalSet localSet = (NestedGeoRegistrationLocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedGeoRegistrationLocalSet nestedLocalSet) {
        Assert.assertEquals(nestedLocalSet.getDisplayName(), "Geo-Registration");
        Assert.assertEquals(nestedLocalSet.getDisplayableValue(), "[Geo-Registration]");
        assertTrue(nestedLocalSet.getLocalSet() instanceof GeoRegistrationLocalSet);
        assertEquals(nestedLocalSet.getLocalSet().getIdentifiers().size(), 3);
        assertEquals(nestedLocalSet.getIdentifiers().size(), 3);
        assertTrue(
                nestedLocalSet
                        .getIdentifiers()
                        .containsAll(
                                Set.<GeoRegistrationKey>of(
                                        GeoRegistrationKey.DocumentVersion,
                                        GeoRegistrationKey.AlgorithmName,
                                        GeoRegistrationKey.AlgorithmVersion)));
        IGeoRegistrationValue docVersion =
                nestedLocalSet.getField(GeoRegistrationKey.DocumentVersion);
        assertTrue(docVersion instanceof ST1601DocumentVersion);
        assertEquals(docVersion.getDisplayName(), "Document Version");
        assertEquals(docVersion.getDisplayableValue(), "ST 1601.1");
        IGeoRegistrationValue name = nestedLocalSet.getField(GeoRegistrationKey.AlgorithmName);
        assertTrue(name instanceof GeoRegistrationAlgorithmName);
        assertEquals(name.getDisplayName(), "Algorithm Name");
        assertEquals(name.getDisplayableValue(), "good tools");
        IGeoRegistrationValue version =
                nestedLocalSet.getField(GeoRegistrationKey.AlgorithmVersion);
        assertTrue(version instanceof GeoRegistrationAlgorithmVersion);
        assertEquals(version.getDisplayName(), "Algorithm Version");
        assertEquals(version.getDisplayableValue(), "6.76a");
    }

    private GeoRegistrationLocalSet makeLocalSet() {
        SortedMap<GeoRegistrationKey, IGeoRegistrationValue> map = new TreeMap<>();
        map.put(GeoRegistrationKey.DocumentVersion, new ST1601DocumentVersion(1));
        map.put(GeoRegistrationKey.AlgorithmName, new GeoRegistrationAlgorithmName("good tools"));
        map.put(GeoRegistrationKey.AlgorithmVersion, new GeoRegistrationAlgorithmVersion("6.76a"));
        GeoRegistrationLocalSet localSet = new GeoRegistrationLocalSet(map);
        return localSet;
    }
}
