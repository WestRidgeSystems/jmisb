package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetTrackGateSizeTest {
    @Test
    public void testConstructFromValueWidth() {
        // Min
        TargetTrackGateWidth size = new TargetTrackGateWidth((short) 0);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x00});

        // Max
        size = new TargetTrackGateWidth((short) 510);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        size = new TargetTrackGateWidth((short) 6);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x03});
        size = new TargetTrackGateWidth((short) 30);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x0F});
    }

    @Test
    public void testConstructFromValueHeight() {
        // Min
        TargetTrackGateHeight size = new TargetTrackGateHeight((short) 0);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x00});

        // Max
        size = new TargetTrackGateHeight((short) 510);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        size = new TargetTrackGateHeight((short) 6);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x03});
        size = new TargetTrackGateHeight((short) 30);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x0F});
    }

    @Test
    public void testConstructFromEncodedWidth() {
        // Min
        TargetTrackGateWidth size = new TargetTrackGateWidth(new byte[] {(byte) 0x00});
        Assert.assertEquals(size.getPixels(), 0);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(size.getDisplayableValue(), "0 px");
        Assert.assertEquals(size.getDisplayName(), "Target Track Gate Width");

        // Max
        size = new TargetTrackGateWidth(new byte[] {(byte) 0xff});
        Assert.assertEquals(size.getPixels(), 510);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(size.getDisplayableValue(), "510 px");
        Assert.assertEquals(size.getDisplayName(), "Target Track Gate Width");

        // From ST:
        size = new TargetTrackGateWidth(new byte[] {(byte) 0x03});
        Assert.assertEquals(size.getPixels(), 6);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals("6 px", size.getDisplayableValue());
        size = new TargetTrackGateWidth(new byte[] {(byte) 0x0F});
        Assert.assertEquals(size.getPixels(), 30);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x0f});
        Assert.assertEquals("30 px", size.getDisplayableValue());
    }

    @Test
    public void testConstructFromEncodedHeight() {
        // Min
        TargetTrackGateHeight size = new TargetTrackGateHeight(new byte[] {(byte) 0x00});
        Assert.assertEquals(size.getPixels(), 0);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(size.getDisplayableValue(), "0 px");
        Assert.assertEquals(size.getDisplayName(), "Target Track Gate Height");

        // Max
        size = new TargetTrackGateHeight(new byte[] {(byte) 0xff});
        Assert.assertEquals(size.getPixels(), 510);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(size.getDisplayableValue(), "510 px");
        Assert.assertEquals(size.getDisplayName(), "Target Track Gate Height");

        // From ST:
        size = new TargetTrackGateHeight(new byte[] {(byte) 0x03});
        Assert.assertEquals(size.getPixels(), 6);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals("6 px", size.getDisplayableValue());
        size = new TargetTrackGateHeight(new byte[] {(byte) 0x0F});
        Assert.assertEquals(size.getPixels(), 30);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x0f});
        Assert.assertEquals("30 px", size.getDisplayableValue());
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.TargetTrackGateWidth, bytes);
        Assert.assertTrue(v instanceof TargetTrackGateWidth);
        TargetTrackGateSize size = (TargetTrackGateWidth) v;
        Assert.assertEquals(size.getPixels(), 0);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals("0 px", size.getDisplayableValue());

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetTrackGateHeight, bytes);
        Assert.assertTrue(v instanceof TargetTrackGateHeight);
        size = (TargetTrackGateHeight) v;
        Assert.assertEquals(size.getPixels(), 510);
        Assert.assertEquals(size.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals("510 px", size.getDisplayableValue());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetTrackGateWidth((short) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetTrackGateHeight((short) 511);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetTrackGateWidth(new byte[] {0x00, 0x00});
    }
}
