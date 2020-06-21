package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WindSpeedTest {
    @Test
    public void testConstructFromValue() {
        // Min
        WindSpeed range = new WindSpeed(0.0);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0x00});

        // Max
        range = new WindSpeed(100.0);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        range = new WindSpeed(69.8039216);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0xB2});

        Assert.assertEquals(range.getDisplayName(), "Wind Speed");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        WindSpeed range = new WindSpeed(new byte[] {(byte) 0x00});
        Assert.assertEquals(range.getMetersPerSecond(), 0.0);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0.0m/s", range.getDisplayableValue());

        // Max
        range = new WindSpeed(new byte[] {(byte) 0xff});
        Assert.assertEquals(range.getMetersPerSecond(), 100.0);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals("100.0m/s", range.getDisplayableValue());

        // From ST:
        range = new WindSpeed(new byte[] {(byte) 0xB2});
        Assert.assertEquals(range.getMetersPerSecond(), 69.8039216, 0.0001);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0xB2});
        Assert.assertEquals("69.8m/s", range.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.WindSpeed, bytes);
        Assert.assertTrue(v instanceof WindSpeed);
        WindSpeed windspeed = (WindSpeed) v;
        Assert.assertEquals(windspeed.getMetersPerSecond(), 0.0);
        Assert.assertEquals(windspeed.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0.0m/s", windspeed.getDisplayableValue());

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.WindSpeed, bytes);
        Assert.assertTrue(v instanceof WindSpeed);
        windspeed = (WindSpeed) v;
        Assert.assertEquals(windspeed.getMetersPerSecond(), 100.0);
        Assert.assertEquals(windspeed.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals("100.0m/s", windspeed.getDisplayableValue());

        bytes = new byte[] {(byte) 0xB2};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.WindSpeed, bytes);
        Assert.assertTrue(v instanceof WindSpeed);
        windspeed = (WindSpeed) v;
        Assert.assertEquals(windspeed.getMetersPerSecond(), 69.8039216, 0.0001);
        Assert.assertEquals(windspeed.getBytes(), new byte[] {(byte) 0xB2});
        Assert.assertEquals("69.8m/s", windspeed.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new WindSpeed(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new WindSpeed(100.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new WindSpeed(new byte[] {0x00, 0x00});
    }
}
