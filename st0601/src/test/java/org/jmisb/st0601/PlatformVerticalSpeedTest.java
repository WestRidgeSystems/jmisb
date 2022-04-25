package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformVerticalSpeedTest {
    @Test
    public void testMinMax() {
        PlatformVerticalSpeed platformVerticalSpeed = new PlatformVerticalSpeed(-180.0);
        byte[] bytes = platformVerticalSpeed.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01}); // -32767 as int16
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), -180.0);
        Assert.assertEquals("-180.000m/s", platformVerticalSpeed.getDisplayableValue());

        platformVerticalSpeed = new PlatformVerticalSpeed(180.0);
        bytes = platformVerticalSpeed.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff}); // 32767 as int16
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), 180.0);
        Assert.assertEquals("180.000m/s", platformVerticalSpeed.getDisplayableValue());

        bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        platformVerticalSpeed = new PlatformVerticalSpeed(bytes);
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), -180.0);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        platformVerticalSpeed = new PlatformVerticalSpeed(bytes);
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), 180.0);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), bytes);

        Assert.assertEquals(platformVerticalSpeed.getDisplayName(), "Platform Vertical Speed");
    }

    @Test
    public void testZero() {
        PlatformVerticalSpeed platformVerticalSpeed = new PlatformVerticalSpeed(0.0);
        byte[] bytes = platformVerticalSpeed.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), 0.0);
    }

    @Test
    public void testExample() {
        double speed = -61.8878750;
        PlatformVerticalSpeed platformVerticalSpeed = new PlatformVerticalSpeed(speed);
        byte[] bytes = platformVerticalSpeed.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xD3, (byte) 0xFE});
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), speed);
        Assert.assertEquals("-61.888m/s", platformVerticalSpeed.getDisplayableValue());

        bytes = new byte[] {(byte) 0xd3, (byte) 0xfe};
        platformVerticalSpeed = new PlatformVerticalSpeed(bytes);
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), speed, 0.00001);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange() {
        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00};
        PlatformVerticalSpeed platformVerticalSpeed =
                new PlatformVerticalSpeed(Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), error);

        platformVerticalSpeed = new PlatformVerticalSpeed(error);
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), error);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformVerticalSpeed(-180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformVerticalSpeed(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformVerticalSpeed(new byte[] {0x00});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformVerticalSpeed, bytes);
        Assert.assertTrue(v instanceof PlatformVerticalSpeed);
        PlatformVerticalSpeed platformVerticalSpeed = (PlatformVerticalSpeed) v;
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), -180.0);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformVerticalSpeed, bytes);
        Assert.assertTrue(v instanceof PlatformVerticalSpeed);
        platformVerticalSpeed = (PlatformVerticalSpeed) v;
        Assert.assertEquals(platformVerticalSpeed.getVerticalSpeed(), 180.0);
        Assert.assertEquals(platformVerticalSpeed.getBytes(), bytes);
    }
}
