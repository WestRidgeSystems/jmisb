package org.jmisb.api.klv.st0601;

/**
 * Represents a UAS Datalink value that is not interpreted by the library
 */
public class OpaqueValue implements IUasDatalinkValue
{

    public static final String DISPLAYNAME = "Opaque Value";
    byte[] bytes;

    /**
     * Create from encoded bytes
     * @param bytes The byte array
     */
    public OpaqueValue(byte[] bytes)
    {
        this.bytes = bytes.clone();
    }

    @Override
    public byte[] getBytes()
    {
        return bytes.clone();
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Not Supported]";
    }

    @Override
    public final String getDisplayName()
    {
        return DISPLAYNAME;
    }

}
