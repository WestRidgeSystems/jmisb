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
        LengthField l1 = BerDecoder.decodeLengthField(data, 0, false);
        LengthField l2 = BerDecoder.decodeLengthField(data, 1, false);
        LengthField l3 = BerDecoder.decodeLengthField(data, 2, false);

        Assert.assertEquals(l1.getSizeOfValue(), 0);
        Assert.assertEquals(l2.getSizeOfValue(), 5);
        Assert.assertEquals(l3.getSizeOfValue(), 127);

        Assert.assertEquals(l1.getSizeOfLength(), 1);
        Assert.assertEquals(l2.getSizeOfLength(), 1);
        Assert.assertEquals(l3.getSizeOfLength(), 1);
    }

    @Test
    public void testLongFormLengthField()
    {
        byte[] data = {(byte)0x81, 0x05, (byte)0x82, 0x01, (byte)0x80, (byte)0x84, 0x01, 0x01, 0x01, 0x01};

        LengthField l1 = BerDecoder.decodeLengthField(data, 0, false);
        LengthField l2 = BerDecoder.decodeLengthField(data, 2, false);
        LengthField l3 = BerDecoder.decodeLengthField(data, 5, false);

        Assert.assertEquals(l1.getSizeOfValue(), 5);
        Assert.assertEquals(l2.getSizeOfValue(), 384);
        Assert.assertEquals(l3.getSizeOfValue(), 16_843_009);

        Assert.assertEquals(l1.getSizeOfLength(), 2);
        Assert.assertEquals(l2.getSizeOfLength(), 3);
        Assert.assertEquals(l3.getSizeOfLength(), 5);
    }
}
