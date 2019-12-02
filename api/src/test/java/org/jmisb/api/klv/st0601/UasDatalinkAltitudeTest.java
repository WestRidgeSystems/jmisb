package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkAltitudeTest
{
    // Resolution is 0.3m, so error is +/-0.15m
    private final double delta = 0.15;

    @Test
    public void testSubClasses()
    {
        // Frame Center Elevation
        UasDatalinkAltitude altitude = new FrameCenterElevation(3216.037);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getMeters(), 3216.037);
        Assert.assertEquals(altitude.getDisplayableValue(), "3216.0m");

        altitude = new FrameCenterElevation(new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getMeters(), 3216.037, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getDisplayableValue(), "3216.0m");

        // Density Altitude
        altitude = new DensityAltitude(14818.68);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68);
        Assert.assertEquals(altitude.getDisplayableValue(), "14818.7m");

        altitude = new DensityAltitude(new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getDisplayableValue(), "14818.7m");

        // Alternate Platform Altitude
        altitude = new AlternatePlatformAltitude(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");

        altitude = new AlternatePlatformAltitude(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");

        // Frame Center Height Above Ellipsoid
        altitude = new FrameCenterHae(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");

        altitude = new FrameCenterHae(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");

        // Target Location Elevation
        altitude = new TargetLocationElevation(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");

        altitude = new TargetLocationElevation(new byte[]{(byte)0xF8, (byte)0x23});
        Assert.assertEquals(altitude.getMeters(), 18389.0471, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xf8, (byte)0x23});
        Assert.assertEquals(altitude.getDisplayableValue(), "18389.0m");
    }

    @Test
    public void testFactoryFrameCenterElevation() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x34, (byte)0xf3};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.FrameCenterElevation, bytes);
        Assert.assertTrue(v instanceof FrameCenterElevation);
        FrameCenterElevation altitude = (FrameCenterElevation)v;
        Assert.assertEquals(altitude.getMeters(), 3216.037, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getDisplayableValue(), "3216.0m");
    }

    @Test
    public void testFactoryDensityAltitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xca, (byte)0x35};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.DensityAltitude, bytes);
        Assert.assertTrue(v instanceof DensityAltitude);
        DensityAltitude altitude = (DensityAltitude)v;
        Assert.assertEquals(altitude.getMeters(), 14818.68, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getDisplayableValue(), "14818.7m");
    }

    @Test
    public void testFactoryAlternatePlatformAltitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x0b, (byte)0xb3};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformAltitude, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformAltitude);
        AlternatePlatformAltitude altitude = (AlternatePlatformAltitude)v;
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
    }

    @Test
    public void testFactoryFrameCenterHae() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x0b, (byte)0xb3};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.FrameCenterHae, bytes);
        Assert.assertTrue(v instanceof FrameCenterHae);
        FrameCenterHae altitude = (FrameCenterHae)v;
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
    }

    @Test
    public void testFactoryTargetLocationElevation() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xF8, (byte)0x23};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetLocationElevation, bytes);
        Assert.assertTrue(v instanceof TargetLocationElevation);
        TargetLocationElevation altitude = (TargetLocationElevation)v;
        Assert.assertEquals(altitude.getMeters(), 18389.0471, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xf8, (byte)0x23});
        Assert.assertEquals(altitude.getDisplayableValue(), "18389.0m");
    }
}
