package org.jmisb.api.klv.st0601;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1206.ISARMIMetadataValue;
import org.jmisb.api.klv.st1206.LookDirection;
import org.jmisb.api.klv.st1206.SARMILocalSet;
import org.jmisb.api.klv.st1206.SARMIMetadataKey;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedSARMILocalSetTest {
    private final byte[] localSetAsByteArray = new byte[] {(byte) 0x03, (byte) 0x01, (byte) 0x01};

    @Test
    public void testConstructFromLocalSet() {
        NestedSARMILocalSet localSet = new NestedSARMILocalSet(makeLocalSet());
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedSARMILocalSet localSetFromBytes = new NestedSARMILocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.SarMotionImageryMetadata, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedSARMILocalSet);
        NestedSARMILocalSet localSet = (NestedSARMILocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedSARMILocalSet localSet) {
        Assert.assertEquals(localSet.getDisplayName(), "SAR Motion Imagery");
        Assert.assertEquals(localSet.getDisplayableValue(), "[SARMI]");
        Assert.assertEquals(localSet.getIdentifiers().size(), 1);
        Assert.assertEquals(
                localSet.getField(SARMIMetadataKey.LookDirection).getDisplayableValue(), "Right");

        Assert.assertEquals(localSet.getSARMI().getIdentifiers().size(), 1);
        Assert.assertEquals(
                localSet.getSARMI().getField(SARMIMetadataKey.LookDirection).getDisplayableValue(),
                "Right");
        byte[] bytes = localSet.getBytes();
        Assert.assertEquals(bytes, localSetAsByteArray);
    }

    private SARMILocalSet makeLocalSet() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> sarmiData = new TreeMap<>();
        sarmiData.put(SARMIMetadataKey.LookDirection, new LookDirection((byte) 1));
        SARMILocalSet localSet = new SARMILocalSet(sarmiData);
        return localSet;
    }
}
