package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RangeToRecoveryLocationTest {
    @Test
    public void testConstructFromValue() {
        // From ST:
        RangeToRecoveryLocation range = new RangeToRecoveryLocation(1.625);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xa0});
        Assert.assertEquals(range.getDisplayableValue(), "1.625km");
        Assert.assertEquals(range.getDisplayName(), "Range to Recovery Location");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST:
        RangeToRecoveryLocation range =
                new RangeToRecoveryLocation(new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xa0});
        Assert.assertEquals(range.getRange(), 1.625, 0.01);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xa0});
        Assert.assertEquals(range.getDisplayableValue(), "1.625km");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xa0};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.RangeToRecoveryLocation, bytes);
        Assert.assertTrue(v instanceof RangeToRecoveryLocation);
        RangeToRecoveryLocation range = (RangeToRecoveryLocation) v;
        Assert.assertEquals(range.getRange(), 1.625, 0.01);
        Assert.assertEquals(range.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xa0});
        Assert.assertEquals(range.getDisplayableValue(), "1.625km");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RangeToRecoveryLocation(-0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RangeToRecoveryLocation(21000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new RangeToRecoveryLocation(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
