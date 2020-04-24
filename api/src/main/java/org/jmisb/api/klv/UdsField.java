package org.jmisb.api.klv;

/**
 * Universal Data Set field, comprised of its Universal Label (key) and its value
 */
public class UdsField
{
    private final UniversalLabel key;

    private final byte[] value;

    public UdsField(UniversalLabel key, byte[] value)
    {
        this.key = key;
        this.value = value;
    }

    public UniversalLabel getKey()
    {
        return key;
    }

    public byte[] getValue()
    {
        return value.clone();
    }
}
