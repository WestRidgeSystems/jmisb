package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetWidthExtendedTest {
    @Test
    public void testConstructFromValue() {
        // From ST:
        TargetWidthExtended width = new TargetWidthExtended(13898.5463);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0xD9, (byte) 0x2A});
        Assert.assertEquals(width.getDisplayableValue(), "13898.5m");
        Assert.assertEquals(width.getDisplayName(), "Target Width Extended");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST:
        TargetWidthExtended width =
                new TargetWidthExtended(new byte[] {(byte) 0x00, (byte) 0xD9, (byte) 0x2A});
        Assert.assertEquals(width.getMeters(), 13898.5463, 0.25);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0xD9, (byte) 0x2A});
        Assert.assertEquals(width.getDisplayableValue(), "13898.5m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0xD9, (byte) 0x2A};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.TargetWidthExtended, bytes);
        Assert.assertTrue(v instanceof TargetWidthExtended);
        TargetWidthExtended width = (TargetWidthExtended) v;
        Assert.assertEquals(width.getMeters(), 13898.5463, 0.25);
        Assert.assertEquals(width.getBytes(), new byte[] {(byte) 0x00, (byte) 0xD9, (byte) 0x2A});
        Assert.assertEquals(width.getDisplayableValue(), "13898.5m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetWidthExtended(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetWidthExtended(1500000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new TargetWidthExtended(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
