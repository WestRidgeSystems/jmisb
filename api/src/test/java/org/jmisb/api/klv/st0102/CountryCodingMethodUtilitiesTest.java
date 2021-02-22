package org.jmisb.api.klv.st0102;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for the Country Coding Method utilities. */
public class CountryCodingMethodUtilitiesTest {
    @Test
    public void checkToEnumeration() {
        assertEquals(
                CountryCodingMethod.ISO3166_TWO_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x01));
        assertEquals(
                CountryCodingMethod.ISO3166_THREE_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x02));
        assertEquals(
                CountryCodingMethod.ISO3166_NUMERIC,
                CountryCodingMethodUtilities.getMethodForValue(0x03));
        assertEquals(
                CountryCodingMethod.FIPS10_4_TWO_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x04));
        assertEquals(
                CountryCodingMethod.FIPS10_4_FOUR_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x05));
        assertEquals(
                CountryCodingMethod.C1059_TWO_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x06));
        assertEquals(
                CountryCodingMethod.C1059_THREE_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x07));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x08));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x09));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x0A));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x0B));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x0C));
        assertEquals(
                CountryCodingMethod.GENC_TWO_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x0D));
        assertEquals(
                CountryCodingMethod.GENC_THREE_LETTER,
                CountryCodingMethodUtilities.getMethodForValue(0x0E));
        assertEquals(
                CountryCodingMethod.GENC_NUMERIC,
                CountryCodingMethodUtilities.getMethodForValue(0x0F));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x3F));
        assertEquals(
                CountryCodingMethod.GENC_ADMINSUB,
                CountryCodingMethodUtilities.getMethodForValue(0x40));
        assertEquals(
                CountryCodingMethod.OMITTED_VALUE,
                CountryCodingMethodUtilities.getMethodForValue(0x41));
    }

    @Test
    public void checkToByte() {
        assertEquals(
                0x01,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.ISO3166_TWO_LETTER));
        assertEquals(
                0x02,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.ISO3166_THREE_LETTER));
        assertEquals(
                0x03,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.ISO3166_NUMERIC));
        assertEquals(
                0x04,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.FIPS10_4_TWO_LETTER));
        assertEquals(
                0x05,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.FIPS10_4_FOUR_LETTER));
        assertEquals(
                0x06,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.C1059_TWO_LETTER));
        assertEquals(
                0x07,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.C1059_THREE_LETTER));
        assertEquals(
                0x08,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.OMITTED_VALUE));
        assertEquals(
                0x0D,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.GENC_TWO_LETTER));
        assertEquals(
                0x0E,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.GENC_THREE_LETTER));
        assertEquals(
                0x0F,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.GENC_NUMERIC));
        assertEquals(
                0x40,
                CountryCodingMethodUtilities.getValueForCodingMethod(
                        CountryCodingMethod.GENC_ADMINSUB));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueFIPS_MIXED() {
        CountryCodingMethodUtilities.getValueForCodingMethod(CountryCodingMethod.FIPS10_4_MIXED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueGENC_MIXED() {
        CountryCodingMethodUtilities.getValueForCodingMethod(CountryCodingMethod.GENC_MIXED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueISO_MIXED() {
        CountryCodingMethodUtilities.getValueForCodingMethod(CountryCodingMethod.ISO3166_MIXED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValueSTANAG_MIXED() {
        CountryCodingMethodUtilities.getValueForCodingMethod(CountryCodingMethod.STANAG_1059_MIXED);
    }

    @Test
    public void tokenTestForDoNothingConstructor() {
        CountryCodingMethodUtilities utils = new CountryCodingMethodUtilities();
        assertNotNull(utils);
    }
}
