package org.jmisb.api.klv.st1201;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Encoding and decoding of floating point values per ST 1201.
 *
 * <p>This requires out-of-band specification of how floating point values are mapped into integer
 * values (encoded as a byte array). That out-of-band specification is provided in the calling
 * document.
 */
public class FpEncoder {

    private double a, b;
    private double bPow, dPow;
    private double sF, sR;
    private double zOffset;
    private int fieldLength;
    private static double logOf2 = Math.log(2.0);
    private static final byte[] EIGHT_BYTE_HIGH_BIT =
            new byte[] {
                (byte) 0x80,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00
            };

    /**
     * Construct an encoder with the desired field length.
     *
     * <p>This is the "Starting Point B" (IMAPB) from ST1201.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes (1-8)
     * @throws IllegalArgumentException if the length is not supported
     */
    public FpEncoder(double min, double max, int length) {
        if (length < 1 || length > 8) {
            throw new IllegalArgumentException("Valid field length for FpEncoder is 1-8 bytes");
        } else {
            computeConstants(min, max, length);
        }
    }

    /**
     * Construct an encoder with the desired precision, automatically selecting field length.
     *
     * <p>This is the "Starting Point A" (IMAPA) from ST1201.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param precision The required precision
     * @throws IllegalArgumentException if the range/precision is too large to represent within 64
     *     bits
     */
    public FpEncoder(double min, double max, double precision) {
        double bits = Math.ceil(log2((max - min) / precision) + 1);
        int length = (int) Math.ceil(bits / 8);

        if (length <= 2) {
            computeConstants(min, max, length);
        } else if (length <= 4) {
            computeConstants(min, max, 4);
        } else if (length <= 8) {
            computeConstants(min, max, 8);
        } else {
            throw new IllegalArgumentException(
                    "The specified range and precision cannot be represented using a 64-bit integer");
        }
    }

    /**
     * Get the length of the encoded byte array.
     *
     * @return The length, in bytes
     */
    public int getFieldLength() {
        return fieldLength;
    }

    /**
     * Encode a floating point value as a byte array.
     *
     * <p>Note: Positive and negative infinity and NaN will be encoded by setting special flags
     * defined by ST1204. To send other special value bit patterns, use the encodeSpecial method.
     *
     * @param val The value to encode
     * @return The encoded byte array
     * @throws IllegalArgumentException if the value is not within the specified range
     */
    public byte[] encode(double val) {
        byte[] encoded = null;

        // Special values defined by the ST
        if (val == Double.POSITIVE_INFINITY) {
            encoded = new byte[fieldLength];
            encoded[0] = (byte) 0xc8;
        } else if (val == Double.NEGATIVE_INFINITY) {
            encoded = new byte[fieldLength];
            encoded[0] = (byte) 0xe8;
        } else if (Double.isNaN(val)) {
            // Send +QNaN as defined by the ST; to send other NaN values defined by the ST, use
            // encodeSpecial method
            encoded = new byte[fieldLength];
            encoded[0] = (byte) 0xd0;
        } else if (val < a || val > b) {
            throw new IllegalArgumentException("Value must be in range [" + a + "," + b + "]");
        } else {
            // Value is normal and in range
            double d = Math.floor(sF * (val - a) + zOffset);
            switch (fieldLength) {
                case 1:
                    byte byt = (byte) d;
                    encoded = ByteBuffer.allocate(1).put(byt).array();
                    break;
                case 2:
                    short s = (short) d;
                    encoded = ByteBuffer.allocate(2).putShort(s).array();
                    break;
                case 3:
                    int i3 = (int) d;
                    byte[] bytes = PrimitiveConverter.int32ToBytes(i3);
                    ByteBuffer bb = ByteBuffer.allocate(3);
                    for (int lv = 0; lv < bb.capacity(); ++lv) {
                        bb.put(lv, bytes[lv + 1]);
                    }
                    encoded = bb.array();
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    byte[] longDecoded = ByteBuffer.allocate(8).putLong((long) d).array();
                    ByteBuffer bbl = ByteBuffer.allocate(fieldLength);
                    for (int lv = 0; lv < bbl.capacity(); ++lv) {
                        int bufferIndex = lv + 8 - fieldLength;
                        bbl.put(lv, longDecoded[bufferIndex]);
                    }
                    encoded = bbl.array();
                    break;
                case 8:
                    if (d >= Long.MAX_VALUE) {
                        // Workaround for lack of unsigned long
                        encoded = EIGHT_BYTE_HIGH_BIT;
                    } else {
                        encoded = ByteBuffer.allocate(8).putLong((long) d).array();
                    }
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Only field lengths of [1-8] are supported");
            }
        }
        return encoded;
    }

