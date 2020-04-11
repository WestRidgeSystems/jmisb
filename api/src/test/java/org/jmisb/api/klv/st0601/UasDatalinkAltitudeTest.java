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
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Elevation");

        altitude = new FrameCenterElevation(new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getMeters(), 3216.037, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x34, (byte)0xf3});
        Assert.assertEquals(altitude.getDisplayableValue(), "3216.0m");
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Elevation");

        // Density Altitude
        altitude = new DensityAltitude(14818.68);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68);
        Assert.assertEquals(altitude.getDisplayableValue(), "14818.7m");
        Assert.assertEquals(altitude.getDisplayName(), "Density Altitude");

        altitude = new DensityAltitude(new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getMeters(), 14818.68, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xca, (byte)0x35});
        Assert.assertEquals(altitude.getDisplayableValue(), "14818.7m");
        Assert.assertEquals(altitude.getDisplayName(), "Density Altitude");

        // Airfield Elevation
        altitude = new AirfieldElevation(8306.80552);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x76, (byte)0x70});
        Assert.assertEquals(altitude.getMeters(), 8306.80552);
        Assert.assertEquals(altitude.getDisplayableValue(), "8306.8m");
        Assert.assertEquals(altitude.getDisplayName(), "Airfield Elevation");

        altitude = new AirfieldElevation(new byte[]{(byte)0x76, (byte)0x70});
        Assert.assertEquals(altitude.getMeters(), 8306.80552, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x76, (byte)0x70});
        Assert.assertEquals(altitude.getDisplayableValue(), "8306.8m");
        Assert.assertEquals(altitude.getDisplayName(), "Airfield Elevation");

        // Alternate Platform Altitude
        altitude = new AlternatePlatformAltitude(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Altitude");

        altitude = new AlternatePlatformAltitude(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Altitude");

        // Frame Center Height Above Ellipsoid
        altitude = new FrameCenterHae(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Height Above Ellipsoid");

        altitude = new FrameCenterHae(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Height Above Ellipsoid");

        // Target Location Elevation
        altitude = new TargetLocationElevation(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Target Location Elevation");

        altitude = new TargetLocationElevation(new byte[]{(byte)0xF8, (byte)0x23});
        Assert.assertEquals(altitude.getMeters(), 18389.0471, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xf8, (byte)0x23});
        Assert.assertEquals(altitude.getDisplayableValue(), "18389.0m");
        Assert.assertEquals(altitude.getDisplayName(), "Target Location Elevation");

        // Sensor Ellipsoid Height
        altitude = new SensorEllipsoidHeight(14190.7195);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xc2, (byte)0x21});
        Assert.assertEquals(altitude.getMeters(), 14190.7195);
        Assert.assertEquals(altitude.getDisplayableValue(), "14190.7m");
        Assert.assertEquals(altitude.getDisplayName(), "Sensor Ellipsoid Height");

        altitude = new SensorEllipsoidHeight(new byte[]{(byte)0xc2, (byte)0x21});
        Assert.assertEquals(altitude.getMeters(), 14190.7, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xc2, (byte)0x21});
        Assert.assertEquals(altitude.getDisplayableValue(), "14190.7m");
        Assert.assertEquals(altitude.getDisplayName(), "Sensor Ellipsoid Height");

        // Alternate Platform Ellipsoid Height
        altitude = new AlternatePlatformEllipsoidHeight(9.445334);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334);
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Ellipsoid Height");

        altitude = new AlternatePlatformEllipsoidHeight(new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Ellipsoid Height");
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
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Elevation");
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
        Assert.assertEquals(altitude.getDisplayName(), "Density Altitude");
    }

    @Test
    public void testFactoryAirfieldElevation() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x76, (byte)0x70};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AirfieldElevation, bytes);
        Assert.assertTrue(v instanceof AirfieldElevation);
        AirfieldElevation altitude = (AirfieldElevation)v;
        Assert.assertEquals(altitude.getMeters(), 8306.80552, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x76, (byte)0x70});
        Assert.assertEquals(altitude.getDisplayableValue(), "8306.8m");
        Assert.assertEquals(altitude.getDisplayName(), "Airfield Elevation");
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
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Altitude");
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
        Assert.assertEquals(altitude.getDisplayName(), "Frame Center Height Above Ellipsoid");
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
        Assert.assertEquals(altitude.getDisplayName(), "Target Location Elevation");
    }

    @Test
    public void testSensorEllipsoidHeight() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xc2, (byte)0x21};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorEllipsoidHeight, bytes);
        Assert.assertTrue(v instanceof SensorEllipsoidHeight);
        SensorEllipsoidHeight altitude = (SensorEllipsoidHeight)v;
        Assert.assertEquals(altitude.getMeters(), 14190.7195, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0xc2, (byte)0x21});
        Assert.assertEquals(altitude.getDisplayableValue(), "14190.7m");
        Assert.assertEquals(altitude.getDisplayName(), "Sensor Ellipsoid Height");
    }

    @Test
    public void testAlternatePlatformEllipsoidHeight() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x0b, (byte)0xb3};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformEllipsoidHeight, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformEllipsoidHeight);
        AlternatePlatformEllipsoidHeight altitude = (AlternatePlatformEllipsoidHeight)v;
        Assert.assertEquals(altitude.getMeters(), 9.445334, delta);
        Assert.assertEquals(altitude.getBytes(), new byte[]{(byte)0x0b, (byte)0xb3});
        Assert.assertEquals(altitude.getDisplayableValue(), "9.4m");
        Assert.assertEquals(altitude.getDisplayName(), "Alternate Platform Ellipsoid Height");
    }
}
