package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformFuelRemainingTest {
    @Test
    public void testMinMax() {
        PlatformFuelRemaining platformFuelRemaining = new PlatformFuelRemaining(0.0);
        byte[] bytes = platformFuelRemaining.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 0.0, 0.00001);
        Assert.assertEquals(platformFuelRemaining.getDisplayableValue(), "0.00kg");

        platformFuelRemaining = new PlatformFuelRemaining(10000.0);
        bytes = platformFuelRemaining.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 10000.0, 0.00001);
        Assert.assertEquals(platformFuelRemaining.getDisplayableValue(), "10000.00kg");

        bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        platformFuelRemaining = new PlatformFuelRemaining(bytes);
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 0.0);
        Assert.assertEquals(platformFuelRemaining.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        platformFuelRemaining = new PlatformFuelRemaining(bytes);
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 10000.0);
        Assert.assertEquals(platformFuelRemaining.getBytes(), bytes);

        Assert.assertEquals(platformFuelRemaining.getDisplayName(), "Platform Fuel Remaining");
    }

    @Test
    public void testExample() {
        double value = 6420.53864;
        PlatformFuelRemaining platformFuelRemaining = new PlatformFuelRemaining(value);
        byte[] bytes = platformFuelRemaining.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xa4, (byte) 0x5d});
        Assert.assertEquals(platformFuelRemaining.getKilograms(), value);
        Assert.assertEquals(platformFuelRemaining.getDisplayableValue(), "6420.54kg");

        bytes = new byte[] {(byte) 0xa4, (byte) 0x5d};
        platformFuelRemaining = new PlatformFuelRemaining(bytes);
        Assert.assertEquals(platformFuelRemaining.getKilograms(), value, 0.00001);
        Assert.assertEquals(platformFuelRemaining.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformFuelRemaining(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformFuelRemaining(10000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformFuelRemaining(new byte[] {0x00, 0x00, 0x00, 0x00});
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformFuelRemaining, bytes);
        Assert.assertTrue(v instanceof PlatformFuelRemaining);
        PlatformFuelRemaining platformFuelRemaining = (PlatformFuelRemaining) v;
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 0.0);
        Assert.assertEquals(platformFuelRemaining.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformFuelRemaining, bytes);
        Assert.assertTrue(v instanceof PlatformFuelRemaining);
        platformFuelRemaining = (PlatformFuelRemaining) v;
        Assert.assertEquals(platformFuelRemaining.getKilograms(), 10000.0);
        Assert.assertEquals(platformFuelRemaining.getBytes(), bytes);
    }
}
