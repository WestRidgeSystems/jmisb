package org.jmisb.api.klv;

/**
 * KLV length field
 */
public class LengthField
{
    /** Size of the length field in bytes */
    private int sizeOfLength;

    /** Size of the value field in bytes */
    private int sizeOfValue;

    /**
     * Constructor
     *
     * @param sizeOfLength Size of the length field in bytes
     * @param sizeOfValue Size of the value field in bytes
     */
    public LengthField(int sizeOfLength, int sizeOfValue)
    {
        this.sizeOfLength = sizeOfLength;
        this.sizeOfValue = sizeOfValue;
    }

    /**
     * Get the size of the length field
     * @return Size of the length field in bytes
     */
    public int getSizeOfLength()
    {
        return sizeOfLength;
    }

    /**
     * Get the size of the value field
     * @return Size of the value field in bytes
     */
    public int getSizeOfValue()
    {
        return sizeOfValue;
    }
}
