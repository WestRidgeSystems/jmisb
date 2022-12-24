package org.jmisb.api.klv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "BER parsing ran out of bytes")
    public void testLongFormLengthFieldInputStreamOverrun() throws IOException {
        byte[] data = {(byte) 0x84, 0x01, 0x02, 0x03};
        InputStream is = new ByteArrayInputStream(data);
        BerDecoder.decode(is, false);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp =
                    "BER long form: BER length is >4 bytes; data is probably corrupt")
    public void testLongFormLengthFieldInputStreamTooLong() throws IOException {
        byte[] data = {(byte) 0x85, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x00};
        InputStream is = new ByteArrayInputStream(data);
        BerDecoder.decode(is, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBufferOverrun() {
        byte[] data = {0x00, 0x05, 0x7f};
        BerField l1 = BerDecoder.decode(data, 3, false);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Cannot read BER from beyond array limit")
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

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp =
                    "BER long form: BER length is >4 bytes; data is probably corrupt")
    public void testParseBufferOverrunLongform5() {
        byte[] data = {(byte) 0x85, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        BerField l1 = BerDecoder.decode(data, 0, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseIndexOverrunOid() {
        byte[] data = {(byte) 0x80};
        BerField l1 = BerDecoder.decode(data, 0, true);
    }

    @Test
    public void testBerOidDecode1() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x01}, 0, true);
        Assert.assertEquals(berField.getLength(), 1);
        Assert.assertEquals(berField.getValue(), 1);
    }

    @Test
    public void testBerOidDecode127() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x7f}, 0, true);
        Assert.assertEquals(berField.getLength(), 1);
        Assert.assertEquals(berField.getValue(), 127);
    }

    @Test
    public void testBerOidDecode128() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x81, (byte) 0x00}, 0, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 128);
    }

    @Test
    public void testBerOidDecode129() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x81, (byte) 0x01}, 0, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 129);
    }

    @Test
    public void testBerOidDecode255() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x81, (byte) 0x7f}, 0, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 255);
    }

    @Test
    public void testBerOidDecode256() {
        BerField berField = BerDecoder.decode(new byte[] {(byte) 0x82, (byte) 0x00}, 0, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 256);
    }

    @Test
    public void testBerOidDecode65535() {
        BerField berField =
                BerDecoder.decode(new byte[] {(byte) 0x83, (byte) 0xff, (byte) 0x7f}, 0, true);
        Assert.assertEquals(berField.getLength(), 3);
        Assert.assertEquals(berField.getValue(), 65535);
    }

    @Test
    public void testBerOidDecode256Offset() {
        BerField berField =
                BerDecoder.decode(new byte[] {(byte) 0x80, (byte) 0x82, (byte) 0x00}, 1, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 256);
    }

    @Test
    public void testBerOidDecode1InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x01});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 1);
        Assert.assertEquals(berField.getValue(), 1);
    }

    @Test
    public void testBerOidDecode127InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x7f});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 1);
        Assert.assertEquals(berField.getValue(), 127);
    }

    @Test
    public void testBerOidDecode128InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x81, (byte) 0x00});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 128);
    }

    @Test
    public void testBerOidDecode129InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x81, (byte) 0x01});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 129);
    }

    @Test
    public void testBerOidDecode255InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x81, (byte) 0x7f});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 255);
    }

    @Test
    public void testBerOidDecode256InputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(new byte[] {(byte) 0x82, (byte) 0x00});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 2);
        Assert.assertEquals(berField.getValue(), 256);
    }

    @Test
    public void testBerOidDecode65535InputStream() throws IOException {
        InputStream is =
                new ByteArrayInputStream(new byte[] {(byte) 0x83, (byte) 0xff, (byte) 0x7f});
        BerField berField = BerDecoder.decode(is, true);
        Assert.assertEquals(berField.getLength(), 3);
        Assert.assertEquals(berField.getValue(), 65535);
    }
}
