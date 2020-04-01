package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimeAirborneTest
{
    @Test
    public void testMinMax()
    {
        TimeAirborne time = new TimeAirborne(0);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        byte[] bytes = time.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x00});
        Assert.assertEquals(time.getSeconds(), 0);
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        time = new TimeAirborne(4294967295L);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        bytes = time.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(time.getSeconds(), 4294967295L);
        Assert.assertEquals(time.getDisplayableValue(), "4294967295s");

        bytes = new byte[]{(byte)0x00};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 255);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "255s");

        bytes = new byte[]{(byte)0x00, (byte)0x00};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 65535);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "65535s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 16777215L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(time.getDisplayableValue(), "16777215s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        time = new TimeAirborne(bytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), 4294967295L);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "4294967295s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMin()
    {
        new TimeAirborne(-1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMax()
    {
        new TimeAirborne(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLength()
    {
        byte[] fiveByteArray = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        new TimeAirborne(fiveByteArray);
    }

    @Test
    public void stExample()
    {
        long value = 19887L;
        byte[] origBytes = new byte[]{(byte)0x4D, (byte)0xaf};

        TimeAirborne time = new TimeAirborne(origBytes);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), value);
        Assert.assertEquals(time.getBytes(), origBytes);
        Assert.assertEquals(time.getDisplayableValue(), "19887s");

        time = new TimeAirborne(value);
        Assert.assertEquals(time.getDisplayName(), "Time Airborne");
        Assert.assertEquals(time.getSeconds(), value);
        Assert.assertEquals(time.getBytes(), origBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        TimeAirborne time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 255);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "255s");

        bytes = new byte[]{(byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 65535);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "65535s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 16777215L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(time.getDisplayableValue(), "16777215s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TimeAirborne, bytes);
        Assert.assertTrue(v instanceof TimeAirborne);
        Assert.assertEquals(v.getDisplayName(), "Time Airborne");
        time = (TimeAirborne)v;
        Assert.assertEquals(time.getSeconds(), 4294967295L);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "4294967295s");
    }
}
