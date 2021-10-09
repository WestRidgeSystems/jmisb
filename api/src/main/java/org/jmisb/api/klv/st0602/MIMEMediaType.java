package org.jmisb.api.klv.st0602;

import java.nio.charset.StandardCharsets;

public class MIMEMediaType implements IAnnotationMetadataValue {

    private final String mimeType;

    /**
     * Create from value.
     *
     * @param mimeMediaType the mime media type.
     */
    public MIMEMediaType(String mimeMediaType) {
        mimeType = mimeMediaType;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of ASCII encoded text
     */
    public MIMEMediaType(byte[] bytes) {
        mimeType = new String(bytes, StandardCharsets.US_ASCII);
    }

    @Override
    public byte[] getBytes() {
        return mimeType.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayName() {
        return "MIME Media Type";
    }

    @Override
    public String getDisplayableValue() {
        return mimeType;
    }
}
