package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorControlModeTest {

    @Test
    public void testConstructFromValue() {
        // Min
        SensorControlMode mode = new SensorControlMode((byte) 0);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(mode.getDisplayableValue(), "Off");

        // Max
        mode = new SensorControlMode((byte) 6);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 6});
        Assert.assertEquals(mode.getDisplayableValue(), "Auto - Tracking");

        // Other values
        mode = new SensorControlMode((byte) 1);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(mode.getDisplayableValue(), "Home Position");
        mode = new SensorControlMode((byte) 2);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 2});
        Assert.assertEquals(mode.getDisplayableValue(), "Uncontrolled");
        mode = new SensorControlMode((byte) 3);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 3});
        Assert.assertEquals(mode.getDisplayableValue(), "Manual Control");
        mode = new SensorControlMode((byte) 4);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 4});
        Assert.assertEquals(mode.getDisplayableValue(), "Calibrating");
        mode = new SensorControlMode((byte) 5);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 5});
        Assert.assertEquals(mode.getDisplayableValue(), "Auto - Holding Position");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        SensorControlMode mode = new SensorControlMode(new byte[] {(byte) 0});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 0);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(mode.getDisplayableValue(), "Off");

        // Max
        mode = new SensorControlMode(new byte[] {(byte) 6});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 6);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x06});
        Assert.assertEquals(mode.getDisplayableValue(), "Auto - Tracking");

        // Other values
        mode = new SensorControlMode(new byte[] {(byte) 1});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 1);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(mode.getDisplayableValue(), "Home Position");
        mode = new SensorControlMode(new byte[] {(byte) 2});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 2);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(mode.getDisplayableValue(), "Uncontrolled");
        mode = new SensorControlMode(new byte[] {(byte) 3});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 3);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(mode.getDisplayableValue(), "Manual Control");
        mode = new SensorControlMode(new byte[] {(byte) 4});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 4);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x04});
        Assert.assertEquals(mode.getDisplayableValue(), "Calibrating");
        mode = new SensorControlMode(new byte[] {(byte) 5});
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 5);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x05});
        Assert.assertEquals(mode.getDisplayableValue(), "Auto - Holding Position");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorControlMode, bytes);
        Assert.assertTrue(v instanceof SensorControlMode);
        SensorControlMode mode = (SensorControlMode) v;
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 0);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(mode.getDisplayableValue(), "Off");

        bytes = new byte[] {(byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorControlMode, bytes);
        Assert.assertTrue(v instanceof SensorControlMode);
        mode = (SensorControlMode) v;
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 1);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(mode.getDisplayableValue(), "Home Position");

        bytes = new byte[] {(byte) 0x02};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorControlMode, bytes);
        Assert.assertTrue(v instanceof SensorControlMode);
        mode = (SensorControlMode) v;
        Assert.assertEquals(mode.getSensorControlMode(), (byte) 2);
        Assert.assertEquals(mode.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(mode.getDisplayableValue(), "Uncontrolled");
        Assert.assertEquals(mode.getDisplayName(), "Sensor Control Mode");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorControlMode((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorControlMode((byte) 7);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorControlMode(new byte[] {0x00, 0x00});
    }
}
