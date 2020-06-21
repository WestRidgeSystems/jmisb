package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorNorthVelocityTest {
    @Test
    public void testMinMax() {
        SensorNorthVelocity velocity = new SensorNorthVelocity(-327.0);
        byte[] bytes = velocity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01}); // -32767 as int16
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals("-327.00m/s", velocity.getDisplayableValue());

        velocity = new SensorNorthVelocity(327.0);
        bytes = velocity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff}); // 32767 as int16
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals("327.00m/s", velocity.getDisplayableValue());

        bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        velocity = new SensorNorthVelocity(bytes);
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        velocity = new SensorNorthVelocity(bytes);
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        Assert.assertEquals(velocity.getDisplayName(), "Sensor North Velocity");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorNorthVelocity, bytes);
        Assert.assertTrue(v instanceof SensorNorthVelocity);
        SensorNorthVelocity velocity = (SensorNorthVelocity) v;
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorNorthVelocity, bytes);
        Assert.assertTrue(v instanceof SensorNorthVelocity);
        velocity = (SensorNorthVelocity) v;
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);
    }

    @Test
    public void testZero() {
        SensorNorthVelocity platformPitchAngle = new SensorNorthVelocity(0.0);
        byte[] bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformPitchAngle.getVelocity(), 0.0);
        Assert.assertEquals("0.00m/s", platformPitchAngle.getDisplayableValue());
    }

    @Test
    public void testExample() {
        double velocity = 25.4977569;
        SensorNorthVelocity v = new SensorNorthVelocity(velocity);
        byte[] bytes = v.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x09, (byte) 0xfb});
        Assert.assertEquals(v.getVelocity(), velocity);
        Assert.assertEquals("25.50m/s", v.getDisplayableValue());

        bytes = new byte[] {(byte) 0x09, (byte) 0xfb};
        v = new SensorNorthVelocity(bytes);
        Assert.assertEquals(v.getVelocity(), velocity, 0.0001);
        Assert.assertEquals(v.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange() {
        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00};

        SensorNorthVelocity velocity = new SensorNorthVelocity(Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getVelocity(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getBytes(), error);

        velocity = new SensorNorthVelocity(error);
        Assert.assertEquals(velocity.getVelocity(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getBytes(), error);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorNorthVelocity(-327.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorNorthVelocity(327.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorNorthVelocity(new byte[] {0x00, 0x00, 0x00});
    }
}
