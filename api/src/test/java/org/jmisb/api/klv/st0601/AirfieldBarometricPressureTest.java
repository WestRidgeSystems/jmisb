package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
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
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        pressure = new AirfieldBarometricPressure(5000.0);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff});
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        // Example from standard
        pressure = new AirfieldBarometricPressure(2088.96010);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x6A, (byte)0xF4});
        Assert.assertEquals(pressure.getMillibars(), 2088.96010);
        Assert.assertEquals("2088.96mB", pressure.getDisplayableValue());
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");
    }

    @Test
    public void testConstructFromEncoded()
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        AirfieldBarometricPressure pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        // Some random byte arrays
        bytes = new byte[]{(byte)0xa8, (byte)0x73};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        bytes = new byte[]{(byte)0x34, (byte)0x9d};
        pressure = new AirfieldBarometricPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x00, 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AirfieldBarometricPressure, bytes);
        Assert.assertTrue(v instanceof AirfieldBarometricPressure);
        AirfieldBarometricPressure pressure = (AirfieldBarometricPressure)v;
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.AirfieldBarometricPressure, bytes);
        Assert.assertTrue(v instanceof AirfieldBarometricPressure);
        pressure = (AirfieldBarometricPressure)v;
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");

        bytes = new byte[]{(byte)0x6A, (byte)0xF4};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.AirfieldBarometricPressure, bytes);
        Assert.assertTrue(v instanceof AirfieldBarometricPressure);
        pressure = (AirfieldBarometricPressure)v;
        Assert.assertEquals(pressure.getMillibars(), 2088.96010, 0.00001);
        Assert.assertEquals("2088.96mB", pressure.getDisplayableValue());
        Assert.assertEquals(pressure.getDisplayName(), "Airfield Barometric Pressure");
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
