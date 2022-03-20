package org.jmisb.st0602;

import java.nio.charset.StandardCharsets;

/**
 * The MIME Media Type describes the MIME Encoding type of the accompanying MIME Data.
 *
 * <p>The supported encodings as of ST 0602.4 are BMP ({@code "image/x-ms-bmp"}), JPEG ({@code
 * "image/jpeg"}), PNG ({@code "image/png"}) and CGM ({@code "image/cgm"}). ST 0602.5 adds SVG
 * ({@code "image/svg+xml"}).
 */
public class MIMEMediaType implements IAnnotationMetadataValue {

    private final String mimeType;

    /**
     * Media type for PNG.
     *
     * <p>Media types were historically called MIME Types, essentially equivalent terms.
     */
    public static final String PNG = "image/png";

    /**
     * Media type for JPEG.
     *
     * <p>Media types were historically called MIME Types, essentially equivalent terms.
     */
    public static final String JPEG = "image/jpeg";

    /**
     * Media type for BMP.
     *
     * <p>The term "bitmap" is generic. This is the media type for the ST 0602 required form.
     *
     * <p>Media types were historically called MIME Types, essentially equivalent terms.
     */
    public static final String BMP = "image/x-ms-bmp";

    /**
     * Media type for CGM.
     *
     * <p>This is the currently accepted media type. Older versions of ST 0602 used "cgm", which is
     * accepted for parsing but not for generation.
     *
     * <p>Media types were historically called MIME Types, essentially equivalent terms.
     */
    public static final String CGM = "image/cgm";

    /**
     * Media type for SVG.
     *
     * <p>While not specified by the media type, as of ST 0602.5 this means SVG 1.1. Previous
     * versions of ST 0602 (ST 0602.4 and earlier) do not allow SVG.
     *
     * <p>Media types were historically called MIME Types, essentially equivalent terms.
     */
    public static final String SVG = "image/svg+xml";

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
        return PNG.equals(mimeType);
    }

    /**
     * Check if the media type is JPEG.
     *
     * @return true if the value is "image/jpeg", otherwise false.
     */
    public boolean isJPEG() {
        return JPEG.equals(mimeType);
    }

    /**
     * Check if the media type is BMP.
     *
     * @return true if the value is "image/x-ms-bmp", otherwise false.
     */
    public boolean isBMP() {
        return BMP.equals(mimeType);
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
        return (CGM.equals(mimeType) || "cgm".equals(mimeType));
    }

    /**
     * Check if the media type is SVG.
     *
     * @return true if the value is "image/svg+xml", otherwise false.
     */
    public boolean isSVG() {
        return SVG.equals(mimeType);
    }
}
