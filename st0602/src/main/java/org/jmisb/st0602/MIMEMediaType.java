package org.jmisb.st0602;

import java.nio.charset.StandardCharsets;

/**
 * The MIME Media Type describes the MIME Encoding type of the accompanying MIME Data.
 *
 * <p>The supported encodings as of ST 0602.4 are BMP ({@code "image/x-ms-bmp"}), JPEG ({@code
 * "image/jpeg"}), PNG ({@code "image/png"}) and CGM ({@code "image/cgm"}). SVG ({@code
 * "image/svg+xml"}) is known to be used, and is expected to be added to a revision of ST 0602 in
 * the near future.
 */
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
     * Check if the media type is PNG.
     *
     * @return true if the value is "image/png", otherwise false.
     */
    public boolean isPNG() {
        return "image/png".equals(mimeType);
    }

    /**
     * Check if the media type is JPEG.
     *
     * @return true if the value is "image/jpeg", otherwise false.
     */
    public boolean isJPEG() {
        return "image/jpeg".equals(mimeType);
    }

    /**
     * Check if the media type is BMP.
     *
     * @return true if the value is "image/x-ms-bmp", otherwise false.
     */
    public boolean isBMP() {
        return "image/x-ms-bmp".equals(mimeType);
    }

    /**
     * Check if the media type is CGM.
     *
     * <p>For historical reasons, this can be "image/cgm" or "cgm", where "cgm" was specified by ST
     * 0602 prior to revision 2.
     *
     * @return true if the value is "image/cgm" or "cgm", otherwise false.
     */
    public boolean isCGM() {
        return ("image/cgm".equals(mimeType) || "cgm".equals(mimeType));
    }

    /**
     * Check if the media type is SVG.
     *
     * @return true if the value is "image/svg+xml", otherwise false.
     */
    public boolean isSVG() {
        return "image/svg+xml".equals(mimeType);
    }
}
