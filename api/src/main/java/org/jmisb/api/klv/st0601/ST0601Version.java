package org.jmisb.api.klv.st0601;

/**
 * UAS Datalink LS Version Number (ST 0601 tag 65)
 * <p>
 * From ST:
 * <blockquote>
 * Version number of the UAS LS document used to generate a source of UAS LS KLV metadata. 0 is pre-release, initial release
 * (0601.0), or test data. 1..255 corresponds to document revisions ST 0601.1 through ST 0601.255.
 * </blockquote>
 */
public class ST0601Version implements IUasDatalinkValue
{
    private byte version;

    /**
     * Create from value
     * @param version The version number
     */
    public ST0601Version(byte version)
    {
        this.version = version;
    }

    /**
     * Create from encoded bytes
     * @param bytes Byte array of length 1
     */
    public ST0601Version(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Version Number encoding is a single unsigned byte");
        }
        version = bytes[0];
    }

    /**
     * Get the version number
     * @return The version number
     */
    public byte getVersion()
    {
        return version;
    }

    @Override
    public byte[] getBytes()
    {
        return new byte[]{version};
    }
}
