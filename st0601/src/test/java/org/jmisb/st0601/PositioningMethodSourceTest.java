package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositioningMethodSourceTest {
    @Test
    public void testConstructFromValue() {
        // Min
        PositioningMethodSource source = new PositioningMethodSource(1);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x01});

        // Max
        source = new PositioningMethodSource(255);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0xff});

        // From ST:
        source = new PositioningMethodSource(3);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(
                source.getPositioningSource(),
                PositioningMethodSource.GPS | PositioningMethodSource.INS);

        // Use constants
        source = new PositioningMethodSource(PositioningMethodSource.INS);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.INS);
        source = new PositioningMethodSource(PositioningMethodSource.GPS);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.GPS);
        source = new PositioningMethodSource(PositioningMethodSource.GALILEO);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.GALILEO);
        source = new PositioningMethodSource(PositioningMethodSource.QZSS);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.QZSS);
        source = new PositioningMethodSource(PositioningMethodSource.NAVIC);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.NAVIC);
        source = new PositioningMethodSource(PositioningMethodSource.GLONASS);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.GLONASS);
        source = new PositioningMethodSource(PositioningMethodSource.BEIDOU1);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.BEIDOU1);
        source = new PositioningMethodSource(PositioningMethodSource.BEIDOU2);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.BEIDOU2);

        Assert.assertEquals(source.getDisplayName(), "Positioning Method Source");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        PositioningMethodSource source = new PositioningMethodSource(new byte[] {(byte) 0x01});
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.INS);
        Assert.assertEquals(source.getPositioningSource(), 1);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(source.getDisplayableValue(), "INS [1]");

        // Max
        source = new PositioningMethodSource(new byte[] {(byte) 0xff});
        Assert.assertEquals(source.getPositioningSource(), 255);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(source.getDisplayableValue(), "Mixed/INS [255]");

        // From ST:
        source = new PositioningMethodSource(new byte[] {(byte) 0x03});
        Assert.assertEquals(source.getPositioningSource(), 3);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(source.getDisplayableValue(), "GPS/INS [3]");

        // No INS:
        source = new PositioningMethodSource(new byte[] {(byte) 0x02});
        Assert.assertEquals(source.getPositioningSource(), 2);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.GPS);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(source.getDisplayableValue(), "GPS [2]");

        // Other Satellite
        source = new PositioningMethodSource(new byte[] {(byte) 0x08});
        Assert.assertEquals(source.getPositioningSource(), 8);
        Assert.assertEquals(source.getPositioningSource(), PositioningMethodSource.QZSS);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x08});
        Assert.assertEquals(source.getDisplayableValue(), "Satellite [8]");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PositioningMethodSource, bytes);
        Assert.assertTrue(v instanceof PositioningMethodSource);
        PositioningMethodSource source = (PositioningMethodSource) v;
        Assert.assertEquals(source.getPositioningSource(), 1);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(source.getDisplayableValue(), "INS [1]");

        bytes = new byte[] {(byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PositioningMethodSource, bytes);
        Assert.assertTrue(v instanceof PositioningMethodSource);
        source = (PositioningMethodSource) v;
        Assert.assertEquals(source.getPositioningSource(), 255);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0xff});
        Assert.assertEquals(source.getDisplayableValue(), "Mixed/INS [255]");

        bytes = new byte[] {(byte) 0x03};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PositioningMethodSource, bytes);
        Assert.assertTrue(v instanceof PositioningMethodSource);
        source = (PositioningMethodSource) v;
        Assert.assertEquals(source.getPositioningSource(), 3);
        Assert.assertEquals(source.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(source.getDisplayableValue(), "GPS/INS [3]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PositioningMethodSource(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PositioningMethodSource(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PositioningMethodSource(new byte[] {0x00, 0x00});
    }
}
