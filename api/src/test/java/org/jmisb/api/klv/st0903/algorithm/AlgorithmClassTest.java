package org.jmisb.api.klv.st0903.algorithm;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlgorithmClassTest {
    private final byte[] bytes = new byte[] {0x6B, 0x61, 0x6C, 0x6D, 0x61, 0x6E, 0x6E};

    @Test
    public void testFromString() {
        // Example from Algorithm Tag 4 Class.
        final String stringVal = "kalmann"; /* sic */

        VmtiTextString string = new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_CLASS);
        Assert.assertEquals(string.getDisplayName(), "Algorithm Class");
        VmtiTextString stringFromBytes = new VmtiTextString(VmtiTextString.ALGORITHM_CLASS, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiTextString.ALGORITHM_CLASS);
        Assert.assertEquals(stringFromBytes.getDisplayName(), "Algorithm Class");

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "kalmann");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "kalmann");
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue v = AlgorithmLS.createValue(AlgorithmMetadataKey.algorithmClass, bytes);
        Assert.assertTrue(v instanceof VmtiTextString);
        VmtiTextString string = (VmtiTextString) v;
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_CLASS);
        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "kalmann");
    }
}
