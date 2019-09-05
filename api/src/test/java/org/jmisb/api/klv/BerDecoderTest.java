package org.jmisb.api.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BerDecoderTest
{
    @Test
    public void testShortFormLengthField()
    {
        // BER Short Form is always encoded in a single byte, and has its high order bit set to 0
        byte[] data = {0x00, 0x05, 0x7f}; // 1, 5, 127
        BerField l1 = BerDecoder.decode(data, 0, false);
        BerField l2 = BerDecoder.decode(data, 1, false);
        BerField l3 = BerDecoder.decode(data, 2, false);

        Assert.assertEquals(l1.getValue(), 0);
        Assert.assertEquals(l2.getValue(), 5);
        Assert.assertEquals(l3.getValue(), 127);

        Assert.assertEquals(l1.getLength(), 1);
        Assert.assertEquals(l2.getLength(), 1);
        Assert.assertEquals(l3.getLength(), 1);
    }

    @Test
    public void testLongFormLengthField()
    {
        byte[] data = {(byte)0x81, 0x05, (byte)0x82, 0x01, (byte)0x80, (byte)0x84, 0x01, 0x01, 0x01, 0x01};

        BerField l1 = BerDecoder.decode(data, 0, false);
        BerField l2 = BerDecoder.decode(data, 2, false);
        BerField l3 = BerDecoder.decode(data, 5, false);

        Assert.assertEquals(l1.getValue(), 5);
        Assert.assertEquals(l2.getValue(), 384);
        Assert.assertEquals(l3.getValue(), 16_843_009);

        Assert.assertEquals(l1.getLength(), 2);
        Assert.assertEquals(l2.getLength(), 3);
        Assert.assertEquals(l3.getLength(), 5);
    }
}
