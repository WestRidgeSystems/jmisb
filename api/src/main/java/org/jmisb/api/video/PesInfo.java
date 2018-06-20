package org.jmisb.api.video;

/**
 * Packetized Elementary Stream (PES) information
 */
public class PesInfo
{
    private final int index;
    private final PesType type;
    private final String codecName;

    /**
     * Constructor
     *
     * @param index The stream index
     * @param type The type
     * @param codecName The codec name
     */
    public PesInfo(int index, PesType type, String codecName)
    {
        this.index = index;
        this.type = type;
        this.codecName = codecName;
    }

    /**
     * Get the stream index
     *
     * @return The stream index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Get the type
     *
     * @return The type
     */
    public PesType getType()
    {
        return type;
    }

    /**
     * Get the codec name
     *
     * @return The codec name
     */
    public String getCodecName()
    {
        return codecName;
    }

    @Override
    public String toString()
    {
        return "Index: " + index + "; " +
               "Type: " + type + "; " +
               "Codec: " + codecName;
    }
}
