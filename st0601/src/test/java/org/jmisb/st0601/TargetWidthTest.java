package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetWidthTest {
    @Test
    public void testConstructFromValue() {
        // Min
        TargetWidth width = new TargetWidth(0.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(width.getDisplayableValue(), "0.00m");

        // Max
        width = new TargetWidth(10000.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(width.getDisplayableValue(), "10000.00m");

        // From ST:
        width = new TargetWidth(722.8199);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x12, (byte) 0x81});
        Assert.assertEquals(width.getDisplayableValue(), "722.82m");

        Assert.assertEquals(width.getDisplayName(), "Target Width");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        TargetWidth width = new TargetWidth(new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(width.getMeters(), 0.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(width.getDisplayableValue(), "0.00m");

        // Max
        width = new TargetWidth(new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(width.getMeters(), 10000.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(width.getDisplayableValue(), "10000.00m");

        // From ST:
        width = new TargetWidth(new byte[] {(byte) 0x12, (byte) 0x81});
        Assert.assertEquals(width.getMeters(), 722.8199, TargetWidth.DELTA);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x12, (byte) 0x81});
        Assert.assertEquals(width.getDisplayableValue(), "722.82m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetWidth, bytes);
        Assert.assertTrue(v instanceof TargetWidth);
        TargetWidth width = (TargetWidth) v;
        Assert.assertEquals(width.getMeters(), 0.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(width.getDisplayableValue(), "0.00m");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetWidth, bytes);
        Assert.assertTrue(v instanceof TargetWidth);
        width = (TargetWidth) v;
        Assert.assertEquals(width.getMeters(), 10000.0);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(width.getDisplayableValue(), "10000.00m");

        bytes = new byte[] {(byte) 0x12, (byte) 0x81};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetWidth, bytes);
        Assert.assertTrue(v instanceof TargetWidth);
        width = (TargetWidth) v;
        Assert.assertEquals(width.getMeters(), 722.8199, TargetWidth.DELTA);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x12, (byte) 0x81});
        Assert.assertEquals(width.getDisplayableValue(), "722.82m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetWidth(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetWidth(10000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetWidth(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
