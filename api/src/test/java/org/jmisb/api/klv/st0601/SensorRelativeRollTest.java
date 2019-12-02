package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorRelativeRollTest
{
    @Test
    public void testConstructFromValue()
    {
        SensorRelativeRoll roll = new SensorRelativeRoll(0.0);
        byte[] min = roll.getBytes();
        Assert.assertEquals(min, new byte[]{0x00, 0x00, 0x00, 0x00});
        Assert.assertEquals(roll.getDegrees(), 0.0);
        Assert.assertEquals("0.0000\u00B0", roll.getDisplayableValue());

        roll = new SensorRelativeRoll(360.0);
        byte[] max = roll.getBytes();
        Assert.assertEquals(max, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(roll.getDegrees(), 360.0);
        Assert.assertEquals("360.0000\u00B0", roll.getDisplayableValue());

        // Example from standard
        final double val = 176.865437690572;
        roll = new SensorRelativeRoll(val);
        byte[] ex = roll.getBytes();
        Assert.assertEquals(ex, new byte[]{(byte)0x7d, (byte)0xc5, (byte)0x5e, (byte)0xce});
        Assert.assertEquals(roll.getDegrees(), val);
        Assert.assertEquals("176.8654\u00B0", roll.getDisplayableValue());
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] min = new byte[]{0x00, 0x00, 0x00, 0x00};
        SensorRelativeRoll roll = new SensorRelativeRoll(min);
        Assert.assertEquals(roll.getDegrees(), 0.0);
        Assert.assertEquals(roll.getBytes(), min);

        byte[] max = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        roll = new SensorRelativeRoll(max);
        Assert.assertEquals(roll.getDegrees(), 360.0);
        Assert.assertEquals(roll.getBytes(), max);

        // Example from standard
        // Resolution is 84 nano degrees, so max error should be +/- 42 nano degrees
        double delta = 42e-9;
        byte[] example = new byte[]{(byte)0x7d, (byte)0xc5, (byte)0x5e, (byte)0xce};
        roll = new SensorRelativeRoll(example);
        Assert.assertEquals(roll.getDegrees(), 176.865437690572, delta);
        Assert.assertEquals(roll.getBytes(), example);

        // Some random byte arrays
        byte[] bytes = new byte[]{(byte)0x34, (byte)0xa8, (byte)0x0d, (byte)0x59};
        roll = new SensorRelativeRoll(bytes);
        Assert.assertEquals(roll.getBytes(), bytes);

        bytes = new byte[]{(byte)0xd7, (byte)0x03, (byte)0x9d, (byte)0xc8};
        roll = new SensorRelativeRoll(bytes);
        Assert.assertEquals(roll.getBytes(), bytes);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x00, 0x00, 0x00, 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeRollAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeRoll);
        SensorRelativeRoll roll = (SensorRelativeRoll)v;
        Assert.assertEquals(roll.getDegrees(), 0.0);

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeRollAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeRoll);
        roll = (SensorRelativeRoll)v;
        Assert.assertEquals(roll.getDegrees(), 360.0);

        bytes = new byte[]{(byte)0x7d, (byte)0xc5, (byte)0x5e, (byte)0xce};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRelativeRollAngle, bytes);
        Assert.assertTrue(v instanceof SensorRelativeRoll);
        roll = (SensorRelativeRoll)v;
        Assert.assertEquals(roll.getDegrees(), 176.865437690572, 0.0000001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SensorRelativeRoll(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SensorRelativeRoll(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new SensorRelativeRoll(new byte[]{0x00, 0x00});
    }
}
