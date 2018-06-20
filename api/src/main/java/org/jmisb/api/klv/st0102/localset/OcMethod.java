package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Object Country Coding Method (ST 0102 tag 12)
 */
public class OcMethod implements ISecurityMetadataValue
{
    private byte method;
    private static Set<Byte> legal = new HashSet<>(Arrays.asList((byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04,
            (byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08, (byte)0x09, (byte)0x0a, (byte)0x0b, (byte)0x0c, (byte)0x0d,
            (byte)0x0e, (byte)0x0f, (byte)0x40));

    /**
     * Create from value
     * @param method Country coding method
     */
    public OcMethod(CountryCodingMethod method)
    {
        switch (method)
        {
            case ISO3166_TWO_LETTER:
                this.method = 1;
                break;
            case ISO3166_THREE_LETTER:
                this.method = 2;
                break;
            case ISO3166_NUMERIC:
                this.method = 3;
                break;
            case FIPS10_4_TWO_LETTER:
                this.method = 4;
                break;
            case FIPS10_4_FOUR_LETTER:
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
            case GENC_TWO_LETTER:
                this.method = 13;
                break;
            case GENC_THREE_LETTER:
                this.method = 14;
                break;
            case GENC_NUMERIC:
                this.method = 15;
                break;
            case GENC_ADMINSUB:
                this.method = 64;
                break;
            default:
                throw new IllegalArgumentException("Invalid object country coding method: " + method);
        }
    }

    /**
     * Create from encoded bytes
     * @param bytes Encoded byte array
     */
    public OcMethod(byte[] bytes)
    {
        if (bytes.length != 1)
        {
            throw new IllegalArgumentException("Country coding method must be one byte");
        }

        if (legal.contains(bytes[0]))
        {
            this.method = bytes[0];
        }
        else
        {
            throw new IllegalArgumentException("Invalid object country coding method: " + bytes[0]);
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
                return CountryCodingMethod.ISO3166_NUMERIC;
            case 4:
                return CountryCodingMethod.FIPS10_4_TWO_LETTER;
            case 5:
                return CountryCodingMethod.FIPS10_4_FOUR_LETTER;
            case 6:
                return CountryCodingMethod.C1059_TWO_LETTER;
            case 7:
                return CountryCodingMethod.C1059_THREE_LETTER;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return CountryCodingMethod.OMITTED_VALUE;
            case 13:
                return CountryCodingMethod.GENC_TWO_LETTER;
            case 14:
                return CountryCodingMethod.GENC_THREE_LETTER;
            case 15:
                return CountryCodingMethod.GENC_NUMERIC;
            case 64:
                return CountryCodingMethod.GENC_ADMINSUB;
        }
        return CountryCodingMethod.OMITTED_VALUE;
    }

    @Override
    public byte[] getBytes()
    {
        return new byte[]{method};
    }

    @Override
    public String getDisplayableValue()
    {
        return getMethod().toString();
    }
}
