package org.jmisb.api.klv;

/**
 * Local Data Set field, comprised of a tag (the key) and its value
 */
public class LdsField
{
    final int tag;

    final byte[] data;

    public LdsField(int tag, byte[] data)
    {
        this.tag = tag;
        this.data = data;
    }

    public int getLabel()
    {
        return tag;
    }

    public byte[] getData()
    {
        return data;
    }
}
