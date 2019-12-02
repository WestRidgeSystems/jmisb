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

        latitude = new AlternatePlatformLatitude(new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
        Assert.assertEquals(latitude.getDegrees(), -86.041207348947, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});

        // Frame Center Latitude
        latitude = new FrameCenterLatitude(-10.5423886331461);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5424\u00B0");

        latitude = new FrameCenterLatitude(new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29});

        // Target Location Latitude
        latitude = new TargetLocationLatitude(-79.1638500518929);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929);
        Assert.assertEquals(latitude.getDisplayableValue(), "-79.1639\u00B0");

        latitude = new TargetLocationLatitude(new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});
        Assert.assertEquals(latitude.getDegrees(), -79.1638500518929, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0x8f, (byte)0x69, (byte)0x52, (byte)0x62});

        // Corner Latitude Point 1
        latitude = new FullCornerLatitude(-10.579637999887);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5796\u00B0");

        latitude = new FullCornerLatitude(new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});
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
    }

    @Test
    public void testFactoryFrameCenterLatitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xf1, (byte)0x01, (byte)0xa2, (byte)0x29};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.FrameCenterLatitude, bytes);
        Assert.assertTrue(v instanceof FrameCenterLatitude);
        FrameCenterLatitude latitude = (FrameCenterLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -10.5423886331461, delta);
        Assert.assertEquals(latitude.getDisplayableValue(), "-10.5424\u00B0");
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
    }

    @Test
    public void testFactoryFullCornerLatitude() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt1, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
        FullCornerLatitude latitude = (FullCornerLatitude)v;
        Assert.assertEquals(latitude.getDegrees(), -10.579637999887, delta);
        Assert.assertEquals(latitude.getBytes(), new byte[]{(byte)0xf0, (byte)0xf4, (byte)0x12, (byte)0x44});

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt2, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt3, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);

        v = UasDatalinkFactory.createValue(UasDatalinkTag.CornerLatPt4, bytes);
        Assert.assertTrue(v instanceof FullCornerLatitude);
    }
}
