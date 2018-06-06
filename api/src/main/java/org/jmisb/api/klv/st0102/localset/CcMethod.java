package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;

import static org.jmisb.api.klv.st0102.CountryCodingMethod.ISO3166_MIXED;

/**
 * Classifying Country and Releasing Instructions Country Coding Method (ST 0102 tag 2)
 */
public class CcMethod implements ISecurityMetadataValue
{
    private byte method;

    /**
     * Create from value
     * @param method The country coding method
     */
    public CcMethod(CountryCodingMethod method)
    {
        switch (method)
        {
            case ISO3166_TWO_LETTER:
                this.method = 1;
                break;
            case ISO3166_THREE_LETTER:
                this.method = 2;
                break;
            case FIPS10_4_TWO_LETTER:
                this.method = 3;
                break;
            case FIPS10_4_FOUR_LETTER:
                this.method = 4;
                break;
            case ISO3166_NUMERIC:
                this.method = 5;
                break;
            case C1059_TWO_LETTER:
                this.method = 6;
                break;
            case C1059_THREE_LETTER:
                this.method = 7;
                break;
            case OMITTED_VALUE:
                this.method = 8;
                break;
            case FIPS10_4_MIXED:
                this.method = 10;
                break;
            case ISO3166_MIXED:
                this.method = 11;
                break;
            case STANAG_1059_MIXED:
                this.method = 12;
                break;
            case GENC_TWO_LETTER:
                this.method = 13;
                break;
            case GENC_THREE_LETTER:
                this.method = 14;
                break;
            case GENC_NUMERIC:
                this.method = 15;
                break;
            case GENC_MIXED:
                this.method = 16;
                break;
            default:
                throw new IllegalArgumentException("Invalid classifying country coding method: " + method);
        }
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public CcMethod(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Country coding method must be one byte");
        }

        if (bytes[0] >= 1 && bytes[0] <= 16)
        {
            method = bytes[0];
        }
        else
        {
            throw new IllegalArgumentException("Invalid classifying country coding method: " + bytes[0]);
        }
    }

    /**
     * Get the country coding method
     * @return The country coding method
     */
    public CountryCodingMethod getMethod()
    {
        // For some reason, classifying country and object country coding method fields use different values to
        // represent essentially the same set of methods. The ugliness here attempts to shield clients from that.
        switch (method)
        {
            case 1:
                return CountryCodingMethod.ISO3166_TWO_LETTER;
            case 2:
                return CountryCodingMethod.ISO3166_THREE_LETTER;
            case 3:
                return CountryCodingMethod.FIPS10_4_TWO_LETTER;
            case 4:
                return CountryCodingMethod.FIPS10_4_FOUR_LETTER;
            case 5:
                return CountryCodingMethod.ISO3166_NUMERIC;
            case 6:
                return CountryCodingMethod.C1059_TWO_LETTER;
            case 7:
                return CountryCodingMethod.C1059_THREE_LETTER;
            case 8:
            case 9:
                return CountryCodingMethod.OMITTED_VALUE;
            case 10:
                return CountryCodingMethod.FIPS10_4_MIXED;
            case 11:
                return ISO3166_MIXED;
            case 12:
                return CountryCodingMethod.STANAG_1059_MIXED;
            case 13:
                return CountryCodingMethod.GENC_TWO_LETTER;
            case 14:
                return CountryCodingMethod.GENC_THREE_LETTER;
            case 15:
                return CountryCodingMethod.GENC_NUMERIC;
            case 16:
                return CountryCodingMethod.GENC_MIXED;
        }
        return CountryCodingMethod.OMITTED_VALUE;
    }

    @Override
    public byte[] getBytes()
    {
        return new byte[]{method};
    }
}
