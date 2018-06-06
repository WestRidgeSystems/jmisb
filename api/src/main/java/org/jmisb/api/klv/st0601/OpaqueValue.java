package org.jmisb.api.klv.st0601;

/**
 * Represents a UAS Datalink value that is not interpreted by the library
 */
public class OpaqueValue implements IUasDatalinkValue
{
    byte[] bytes;

    /**
     * Create from encoded bytes
     * @param bytes The byte array
     */
    public OpaqueValue(byte[] bytes)
    {
        this.bytes = bytes;
    }

    @Override
    public byte[] getBytes()
    {
        return bytes;
    }
}
