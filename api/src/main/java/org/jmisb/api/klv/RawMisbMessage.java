package org.jmisb.api.klv;

/**
 * Represents an unparsed {@link IMisbMessage}
 */
public class RawMisbMessage implements IMisbMessage
{
    private UniversalLabel universalLabel;
    private byte[] bytes;

    public RawMisbMessage(UniversalLabel universalLabel, byte[] bytes)
    {
        this.universalLabel = universalLabel;
        this.bytes = bytes;
    }

    @Override
    public UniversalLabel getUniversalLabel()
    {
        return universalLabel;
    }

    /**
     * Get the raw data, including 16-byte UL and length fields
     *
     * @return The raw byte array
     */
    public byte[] getBytes()
    {
        return bytes;
    }

    @Override
    public byte[] frameMessage(boolean isNested)
    {
        return bytes;
    }

    @Override
    public String displayHeader() {
        return "Unknown";
    }
}
