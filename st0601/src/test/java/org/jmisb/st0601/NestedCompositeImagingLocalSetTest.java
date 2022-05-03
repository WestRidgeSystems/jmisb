package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st1602.CompositeImagingKey;
import org.jmisb.st1602.CompositeImagingLocalSet;
import org.jmisb.st1602.ICompositeImagingValue;
import org.jmisb.st1602.ST1602DocumentVersion;
import org.jmisb.st1602.SubImageColumns;
import org.jmisb.st1602.SubImagePositionX;
import org.jmisb.st1602.SubImagePositionY;
import org.jmisb.st1602.SubImageRows;
import org.jmisb.st1602.ZOrder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedCompositeImagingLocalSetTest {
    private final byte[] localSetAsByteArray =
            new byte[] {
                0x02,
                0x01,
                0x01,
                0x09,
                0x02,
                0x05,
                0x78,
                0x0a,
                0x02,
                0x07,
                (byte) 0x80,
                0x0b,
                0x02,
                0x03,
                (byte) 0xc0,
                0x0c,
                0x02,
                0x02,
                (byte) 0xbc,
                0x12,
                0x01,
                0x04
            };

    @Test
    public void testConstructFromLocalSet() {
        NestedCompositeImagingLocalSet uut = new NestedCompositeImagingLocalSet(makeLocalSet());
        Assert.assertNotNull(uut);
        checkLocalSetValues(uut);
        assertEquals(uut.getBytes(), localSetAsByteArray);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedCompositeImagingLocalSet localSetFromBytes =
                new NestedCompositeImagingLocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.CompositeImaging, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedCompositeImagingLocalSet);
        NestedCompositeImagingLocalSet localSet = (NestedCompositeImagingLocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedCompositeImagingLocalSet nestedLocalSet) {
        Assert.assertEquals(nestedLocalSet.getDisplayName(), "Composite Imaging");
        Assert.assertEquals(nestedLocalSet.getDisplayableValue(), "[Composite Imaging]");
        assertTrue(nestedLocalSet.getLocalSet() instanceof CompositeImagingLocalSet);
        assertEquals(nestedLocalSet.getLocalSet().getIdentifiers().size(), 6);
        assertEquals(nestedLocalSet.getIdentifiers().size(), 6);
        assertTrue(
                nestedLocalSet
                        .getIdentifiers()
                        .containsAll(
                                Set.<CompositeImagingKey>of(
                                        CompositeImagingKey.DocumentVersion,
                                        CompositeImagingKey.SubImageColumns,
                                        CompositeImagingKey.SubImageRows,
                                        CompositeImagingKey.SubImagePositionX,
                                        CompositeImagingKey.SubImagePositionY,
                                        CompositeImagingKey.ZOrder)));
        ICompositeImagingValue docVersion =
                nestedLocalSet.getField(CompositeImagingKey.DocumentVersion);
        assertTrue(docVersion instanceof ST1602DocumentVersion);
        assertEquals(docVersion.getDisplayName(), "Document Version");
        assertEquals(docVersion.getDisplayableValue(), "ST 1602.1");
        ICompositeImagingValue zOrder = nestedLocalSet.getField(CompositeImagingKey.ZOrder);
        assertTrue(zOrder instanceof ZOrder);
        assertEquals(zOrder.getDisplayName(), "Z-Order");
        assertEquals(zOrder.getDisplayableValue(), "4");
    }

    private CompositeImagingLocalSet makeLocalSet() {
        SortedMap<CompositeImagingKey, ICompositeImagingValue> map = new TreeMap<>();
        map.put(CompositeImagingKey.DocumentVersion, new ST1602DocumentVersion(1));
        map.put(CompositeImagingKey.SubImageColumns, new SubImageColumns(1920));
        map.put(CompositeImagingKey.SubImageRows, new SubImageRows(1400));
        map.put(CompositeImagingKey.SubImagePositionX, new SubImagePositionX(960));
        map.put(CompositeImagingKey.SubImagePositionY, new SubImagePositionY(700));
        map.put(CompositeImagingKey.ZOrder, new ZOrder(4));

        CompositeImagingLocalSet localSet = new CompositeImagingLocalSet(map);
        return localSet;
    }
}
