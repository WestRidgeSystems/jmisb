package org.jmisb.st0806;

/**
 * Digital Video File Format (ST 0806 Tag 10).
 *
 * <p>Video Compression being used. Maximum 127 characters.
 *
 * <p>Examples: MPEG2, MPEG4, H.264, Analog FM (non-compressed). As this list is not exhaustive,
 * other values or variants are also acceptable.
 */
public class DigitalVideoFileFormat extends RvtString implements IRvtMetadataValue {
    private static final String DIGITAL_VIDEO_FILE_FORMAT = "Digital Video File Format";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the ASCII subset of UTF-8.
     */
    public DigitalVideoFileFormat(String value) {
        super(DIGITAL_VIDEO_FILE_FORMAT, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public DigitalVideoFileFormat(byte[] bytes) {
        super(DIGITAL_VIDEO_FILE_FORMAT, bytes);
    }
}
