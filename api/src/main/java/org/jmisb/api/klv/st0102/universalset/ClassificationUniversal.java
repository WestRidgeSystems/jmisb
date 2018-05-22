package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.SecurityMetadataValue;

import java.nio.charset.StandardCharsets;

/**
 * Security classification level as defined by Security Metadata Universal Set (ST 0102 tag 1)
 */
public class ClassificationUniversal implements SecurityMetadataValue
{
    private Classification value;

    /**
     * Create from value
     * @param classification The classification level
     */
    public ClassificationUniversal(Classification classification)
    {
        this.value = classification;
    }

    /**
     * Create from encoded bytes
     * @param bytes The encoded byte array
     */
    public ClassificationUniversal(byte[] bytes)
    {
        String inputString = new String(bytes);
        switch(inputString)
        {
            case "TOP SECRET//":
                this.value = Classification.TOP_SECRET;
                break;
            case "SECRET//":
                this.value = Classification.SECRET;
                break;
            case "CONFIDENTIAL//":
                this.value = Classification.CONFIDENTIAL;
                break;
            case "RESTRICTED//":
                this.value = Classification.RESTRICTED;
                break;
            case "UNCLASSIFIED//":
                this.value = Classification.UNCLASSIFIED;
                break;
            default:
                throw new IllegalArgumentException("Invalid security classification: " + inputString);
        }
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
        String string = "";
        switch(value)
        {
            case TOP_SECRET:
                string = "TOP SECRET//";
                break;
            case SECRET:
                string = "SECRET//";
                break;
            case CONFIDENTIAL:
                string = "CONFIDENTIAL//";
                break;
            case RESTRICTED:
                string = "RESTRICTED//";
                break;
            case UNCLASSIFIED:
                string = "UNCLASSIFIED//";
                break;
        }
        return string.getBytes(StandardCharsets.US_ASCII);
    }
}
