package org.jmisb.api.klv.st0806.poi;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0806 POI LS.
 */
public class RvtPoiLocalSetTest extends LoggerChecks
{
    public RvtPoiLocalSetTest()
    {
        super(RvtPoiLocalSet.class);
    }

    @Test
    public void parseTag1() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x01, 0x02, 0x02, 0x04};
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes, 0, bytes.length);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 1);
        checkPoiAoiNumberExample(poiLocalSet);
    }
/*
    @Test
    public void parseTag2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{0x02, 46, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 1);
        checkImageUriExample(poiLocalSet);
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
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 1);
        checkEmbeddedImageExample(poiLocalSet);
    }

    @Test
    public void parseTag1andTag2() throws KlvParseException, URISyntaxException
    {
        final byte[] bytes = new byte[]{0x01, 0x04, 0x6A, 0x70, 0x65, 0x67, 0x02, 46, 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 2);
        checkPoiAoiNumberExamle(poiLocalSet);
        checkImageUriExample(poiLocalSet);
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
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 2);
        checkPoiAoiNumberExamle(poiLocalSet);
        checkEmbeddedImageExample(poiLocalSet);
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
        verifyNoLoggerMessages();
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes);
        verifySingleLoggerMessage("Unknown VMTI VChip Metadata tag: 4");
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 2);
        checkPoiAoiNumberExamle(poiLocalSet);
        checkEmbeddedImageExample(poiLocalSet);
    }
*/
    private void checkPoiAoiNumberExample(RvtPoiLocalSet poiLocalSet)
    {
        assertTrue(poiLocalSet.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber));
        IRvtPoiMetadataValue v = poiLocalSet.getField(RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(v.getDisplayName(), "POI/AOI Number");
        assertEquals(v.getDisplayableValue(), "516");
        assertTrue(v instanceof PoiAoiNumber);
        PoiAoiNumber number = (PoiAoiNumber)v;
        assertNotNull(number);
        assertEquals(number.getNumber(), 516);
    }
/*
    private void checkImageUriExample(RvtPoiLocalSet poiLocalSet) throws URISyntaxException
    {
        final String stringVal = "https://www.gwg.nga.mil/misb/images/banner.jpg";
        assertTrue(poiLocalSet.getTags().contains(VChipMetadataKey.imageUri));
        IVmtiMetadataValue v = poiLocalSet.getField(VChipMetadataKey.imageUri);
        assertEquals(v.getDisplayName(), "Image URI");
        assertEquals(v.getDisplayName(), VmtiUri.IMAGE_URI);
        assertEquals(v.getDisplayableValue(), stringVal);
        assertTrue(v instanceof VmtiUri);
        VmtiUri uri = (VmtiUri) poiLocalSet.getField(VChipMetadataKey.imageUri);
        assertEquals(uri.getUri().toString(), stringVal);
    }

    private void checkEmbeddedImageExample(RvtPoiLocalSet poiLocalSet) throws URISyntaxException
    {
        assertTrue(poiLocalSet.getTags().contains(VChipMetadataKey.embeddedImage));
        IVmtiMetadataValue v = poiLocalSet.getField(VChipMetadataKey.embeddedImage);
        assertEquals(v.getDisplayName(), "Embedded Image");
        assertEquals(v.getDisplayableValue(), "[Image]");
        assertTrue(v instanceof EmbeddedImage);
        EmbeddedImage image = (EmbeddedImage) poiLocalSet.getField(VChipMetadataKey.embeddedImage);
    }
*/
    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x03};
        verifyNoLoggerMessages();
        IRvtPoiMetadataValue value = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized RVT POI tag: Undefined");
        assertNull(value);
    }
/*
    @Test
    public void constructFromMap() throws KlvParseException, URISyntaxException
    {
        Map<VChipMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue imageType = RvtPoiLocalSet.createValue(VChipMetadataKey.imageType, new byte[]{0x6A, 0x70, 0x65, 0x67});
        values.put(VChipMetadataKey.imageType, imageType);
        final byte[] urlBytes = new byte[]{0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        IVmtiMetadataValue imageUri = RvtPoiLocalSet.createValue(VChipMetadataKey.imageUri, urlBytes);
        values.put(VChipMetadataKey.imageUri, imageUri);
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(values);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 2);
        checkPoiAoiNumberExamle(poiLocalSet);
        checkImageUriExample(poiLocalSet);
    }
*/
}
