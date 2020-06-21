package org.jmisb.api.klv.st0601;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0806.IRvtMetadataValue;
import org.jmisb.api.klv.st0806.RvtLocalSet;
import org.jmisb.api.klv.st0806.RvtMetadataKey;
import org.jmisb.api.klv.st0806.ST0806Version;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedRvtLocalSetTest {
    private final byte[] localSetAsByteArray = new byte[] {(byte) 0x08, (byte) 0x01, (byte) 0x04};

    @Test
    public void testConstructFromLocalSet() {
        NestedRvtLocalSet localSet = new NestedRvtLocalSet(makeLocalSet());
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedRvtLocalSet localSetFromBytes = new NestedRvtLocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(UasDatalinkTag.RvtLocalDataSet, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedRvtLocalSet);
        NestedRvtLocalSet localSet = (NestedRvtLocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedRvtLocalSet localSet) {
        Assert.assertEquals(localSet.getDisplayName(), "RVT");
        Assert.assertEquals(localSet.getDisplayableValue(), "[RVT]");
        Assert.assertEquals(localSet.getRVT().getTags().size(), 1);
        Assert.assertEquals(
                localSet.getRVT().getField(RvtMetadataKey.UASLSVersionNumber).getDisplayableValue(),
                "ST0806.4");
        byte[] bytes = localSet.getBytes();
        Assert.assertEquals(bytes, localSetAsByteArray);
    }

    private RvtLocalSet makeLocalSet() {
        SortedMap<RvtMetadataKey, IRvtMetadataValue> rvtData = new TreeMap<>();
        rvtData.put(RvtMetadataKey.UASLSVersionNumber, new ST0806Version(4));
        RvtLocalSet localSet = new RvtLocalSet(rvtData);
        return localSet;
    }
}
