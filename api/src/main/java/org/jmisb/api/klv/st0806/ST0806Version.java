package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Remote Video Terminal LS Version Number (ST 0806 RVT LS Tag 8).
 * <p>
 * From ST0806:
 * <blockquote>
 * Version number of the LS document used to generate a source of LS KLV
 * metadata. 0 is pre-release, initial release (0806.0), or test data. 1..255
 * corresponds to document revisions 1 thru 255.
 * </blockquote>
 */
public class ST0806Version implements IRvtMetadataValue
{
    private int version;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value
     *
     * @param version The version number
     */
    public ST0806Version(int version)
    {
        if (version < MIN_VALUE || version > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " value must be in range [0,255]");
        }
        this.version = version;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Byte array of variable length, maximum 2
     */
    public ST0806Version(byte[] bytes)
    {
        if (bytes.length != REQUIRED_BYTES)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        version = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the version number
     *
     * @return The version number
     */
    public int getVersion()
    {
        return version;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint8ToBytes((short)version);
    }

    @Override
    public String getDisplayableValue()
    {
        return "ST0806." + version;
    }

    @Override
    public final String getDisplayName()
    {
        return "Version Number";
    }
}
