package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OcMethodTest
{
    @Test
    public void testConstructFromValue()
    {
        OcMethod method = new OcMethod(CountryCodingMethod.GENC_NUMERIC);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 15);
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.GENC_NUMERIC);
    }

    @Test
    public void testConstructFromEncoded()
    {
        OcMethod method = new OcMethod(new byte[]{0x03});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.ISO3166_NUMERIC);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x03);

        method = new OcMethod(new byte[]{0x0a});
        Assert.assertEquals(method.getMethod(), CountryCodingMethod.OMITTED_VALUE);
        Assert.assertEquals(method.getBytes().length, 1);
        Assert.assertEquals(method.getBytes()[0], 0x0a);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalValue()
    {
        new OcMethod(CountryCodingMethod.STANAG_1059_MIXED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalCode()
    {
        new OcMethod(new byte[]{0x10});
    }
}
