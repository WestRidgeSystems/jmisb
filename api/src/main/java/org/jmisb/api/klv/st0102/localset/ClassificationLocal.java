package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.SecurityMetadataValue;

/**
 * Security classification level as defined by Security Metadata Local Set (ST 0102 tag 1)
 */
public class ClassificationLocal implements SecurityMetadataValue
{
    private Classification value;

    /**
     * Create from value
     * @param classification The classification level
     */
    public ClassificationLocal(Classification classification)
    {
        this.value = classification;
    }

    /**
     * Create from encoded bytes
     * @param bytes The encoded byte array
     */
    public ClassificationLocal(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Security Classification is encoded as one byte");
        }

        this.value = Classification.getClassification(bytes[0]);
    }

    /**
     * Get the classification level
     * @return The classification level
     */
    public Classification getClassification()
    {
        return value;
    }

    @Override
    public byte[] getBytes()
    {
        return new byte[]{value.getCode()};
    }
}
