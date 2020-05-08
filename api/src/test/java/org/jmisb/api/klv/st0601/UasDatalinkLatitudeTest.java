package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkLatitudeTest
{
    // Resolution is 42 nano degrees, so error is +/-21 nano degrees
    private final double delta = 21e-9;

    @Test
    public void testSubClasses()
    {
        // Alternative Platform Latitude
        UasDatalinkLatitude latitude = new AlternatePlatformLatitude(-86.041207348947);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947);
        Assert.assertEquals(latitude.getDisplayableValue(), "-86.0412\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Alternate Platform Latitude");

        latitude = new AlternatePlatformLatitude(new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDisplayName(), "Alternate Platform Latitude");

        // Frame Center Latitude
        latitude = new FrameCenterLatitude(-10.5423886331461);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5424\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Frame Center Latitude");

        latitude = new FrameCenterLatitude(new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDisplayName(), "Frame Center Latitude");

        // Target Location Latitude
        latitude = new TargetLocationLatitude(-79.1638500518929);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929);
        Assert.assertEquals(latitude.getDisplayableValue(), "-79.1639\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Target Location Latitude");

        latitude = new TargetLocationLatitude(new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDisplayName(), "Target Location Latitude");

        // Corner Latitude Point 1 - prior to ST0601.16
        latitude = new FullCornerLatitude(-10.579637999887, FullCornerLatitude.CORNER_LAT_1);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5796\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Corner Latitude Point 1 (Full)");

        latitude = new FullCornerLatitude(new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44}, FullCornerLatitude.CORNER_LAT_1);
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDisplayName(), "Corner Latitude Point 1 (Full)");
    }

    @Test
    public void testFactoryAlternatePlatformLatitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformLatitude, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformLatitude);
        AlternatePlatformLatitude latitude = (AlternatePlatformLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDisplayableValue(), "-86.0412\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Alternate Platform Latitude");
    }

    @Test
    public void testFactoryFrameCenterLatitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.FrameCenterLatitude, bytes);
        Assert.assertTrue(v instanceof FrameCenterLatitude);
        FrameCenterLatitude latitude = (FrameCenterLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5424\u00B0");
        Assert.assertEquals(latitude.getDisplayName(), "Frame Center Latitude");
    }

    @Test
    public void testFactoryTargetLocationLatitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetLocationLatitude, bytes);
        Assert.assertTrue(v instanceof TargetLocationLatitude);
        TargetLocationLatitude latitude = (TargetLocationLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-79.1639\u00B0");
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDisplayName(), "Target Location Latitude");
    }

    @Test
    public void testFactoryFullCornerLatitude() throws KlvParseException {
        // ST example, prior to ST0601.16
        byte[] bytes = new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt1, bytes);
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 1 (Full)");
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt2, bytes);
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 2 (Full)");
        Assert.assertTrue(v instanceof FullCornerLatitude);

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt3, bytes);
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 3 (Full)");
        Assert.assertTrue(v instanceof FullCornerLatitude);

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt4, bytes);
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 4 (Full)");
        Assert.assertTrue(v instanceof FullCornerLatitude);
    }

    // Test values changed in ST0601.16
    @Test
    public void testST0601_16_Tag82() throws KlvParseException
    {
        // ST example, prior to ST0601.16
        byte[] bytes = new byte[]{(byte)0xf1, (byte)0x06, (byte)0x9B, (byte)0x63};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt1, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(bytes, latitude.getBytes());
        Assert.assertEquals(latitude.getDegrees(), -10.528728379108287, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5287\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 1 (Full)");
    }

    @Test
    public void testST0601_16_Tag84() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0xf1, (byte)0x00, (byte)0x4D, (byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt2, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(bytes, latitude.getBytes());
        Assert.assertEquals(latitude.getDegrees(), -10.546048887183977, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5460\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 2 (Full)");
    }

    @Test
    public void testST0601_16_Tag86() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0xf0, (byte)0xFD, (byte)0x9B, (byte)0x17};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt3, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(bytes, latitude.getBytes());
        Assert.assertEquals(latitude.getDegrees(), -10.553450810972622, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5535\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 3 (Full)");
    }

    @Test
    public void testST0601_16_Tag88() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0xf1, (byte)0x02, (byte)0x05, (byte)0x2A};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt4, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(bytes, latitude.getBytes());
        Assert.assertEquals(latitude.getDegrees(), -10.541326455319641, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5413\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Latitude Point 4 (Full)");
    }

    @Test
    public void testDegreesRadians() {
        SensorLatitude latitude = new SensorLatitude(90.0);
        Assert.assertEquals(latitude.getRadians(), Math.PI/2, delta);

        latitude = new SensorLatitude(-90.0);
        Assert.assertEquals(latitude.getRadians(), -Math.PI/2, delta);

        latitude = new SensorLatitude(Double.POSITIVE_INFINITY);
        Assert.assertEquals(latitude.getRadians(), Double.POSITIVE_INFINITY);
    }
}
