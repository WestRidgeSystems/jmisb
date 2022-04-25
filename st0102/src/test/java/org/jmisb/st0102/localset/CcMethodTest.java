package org.jmisb.st0102.localset;

import org.jmisb.st0102.CountryCodingMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CcMethodTest {
    @Test
    public void testConstructFromValue() {
        CcMethod method = new CcMethod(CountryCodingMethod.GENC_NUMERIC);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 15);
        Assert.assertEquals(method.getDisplayName(), "Country Coding Method");
        Assert.assertEquals(method.getDisplayableValue(), "GENC_NUMERIC");
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.GENC_NUMERIC);

        method = new CcMethod(CountryCodingMethod.FIPS10_4_MIXED);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 10);
        Assert.assertEquals(method.getDisplayName(), "Country Coding Method");
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.FIPS10_4_MIXED);
    }

    @Test
    public void testConstructFromEncoded() {
        CcMethod method = new CcMethod(new byte[] {0x03});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.FIPS10_4_TWO_LETTER);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x03);
        Assert.assertEquals(method.getDisplayName(), "Country Coding Method");
        Assert.assertEquals(method.getDisplayableValue(), "FIPS10_4_TWO_LETTER");

        method = new CcMethod(new byte[] {0x09});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.OMITTED_VALUE);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x09);
        Assert.assertEquals(method.getDisplayName(), "Country Coding Method");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValue() {
        new CcMethod(CountryCodingMethod.GENC_ADMINSUB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalCode0() {
        new CcMethod(new byte[] {0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalCode() {
        new CcMethod(new byte[] {0x40});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalTooLong() {
        new CcMethod(new byte[] {0x01, 0x02});
    }

    @Test
    public void testMethods() {
        CcMethod method1 = new CcMethod(CountryCodingMethod.ISO3166_TWO_LETTER);
        Assert.assertEquals(method1.getMethod(), CountryCodingMethod.ISO3166_TWO_LETTER);
        CcMethod method2 = new CcMethod(CountryCodingMethod.ISO3166_THREE_LETTER);
        Assert.assertEquals(method2.getMethod(), CountryCodingMethod.ISO3166_THREE_LETTER);
        CcMethod method3 = new CcMethod(CountryCodingMethod.FIPS10_4_TWO_LETTER);
        Assert.assertEquals(method3.getMethod(), CountryCodingMethod.FIPS10_4_TWO_LETTER);
        CcMethod method4 = new CcMethod(CountryCodingMethod.FIPS10_4_FOUR_LETTER);
        Assert.assertEquals(method4.getMethod(), CountryCodingMethod.FIPS10_4_FOUR_LETTER);
        CcMethod method5 = new CcMethod(CountryCodingMethod.ISO3166_NUMERIC);
        Assert.assertEquals(method5.getMethod(), CountryCodingMethod.ISO3166_NUMERIC);
        CcMethod method6 = new CcMethod(CountryCodingMethod.C1059_TWO_LETTER);
        Assert.assertEquals(method6.getMethod(), CountryCodingMethod.C1059_TWO_LETTER);
        CcMethod method7 = new CcMethod(CountryCodingMethod.C1059_THREE_LETTER);
        Assert.assertEquals(method7.getMethod(), CountryCodingMethod.C1059_THREE_LETTER);
        CcMethod method8 = new CcMethod(CountryCodingMethod.OMITTED_VALUE);
        Assert.assertEquals(method8.getMethod(), CountryCodingMethod.OMITTED_VALUE);
        CcMethod method10 = new CcMethod(CountryCodingMethod.FIPS10_4_MIXED);
        Assert.assertEquals(method10.getMethod(), CountryCodingMethod.FIPS10_4_MIXED);
        CcMethod method11 = new CcMethod(CountryCodingMethod.ISO3166_MIXED);
        Assert.assertEquals(method11.getMethod(), CountryCodingMethod.ISO3166_MIXED);
        CcMethod method12 = new CcMethod(CountryCodingMethod.STANAG_1059_MIXED);
        Assert.assertEquals(method12.getMethod(), CountryCodingMethod.STANAG_1059_MIXED);
        CcMethod method13 = new CcMethod(CountryCodingMethod.GENC_TWO_LETTER);
        Assert.assertEquals(method13.getMethod(), CountryCodingMethod.GENC_TWO_LETTER);
        CcMethod method14 = new CcMethod(CountryCodingMethod.GENC_THREE_LETTER);
        Assert.assertEquals(method14.getMethod(), CountryCodingMethod.GENC_THREE_LETTER);
        CcMethod method15 = new CcMethod(CountryCodingMethod.GENC_NUMERIC);
        Assert.assertEquals(method15.getMethod(), CountryCodingMethod.GENC_NUMERIC);
        CcMethod method16 = new CcMethod(CountryCodingMethod.GENC_MIXED);
        Assert.assertEquals(method16.getMethod(), CountryCodingMethod.GENC_MIXED);
    }
}
