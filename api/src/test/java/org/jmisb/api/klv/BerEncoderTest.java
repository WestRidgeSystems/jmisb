package org.jmisb.api.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by dan on 6/5/17.
 */
public class BerEncoderTest
{
    @Test
    public void testShortFormLengthField()
    {
        byte[] bytes = BerEncoder.encodeLengthField(5, Ber.SHORT_FORM);
        Assert.assertEquals(bytes, new byte[]{(byte)0x05});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testShortFormNegative()
    {
        BerEncoder.encodeLengthField(-5, Ber.SHORT_FORM);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testShortFormOverrun()
    {
        BerEncoder.encodeLengthField(180, Ber.SHORT_FORM);
    }

    @Test
    public void testLongFormLengthField()
    {
        byte[] bytes = BerEncoder.encodeLengthField(5, Ber.LONG_FORM);
        Assert.assertEquals(bytes, new byte[]{(byte)0x81, (byte)0x05});

        // 1,000 needs 2 bytes, and should be 0x03 0xe8
        bytes = BerEncoder.encodeLengthField(1000, Ber.LONG_FORM);
        Assert.assertEquals(bytes, new byte[]{(byte)0x82, (byte)0x03, (byte)0xe8});

        // 100,000 needs 3 bytes, and should be 0x01 0x86 0xa0
        bytes = BerEncoder.encodeLengthField(100_000, Ber.LONG_FORM);
        Assert.assertEquals(bytes, new byte[]{(byte)0x83, (byte)0x01, (byte)0x86, (byte)0xa0});

        // 100,000,000 needs 4 bytes, and should be 0x05 0xf5 0xe1 0x00
        bytes = BerEncoder.encodeLengthField(100_000_000, Ber.LONG_FORM);
        Assert.assertEquals(bytes, new byte[]{(byte)0x84, (byte)0x05, (byte)0xf5, (byte)0xe1, (byte)0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testLongFormNegative()
    {
        BerEncoder.encodeLengthField(-5, Ber.LONG_FORM);
    }
}
