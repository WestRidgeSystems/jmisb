package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DifferentialPressureTest
{
    @Test
    public void testConstructFromValue()
    {
        DifferentialPressure pressure = new DifferentialPressure(0.0);
        byte[] bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00});
        Assert.assertEquals(pressure.getMillibars(), 0.0);

        pressure = new DifferentialPressure(5000.0);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(pressure.getMillibars(), 5000.0);

        // Example from standard
        pressure = new DifferentialPressure(1191.95850);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x3D, (byte)0x07});
        Assert.assertEquals(pressure.getMillibars(), 1191.95850);
        Assert.assertEquals("1191.96mB", pressure.getDisplayableValue());
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        DifferentialPressure pressure = new DifferentialPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        pressure = new DifferentialPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        // Some random byte arrays
        bytes = new byte[]{(byte)0xa8, (byte)0x73};
        pressure = new DifferentialPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[]{(byte)0x34, (byte)0x9d};
        pressure = new DifferentialPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new DifferentialPressure(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new DifferentialPressure(5000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new DifferentialPressure(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
