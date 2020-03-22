package org.jmisb.api.klv.st0903.vchip;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.shared.VmtiUri;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0903 VChip LS.
 */
public class VChipLSTest
{
    @Test
    public void parseTag1() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x01, 0x04, 0x6A, 0x70, 0x65, 0x67};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 1);
        checkImageTypeExample(vChipLS);
    }

    @Test
    public void parseTag2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{0x02, 46, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 1);
        checkImageUriExample(vChipLS);
    }

    @Test
    public void parseTag3() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            (byte) 0x03,
            70,
            (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x08, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1F, (byte) 0x15, (byte) 0xC4,
            (byte) 0x89, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x44, (byte) 0x41,
            (byte) 0x54, (byte) 0x78, (byte) 0xDA, (byte) 0x63, (byte) 0x64, (byte) 0xD8, (byte) 0xF8, (byte) 0xFF,
            (byte) 0x3F, (byte) 0x00, (byte) 0x05, (byte) 0x1A, (byte) 0x02, (byte) 0xB1, (byte) 0x49, (byte) 0xC5,
            (byte) 0x4C, (byte) 0x37, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45,
            (byte) 0x4E, (byte) 0x44, (byte) 0xAE, (byte) 0x42, (byte) 0x60, (byte) 0x82};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 1);
        checkEmbeddedImageExample(vChipLS);
    }

    @Test
    public void parseTag1andTag2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{0x01, 0x04, 0x6A, 0x70, 0x65, 0x67, 0x02, 46, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 2);
        checkImageTypeExample(vChipLS);
        checkImageUriExample(vChipLS);
    }

    @Test
    public void parseTag1andTag3() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x01, 0x04, 0x6A, 0x70, 0x65, 0x67, // Tag 1
            (byte) 0x03, // Tag 3 key
            70, // Tag 3 length
            (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x08, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1F, (byte) 0x15, (byte) 0xC4,
            (byte) 0x89, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x44, (byte) 0x41,
            (byte) 0x54, (byte) 0x78, (byte) 0xDA, (byte) 0x63, (byte) 0x64, (byte) 0xD8, (byte) 0xF8, (byte) 0xFF,
            (byte) 0x3F, (byte) 0x00, (byte) 0x05, (byte) 0x1A, (byte) 0x02, (byte) 0xB1, (byte) 0x49, (byte) 0xC5,
            (byte) 0x4C, (byte) 0x37, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45,
            (byte) 0x4E, (byte) 0x44, (byte) 0xAE, (byte) 0x42, (byte) 0x60, (byte) 0x82};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 2);
        checkImageTypeExample(vChipLS);
        checkEmbeddedImageExample(vChipLS);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{
            0x04, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x01, 0x04, 0x6A, 0x70, 0x65, 0x67, // Tag 1
            (byte) 0x03, // Tag 3 key
            70, // Tag 3 length
            (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x08, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1F, (byte) 0x15, (byte) 0xC4,
            (byte) 0x89, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x44, (byte) 0x41,
            (byte) 0x54, (byte) 0x78, (byte) 0xDA, (byte) 0x63, (byte) 0x64, (byte) 0xD8, (byte) 0xF8, (byte) 0xFF,
            (byte) 0x3F, (byte) 0x00, (byte) 0x05, (byte) 0x1A, (byte) 0x02, (byte) 0xB1, (byte) 0x49, (byte) 0xC5,
            (byte) 0x4C, (byte) 0x37, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45,
            (byte) 0x4E, (byte) 0x44, (byte) 0xAE, (byte) 0x42, (byte) 0x60, (byte) 0x82};
        VChipLS vChipLS = new VChipLS(bytes);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 2);
        checkImageTypeExample(vChipLS);
        checkEmbeddedImageExample(vChipLS);
    }

    private void checkImageTypeExample(VChipLS vChipLS)
    {
        assertTrue(vChipLS.getTags().contains(VChipMetadataKey.imageType));
        IVmtiMetadataValue v = vChipLS.getField(VChipMetadataKey.imageType);
        assertEquals(v.getDisplayName(), "Image Type");
        assertEquals(v.getDisplayName(), VmtiTextString.IMAGE_TYPE);
        assertEquals(v.getDisplayableValue(), "jpeg");
        assertTrue(v instanceof VmtiTextString);
        VmtiTextString textString = (VmtiTextString) vChipLS.getField(VChipMetadataKey.imageType);
        assertEquals(textString.getValue(), "jpeg");
    }

    private void checkImageUriExample(VChipLS vChipLS) throws URISyntaxException
    {
        final String stringVal = "https://www.gwg.nga.mil/misb/images/banner.jpg";
        assertTrue(vChipLS.getTags().contains(VChipMetadataKey.imageUri));
        IVmtiMetadataValue v = vChipLS.getField(VChipMetadataKey.imageUri);
        assertEquals(v.getDisplayName(), "Image URI");
        assertEquals(v.getDisplayName(), VmtiUri.IMAGE_URI);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri) vChipLS.getField(VChipMetadataKey.imageUri);
        assertEquals(uri.getUri().toString(), stringVal);
    }

    private void checkEmbeddedImageExample(VChipLS vChipLS) throws URISyntaxException
    {
        assertTrue(vChipLS.getTags().contains(VChipMetadataKey.embeddedImage));
        IVmtiMetadataValue v = vChipLS.getField(VChipMetadataKey.embeddedImage);
        assertEquals(v.getDisplayName(), "Embedded Image");
        assertEquals(v.getDisplayableValue(), "[Image]");
        assertTrue(v instanceof EmbeddedImage);
        EmbeddedImage image = (EmbeddedImage) vChipLS.getField(VChipMetadataKey.embeddedImage);
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x6A, 0x70};
        IVmtiMetadataValue value = VChipLS.createValue(VChipMetadataKey.Undefined, bytes);
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException, URISyntaxException
    {
        Map<VChipMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue imageType = VChipLS.createValue(VChipMetadataKey.imageType, new byte[]{0x6A, 0x70, 0x65, 0x67});
        values.put(VChipMetadataKey.imageType, imageType);
        final byte[] urlBytes = new byte[]{0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        IVmtiMetadataValue imageUri = VChipLS.createValue(VChipMetadataKey.imageUri, urlBytes);
        values.put(VChipMetadataKey.imageUri, imageUri);
        VChipLS vChipLS = new VChipLS(values);
        assertNotNull(vChipLS);
        assertEquals(vChipLS.getTags().size(), 2);
        checkImageTypeExample(vChipLS);
        checkImageUriExample(vChipLS);
    }
}
