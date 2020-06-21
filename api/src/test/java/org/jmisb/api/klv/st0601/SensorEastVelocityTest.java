package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorEastVelocityTest {
    @Test
    public void testMinMax() {
        SensorEastVelocity velocity = new SensorEastVelocity(-327.0);
        byte[] bytes = velocity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x80, (byte) 0x01}); // -32767 as int16
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals("-327.00m/s", velocity.getDisplayableValue());

        velocity = new SensorEastVelocity(327.0);
        bytes = velocity.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x7f, (byte) 0xff}); // 32767 as int16
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals("327.00m/s", velocity.getDisplayableValue());

        bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        velocity = new SensorEastVelocity(bytes);
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        velocity = new SensorEastVelocity(bytes);
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        Assert.assertEquals(velocity.getDisplayName(), "Sensor East Velocity");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x80, (byte) 0x01}; // -32767 as int16
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorEastVelocity, bytes);
        Assert.assertTrue(v instanceof SensorEastVelocity);
        SensorEastVelocity velocity = (SensorEastVelocity) v;
        Assert.assertEquals(velocity.getVelocity(), -327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x7f, (byte) 0xff}; // 32767 as int16
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorEastVelocity, bytes);
        Assert.assertTrue(v instanceof SensorEastVelocity);
        velocity = (SensorEastVelocity) v;
        Assert.assertEquals(velocity.getVelocity(), 327.0);
        Assert.assertEquals(velocity.getBytes(), bytes);
    }

    @Test
    public void testZero() {
        SensorEastVelocity platformPitchAngle = new SensorEastVelocity(0.0);
        byte[] bytes = platformPitchAngle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformPitchAngle.getVelocity(), 0.0);
        Assert.assertEquals("0.00m/s", platformPitchAngle.getDisplayableValue());
    }

    @Test
    public void testExample() {
        double velocity = 12.1;
        SensorEastVelocity v = new SensorEastVelocity(velocity);
        byte[] bytes = v.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x04, (byte) 0xbc});
        Assert.assertEquals(v.getVelocity(), velocity, 0.01);
        Assert.assertEquals("12.10m/s", v.getDisplayableValue());

        bytes = new byte[] {(byte) 0x04, (byte) 0xbc};
        v = new SensorEastVelocity(bytes);
        Assert.assertEquals(v.getVelocity(), velocity, 0.01);
        Assert.assertEquals(v.getBytes(), bytes);
    }

    @Test
    public void testOutOfRange() {
        byte[] error = new byte[] {(byte) 0x80, (byte) 0x00};

        SensorEastVelocity velocity = new SensorEastVelocity(Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getVelocity(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getBytes(), error);

        velocity = new SensorEastVelocity(error);
        Assert.assertEquals(velocity.getVelocity(), Double.POSITIVE_INFINITY);
        Assert.assertEquals(velocity.getBytes(), error);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorEastVelocity(-327.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorEastVelocity(327.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SensorEastVelocity(new byte[] {0x00, 0x00, 0x00});
    }
}
