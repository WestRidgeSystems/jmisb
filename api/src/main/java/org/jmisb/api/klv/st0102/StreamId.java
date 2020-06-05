package org.jmisb.api.klv.st0102;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Security Stream Id (ST 0102 tag 19).
 *
 * <p>
 * This was historically used to link specific security markings to particular
 * streams.
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
 * StreamId should not be used in newly generated files, and is not valid as of
 * ST0102.12. It is provided for backwards compatibility only. As such, there is
 * no value-based constructor for this class.
 * </p>
 */
public class StreamId implements ISecurityMetadataValue
{
    private int id;

    /**
     * Create from encoded bytes
     * @param bytes Byte array of length 1
     */
    public StreamId(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Stream ID encoding is a one-byte unsigned int");
        }
        id = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the stream identifier
     * @return The stream identifier
     */
    public int getStreamIdentifier()
    {
        return id;
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint8ToBytes((short)id);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + id;
    }

    @Override
    public String getDisplayName()
    {
        return "Stream Identifier";
    }
}
