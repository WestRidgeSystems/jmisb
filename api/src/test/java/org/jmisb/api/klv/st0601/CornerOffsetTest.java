package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CornerOffsetTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        CornerOffset offset = new CornerOffset(-0.075, CornerOffset.CORNER_LAT_1);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x01});
        Assert.assertEquals(offset.getDegrees(), -0.075);
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 1");

        // Max
        offset = new CornerOffset(0.075, CornerOffset.CORNER_LON_1);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(offset.getDegrees(), 0.075);
        Assert.assertEquals(offset.getDisplayableValue(), "0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 1");

        // Zero
        offset = new CornerOffset(0, CornerOffset.CORNER_LAT_2);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), 0.0);
        Assert.assertEquals(offset.getDisplayableValue(), "0.0000\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 2");

        // ST example from tag 26 (pre ST0601.16)
        // frame center lat = -10.5423886331461 (from tag 23)
        // point 1 lat = -10.579637999887
        // offset = −0.037249367
        offset = new CornerOffset(-0.037249367, CornerOffset.CORNER_LAT_1);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xc0, (byte)0x6e});
        Assert.assertEquals(offset.getDegrees(), -0.037249367);
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0372\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 1");

        // Error condition
        offset = new CornerOffset(Double.POSITIVE_INFINITY,  CornerOffset.CORNER_LON_2);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x00});
        Assert.assertEquals(offset.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(offset.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 2");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        CornerOffset offset = new CornerOffset(new byte[]{(byte)0x80, (byte)0x01}, CornerOffset.CORNER_LAT_3);
        Assert.assertEquals(offset.getDegrees(), -0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x01});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 3");

        // Max
        offset = new CornerOffset(new byte[]{(byte)0x7f, (byte)0xff}, CornerOffset.CORNER_LON_3);
        Assert.assertEquals(offset.getDegrees(), 0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 3");

        // Zero
        offset = new CornerOffset(new byte[]{(byte)0x00, (byte)0x00}, CornerOffset.CORNER_LAT_4);
        Assert.assertEquals(offset.getDegrees(), 0.0);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0000\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 4");

        // ST example
        // Resolution is 1.2 micro degrees, so error is +/- 0.6 micro degrees
        final double delta = 0.6e-6;
        offset = new CornerOffset(new byte[]{(byte)0xc0, (byte)0x6e}, CornerOffset.CORNER_LAT_1);
        Assert.assertEquals(offset.getDegrees(), -0.037249367, delta);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xc0, (byte)0x6e});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0372\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 1");

        // Error condition
        offset = new CornerOffset(new byte[]{(byte)0x80, (byte)0x00}, CornerOffset.CORNER_LON_4);
        Assert.assertEquals(offset.getDegrees(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x00});
        Assert.assertEquals(offset.getDisplayableValue(), "Infinity\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 4");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x80, (byte)0x01};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint1, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        CornerOffset offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x80, (byte)0x01});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 1");

        // Max
        bytes = new byte[]{(byte)0x7f, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint1, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.075);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x7f, (byte)0xff});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0750\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 1");

        // Zero
        bytes = new byte[]{(byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint2, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.0);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0000\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 2");

        // ST example (pre ST0601.16)
        bytes = new byte[]{(byte)0xc0, (byte)0x6e};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint2, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.037249367, 0.000001);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xc0, (byte)0x6e});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0372\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 2");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint3, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        Assert.assertEquals(v.getDisplayName(), "Offset Corner Latitude Point 3");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint3, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        Assert.assertEquals(v.getDisplayName(), "Offset Corner Longitude Point 3");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint4, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        Assert.assertEquals(v.getDisplayName(), "Offset Corner Latitude Point 4");

        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint4, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        Assert.assertEquals(v.getDisplayName(), "Offset Corner Longitude Point 4");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToSmall()
    {
        new CornerOffset(-0.0751, CornerOffset.CORNER_LAT_1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToBig()
    {
        new CornerOffset(0.0751, CornerOffset.CORNER_LON_1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new CornerOffset(new byte[]{0x00, 0x00, 0x00, 0x00}, CornerOffset.CORNER_LAT_2);
    }

    // ST0601.16 changed the test values.
    @Test
    public void testST0601_16() throws KlvParseException {
        // Tag 26
        byte[] bytes = new byte[]{(byte)0x17, (byte)0x50};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint1, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        CornerOffset offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.0136602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x17, (byte)0x50});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0137\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 1");

        // Tag 27
        bytes = new byte[]{(byte)0x06, (byte)0x3f};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint1, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.0036602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x06, (byte)0x3f});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0037\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 1");

        // Tag 28
        bytes = new byte[]{(byte)0xF9, (byte)0xC1};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint2, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.0036602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xF9, (byte)0xC1});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0037\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 2");

        // Tag 29
        bytes = new byte[]{(byte)0x17, (byte)0x50};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint2, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.0136602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x17, (byte)0x50});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0137\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 2");

        // Tag 30
        bytes = new byte[]{(byte)0xED, (byte)0x1F};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint3, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.0110621778, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xED, (byte)0x1F});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0111\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 3");

        // Tag 31
        bytes = new byte[]{(byte)0xF7, (byte)0x32};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint3, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.0051602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xF7, (byte)0x32});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0052\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 3");

        // Tag 32
        bytes = new byte[]{(byte)0x01, (byte)0xD0};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLatitudePoint4, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), 0.0010621778, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0x01, (byte)0xD0});
        Assert.assertEquals(offset.getDisplayableValue(), "0.0011\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Latitude Point 4");

        // Tag 33
        bytes = new byte[]{(byte)0xEB, (byte)0x3F};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OffsetCornerLongitudePoint4, bytes);
        Assert.assertTrue(v instanceof CornerOffset);
        offset = (CornerOffset)v;
        Assert.assertEquals(offset.getDegrees(), -0.0121602540, 0.0000012);
        Assert.assertEquals(offset.getBytes(), new byte[]{(byte)0xEB, (byte)0x3F});
        Assert.assertEquals(offset.getDisplayableValue(), "-0.0122\u00B0");
        Assert.assertEquals(offset.getDisplayName(), "Offset Corner Longitude Point 4");
    }
}
