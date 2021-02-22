package org.jmisb.api.klv.st1201;

import org.testng.Assert;
import org.testng.annotations.*;

public class FpEncoderTest {
    @Test
    public void testIntegerLength() {
        // 100 values, should be encoded in 1 byte
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 0.1);
        Assert.assertEquals(fpEncoder.getFieldLength(), 1);

        // 1000 values, should require 2 bytes
        FpEncoder fpEncoder2 = new FpEncoder(0.0, 100.0, 0.1);
        Assert.assertEquals(fpEncoder2.getFieldLength(), 2);

        // 2 billion values, should require 4 bytes
        FpEncoder fpEncoder4 = new FpEncoder(0.0, 2000000000.0, 1.0);
        Assert.assertEquals(fpEncoder4.getFieldLength(), 4);

        // Provide explicit integer length
        FpEncoder fpEncoder8 = new FpEncoder(0.0, 15000.0, 8);
        Assert.assertEquals(fpEncoder8.getFieldLength(), 8);
    }

    @Test
    public void testIntegerLength8() {
        // >4 billion values, should require 8 bytes
        FpEncoder fpEncoder4plus = new FpEncoder(0.0, 4100000000.0, 1.0);
        Assert.assertEquals(fpEncoder4plus.getFieldLength(), 8);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidLength() {
        FpEncoder invalid = new FpEncoder(0.0, 10.0, 27);
        invalid.encode(3.14159);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidPrecision() {
        FpEncoder invalid = new FpEncoder(0.0, 1e9, 1e-30);
        invalid.encode(3.14159);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRange() {
        FpEncoder encoder = new FpEncoder(-100.0, 100.0, 4);
        encoder.encode(-500.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfRangeOver() {
        FpEncoder encoder = new FpEncoder(-100.0, 100.0, 4);
        encoder.encode(100.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLengthJustTooSmall() {
        new FpEncoder(-100.0, 100.0, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLengthJustTooLarge() {
        new FpEncoder(-100.0, 100.0, 9);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMismatchedLength() {
        FpEncoder encoder = new FpEncoder(-100.0, +100.0, 2);
        byte[] array = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        encoder.decode(array);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMismatchedLengthDecodeSpecial() {
        FpEncoder encoder = new FpEncoder(-100.0, +100.0, 3);
        byte[] array = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
        encoder.decodeSpecial(array);
    }

    @Test
    public void testEncode() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        // Test a normal floating point number, pi
        double value = 3.14159;
        byte[] expected = {0x00, 0x00, 0x00, 0x06, 0x48, 0x7e, 0x7c, 0x06};
        byte[] encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded.length, 8);
        Assert.assertEquals(encoded, expected);

        // Test +infinity
        value = Double.POSITIVE_INFINITY;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte) 0xc8);

        // Test -infinity
        value = Double.NEGATIVE_INFINITY;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte) 0xe8);

        // Test NaN
        value = Double.NaN;
        encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded[0], (byte) 0xd0);
    }

    @Test
    public void testEncodeLen1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 1);
        double value = 6.0;
        byte[] expected = {0x30};
        byte[] encoded = fpEncoder.encode(value);
        Assert.assertEquals(encoded.length, 1);
        Assert.assertEquals(encoded, expected);
    }

    @Test
    public void testDecodeLen1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 10.0, 1);
        double expected = 6.0;
        byte[] value = {0x30};
        double decoded = fpEncoder.decode(value);
        Assert.assertEquals(decoded, expected);
    }

    @Test
    public void testDecode() {
        // Test a normal floating point number, pi
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);
        byte[] encoded = {0x00, 0x00, 0x00, 0x06, 0x48, 0x7e, 0x7c, 0x06};
        double val = fpEncoder.decode(encoded);
        Assert.assertEquals(val, 3.14159, 1e-8);

        // Test +infinity
        byte[] posInf = {
            (byte) 0xc8,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00
        };
        val = fpEncoder.decode(posInf);
        Assert.assertEquals(val, Double.POSITIVE_INFINITY);

        // Test -infinity
        byte[] negInf = {
            (byte) 0xe8,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00
        };
        val = fpEncoder.decode(negInf);
        Assert.assertEquals(val, Double.NEGATIVE_INFINITY);

        // Test NaN
        byte[] nan = {
            (byte) 0xd0,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00
        };
        val = fpEncoder.decode(nan);
        Assert.assertTrue(Double.isNaN(val));
    }

    @Test
    public void testEncodeSpecial1BytePositiveInfinity() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 100, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveInfinity, 0);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xc8});
    }

    @Test
    public void testEncodeSpecial1ByteNegativeInfinity() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 100, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeInfinity, 0);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xe8});
    }

    @Test
    public void testEncodeSpecial8BytesPositiveInfinity() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveInfinity, 0);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xc8,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testEncodeSpecial8BytesNegativeInfinity() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeInfinity, 0);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xe8,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testEncodeSpecial1BytePositiveInfinityDefault() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 100, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveInfinity);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xc8});
    }

    @Test
    public void testEncodeSpecial1ByteNegativeInfinityDefault() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 100, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeInfinity);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xe8});
    }

    @Test
    public void testEncodeSpecial8BytesPositiveInfinityDefault() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveInfinity);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xc8,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testEncodeSpecial8BytesNegativeInfinityDefault() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeInfinity);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xe8,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testEncodeSpecial8BytesPositiveQNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PostiveQuietNaN);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xd0,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void testEncodeSpecial8BytesPositiveQNaN2() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PostiveQuietNaN, 2);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xd0,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x02
                });
    }

    @Test
    public void testEncodeSpecial1BytePositiveQNaN2() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PostiveQuietNaN, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xd2});
    }

    @Test
    public void testEncodeSpecial3BytesNegativeQNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 3);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeQuietNaN);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void testEncodeSpecial8BytesNegativeQNaN2() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 8);

        byte[] encoded =
                fpEncoder.encodeSpecial(ValueMappingKind.NegativeQuietNaN, 0x01000000000001L);
        Assert.assertEquals(
                encoded,
                new byte[] {
                    (byte) 0xf0,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01
                });
    }

    @Test
    public void testEncodeSpecial1ByteNegativeQNaN4() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeQuietNaN, 4);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xf4});
    }

    @Test
    public void testEncodeSpecial1BytePositiveSNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveSignalNaN, 0);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xd8});
    }

    @Test
    public void testEncodeSpecial2BytePositiveSNaN256() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.PositiveSignalNaN, 256);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xd9, (byte) 0x00});
    }

    @Test
    public void testEncodeSpecial1ByteNegativeSNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeSignalNaN, 0);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xf8});
    }

    @Test
    public void testEncodeSpecial1ByteReserved1Value1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.ReservedKind1, 1);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x81});
        DecodeResult decodeResult = fpEncoder.decodeSpecial(encoded);
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.ReservedKind1);
        Assert.assertEquals(decodeResult.getIdentifier(), 1);
    }

    @Test
    public void testEncodeSpecial2ByteReserved1Value1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.ReservedKind1, 1);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x80, (byte) 0x01});
        DecodeResult decodeResult = fpEncoder.decodeSpecial(encoded);
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.ReservedKind1);
        Assert.assertEquals(decodeResult.getIdentifier(), 1);
        double decode = fpEncoder.decode(encoded);
        Assert.assertEquals(decode, Double.NaN);
    }

    @Test
    public void testEncodeSpecial1ByteReserved2Value1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.ReservedKind2, 1);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xe1});
    }

    @Test
    public void testEncodeSpecial1ByteUserDefinedValue3() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.UserDefined, 3);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xc3});
    }

    @Test
    public void testEncodeSpecial1ByteNormalMappedValue3() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NormalMappedValue, 3);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x03});
    }

    @Test
    public void testEncodeSpecial2ByteNormalMappedValue65535() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NormalMappedValue, 65535);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x7f, (byte) 0xff});
    }

    @Test
    public void testEncodeSpecial3ByteNormalMappedValue65535() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 3);

        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NormalMappedValue, 65535);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x00, (byte) 0xff, (byte) 0xff});
    }

    @Test
    public void testDecodeSpecial1BytePositiveSNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xd8});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.PositiveSignalNaN);
        Assert.assertEquals(decodeResult.getIdentifier(), 0L);
    }

    @Test
    public void testDecodeSpecial2BytePositiveSNaN256() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xd9, (byte) 0x00});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.PositiveSignalNaN);
        Assert.assertEquals(decodeResult.getIdentifier(), 256);
    }

    @Test
    public void testDecodeSpecial1ByteNegativeSNaN0() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xf8});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.NegativeSignalNaN);
        Assert.assertEquals(decodeResult.getIdentifier(), 0L);
    }

    @Test
    public void testDecodeSpecial2ByteNegativeSNaN255() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xf8, (byte) 0xff});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.NegativeSignalNaN);
        Assert.assertEquals(decodeResult.getIdentifier(), 255);
    }

    @Test
    public void testDecodeSpecial2ByteNegativeSNaN257() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 2);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xf9, (byte) 0x01});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.NegativeSignalNaN);
        Assert.assertEquals(decodeResult.getIdentifier(), 257);
    }

    @Test
    public void testDecodeSpecial1ByteReserved2Value1() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xe1});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.ReservedKind2);
        Assert.assertEquals(decodeResult.getIdentifier(), 1);
    }

    @Test
    public void testDecodeSpecial1ByteUserDefinedValue3() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 1);

        DecodeResult decodeResult = fpEncoder.decodeSpecial(new byte[] {(byte) 0xc3});
        Assert.assertEquals(decodeResult.getKind(), ValueMappingKind.UserDefined);
        Assert.assertEquals(decodeResult.getIdentifier(), 3);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEncodeTooLong() {
        FpEncoder fpEncoder = new FpEncoder(0.0, 1e9, 3);
        byte[] encoded = fpEncoder.encodeSpecial(ValueMappingKind.NegativeQuietNaN, 1 << 24);
    }

    class TestEncoder extends FpEncoder {
        public TestEncoder() {
            super(0.0, 1e9, 2);
        }

        public void setFieldLength(int fieldLength) {
            this.fieldLength = fieldLength;
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLongEncode() {
        TestEncoder fpEncoder = new TestEncoder();
        fpEncoder.setFieldLength(9);
        fpEncoder.encode(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooShortEncode() {
        TestEncoder fpEncoder = new TestEncoder();
        fpEncoder.setFieldLength(0);
        fpEncoder.encode(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooShortDecode() {
        TestEncoder fpEncoder = new TestEncoder();
        fpEncoder.setFieldLength(0);
        fpEncoder.decode(new byte[] {0x00}, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLongDecode() {
        TestEncoder fpEncoder = new TestEncoder();
        fpEncoder.setFieldLength(2);
        fpEncoder.decode(new byte[] {0x01, 0x02}, 1);
    }
}
