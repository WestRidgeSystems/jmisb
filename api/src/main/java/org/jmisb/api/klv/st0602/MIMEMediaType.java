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

    /**
     * Check is the media type is PNG.
     *
     * @return true if the value is "image/png", otherwise false.
     */
    public boolean isPNG() {
        return "image/png".equals(mimeType);
    }
    
    /**
     * Check is the media type is JPEG.
     *
     * @return true if the value is "image/jpeg", otherwise false.
     */
    public boolean isJPEG() {
        return "image/jpeg".equals(mimeType);
    }

    /**
     * Check is the media type is BMP.
     *
     * @return true if the value is "image/x-ms-bmp", otherwise false.
     */
    public boolean isBMP() {
        return "image/x-ms-bmp".equals(mimeType);
    }
}
