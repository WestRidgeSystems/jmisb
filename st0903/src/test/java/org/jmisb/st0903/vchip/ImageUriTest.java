package org.jmisb.st0903.vchip;

import java.net.URI;
import java.net.URISyntaxException;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.VmtiUri;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ImageUriTest {
    @Test
    public void testImageUri() {
        // There is no example in the ST document.
        final String stringVal = "https://www.gwg.nga.mil/misb/images/banner.jpg";
        final byte[] bytes =
                new byte[] {
                    0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67,
                    0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69,
                    0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E,
                    0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67
                };

        VmtiUri string = new VmtiUri(VmtiUri.IMAGE_URI, stringVal);
        Assert.assertEquals(string.getDisplayName(), VmtiUri.IMAGE_URI);
        VmtiUri stringFromBytes = new VmtiUri(VmtiUri.IMAGE_URI, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiUri.IMAGE_URI);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(
                string.getDisplayableValue(), "https://www.gwg.nga.mil/misb/images/banner.jpg");
        Assert.assertEquals(stringFromBytes.getValue(), stringVal);
        Assert.assertEquals(
                stringFromBytes.getDisplayableValue(),
                "https://www.gwg.nga.mil/misb/images/banner.jpg");
    }

    @Test
    public void testImageUriFromURI() throws URISyntaxException {
        // There is no example in the ST document.
        final URI uriSource = new URI("https://www.gwg.nga.mil/misb/images/banner.jpg");
        final byte[] bytes =
                new byte[] {
                    0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67,
                    0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69,
                    0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E,
                    0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67
                };

        VmtiUri string = new VmtiUri(VmtiUri.IMAGE_URI, uriSource);
        Assert.assertEquals(string.getDisplayName(), VmtiUri.IMAGE_URI);
        VmtiUri stringFromBytes = new VmtiUri(VmtiUri.IMAGE_URI, bytes);
        Assert.assertEquals(stringFromBytes.getDisplayName(), VmtiUri.IMAGE_URI);

        Assert.assertEquals(string.getBytes(), bytes);
        Assert.assertEquals(
                string.getDisplayableValue(), "https://www.gwg.nga.mil/misb/images/banner.jpg");
        Assert.assertEquals(
                stringFromBytes.getValue(), "https://www.gwg.nga.mil/misb/images/banner.jpg");
        Assert.assertEquals(
                stringFromBytes.getDisplayableValue(),
                "https://www.gwg.nga.mil/misb/images/banner.jpg");
    }

    @Test
    public void testFactoryImageUri() throws KlvParseException, URISyntaxException {
        final byte[] bytes =
                new byte[] {
                    0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67,
                    0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69,
                    0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E,
                    0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67
                };
        IVmtiMetadataValue v = VChipLS.createValue(VChipMetadataKey.imageUri, bytes);
        Assert.assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri) v;
        Assert.assertEquals(uri.getDisplayName(), VmtiUri.IMAGE_URI);
        Assert.assertEquals(uri.getBytes(), bytes);
        Assert.assertEquals(
                uri.getDisplayableValue(), "https://www.gwg.nga.mil/misb/images/banner.jpg");
        Assert.assertEquals(
                uri.getUri(), new URI("https://www.gwg.nga.mil/misb/images/banner.jpg"));
    }
}
