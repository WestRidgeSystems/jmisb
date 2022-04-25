package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SlantRangeTest {
    @Test
    public void testConstructFromValue() {
        // Min
        SlantRange range = new SlantRange(0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");

        // Max
        range = new SlantRange(5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");

        // From ST:
        range = new SlantRange(68590.983298744770);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x03, (byte) 0x83, (byte) 0x09, (byte) 0x26});
        Assert.assertEquals(range.getDisplayableValue(), "68590.983m");

        Assert.assertEquals(range.getDisplayName(), "Slant Range");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        SlantRange range =
                new SlantRange(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getMeters(), 0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");

        // Max
        range = new SlantRange(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getMeters(), 5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");

        // From ST:
        range = new SlantRange(new byte[] {(byte) 0x03, (byte) 0x83, (byte) 0x09, (byte) 0x26});
        Assert.assertEquals(range.getMeters(), 68590.983298744770, SlantRange.DELTA);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x03, (byte) 0x83, (byte) 0x09, (byte) 0x26});
        Assert.assertEquals(range.getDisplayableValue(), "68590.983m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.SlantRange, bytes);
        Assert.assertTrue(v instanceof SlantRange);
        SlantRange range = (SlantRange) v;
        Assert.assertEquals(range.getMeters(), 0.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        Assert.assertEquals(range.getDisplayableValue(), "0.000m");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SlantRange, bytes);
        Assert.assertTrue(v instanceof SlantRange);
        range = (SlantRange) v;
        Assert.assertEquals(range.getMeters(), 5000000.0);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});
        Assert.assertEquals(range.getDisplayableValue(), "5000000.000m");

        bytes = new byte[] {(byte) 0x03, (byte) 0x83, (byte) 0x09, (byte) 0x26};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.SlantRange, bytes);
        Assert.assertTrue(v instanceof SlantRange);
        range = (SlantRange) v;
        Assert.assertEquals(range.getMeters(), 68590.983298744770, SlantRange.DELTA);
        Assert.assertEquals(
                range.getBytes(), new byte[] {(byte) 0x03, (byte) 0x83, (byte) 0x09, (byte) 0x26});
        Assert.assertEquals(range.getDisplayableValue(), "68590.983m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new SlantRange(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new SlantRange(5000000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new SlantRange(new byte[] {0x00, 0x00});
    }
}
