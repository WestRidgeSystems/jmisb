package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AirfieldBarometricPressureTest
{
    @Test
    public void testConstructFromValue()
    {
        AirfieldBarometricPressure pressure = new AirfieldBarometricPressure(0.0);
        byte[] bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{0x00, 0x00});
        Assert.assertEquals(pressure.getMillibars(), 0.0);

        pressure = new AirfieldBarometricPressure(5000.0);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(pressure.getMillibars(), 5000.0);

        // Example from standard
        pressure = new AirfieldBarometricPressure(2088.96010);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x6A, (byte)0xF4});
        Assert.assertEquals(pressure.getMillibars(), 2088.96010);
        Assert.assertEquals("2088.96mB", pressure.getDisplayableValue());
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        AirfieldBarometricPressure pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        // Some random byte arrays
        bytes = new byte[]{(byte)0xa8, (byte)0x73};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[]{(byte)0x34, (byte)0x9d};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new AirfieldBarometricPressure(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new AirfieldBarometricPressure(5000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new AirfieldBarometricPressure(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
