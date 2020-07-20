package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorAngleRateTest {
    @Test
    public void testConstructFromValueItem117() {
        // From ST
        SensorAngleRate rate = new SensorAzimuthRate(1);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x90, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "1.000 dps");
        Assert.assertEquals(rate.getDisplayName(), "Sensor Azimuth Rate");
    }

    @Test
    public void testConstructFromEncodedItem117() {
        // From ST
        SensorAngleRate rate = new SensorAzimuthRate(new byte[] {(byte) 0x3E, (byte) 0x90});
        Assert.assertEquals(rate.getAngleRate(), 1.0, 0.01);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x90, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "1.000 dps");
    }

    @Test
    public void testFactoryItem117() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x3E, (byte) 0x90};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorAzimuthRate, bytes);
        Assert.assertTrue(v instanceof SensorAzimuthRate);
        SensorAzimuthRate rate = (SensorAzimuthRate) v;
        Assert.assertEquals(rate.getAngleRate(), 1.0, 0.001);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x90, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "1.000 dps");
    }

    @Test
    public void testConstructFromValueItem118() {
        // From ST
        SensorAngleRate rate = new SensorElevationRate(0.004176);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x80, (byte) 0x11});
        Assert.assertEquals(rate.getDisplayableValue(), "0.004 dps");
        Assert.assertEquals(rate.getDisplayName(), "Sensor Elevation Rate");
    }

    @Test
    public void testConstructFromEncodedItem118() {
        // From ST:
        SensorAngleRate rate =
                new SensorElevationRate(new byte[] {(byte) 0x3E, (byte) 0x80, (byte) 0x11});
        Assert.assertEquals(rate.getAngleRate(), 0.004176, 0.001);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x80, (byte) 0x11});
        Assert.assertEquals(rate.getDisplayableValue(), "0.004 dps");
    }

    @Test
    public void testFactoryItem118() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x3E, (byte) 0x80, (byte) 0x11};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorElevationRate, bytes);
        Assert.assertTrue(v instanceof SensorElevationRate);
        SensorElevationRate rate = (SensorElevationRate) v;
        Assert.assertEquals(rate.getAngleRate(), 0.004176, 0.001);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3E, (byte) 0x80, (byte) 0x11});
        Assert.assertEquals(rate.getDisplayableValue(), "0.004 dps");
    }

    @Test
    public void testConstructFromValueItem119() {
        // From ST
        SensorAngleRate rate = new SensorRollRate(-50);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3B, (byte) 0x60, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "-50.000 dps");
        Assert.assertEquals(rate.getDisplayName(), "Sensor Roll Rate");
    }

    @Test
    public void testConstructFromEncodedItem119() {
        // From ST:
        SensorAngleRate rate = new SensorRollRate(new byte[] {(byte) 0x3B, (byte) 0x60});
        Assert.assertEquals(rate.getAngleRate(), -50.0, 0.001);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3B, (byte) 0x60, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "-50.000 dps");
    }

    @Test
    public void testFactoryItem119() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x3B, (byte) 0x60};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SensorRollRate, bytes);
        Assert.assertTrue(v instanceof SensorRollRate);
        SensorRollRate rate = (SensorRollRate) v;
        Assert.assertEquals(rate.getAngleRate(), -50.0, 0.001);
        Assert.assertEquals(rate.getBytes(), new byte[] {(byte) 0x3B, (byte) 0x60, (byte) 0x00});
        Assert.assertEquals(rate.getDisplayableValue(), "-50.000 dps");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SensorAzimuthRate(-1000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SensorElevationRate(1000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new SensorRollRate(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
