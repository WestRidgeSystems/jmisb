package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlgorithmTest {
    @Test
    public void testAlgorithm() {
        // Example from VTracker Tag 6 Algorithm.
        final String stringVal = "test";
        final byte[] bytes = new byte[] {0x74, 0x65, 0x73, 0x74};

        VmtiTextString string = new VmtiTextString(VmtiTextString.ALGORITHM, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM);
        VmtiTextString stringFromBytes = new VmtiTextString(VmtiTextString.ALGORITHM, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiTextString.ALGORITHM);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "test");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "test");
    }

    @Test
    public void testFactoryAlgorithm() throws KlvParseException {
        byte[] bytes = new byte[] {0x74, 0x65, 0x73, 0x74};
        IVmtiMetadataValue v =
                VTrackerLS.createValue(VTrackerMetadataKey.algorithm, bytes, EncodingMode.IMAPB);
        Assert.assertTrue(v instanceof VmtiTextString);
        VmtiTextString string = (VmtiTextString) v;
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM);
        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "test");
    }
}