    /**
     * Decode an encoded floating point value from a byte array
     *
     * @param bytes The encoded array
     * @return The floating point value
     * @throws IllegalArgumentException if the array is invalid
     */
    public double decode(byte[] bytes) throws IllegalArgumentException {
        int offset = 0;

        if (bytes.length != fieldLength) {
            throw new IllegalArgumentException("Array length does not match expected field length");
        }
        return decode(bytes, offset);
    }

    /**
     * Decode an encoded floating point value from a byte array with offset
     *
     * @param bytes The encoded array
     * @param offset the offset into the byte array to decode from
     * @return The floating point value
     * @throws IllegalArgumentException if the array is invalid
     */
    public double decode(byte[] bytes, int offset) throws IllegalArgumentException {
        double val = 0.0;
        if (bytes[offset] == (byte) 0xc8) {
            val = Double.POSITIVE_INFINITY;
        } else if (bytes[offset] == (byte) 0xe8) {
            val = Double.NEGATIVE_INFINITY;
        } else if (bytes[offset] == (byte) 0xd0) {
            val = Double.NaN;
        } else if (bytes[offset] == (byte) 0xf0) {
            // TODO: handle properly
            val = Double.NaN;
        } else {
            // Normal floating point value
            ByteBuffer wrapped = ByteBuffer.wrap(bytes, offset, fieldLength);
            switch (fieldLength) {
                case 1:
                    int b1 = wrapped.get() & 0xFF;
                    val = sR * (b1 - zOffset) + a;
                    break;
                case 2:
                    int s = (int) wrapped.getShort() & 0xFFFF;
                    val = sR * (s - zOffset) + a;
                    break;
                case 3:
                    int i3 = (int) wrapped.getShort() & 0xFFFF;
                    byte lowByte = wrapped.get(offset + fieldLength - 1);
                    i3 = (i3 << 8) + (lowByte & 0xFF);
                    val = sR * (i3 - zOffset) + a;
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    long lx = (long) wrapped.getInt() & 0xFFFFFFFFl;
                    for (int j = 0; j < (fieldLength - Integer.BYTES); ++j) {
                        byte byt = wrapped.get(offset + Integer.BYTES + j);
                        lx = (lx << 8) + (byt & 0xFF);
                    }
                    val = sR * (lx - zOffset) + a;
                    break;
                case 8:
                    if (Arrays.equals(bytes, EIGHT_BYTE_HIGH_BIT)) {
                        val = b;
                    } else {
                        long l = wrapped.getLong();
                        val = sR * (l - zOffset) + a;
                    }
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Only field lengths of [1-8] are supported");
            }
        }

        return val;
    }

    /**
     * Compute constants used for encoding and decoding
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes
     */
    private void computeConstants(double min, double max, int length) {
        fieldLength = length;
        a = min;
        b = max;
        bPow = Math.ceil(log2(b - a));
        dPow = 8 * fieldLength - 1;
        sF = Math.pow(2, dPow - bPow);
        sR = Math.pow(2, bPow - dPow);
        zOffset = 0.0;
        if (a < 0 && b > 0) {
            zOffset = sF * a - Math.floor(sF * a);
        }
    }

    /**
     * Base-2 logarithm
     *
     * @param val Input value
     * @return Log value
     */
    private static double log2(double val) {
        return Math.log(val) / logOf2;
    }
}
