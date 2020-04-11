package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorEllipsoidHeightExtendedTest
{
    @Test
    public void testConstructFromValue()
    {
        // From ST:
        SensorEllipsoidHeightExtended alt = new SensorEllipsoidHeightExtended(23456.24);
        Assert.assertEquals(alt.getBytes(), new byte[]{(byte)0x2F, (byte)0x92, (byte)0x1E});
        Assert.assertEquals(alt.getDisplayableValue(), "23456.2m");
        Assert.assertEquals(alt.getDisplayName(), "Sensor Ellipsoid Height Extended");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // From ST:
        SensorEllipsoidHeightExtended alt = new SensorEllipsoidHeightExtended(new byte[]{(byte)0x2F, (byte)0x92, (byte)0x1E});
        Assert.assertEquals(alt.getMeters(), 23456.24, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[]{(byte)0x2F, (byte)0x92, (byte)0x1E});
        Assert.assertEquals(alt.getDisplayableValue(), "23456.2m");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x2F, (byte)0x92, (byte)0x1E};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorEllipsoidHeightExtended, bytes);
        Assert.assertTrue(v instanceof SensorEllipsoidHeightExtended);
        SensorEllipsoidHeightExtended alt = (SensorEllipsoidHeightExtended)v;
        Assert.assertEquals(alt.getMeters(), 23456.24, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[]{(byte)0x2F, (byte)0x92, (byte)0x1E});
        Assert.assertEquals(alt.getDisplayableValue(), "23456.2m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new SensorEllipsoidHeightExtended(-900.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new SensorEllipsoidHeightExtended(40000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong()
    {
        new SensorEllipsoidHeightExtended(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
