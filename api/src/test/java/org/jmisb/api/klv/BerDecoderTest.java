package org.jmisb.api.klv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BerDecoderTest {
    @Test
    public void testShortFormLengthField() {
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
    public void testShortFormLengthFieldInputStream() throws IOException {
        // BER Short Form is always encoded in a single byte, and has its high order bit set to 0
        byte[] data = {0x00, 0x05, 0x7f}; // 1, 5, 127
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BerField l1 = BerDecoder.decode(bais, false);
        BerField l2 = BerDecoder.decode(bais, false);
        BerField l3 = BerDecoder.decode(bais, false);

        Assert.assertEquals(l1.getValue(), 0);
        Assert.assertEquals(l2.getValue(), 5);
        Assert.assertEquals(l3.getValue(), 127);

        Assert.assertEquals(l1.getLength(), 1);
        Assert.assertEquals(l2.getLength(), 1);
        Assert.assertEquals(l3.getLength(), 1);
    }

    @Test
    public void testLongFormLengthField() {
        byte[] data = {
            (byte) 0x81, 0x05, (byte) 0x82, 0x01, (byte) 0x80, (byte) 0x84, 0x01, 0x01, 0x01, 0x01
        };

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

    @Test
    public void testLongFormLengthFieldInputStream() throws IOException {
        byte[] data = {
            (byte) 0x81, 0x05, (byte) 0x82, 0x01, (byte) 0x80, (byte) 0x84, 0x01, 0x01, 0x01, 0x01
        };

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BerField l1 = BerDecoder.decode(bais, false);
        BerField l2 = BerDecoder.decode(bais, false);
        BerField l3 = BerDecoder.decode(bais, false);

        Assert.assertEquals(l1.getValue(), 5);
        Assert.assertEquals(l2.getValue(), 384);
        Assert.assertEquals(l3.getValue(), 16_843_009);

        Assert.assertEquals(l1.getLength(), 2);
        Assert.assertEquals(l2.getLength(), 3);
        Assert.assertEquals(l3.getLength(), 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrun() {
        byte[] data = {0x00, 0x05, 0x7f};
        BerField l1 = BerDecoder.decode(data, 3, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrunGreater() {
        byte[] data = {0x00, 0x05, 0x7f};
        BerField l1 = BerDecoder.decode(data, 4, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrunOid() {
        byte[] data = {0x00, 0x05, 0x7f};
        BerField l1 = BerDecoder.decode(data, 3, true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrunLongform() {
        byte[] data = {0x00, 0x05, (byte) 0x81};
        BerField l1 = BerDecoder.decode(data, 2, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrunLongform5() {
        byte[] data = {(byte) 0x85, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        BerField l1 = BerDecoder.decode(data, 0, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseIndexOverrunOid() {
        byte[] data = {(byte) 0x80};
        BerField l1 = BerDecoder.decode(data, 0, true);
    }
}
