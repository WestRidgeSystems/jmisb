package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LaserPrfCodeTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        LaserPrfCode code = new LaserPrfCode(111);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x00, (byte)0x6F});

        // Max
        code = new LaserPrfCode(8888);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x22, (byte)0xb8});

        // From ST:
        code = new LaserPrfCode(1743);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x06, (byte)0xCF});

        Assert.assertEquals(code.getDisplayName(), "Laser PRF Code");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        LaserPrfCode code = new LaserPrfCode(new byte[]{(byte)0x00, (byte)0x6F});
        Assert.assertEquals(code.getCode(), 111);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x00, (byte)0x6F});
        Assert.assertEquals(code.getDisplayableValue(), "111");

        // Max
        code = new LaserPrfCode(new byte[]{(byte)0x22, (byte)0xb8});
        Assert.assertEquals(code.getCode(), 8888);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x22, (byte)0xb8});
        Assert.assertEquals(code.getDisplayableValue(), "8888");

        // From ST:
        code = new LaserPrfCode(new byte[]{(byte)0x06, (byte)0xCF});
        Assert.assertEquals(code.getCode(), 1743);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x06, (byte)0xCF});
        Assert.assertEquals(code.getDisplayableValue(), "1743");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x6F};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.LaserPrfCode, bytes);
        Assert.assertTrue(v instanceof LaserPrfCode);
        LaserPrfCode code = (LaserPrfCode)v;
        Assert.assertEquals(code.getCode(), 111);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x00, (byte)0x6F});
        Assert.assertEquals(code.getDisplayableValue(), "111");

        bytes = new byte[]{(byte)0x22, (byte)0xb8};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LaserPrfCode, bytes);
        Assert.assertTrue(v instanceof LaserPrfCode);
        code = (LaserPrfCode)v;
        Assert.assertEquals(code.getCode(), 8888);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x22, (byte)0xb8});
        Assert.assertEquals(code.getDisplayableValue(), "8888");

        bytes = new byte[]{(byte)0x06, (byte)0xCF};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LaserPrfCode, bytes);
        Assert.assertTrue(v instanceof LaserPrfCode);
        code = (LaserPrfCode)v;
        Assert.assertEquals(code.getCode(), 1743);
        Assert.assertEquals(code.getBytes(), new byte[]{(byte)0x06, (byte)0xCF});
        Assert.assertEquals(code.getDisplayableValue(), "1743");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Laser PRF Code must be in the range \\[111, 8888\\]")
    public void testTooSmall()
    {
        new LaserPrfCode(110);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Laser PRF Code must be in the range \\[111, 8888\\]")
    public void testTooBig()
    {
        new LaserPrfCode(8889);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Laser PRF Code must only contain 1 through 8, not 0 or 9")
    public void testHasZero()
    {
        new LaserPrfCode(1130);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Laser PRF Code must only contain 1 through 8, not 0 or 9")
    public void testHasNine()
    {
        new LaserPrfCode(1932);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Laser PRF Code encoding is a 2-byte unsigned int")
    public void badArrayLength()
    {
        new LaserPrfCode(new byte[]{0x00});
    }
}
