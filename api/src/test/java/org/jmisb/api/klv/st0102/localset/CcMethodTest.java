package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CcMethodTest
{
    @Test
    public void testConstructFromValue()
    {
        CcMethod method = new CcMethod(CountryCodingMethod.GENC_NUMERIC);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 15);
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.GENC_NUMERIC);

        method = new CcMethod(CountryCodingMethod.FIPS10_4_MIXED);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 10);
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.FIPS10_4_MIXED);
    }

    @Test
    public void testConstructFromEncoded()
    {
        CcMethod method = new CcMethod(new byte[]{0x03});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.FIPS10_4_TWO_LETTER);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x03);

        method = new CcMethod(new byte[]{0x09});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.OMITTED_VALUE);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x09);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValue()
    {
        new CcMethod(CountryCodingMethod.GENC_ADMINSUB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalCode()
    {
        new CcMethod(new byte[]{0x40});
    }
}
