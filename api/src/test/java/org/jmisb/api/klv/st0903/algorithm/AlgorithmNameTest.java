package org.jmisb.api.klv.st0903.algorithm;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlgorithmNameTest
{
    private final byte[] bytes = new byte[]{0x6B, 0x36, 0x5F, 0x79, 0x6F, 0x6C, 0x6F, 0x5F, 0x39, 0x30, 0x30, 0x30, 0x5F, 0x74, 0x72, 0x61, 0x63, 0x6B, 0x65, 0x72};

    @Test
    public void testFromString()
    {
        // Example from Algorithm Tag 2 Name.
        final String stringVal = "k6_yolo_9000_tracker";

        VmtiTextString string = new VmtiTextString(VmtiTextString.ALGORITHM_NAME, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_NAME);
        Assert.assertEquals(string.getDisplayName(), "Algorithm Name");
        VmtiTextString stringFromBytes = new VmtiTextString(VmtiTextString.ALGORITHM_NAME, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiTextString.ALGORITHM_NAME);
        Assert.assertEquals(stringFromBytes.getDisplayName(), "Algorithm Name");

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "k6_yolo_9000_tracker");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "k6_yolo_9000_tracker");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue v = AlgorithmLS.createValue(AlgorithmMetadataKey.name, bytes);
        Assert.assertTrue(v instanceof VmtiTextString);
        VmtiTextString string = (VmtiTextString)v;
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_NAME);
        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "k6_yolo_9000_tracker");
    }
}
