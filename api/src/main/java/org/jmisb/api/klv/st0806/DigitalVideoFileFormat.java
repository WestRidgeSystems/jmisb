package org.jmisb.api.klv.st0806;

/**
 * Represents a string value in ST 0903.
 */
public class DigitalVideoFileFormat extends RvtString implements IRvtMetadataValue
{
    public static final String DIGITAL_VIDEO_FILE_FORMAT = "Digital Video File Format";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the ASCII subset of UTF-8.
     */
    public DigitalVideoFileFormat(String value)
    {
        super(DIGITAL_VIDEO_FILE_FORMAT, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public DigitalVideoFileFormat(byte[] bytes)
    {
        super(DIGITAL_VIDEO_FILE_FORMAT, bytes);
    }
}
