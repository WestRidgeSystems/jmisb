package org.jmisb.api.klv.st0903.vchip;

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

    private byte[] imageBytes;

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte array corresponding to the image data.
     */
    public EmbeddedImage(byte[] bytes) {
        imageBytes = bytes;
    }

    @Override
    public byte[] getBytes() {
        return imageBytes;
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
