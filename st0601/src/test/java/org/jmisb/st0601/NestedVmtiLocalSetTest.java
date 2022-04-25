package org.jmisb.st0601;

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.ST0903Version;
import org.jmisb.st0903.VmtiLocalSet;
import org.jmisb.st0903.VmtiMetadataKey;
import org.jmisb.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedVmtiLocalSetTest {
    private final byte[] localSetAsByteArray =
            new byte[] {
                (byte) 0x03,
                (byte) 0x05,
                (byte) 0x54,
                (byte) 0x65,
                (byte) 0x73,
                (byte) 0x54,
                (byte) 0x31,
                (byte) 0x04,
                (byte) 0x01,
                (byte) 0x04
            };

    @Test
    public void testConstructFromLocalSet() {
        NestedVmtiLocalSet localSet = new NestedVmtiLocalSet(makeLocalSet());
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        NestedVmtiLocalSet localSetFromBytes = new NestedVmtiLocalSet(localSetAsByteArray);
        Assert.assertNotNull(localSetFromBytes);
        checkLocalSetValues(localSetFromBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue value =
                UasDatalinkFactory.createValue(
                        UasDatalinkTag.VmtiLocalDataSet, localSetAsByteArray);
        Assert.assertTrue(value instanceof NestedVmtiLocalSet);
        NestedVmtiLocalSet localSet = (NestedVmtiLocalSet) value;
        Assert.assertNotNull(localSet);
        checkLocalSetValues(localSet);
    }

    private void checkLocalSetValues(NestedVmtiLocalSet localSet) {
        Assert.assertEquals(localSet.getDisplayName(), "VMTI");
        Assert.assertEquals(localSet.getDisplayableValue(), "[VMTI]");
        Assert.assertEquals(localSet.getVmti().getIdentifiers().size(), 2);
        Assert.assertEquals(
                localSet.getVmti().getField(VmtiMetadataKey.SystemName).getDisplayableValue(),
                "TesT1");
        Assert.assertEquals(
                localSet.getVmti().getField(VmtiMetadataKey.VersionNumber).getDisplayableValue(),
                "ST0903.4");
        byte[] bytes = localSet.getBytes();
        Assert.assertEquals(bytes, localSetAsByteArray);
        Assert.assertEquals(localSet.getIdentifiers().size(), 2);
        Assert.assertTrue(localSet.getIdentifiers().contains(VmtiMetadataKey.SystemName));
        Assert.assertEquals(
                localSet.getField(VmtiMetadataKey.SystemName).getDisplayableValue(), "TesT1");
        Assert.assertTrue(localSet.getIdentifiers().contains(VmtiMetadataKey.VersionNumber));
        Assert.assertEquals(
                localSet.getField(VmtiMetadataKey.VersionNumber).getDisplayableValue(), "ST0903.4");
    }

    private VmtiLocalSet makeLocalSet() {
        SortedMap<VmtiMetadataKey, IVmtiMetadataValue> vmtiData = new TreeMap<>();
        vmtiData.put(
                VmtiMetadataKey.SystemName,
                new VmtiTextString(VmtiTextString.SYSTEM_NAME, "TesT1"));
        vmtiData.put(VmtiMetadataKey.VersionNumber, new ST0903Version(4));
        VmtiLocalSet localSet = new VmtiLocalSet(vmtiData);
        return localSet;
    }
}
