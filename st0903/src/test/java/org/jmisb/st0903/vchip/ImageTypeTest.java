package org.jmisb.st0903.vchip;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.VmtiTextString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ImageTypeTest {
    @Test
    public void testImageType() {
        // Example from VChip Tag 1 Image Type.
        final String stringVal = "jpeg";
        final byte[] bytes = new byte[] {0x6A, 0x70, 0x65, 0x67};

        VmtiTextString string = new VmtiTextString(VmtiTextString.IMAGE_TYPE, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.IMAGE_TYPE);
        VmtiTextString stringFromBytes = new VmtiTextString(VmtiTextString.IMAGE_TYPE, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiTextString.IMAGE_TYPE);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "jpeg");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(stringFromBytes.getDisplayableValue(), "jpeg");
    }

    @Test
    public void testFactoryImageType() throws KlvParseException {
        byte[] bytes = new byte[] {0x6A, 0x70, 0x65, 0x67};
        IVmtiMetadataValue v = VChipLS.createValue(VChipMetadataKey.imageType, bytes);
        Assert.assertTrue(v instanceof VmtiTextString);
        VmtiTextString string = (VmtiTextString) v;
        Assert.assertEquals(string.getDisplayName(), VmtiTextString.IMAGE_TYPE);
        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(string.getDisplayableValue(), "jpeg");
    }
}
