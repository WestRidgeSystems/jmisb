package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st1002.IRangeImageMetadataValue;
import org.jmisb.st1002.RangeImageLocalSet;
import org.jmisb.st1002.RangeImageMetadataKey;
import org.jmisb.st1002.ST1002PrecisionTimeStamp;
import org.jmisb.st1002.ST1002VersionNumber;
import org.jmisb.st1002.SinglePointRangeMeasurement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedRangeImageLocalSetTest {

    private final byte[] localSetAsByteArray =
            new byte[] {
                0x01,
                0x08,
                0x00,
                0x05,
                (byte) 0xf2,
                (byte) 0xf6,
                0x17,
                0x66,
                0x25,
                0x00,
                0x0b,
                0x01,
                0x02,
                0x0d,
                0x08,
                0x40,
                (byte) 0xa5,
                (byte) 0xe8,
                0x33,
                0x33,
                0x33,
                0x33,
                0x33,
                0x15,
                0x02,
                (byte) 0x72,
                (byte) 0x41
            };

    @Test
    public void testConstructFromLocalSet() {
        NestedRangeImageLocalSet uut = new NestedRangeImageLocalSet(makeLocalSet());
        Assert.assertNotNull(uut);
        checkLocalSetValues(uut);
        assertEquals(uut.getBytes(), localSetAsByteArray);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedRangeImageLocalSet localSetFromBytes =
                new NestedRangeImageLocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(UasDatalinkTag.RangeImage, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedRangeImageLocalSet);
        NestedRangeImageLocalSet localSet = (NestedRangeImageLocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedRangeImageLocalSet nestedLocalSet) {
        Assert.assertEquals(nestedLocalSet.getDisplayName(), "Range Image");
        Assert.assertEquals(nestedLocalSet.getDisplayableValue(), "[Range Image]");
        assertTrue(nestedLocalSet.getRangeImage() instanceof RangeImageLocalSet);
        assertEquals(nestedLocalSet.getRangeImage().getIdentifiers().size(), 3);
        assertEquals(nestedLocalSet.getIdentifiers().size(), 3);
        assertTrue(
                nestedLocalSet
                        .getIdentifiers()
                        .containsAll(
                                Set.<RangeImageMetadataKey>of(
                                        RangeImageMetadataKey.DocumentVersion,
                                        RangeImageMetadataKey.PrecisionTimeStamp,
                                        RangeImageMetadataKey.SinglePointRangeMeasurement)));
        IRangeImageMetadataValue docVersion =
                nestedLocalSet.getField(RangeImageMetadataKey.DocumentVersion);
        assertTrue(docVersion instanceof ST1002VersionNumber);
        assertEquals(docVersion.getDisplayName(), "Version Number");
        assertEquals(docVersion.getDisplayableValue(), "ST 1002.2");
        IRangeImageMetadataValue name =
                nestedLocalSet.getField(RangeImageMetadataKey.PrecisionTimeStamp);
        assertTrue(name instanceof ST1002PrecisionTimeStamp);
        assertEquals(name.getDisplayName(), "Precision Time Stamp");
        assertEquals(name.getDisplayableValue(), "1674513652000000");
        IRangeImageMetadataValue version =
                nestedLocalSet.getField(RangeImageMetadataKey.SinglePointRangeMeasurement);
        assertTrue(version instanceof SinglePointRangeMeasurement);
        assertEquals(version.getDisplayName(), "Single Point Range Measurement");
        assertEquals(version.getDisplayableValue(), "2804.100 m");
    }

    private RangeImageLocalSet makeLocalSet() {
        SortedMap<RangeImageMetadataKey, IRangeImageMetadataValue> map = new TreeMap<>();
        map.put(RangeImageMetadataKey.DocumentVersion, new ST1002VersionNumber(2));
        map.put(
                RangeImageMetadataKey.PrecisionTimeStamp,
                new ST1002PrecisionTimeStamp(1674513652000000L));
        map.put(
                RangeImageMetadataKey.SinglePointRangeMeasurement,
                new SinglePointRangeMeasurement(2804.1));
        RangeImageLocalSet localSet = new RangeImageLocalSet(map);
        return localSet;
    }
}
