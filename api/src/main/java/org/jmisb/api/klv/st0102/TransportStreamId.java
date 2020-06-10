package org.jmisb.api.klv.st0102;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Security Transport Stream Id (ST 0102 tag 20).
 *
 * <p>
 * This was historically used to link specific security markings to particular
 * transport streams.
 * </p>
 * <p>
 * As of ST0102.12, this is no longer accepted practice. The current MISB
 * position is:
 * </p>
 * <blockquote>
 * a transport stream or file is governed by one overall classification; there
 * is no allowance for individual markings of elementary streams, groups of
 * metadata, or individual metadata elements.
 * </blockquote>
 * <p>
 * TransportStreamId should not be used in newly generated files, and is not
 * valid as of ST0102.12. It is provided for backwards compatibility only. As
 * such, there is no value-based constructor for this class.
 * </p>
 */
public class TransportStreamId implements ISecurityMetadataValue
{
    private int id;

    /**
     * Create from encoded bytes
     * @param bytes Byte array of length 2
     */
    public TransportStreamId(byte[] bytes)
    {
        if (bytes.length != 2)
        {
            throw new IllegalArgumentException("Transport Stream ID encoding is a two-byte unsigned int");
        }
        id = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the transport stream identifier
     * @return The transport stream identifier
     */
    public int getTransportStreamIdentifier()
    {
        return id;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint16ToBytes(id);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + id;
    }

    @Override
    public String getDisplayName()
    {
        return "Transport Stream Identifier";
    }
}
