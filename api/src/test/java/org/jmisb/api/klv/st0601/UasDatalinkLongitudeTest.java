package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkLongitudeTest
{
    // Resolution is 84 nano degrees, so error is +/-42 nano degrees
    private final double delta = 42e-9;

    @Test
    public void testSubClasses()
    {
        // Alternate Platform Longitude
        UasDatalinkLongitude longitude = new AlternatePlatformLongitude(0.155527554524842);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});
        Assert.assertEquals(longitude.getDegrees(), 0.155527554524842);
        Assert.assertEquals(longitude.getDisplayableValue(), "0.1555\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Alternate Platform Longitude");

        longitude = new AlternatePlatformLongitude(new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});
        Assert.assertEquals(longitude.getDegrees(), 0.155527554524842, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c});
        Assert.assertEquals(longitude.getDisplayName(), "Alternate Platform Longitude");

        // Frame Center Longitude
        longitude = new FrameCenterLongitude(29.157890122923);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1579\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Frame Center Longitude");

        longitude = new FrameCenterLongitude(new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b});
        Assert.assertEquals(longitude.getDisplayName(), "Frame Center Longitude");

        // Target Location Longitude
        longitude = new TargetLocationLongitude(166.400812960416);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});
        Assert.assertEquals(longitude.getDegrees(), 166.400812960416);
        Assert.assertEquals(longitude.getDisplayableValue(), "166.4008\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Target Location Longitude");

        longitude = new TargetLocationLongitude(new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});
        Assert.assertEquals(longitude.getDegrees(), 166.400812960416, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2});
        Assert.assertEquals(longitude.getDisplayName(), "Target Location Longitude");

        // Corner Longitude Point 1
        longitude = new FullCornerLongitude(29.1273677986333, FullCornerLongitude.CORNER_LON_1);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9});
        Assert.assertEquals(longitude.getDegrees(), 29.1273677986333);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1274\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Corner Longitude Point 1 (Full)");

        longitude = new FullCornerLongitude(new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9}, FullCornerLongitude.CORNER_LON_1);
        Assert.assertEquals(longitude.getDegrees(), 29.1273677986333, delta);
        Assert.assertEquals(longitude.getBytes(), new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9});
        Assert.assertEquals(longitude.getDisplayName(), "Corner Longitude Point 1 (Full)");
    }

    @Test
    public void testFactoryAlternatePlatformLongitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x1c, (byte)0x50, (byte)0x1c};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformLongitude, bytes);
        Assert.assertTrue(v instanceof AlternatePlatformLongitude);
        AlternatePlatformLongitude longitude = (AlternatePlatformLongitude)v;
        Assert.assertEquals(longitude.getBytes(),bytes);
        Assert.assertEquals(longitude.getDegrees(), 0.155527554524842, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "0.1555\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Alternate Platform Longitude");
    }

    @Test
    public void testFactoryFrameCenterLongitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xbc, (byte)0x08, (byte)0x2b};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.FrameCenterLongitude, bytes);
        Assert.assertTrue(v instanceof FrameCenterLongitude);
        FrameCenterLongitude longitude = (FrameCenterLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 29.157890122923, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1579\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Frame Center Longitude");
    }

    @Test
    public void testFactoryTargetLocationLongitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x76, (byte)0x54, (byte)0x57, (byte)0xf2};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetLocationLongitude, bytes);
        Assert.assertTrue(v instanceof TargetLocationLongitude);
        TargetLocationLongitude longitude = (TargetLocationLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 166.400812960416, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "166.4008\u00B0");
        Assert.assertEquals(longitude.getDisplayName(), "Target Location Longitude");
    }

    @Test
    public void testFactoryFullCornerLongitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xb6, (byte)0x79, (byte)0xb9};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt1, bytes);
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 1 (Full)");
        Assert.assertTrue(v instanceof FullCornerLongitude);
        FullCornerLongitude longitude = (FullCornerLongitude)v;
        Assert.assertEquals(longitude.getDegrees(), 29.1273677986333, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1274\u00B0");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt2, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 2 (Full)");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt3, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 3 (Full)");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt4, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 4 (Full)");
    }

    // Test values changed in ST0601.16
    @Test
    public void testST0601_16_Tag83() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xbc, (byte)0xb2, (byte)0xc0};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt1, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        FullCornerLongitude longitude = (FullCornerLongitude)v;
        Assert.assertEquals(bytes, longitude.getBytes());
        Assert.assertEquals(longitude.getDegrees(), 29.161550376960857, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1616\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 1 (Full)");
    }

    @Test
    public void testST0601_16_Tag85() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xbe, (byte)0x84, (byte)0xc8};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt2, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        FullCornerLongitude longitude = (FullCornerLongitude)v;
        Assert.assertEquals(bytes, longitude.getBytes());
        Assert.assertEquals(longitude.getDegrees(), 29.171550376960860, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1716\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 2 (Full)");
    }

    @Test
    public void testST0601_16_Tag87() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xBB, (byte)0x17, (byte)0xAF};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt3, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        FullCornerLongitude longitude = (FullCornerLongitude)v;
        Assert.assertEquals(bytes, longitude.getBytes());
        Assert.assertEquals(longitude.getDegrees(), 29.152729868885170, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1527\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 3 (Full)");
    }

    @Test
    public void testST0601_16_Tag89() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x14, (byte)0xB9,(byte)0xD1, (byte)0x76};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLonPt4, bytes);
        Assert.assertTrue(v instanceof FullCornerLongitude);
        FullCornerLongitude longitude = (FullCornerLongitude)v;
        Assert.assertEquals(bytes, longitude.getBytes());
        Assert.assertEquals(longitude.getDegrees(), 29.145729868885170, delta);
        Assert.assertEquals(longitude.getDisplayableValue(), "29.1457\u00B0");
        Assert.assertEquals(v.getDisplayName(), "Corner Longitude Point 4 (Full)");
    }
}
