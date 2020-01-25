package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WindDirectionAngleTest
{
    @Test
    public void testConstructFromValue()
    {
        WindDirectionAngle angle = new WindDirectionAngle(0.0);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00});
        Assert.assertEquals(angle.getDegrees(), 0.0);

        angle = new WindDirectionAngle(360.0);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(angle.getDegrees(), 360.0);

        // Example from standard
        angle = new WindDirectionAngle(235.924010);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xA7, (byte)0xC4});
        Assert.assertEquals(angle.getDegrees(), 235.924010);
        Assert.assertEquals("235.9240\u00B0", angle.getDisplayableValue());

        Assert.assertEquals(angle.getDisplayName(), "Wind Direction");
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        WindDirectionAngle angle = new WindDirectionAngle(bytes);
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        angle = new WindDirectionAngle(bytes);
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        // Some random byte arrays
        bytes = new byte[]{(byte)0xa8, (byte)0x73};
        angle = new WindDirectionAngle(bytes);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[]{(byte)0x34, (byte)0x9d};
        angle = new WindDirectionAngle(bytes);
        Assert.assertEquals(angle.getBytes(), bytes);

        Assert.assertEquals(angle.getDisplayName(), "Wind Direction");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.WindDirection, bytes);
        Assert.assertTrue(v instanceof WindDirectionAngle);
        WindDirectionAngle angle = (WindDirectionAngle)v;
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Wind Direction");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.WindDirection, bytes);
        Assert.assertTrue(v instanceof WindDirectionAngle);
        angle = (WindDirectionAngle)v;
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Wind Direction");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new WindDirectionAngle(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new WindDirectionAngle(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new WindDirectionAngle(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
