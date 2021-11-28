package org.jmisb.api.klv.st0903.vchip;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.annotations.Test;

/* Note: logging isn't really tested */
public class EmbeddedImageTest extends LoggerChecks {
    public EmbeddedImageTest() {
        super(EmbeddedImage.class);
    }

    private final byte[] bytes =
            new byte[] {
                (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A,
                        (byte) 0x1A, (byte) 0x0A,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48,
                        (byte) 0x44, (byte) 0x52,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x01,
                (byte) 0x08, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1F,
                        (byte) 0x15, (byte) 0xC4,
                (byte) 0x89, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49,
                        (byte) 0x44, (byte) 0x41,
                (byte) 0x54, (byte) 0x78, (byte) 0xDA, (byte) 0x63, (byte) 0x64, (byte) 0xD8,
                        (byte) 0xF8, (byte) 0xFF,
                (byte) 0x3F, (byte) 0x00, (byte) 0x05, (byte) 0x1A, (byte) 0x02, (byte) 0xB1,
                        (byte) 0x49, (byte) 0xC5,
                (byte) 0x4C, (byte) 0x37, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x49, (byte) 0x45,
                (byte) 0x4E, (byte) 0x44, (byte) 0xAE, (byte) 0x42, (byte) 0x60, (byte) 0x82
            };

    @Test
    public void testImageFromBytes() throws KlvParseException {
        EmbeddedImage imageFromBytes = new EmbeddedImage(bytes);
        assertEquals(imageFromBytes.getDisplayName(), "Embedded Image");
        assertEquals(imageFromBytes.getDisplayableValue(), "[Image]");
        assertEquals(bytes, imageFromBytes.getBytes());
    }

    @Test
    public void testFactoryEmbeddedImage() throws KlvParseException {
        IVmtiMetadataValue uut = VChipLS.createValue(VChipMetadataKey.embeddedImage, bytes);
        assertTrue(uut instanceof EmbeddedImage);
        EmbeddedImage embeddedImage = (EmbeddedImage) uut;
        assertEquals(embeddedImage.getDisplayName(), "Embedded Image");
        assertEquals(bytes, uut.getBytes());
    }
}
