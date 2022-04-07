package org.jmisb.st0102;

/**
 * Utilities methods for converting country coding methods between enumeration and encoded values.
 *
 * <p>These methods are only valid for "Tag 12" values, such as Object Country Coding in ST0102 and
 * Country Codes in ST0601.
 *
 * <p>They are not valid for ST0102 Tag 2 "Classifying Country and Releasing Instructions Country
 * Coding Method".
 */
public class CountryCodingMethodUtilities {
    /**
     * Convert the given country coding method to an encoded value.
     *
     * <p>This method is only valid for "Tag 12" values, such as Object Country Coding in ST0102 and
     * Country Codes in ST0601.
     *
     * <p>It is not valid for ST0102 Tag 2 "Classifying Country and Releasing Instructions Country
     * Coding Method".
     *
     * @param method the country coding method
     * @return the equivalent integer value (as a byte)
     * @throws IllegalArgumentException if the country coding method is not valid
     */
    public static byte getValueForCodingMethod(CountryCodingMethod method)
            throws IllegalArgumentException {
        switch (method) {
            case ISO3166_TWO_LETTER:
                return 0x01;
            case ISO3166_THREE_LETTER:
                return 0x02;
            case ISO3166_NUMERIC:
                return 0x03;
            case FIPS10_4_TWO_LETTER:
                return 0x04;
            case FIPS10_4_FOUR_LETTER:
                return 0x05;
            case C1059_TWO_LETTER:
                return 0x06;
            case C1059_THREE_LETTER:
                return 0x07;
            case OMITTED_VALUE:
                return 0x08;
            case GENC_TWO_LETTER:
                return 0x0D;
            case GENC_THREE_LETTER:
                return 0x0E;
            case GENC_NUMERIC:
                return 0x0F;
            case GENC_ADMINSUB:
                return 0x40;
            default:
                throw new IllegalArgumentException("Invalid country coding method: " + method);
        }
    }

    /**
     * Convert the given country coding method to an enumerated value.
     *
     * <p>This method is only valid for "Tag 12" values, such as Object Country Coding in ST0102 and
     * Country Codes in ST0601.
     *
     * <p>It is not valid for ST0102 Tag 2 "Classifying Country and Releasing Instructions Country
     * Coding Method".
     *
     * @param value the encoded country coding method, or OMITTED_VALUE for unknown.
     * @return the equivalent enumeration value
     */
    public static CountryCodingMethod getMethodForValue(int value) {
        switch (value) {
            case 0x01:
                return CountryCodingMethod.ISO3166_TWO_LETTER;
            case 0x02:
                return CountryCodingMethod.ISO3166_THREE_LETTER;
            case 0x03:
                return CountryCodingMethod.ISO3166_NUMERIC;
            case 0x04:
                return CountryCodingMethod.FIPS10_4_TWO_LETTER;
            case 0x05:
                return CountryCodingMethod.FIPS10_4_FOUR_LETTER;
            case 0x06:
                return CountryCodingMethod.C1059_TWO_LETTER;
            case 0x07:
                return CountryCodingMethod.C1059_THREE_LETTER;
            case 0x0D:
                return CountryCodingMethod.GENC_TWO_LETTER;
            case 0x0E:
                return CountryCodingMethod.GENC_THREE_LETTER;
            case 0x0F:
                return CountryCodingMethod.GENC_NUMERIC;
            case 0x40:
                return CountryCodingMethod.GENC_ADMINSUB;
        }
        return CountryCodingMethod.OMITTED_VALUE;
    }
}
