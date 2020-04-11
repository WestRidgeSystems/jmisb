package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LeapSecondsTest
{
    @Test
    public void testMinMax()
    {
        LeapSeconds time = new LeapSeconds(2147483647);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        byte[] bytes = time.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x7F, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(time.getSeconds(), 2147483647);
        Assert.assertEquals(time.getDisplayableValue(), "2147483647s");

        time = new LeapSeconds(-2147483648);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        bytes = time.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00});
        Assert.assertEquals(time.getSeconds(), -2147483648);
        Assert.assertEquals(time.getDisplayableValue(), "-2147483648s");

        bytes = new byte[]{(byte)0x7F};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), 127);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x7F});
        Assert.assertEquals(time.getDisplayableValue(), "127s");

        bytes = new byte[]{(byte)0x80};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), -128);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "-128s");

        bytes = new byte[]{(byte)0x7F, (byte)0xFF};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), 32767);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x7F, (byte)0xFF});
        Assert.assertEquals(time.getDisplayableValue(), "32767s");

        bytes = new byte[]{(byte)0x80, (byte)0x00};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), -32768);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "-32768s");

        bytes = new byte[]{(byte)0x7F, (byte)0xFF, (byte)0xFF};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), 8388607);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00, (byte)0x7F, (byte)0xFF, (byte)0xFF});
        Assert.assertEquals(time.getDisplayableValue(), "8388607s");

        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), -8388608);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "-8388608s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0x7f, (byte)0xff, (byte)0xff, (byte)0xff};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), 2147483647);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "2147483647s");
        
        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
        time = new LeapSeconds(bytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), -2147483648);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "-2147483648s");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLength()
    {
        byte[] fiveByteArray = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        new LeapSeconds(fiveByteArray);
    }

    @Test
    public void stExample()
    {
        int value = 30;
        byte[] origBytes = new byte[]{(byte)0x1E};

        LeapSeconds time = new LeapSeconds(origBytes);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), value);
        Assert.assertEquals(time.getBytes(), origBytes);
        Assert.assertEquals(time.getDisplayableValue(), "30s");

        time = new LeapSeconds(value);
        Assert.assertEquals(time.getDisplayName(), "Leap Seconds");
        Assert.assertEquals(time.getSeconds(), value);
        Assert.assertEquals(time.getBytes(), origBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        LeapSeconds time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0x7f};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), 127);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "127s");

        bytes = new byte[]{(byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0x80, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), -32768);
        Assert.assertEquals(time.getBytes(), bytes);
        Assert.assertEquals(time.getDisplayableValue(), "-32768s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0x80, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), -8388608);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0xff, (byte)0x80, (byte)0x00, (byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "-8388608s");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), 0L);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(time.getDisplayableValue(), "0s");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.LeapSeconds, bytes);
        Assert.assertTrue(v instanceof LeapSeconds);
        Assert.assertEquals(v.getDisplayName(), "Leap Seconds");
        time = (LeapSeconds)v;
        Assert.assertEquals(time.getSeconds(), -1);
        Assert.assertEquals(time.getBytes(), new byte[]{(byte)0xff});
        Assert.assertEquals(time.getDisplayableValue(), "-1s");
    }
}
