package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlternatePlatformHeadingTest
{
    @Test
    public void testConstructFromValue()
    {
        AlternatePlatformHeading angle = new AlternatePlatformHeading(0.0);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00});
        Assert.assertEquals(angle.getDegrees(), 0.0);

        angle = new AlternatePlatformHeading(360.0);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(angle.getDegrees(), 360.0);

        // Example from standard
        angle = new AlternatePlatformHeading(32.6024262);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x17, (byte)0x2F});
        Assert.assertEquals(angle.getDegrees(), 32.6024262, 0.0001);
        Assert.assertEquals("32.6024\u00B0", angle.getDisplayableValue());

        Assert.assertEquals(angle.getDisplayName(), "Alternate Platform Heading");
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        AlternatePlatformHeading angle = new AlternatePlatformHeading(bytes);
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        angle = new AlternatePlatformHeading(bytes);
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        Assert.assertEquals(angle.getDisplayName(), "Alternate Platform Heading");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformHeading, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformHeading);
        AlternatePlatformHeading angle = (AlternatePlatformHeading)v;
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Alternate Platform Heading");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformHeading, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformHeading);
        angle = (AlternatePlatformHeading)v;
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Alternate Platform Heading");

        bytes = new byte[]{(byte)0x17, (byte)0x2f};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformHeading, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformHeading);
        angle = (AlternatePlatformHeading)v;
        Assert.assertEquals(angle.getDegrees(), 32.6024262, 0.00001);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Alternate Platform Heading");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new AlternatePlatformHeading(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new AlternatePlatformHeading(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new AlternatePlatformHeading(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
