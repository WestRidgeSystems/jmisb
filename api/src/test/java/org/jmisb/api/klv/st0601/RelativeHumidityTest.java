package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RelativeHumidityTest {
    @Test
    public void testConstructFromValue() {
        // Min
        RelativeHumidity humidity = new RelativeHumidity(0.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0x00});

        // Max
        humidity = new RelativeHumidity(100.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        humidity = new RelativeHumidity(69.8039216);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0xB2});
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        RelativeHumidity humidity = new RelativeHumidity(new byte[] {(byte) 0x00});
        Assert.assertEquals(humidity.getRelativeHumidity(), 0.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0.0%", humidity.getDisplayableValue());
        Assert.assertEquals("Relative Humidity", humidity.getDisplayName());

        // Max
        humidity = new RelativeHumidity(new byte[] {(byte) 0xff});
        Assert.assertEquals(humidity.getRelativeHumidity(), 100.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals("100.0%", humidity.getDisplayableValue());

        // From ST:
        humidity = new RelativeHumidity(new byte[] {(byte) 0x81});
        Assert.assertEquals(humidity.getRelativeHumidity(), 50.5882353, 0.0001);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0x81});
        Assert.assertEquals("50.6%", humidity.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.RelativeHumidity, bytes);
        Assert.assertTrue(v instanceof RelativeHumidity);
        Assert.assertEquals("Relative Humidity", v.getDisplayName());
        RelativeHumidity humidity = (RelativeHumidity) v;
        Assert.assertEquals(humidity.getRelativeHumidity(), 0.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0.0%", humidity.getDisplayableValue());

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.RelativeHumidity, bytes);
        Assert.assertTrue(v instanceof RelativeHumidity);
        humidity = (RelativeHumidity) v;
        Assert.assertEquals(humidity.getRelativeHumidity(), 100.0);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals("100.0%", humidity.getDisplayableValue());

        bytes = new byte[] {(byte) 0x81};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.RelativeHumidity, bytes);
        Assert.assertTrue(v instanceof RelativeHumidity);
        humidity = (RelativeHumidity) v;
        Assert.assertEquals(humidity.getRelativeHumidity(), 50.5882353, 0.0001);
        Assert.assertEquals(humidity.getBytes(), new byte[] {(byte) 0x81});
        Assert.assertEquals("50.6%", humidity.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RelativeHumidity(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RelativeHumidity(100.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new RelativeHumidity(new byte[] {0x00, 0x00});
    }
}
