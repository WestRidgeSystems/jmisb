package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroundRangeTest {
    @Test
    public void testConstructFromValue() {
        // Min
        GroundRange range = new GroundRange(0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");

        // Max
        range = new GroundRange(5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");

        // From ST:
        range = new GroundRange(3506979.0316063400);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xB3, (byte) 0x8E, (byte) 0xAC, (byte) 0xF1});
        Assert.assertEquals(range.getDisplayableValue(), "3506979.032m");

        Assert.assertEquals(range.getDisplayName(), "Ground Range");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        GroundRange range =
                new GroundRange(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getMeters(), 0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");
        Assert.assertEquals(range.getDisplayName(), "Ground Range");

        // Max
        range = new GroundRange(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getMeters(), 5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");
        Assert.assertEquals(range.getDisplayName(), "Ground Range");

        // From ST:
        range = new GroundRange(new byte[] {(byte) 0xB3, (byte) 0x8E, (byte) 0xAC, (byte) 0xF1});
        Assert.assertEquals(range.getMeters(), 3506979.0316063400, GroundRange.DELTA);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xB3, (byte) 0x8E, (byte) 0xAC, (byte) 0xF1});
        Assert.assertEquals(range.getDisplayableValue(), "3506979.032m");
        Assert.assertEquals(range.getDisplayName(), "Ground Range");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.GroundRange, bytes);
        Assert.assertTrue(v instanceof GroundRange);
        GroundRange range = (GroundRange) v;
        Assert.assertEquals(range.getMeters(), 0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");
        Assert.assertEquals(range.getDisplayName(), "Ground Range");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.GroundRange, bytes);
        Assert.assertTrue(v instanceof GroundRange);
        range = (GroundRange) v;
        Assert.assertEquals(range.getMeters(), 5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");

        bytes = new byte[] {(byte) 0xB3, (byte) 0x8E, (byte) 0xAC, (byte) 0xF1};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.GroundRange, bytes);
        Assert.assertTrue(v instanceof GroundRange);
        range = (GroundRange) v;
        Assert.assertEquals(range.getMeters(), 3506979.0316063400, GroundRange.DELTA);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xB3, (byte) 0x8E, (byte) 0xAC, (byte) 0xF1});
        Assert.assertEquals(range.getDisplayableValue(), "3506979.032m");
        Assert.assertEquals(range.getDisplayName(), "Ground Range");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new GroundRange(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new GroundRange(5000000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new GroundRange(new byte[] {0x00, 0x00, 0x01});
    }
}
