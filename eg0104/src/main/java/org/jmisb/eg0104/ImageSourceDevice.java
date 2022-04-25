package org.jmisb.eg0104;

import java.nio.charset.StandardCharsets;

/**
 * Image Source Device value for EG 0104.
 *
 * <p>This was Sensor Name in the NIMA-MIPO memo.
 */
public class ImageSourceDevice implements IPredatorMetadataValue {
    private final String value;

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing the Image Source Device value.
     */
    public ImageSourceDevice(byte[] bytes) {
        value = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue() {
        return value;
    }

    @Override
    public String getDisplayName() {
        return "Image Source Device";
    }
}
