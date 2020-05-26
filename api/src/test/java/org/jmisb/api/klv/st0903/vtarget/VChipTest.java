package org.jmisb.api.klv.st0903.vtarget;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vchip.VChipLS;
import org.jmisb.api.klv.st0903.vchip.VChipMetadataKey;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VChip (ST0903 Target Tag 105).
 */
public class VChipTest
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

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException
    {
        VChip chip = new VChip(bytes, EnumSet.noneOf(ParseOptions.class));
        // Content can vary, but length should be OK.
        assertEquals(chip.getBytes().length, bytes.length);
        assertEquals(chip.getDisplayName(), "Image Chip");
        assertEquals(chip.getDisplayableValue(), "[VChip]");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VChip, bytes);
        assertTrue(value instanceof VChip);
        VChip chip = (VChip)value;
        // Content can vary, but length should be OK.
        assertEquals(chip.getBytes().length, bytes.length);
        assertEquals(chip.getDisplayName(), "Image Chip");
        assertEquals(chip.getDisplayableValue(), "[VChip]");
        assertEquals(chip.getChip().getTags().size(), 2);
    }

    @Test
    public void constructFromValue() throws KlvParseException, URISyntaxException
    {
        Map<VChipMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue imageType = VChipLS.createValue(VChipMetadataKey.imageType, new byte[]{0x6A, 0x70, 0x65, 0x67});
        values.put(VChipMetadataKey.imageType, imageType);
        final byte[] urlBytes = new byte[]{0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x77, 0x77, 0x77, 0x2E, 0x67, 0x77, 0x67, 0x2E, 0x6E, 0x67, 0x61, 0x2E, 0x6D, 0x69, 0x6C, 0x2F, 0x6D, 0x69, 0x73, 0x62, 0x2F, 0x69, 0x6D, 0x61, 0x67, 0x65, 0x73, 0x2F, 0x62, 0x61, 0x6E, 0x6E, 0x65, 0x72, 0x2E, 0x6A, 0x70, 0x67};
        IVmtiMetadataValue imageUri = VChipLS.createValue(VChipMetadataKey.imageUri, urlBytes);
        values.put(VChipMetadataKey.imageUri, imageUri);
        VChipLS vChipLS = new VChipLS(values);
        VChip vChip = new VChip(vChipLS);
        assertNotNull(vChip);
        assertEquals(vChip.getChip().getTags().size(), 2);
    }
}
