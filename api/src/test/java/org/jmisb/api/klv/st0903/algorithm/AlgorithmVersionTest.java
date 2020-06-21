package org.jmisb.api.klv.st0903.algorithm;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlgorithmVersionTest {
    private final byte[] bytes = new byte[] {0x32, 0x2E, 0x36, 0x61};

    @Test
    public void testFromString() {
        // Example from Algorithm Tag 3 Version.
        final String stringVal = "2.6a";

        VmtiTextString string = new VmtiTextString(VmtiTextString.ALGORITHM_VERSION, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_VERSION);
        Assert.assertEquals(string.getDisplayName(), "Algorithm Version");
        VmtiTextString stringFromBytes =
                new VmtiTextString(VmtiTextString.ALGORITHM_VERSION, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiTextString.ALGORITHM_VERSION);
        Assert.assertEquals(stringFromBytes.getDisplayName(), "Algorithm Version");

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "2.6a");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "2.6a");
    }

    @Test
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue v = AlgorithmLS.createValue(AlgorithmMetadataKey.version, bytes);
        Assert.assertTrue(v instanceof VmtiTextString);
        VmtiTextString string = (VmtiTextString) v;
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.ALGORITHM_VERSION);
        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "2.6a");
    }
}
