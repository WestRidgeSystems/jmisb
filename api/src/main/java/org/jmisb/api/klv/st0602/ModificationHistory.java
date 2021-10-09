package org.jmisb.api.klv.st0602;

import java.nio.charset.StandardCharsets;

public class ModificationHistory implements IAnnotationMetadataValue {

    private final String history;

    /**
     * Create from value.
     *
     * @param author the most recent significant event's author.
     */
    public ModificationHistory(String author) {
        history = author;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of ASCII encoded text, maximum length 127 bytes.
     */
    public ModificationHistory(byte[] bytes) {
        if (bytes.length > 127) {
            throw new IllegalArgumentException(
                    "Modification history encoding is maximum length 127 bytes");
        }
        history = new String(bytes, StandardCharsets.US_ASCII);
    }

    @Override
    public byte[] getBytes() {
        return history.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayName() {
        return "Modification History";
    }

    @Override
    public String getDisplayableValue() {
        return history;
    }
}
