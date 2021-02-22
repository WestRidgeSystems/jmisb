package org.jmisb.api.klv.st0903.vchip;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Embedded Image (ST0903 VChip Tag 3).
 *
 * <p>An image “chip” of the type specified by VChip Image Type Tag 1, embedded in the VMTI stream.
 */
public class EmbeddedImage implements IVmtiMetadataValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedImage.class);

    private BufferedImage embeddedImage;

    /**
     * Create from image.
     *
     * @param image The image to embed.
     */
    public EmbeddedImage(BufferedImage image) {
        embeddedImage = image;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array corresponding to the image data.
     * @throws KlvParseException if the image could not be read or parsed
     */
    public EmbeddedImage(byte[] bytes) throws KlvParseException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            embeddedImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException ex) {
            LOGGER.error("", ex);
            embeddedImage = null;
        }
    }

    @Override
    public byte[] getBytes() {
        return getBytes("png");
    }

    /**
     * Get the embedded image.
     *
     * @return the image as a BufferedImage.
     */
    public BufferedImage getImage() {
        return embeddedImage;
    }

    /**
     * Get the encoded bytes.
     *
     * @param format the output format (e.g. "png" or "jpeg")
     * @return The encoded byte array
     */
    public byte[] getBytes(String format) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(embeddedImage, format, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            LOGGER.error("", ex);
        }
        return null;
    }

    @Override
    public String getDisplayableValue() {
        return "[Image]";
    }

    @Override
    public String getDisplayName() {
        return "Embedded Image";
    }
}
