package org.jmisb.st0903.vchip;

import org.jmisb.st0903.IVmtiMetadataValue;

/**
 * Embedded Image (ST0903 VChip Tag 3).
 *
 * <p>An image “chip” of the type specified by VChip Image Type Tag 1, embedded in the VMTI stream.
 */
public class EmbeddedImage implements IVmtiMetadataValue {
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
