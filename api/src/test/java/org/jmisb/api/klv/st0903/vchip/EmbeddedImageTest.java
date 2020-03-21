package org.jmisb.api.klv.st0903.vchip;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmbeddedImageTest
{
    private final byte[] bytes = new byte[]{
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
    public void testImageFromBytes() throws KlvParseException
    {
        EmbeddedImage imageFromBytes = new EmbeddedImage(bytes);
        Assert.assertEquals(imageFromBytes.getDisplayName(), "Embedded Image");
        Assert.assertEquals(imageFromBytes.getDisplayableValue(), "[Image]");
    }

    @Test
    public void testFromImage() throws KlvParseException, IOException
    {
        BufferedImage bufferedImage = new BufferedImage(1, 1, TYPE_3BYTE_BGR);
        bufferedImage.setRGB(0, 0, 0);
        EmbeddedImage embeddedImage = new EmbeddedImage(bufferedImage);
        Assert.assertEquals(embeddedImage.getDisplayName(), "Embedded Image");
        Assert.assertEquals(embeddedImage.getDisplayableValue(), "[Image]");
        byte[] embeddedImageBytes = embeddedImage.getBytes("jpg");
        Assert.assertNotNull(embeddedImageBytes);
    }

    @Test
    public void testFactoryEmbeddedImage() throws KlvParseException
    {
        IVmtiMetadataValue v = VChipLS.createValue(VChipMetadataKey.embeddedImage, bytes);
        Assert.assertTrue(v instanceof EmbeddedImage);
        EmbeddedImage embeddedImage = (EmbeddedImage)v;
        Assert.assertEquals(embeddedImage.getDisplayName(), "Embedded Image");
        BufferedImage image = embeddedImage.getImage();
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getHeight(), 1);
        Assert.assertEquals(image.getWidth(), 1);
        byte[] embeddedImageBytes = embeddedImage.getBytes();
        Assert.assertNotNull(embeddedImageBytes);
        Assert.assertEquals(embeddedImageBytes[0], (byte)0x89);
        Assert.assertEquals(embeddedImageBytes[1], 'P');
        Assert.assertEquals(embeddedImageBytes[2], 'N');
        Assert.assertEquals(embeddedImageBytes[3], 'G');
        Assert.assertEquals(embeddedImageBytes[4], '\r');
        Assert.assertEquals(embeddedImageBytes[5], '\n');
        embeddedImageBytes = embeddedImage.getBytes("png");
        Assert.assertNotNull(embeddedImageBytes);
        Assert.assertEquals(embeddedImageBytes[0], (byte)0x89);
        Assert.assertEquals(embeddedImageBytes[1], 'P');
        Assert.assertEquals(embeddedImageBytes[2], 'N');
        Assert.assertEquals(embeddedImageBytes[3], 'G');
        Assert.assertEquals(embeddedImageBytes[4], '\r');
        Assert.assertEquals(embeddedImageBytes[5], '\n');
    }
}
