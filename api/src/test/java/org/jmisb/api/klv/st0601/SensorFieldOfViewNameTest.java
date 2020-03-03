package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorFieldOfViewNameTest {

    @Test
    public void testConstructFromValue() {
        // Min
        SensorFieldOfViewName mode = new SensorFieldOfViewName((byte) 0);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0});
        Assert.assertEquals(mode.getDisplayableValue(), "Ultranarrow");

        // Max
        mode = new SensorFieldOfViewName((byte) 8);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 8});
        Assert.assertEquals(mode.getDisplayableValue(), "Continuous Zoom");

        // Other values
        mode = new SensorFieldOfViewName((byte) 1);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 1});
        Assert.assertEquals(mode.getDisplayableValue(), "Narrow");
        mode = new SensorFieldOfViewName((byte) 2);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 2});
        Assert.assertEquals(mode.getDisplayableValue(), "Medium");
        mode = new SensorFieldOfViewName((byte) 3);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 3});
        Assert.assertEquals(mode.getDisplayableValue(), "Wide");
        mode = new SensorFieldOfViewName((byte) 4);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 4});
        Assert.assertEquals(mode.getDisplayableValue(), "Ultrawide");
        mode = new SensorFieldOfViewName((byte) 5);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 5});
        Assert.assertEquals(mode.getDisplayableValue(), "Narrow Medium");
        mode = new SensorFieldOfViewName((byte) 6);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 6});
        Assert.assertEquals(mode.getDisplayableValue(), "2x Ultranarrow");
        mode = new SensorFieldOfViewName((byte) 7);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 7});
        Assert.assertEquals(mode.getDisplayableValue(), "4x Ultranarrow");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        SensorFieldOfViewName mode = new SensorFieldOfViewName(new byte[]{(byte) 0});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 0);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(mode.getDisplayableValue(), "Ultranarrow");

        // Max
        mode = new SensorFieldOfViewName(new byte[]{(byte) 8});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 8);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x08});
        Assert.assertEquals(mode.getDisplayableValue(), "Continuous Zoom");

        // Other values
        mode = new SensorFieldOfViewName(new byte[]{(byte) 1});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 1);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(mode.getDisplayableValue(), "Narrow");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 2});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 2);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(mode.getDisplayableValue(), "Medium");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 3});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 3);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x03});
        Assert.assertEquals(mode.getDisplayableValue(), "Wide");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 4});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 4);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x04});
        Assert.assertEquals(mode.getDisplayableValue(), "Ultrawide");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 5});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 5);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x05});
        Assert.assertEquals(mode.getDisplayableValue(), "Narrow Medium");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 6});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 6);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x06});
        Assert.assertEquals(mode.getDisplayableValue(), "2x Ultranarrow");
        mode = new SensorFieldOfViewName(new byte[]{(byte) 7});
        Assert.assertEquals(mode.getSensorFieldOfViewName(), (byte) 7);
        Assert.assertEquals(mode.getBytes(), new byte[]{(byte) 0x07});
        Assert.assertEquals(mode.getDisplayableValue(), "4x Ultranarrow");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorFovName, bytes);
        Assert.assertTrue(v instanceof SensorFieldOfViewName);
        SensorFieldOfViewName operationalMode = (SensorFieldOfViewName) v;
        Assert.assertEquals(operationalMode.getSensorFieldOfViewName(), (byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Ultranarrow");

        bytes = new byte[]{(byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorFovName, bytes);
        Assert.assertTrue(v instanceof SensorFieldOfViewName);
        operationalMode = (SensorFieldOfViewName) v;
        Assert.assertEquals(operationalMode.getSensorFieldOfViewName(), (byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Narrow");

        bytes = new byte[]{(byte) 0x02};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorFovName, bytes);
        Assert.assertTrue(v instanceof SensorFieldOfViewName);
        operationalMode = (SensorFieldOfViewName) v;
        Assert.assertEquals(operationalMode.getSensorFieldOfViewName(), (byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Medium");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorFieldOfViewName((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorFieldOfViewName((byte) 9);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorFieldOfViewName(new byte[]{0x00, 0x00});
    }
}
