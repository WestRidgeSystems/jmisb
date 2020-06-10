package org.jmisb.api.klv.st0102;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Security Metadata version number (ST 0102 tag 22)
 */
public class ST0102Version implements ISecurityMetadataValue
{
    private int version;

    /**
     * Create from value
     * @param version The version number
     */
    public ST0102Version(int version)
    {
        this.version = version;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array of length 2
     */
    public ST0102Version(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Version Number encoding is a two-byte unsigned int");
        }
        version = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the version number
     * @return The version number
     */
    public int getVersion()
    {
        return version;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint16ToBytes(version);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + version;
    }

    @Override
    public String getDisplayName()
    {
        return "ST0102 Version";
    }
}
