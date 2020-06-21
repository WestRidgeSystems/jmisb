package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OutsideAirTemperatureTest {
    @Test
    public void testConstructFromValue() {
        // Min
        OutsideAirTemperature temperature = new OutsideAirTemperature((short) -128);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x80});

        // Max
        temperature = new OutsideAirTemperature((short) 127);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x7f});

        // From ST:
        temperature = new OutsideAirTemperature((short) 84);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x54});

        Assert.assertEquals(temperature.getDisplayName(), "Outside Air Temperature");
    }

    @Test
    public void testConstructFromEncoded() {
        // zero
        OutsideAirTemperature temperature = new OutsideAirTemperature(new byte[] {(byte) 0x00});
        Assert.assertEquals(temperature.getTemperature(), 0);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0\u00B0C", temperature.getDisplayableValue());

        // Min
        temperature = new OutsideAirTemperature(new byte[] {(byte) 0x80});
        Assert.assertEquals(temperature.getTemperature(), -128);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x80});
        Assert.assertEquals("-128\u00B0C", temperature.getDisplayableValue());

        // Max
        temperature = new OutsideAirTemperature(new byte[] {(byte) 0x7f});
        Assert.assertEquals(temperature.getTemperature(), 127);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x7f});
        Assert.assertEquals("127\u00B0C", temperature.getDisplayableValue());

        // From ST:
        temperature = new OutsideAirTemperature(new byte[] {(byte) 0x54});
        Assert.assertEquals(temperature.getTemperature(), 84);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x54});
        Assert.assertEquals("84\u00B0C", temperature.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OutsideAirTemp, bytes);
        Assert.assertTrue(v instanceof OutsideAirTemperature);
        OutsideAirTemperature temperature = (OutsideAirTemperature) v;
        Assert.assertEquals(temperature.getTemperature(), 0.0);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0\u00B0C", temperature.getDisplayableValue());

        bytes = new byte[] {(byte) 0x80};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OutsideAirTemp, bytes);
        Assert.assertTrue(v instanceof OutsideAirTemperature);
        temperature = (OutsideAirTemperature) v;
        Assert.assertEquals(temperature.getTemperature(), -128);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x80});
        Assert.assertEquals("-128\u00B0C", temperature.getDisplayableValue());

        bytes = new byte[] {(byte) 0x7f};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OutsideAirTemp, bytes);
        Assert.assertTrue(v instanceof OutsideAirTemperature);
        temperature = (OutsideAirTemperature) v;
        Assert.assertEquals(temperature.getTemperature(), 127);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x7f});
        Assert.assertEquals("127\u00B0C", temperature.getDisplayableValue());

        bytes = new byte[] {(byte) 0x54};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OutsideAirTemp, bytes);
        Assert.assertTrue(v instanceof OutsideAirTemperature);
        temperature = (OutsideAirTemperature) v;
        Assert.assertEquals(temperature.getTemperature(), 84);
        Assert.assertEquals(temperature.getBytes(), new byte[] {(byte) 0x54});
        Assert.assertEquals("84\u00B0C", temperature.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new OutsideAirTemperature((short) -129);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new OutsideAirTemperature((short) 128);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new OutsideAirTemperature(new byte[] {0x00, 0x00});
    }
}
