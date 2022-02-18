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
 *
 * <p>The typical case for this kind of conversion is IMAPB, which is used extensively in ST0601
 * among other standards. You create an encoder specifying the minimum value of the range, maximum
 * value of the range and number of bytes; then use that to encoding and decoding conversions
 * between the floating point representation and the byte array equivalent. An example is shown
 * here:
 *
 * <blockquote>
 *
 * <pre>
 *     FpEncoder encoder = new FpEncoder(0, 100.0, 3);
 *     byte[] firstEncodingResult = encoder.encode(3.14159);
 *     byte[] secondEncodingResult = encoder.encode(87.3);
 *     double decoded = encoder.decode(firstEncodingResult);
 * </pre>
 *
 * </blockquote>
 *
 * <p>It is also possible to construct an encoder that produces a specified level of accuracy rather
 * than needing to specify the number of bytes to use. This is known as IMAPA, and is used in MIMD,
 * but is otherwise uncommon.
 */
public class FpEncoder {

    private double a, b;
    private double sF, sR;
    private double zOffset;
    /**
     * The field length.
     *
     * <p>This mainly intended for unit testing.
     */
    protected int fieldLength;

    private static final double logOf2 = Math.log(2.0);
    private final OutOfRangeBehaviour behaviour;
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

    private static final byte POSITIVE_INFINITY_HIGH_BYTE = (byte) 0b11001000;
    private static final byte NEGATIVE_INFINITY_HIGH_BYTE = (byte) 0b11101000;
    private static final byte POSITIVE_QUIET_NAN_HIGH_BYTE = (byte) 0b11010000;
    private static final byte NEGATIVE_QUIET_NAN_HIGH_BYTE = (byte) 0b11110000;
    private static final byte POSITIVE_SIGNAL_NAN_HIGH_BYTE = (byte) 0b11011000;
    private static final byte NEGATIVE_SIGNAL_NAN_HIGH_BYTE = (byte) 0b11111000;
    private static final byte RESERVED_KIND1_HIGH_BYTE = (byte) 0b10000000;
    private static final byte MISB_DEFINED_HIGH_BYTE = (byte) 0b11100000;
    private static final byte IMAP_BELOW_MINIMUM = MISB_DEFINED_HIGH_BYTE | (byte) 0b000;
    private static final byte IMAP_ABOVE_MAXIMUM = MISB_DEFINED_HIGH_BYTE | (byte) 0b001;
    private static final byte USER_DEFINED_HIGH_BYTE = (byte) 0b11000000;
    private static final byte HIGH_BITS_MASK = (byte) 0b11111000;
    private static final byte LOW_BITS_MASK = (byte) 0b00000111;

    /**
     * Construct an encoder with the desired field length.
     *
     * <p>This is the "Starting Point B" (IMAPB) from ST1201.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes (1-8)
     * @throws IllegalArgumentException if the length is not supported
     * @deprecated use alternative constructor with specific out-of-range behavior.
     */
    @Deprecated
    public FpEncoder(double min, double max, int length) {
        this(min, max, length, OutOfRangeBehaviour.Throw);
    }

    /**
     * Construct an encoder with the desired field length.
     *
     * <p>This is the "Starting Point B" (IMAPB) from ST1201.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes (1-8)
     * @param outOfRangeBehavior behavior for out-of-range conditions (e.g. above {@code max}, or
     *     below {@code min})
     * @throws IllegalArgumentException if the length is not supported
     */
    public FpEncoder(double min, double max, int length, OutOfRangeBehaviour outOfRangeBehavior) {
        if (length < 1 || length > 8) {
            throw new IllegalArgumentException("Valid field length for FpEncoder is 1-8 bytes");
        } else {
            computeConstants(min, max, length);
            this.behaviour = outOfRangeBehavior;
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
     * @deprecated use alternative constructor with specific out-of-range behavior.
     */
    @Deprecated
    public FpEncoder(double min, double max, double precision) {
        this(min, max, precision, OutOfRangeBehaviour.Throw);
    }

    /**
     * Construct an encoder with the desired precision, automatically selecting field length.
     *
     * <p>This is the "Starting Point A" (IMAPA) from ST1201.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param precision The required precision
     * @param behavior behavior for out-of-range conditions (e.g. above {@code max}, or below {@code
     *     min})
     * @throws IllegalArgumentException if the range/precision is too large to represent within 64
     *     bits
     */
    public FpEncoder(double min, double max, double precision, OutOfRangeBehaviour behavior) {
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
        this.behaviour = behavior;
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
     * defined by ST1201. To send other special value bit patterns, use the {@link
     * #encodeSpecial(ValueMappingKind, long)} method.
     *
     * @param val The value to encode
     * @return The encoded byte array
     * @throws IllegalArgumentException if the field length is not valid, or the "Throw" behavior is
     *     specified and the value is not within the valid range
     */
    public byte[] encode(double val) {
        byte[] encoded;

        // Special values defined by the ST
        if (val == Double.POSITIVE_INFINITY) {
            encoded = new byte[fieldLength];
            encoded[0] = POSITIVE_INFINITY_HIGH_BYTE;
        } else if (val == Double.NEGATIVE_INFINITY) {
            encoded = new byte[fieldLength];
            encoded[0] = NEGATIVE_INFINITY_HIGH_BYTE;
        } else if (Double.isNaN(val)) {
            // Send +QNaN as defined by the ST; to send other NaN values defined by the ST, use
            // encodeSpecial method
            encoded = new byte[fieldLength];
            encoded[0] = POSITIVE_QUIET_NAN_HIGH_BYTE;
        } else if ((val < a || val > b) && (behaviour == OutOfRangeBehaviour.Throw)) {
            throw new IllegalArgumentException("Value must be in range [" + a + "," + b + "]");
        } else if (val < a) {
            encoded = new byte[fieldLength];
            encoded[0] = IMAP_BELOW_MINIMUM;
        } else if (val > b) {
            encoded = new byte[fieldLength];
            encoded[0] = IMAP_ABOVE_MAXIMUM;
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
                    throw new IllegalArgumentException("Only field lengths of [1-8] are supported");
            }
        }
        return encoded;
    }

    /**
     * Encode a special value as a byte array.
     *
     * <p>This method assumes that the "other bits" allowed by ST1201 are all zero.
     *
     * <p>If the encoded value would not fit into the required length, an exception will be thrown.
     *
     * @param kind The kind of value to encode
     * @return The encoded byte array, as specified in the constructor field length.
     * @throws IllegalArgumentException if value could not be encoded (e.g. it is too long)
     */
    public byte[] encodeSpecial(ValueMappingKind kind) {
        return encodeSpecial(kind, 0);
    }

    /**
     * Encode a special value as a byte array.
     *
     * <p>The exact behavior depends on the kind of value. If the {@code kind} is positive or
     * negative infinity, the {@code identifier} is ignored. For other types, the {@code identifier}
     * is combined with the {@code kind} as specified in ST1201 to build the encoded value.
     *
     * <p>Note: {@code identifier} is treated as an unsigned value.
     *
     * <p>If the encoded value would not fit into the required length, an exception will be thrown.
     * Values will not be sanity checked for the NormalMappedValue case.
     *
     * @param kind The kind of value to encode
     * @param identifier the special identifier to combine with the kind, if needed.
     * @return The encoded byte array, as specified in the constructor field length.
     * @throws IllegalArgumentException if value could not be encoded (e.g. it is too long)
     */
    public byte[] encodeSpecial(ValueMappingKind kind, long identifier) {
        byte[] encoded = new byte[fieldLength];
        Arrays.fill(encoded, (byte) 0x00);
        switch (kind) {
            case NormalMappedValue:
                fillBytes(encoded, identifier);
                // Mask off high bit - must be 0 for normal mapped value
                encoded[0] &= (byte) 0x7f;
                break;
            case ReservedKind1:
                fillBytes(encoded, identifier);
                setHighBitsReservedKind1(encoded);
                break;
            case PositiveInfinity:
                encoded[0] = POSITIVE_INFINITY_HIGH_BYTE;
                break;
            case NegativeInfinity:
                encoded[0] = NEGATIVE_INFINITY_HIGH_BYTE;
                break;
            case PositiveQuietNaN:
                fillBytes(encoded, identifier);
                setHighBits(encoded, POSITIVE_QUIET_NAN_HIGH_BYTE);
                break;
            case NegativeQuietNaN:
                fillBytes(encoded, identifier);
                setHighBits(encoded, NEGATIVE_QUIET_NAN_HIGH_BYTE);
                break;
            case PositiveSignalNaN:
                fillBytes(encoded, identifier);
                setHighBits(encoded, POSITIVE_SIGNAL_NAN_HIGH_BYTE);
                break;
            case NegativeSignalNaN:
                fillBytes(encoded, identifier);
                setHighBits(encoded, NEGATIVE_SIGNAL_NAN_HIGH_BYTE);
                break;
            case UserDefined:
                fillBytes(encoded, identifier);
                setHighBits(encoded, USER_DEFINED_HIGH_BYTE);
                break;
            default:
                fillBytes(encoded, identifier);
                setHighBits(encoded, MISB_DEFINED_HIGH_BYTE);
                break;
        }
        return encoded;
    }

    /**
     * Decode an encoded floating point value from a byte array.
     *
     * <p>Java {@code double} doesn't support all of the special value options that are
     * representable in ST1201. Only normal mapped values, and positive and negative infinity will
     * be mapped to the usual double type equivalent values. All other values will be mapped to NaN.
     * If you need to handle decoding of these special values, use {@link #decodeSpecial(byte[],
     * int)}.
     *
     * @param bytes The encoded array
     * @return The floating point value
     * @throws IllegalArgumentException if the array is invalid
     */
    public double decode(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != fieldLength) {
            throw new IllegalArgumentException("Array length does not match expected field length");
        }
        return decode(bytes, 0);
    }

    /**
     * Decode an encoded floating point value from a byte array starting at an offset.
     *
     * <p>Java {@code double} doesn't support all of the special value options that are
     * representable in ST1201. Only normal mapped values, and positive and negative infinity will
     * be mapped to the usual double type equivalent values. All other values will be mapped to NaN.
     * If you need to handle decoding of these special values, use {@link #decodeSpecial(byte[],
     * int)}.
     *
     * @param bytes The encoded array
     * @param offset the offset into the byte array to decode from
     * @return The floating point value
     * @throws IllegalArgumentException if the array is invalid
     */
    public double decode(byte[] bytes, int offset) throws IllegalArgumentException {
        if (offset + fieldLength > bytes.length) {
            throw new IllegalArgumentException(
                    "Array length does not match offset and required field length");
        }
        if ((bytes[offset] & 0x80) == 0x00) {
            return decodeAsNormalMappedValue(bytes, offset);
        } else if (bytes[offset] == (byte) 0x80) {
            boolean allZeros = true;
            for (int i = offset + 1; i < offset + fieldLength; i++) {
                if (bytes[i] != (byte) 0x00) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros) {
                return decodeAsNormalMappedValue(bytes, offset);
            }
        }
        byte highByte = bytes[offset];
        if (highByte == IMAP_BELOW_MINIMUM) {
            return a;
        }
        if (highByte == IMAP_ABOVE_MAXIMUM) {
            return b;
        }
        byte highByteHighBits = (byte) (highByte & HIGH_BITS_MASK);
        if (highByteHighBits == POSITIVE_INFINITY_HIGH_BYTE) {
            return Double.POSITIVE_INFINITY;
        } else if (highByteHighBits == NEGATIVE_INFINITY_HIGH_BYTE) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return Double.NaN;
        }
    }

    /**
     * Decode an encoded floating point value from a byte array starting at an offset.
     *
     * <p>This method supports all the special value encodings from ST1201. If you only need basic
     * values (normal mapped values, positive and negative infinity) and can tolerate everything
     * else being mapped to {@code Double.NaN}, then you can use {@link #decode(byte[], int)} to get
     * a double value back directly.
     *
     * @param bytes The encoded array
     * @param offset the offset into the byte array to decode from
     * @return the results of the decoding
     * @throws IllegalArgumentException if the array is invalid
     */
    public DecodeResult decodeSpecial(byte[] bytes, int offset) throws IllegalArgumentException {
        DecodeResult decodeResult = new DecodeResult();
        if ((bytes[offset] & 0x80) == 0x00) {
            decodeResult.setKind(ValueMappingKind.NormalMappedValue);
            decodeResult.setValue(decodeAsNormalMappedValue(bytes, offset));
            return decodeResult;
        } else if (bytes[offset] == (byte) 0x80) {
            boolean allZeros = true;
            for (int i = offset + 1; i < offset + fieldLength; i++) {
                if (bytes[i] != (byte) 0x00) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros) {
                decodeResult.setKind(ValueMappingKind.NormalMappedValue);
                decodeResult.setValue(decodeAsNormalMappedValue(bytes, offset));
                return decodeResult;
            } else {
                return parseAsReservedKind1(bytes, offset);
            }
        }
        if ((byte) ((bytes[offset] & (byte) 0b11000000)) == (byte) 0x80) {
            return parseAsReservedKind1(bytes, offset);
        }
        byte highByteHighBits = (byte) (bytes[offset] & HIGH_BITS_MASK);
        if (highByteHighBits == POSITIVE_INFINITY_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.PositiveInfinity);
            decodeResult.setValue(Double.POSITIVE_INFINITY);
            decodeResult.setIdentifier(0L);
        } else if (highByteHighBits == NEGATIVE_INFINITY_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.NegativeInfinity);
            decodeResult.setValue(Double.NEGATIVE_INFINITY);
            decodeResult.setIdentifier(0L);
        } else if (highByteHighBits == POSITIVE_QUIET_NAN_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.PositiveQuietNaN);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        } else if (highByteHighBits == NEGATIVE_QUIET_NAN_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.NegativeQuietNaN);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        } else if (highByteHighBits == POSITIVE_SIGNAL_NAN_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.PositiveSignalNaN);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        } else if (highByteHighBits == NEGATIVE_SIGNAL_NAN_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.NegativeSignalNaN);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        } else if (highByteHighBits == USER_DEFINED_HIGH_BYTE) {
            decodeResult.setKind(ValueMappingKind.UserDefined);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        } else {
            decodeResult.setKind(ValueMappingKind.MISBDefined);
            decodeResult.setIdentifier(getIdentifier(bytes, offset));
        }
        return decodeResult;
    }

    private DecodeResult parseAsReservedKind1(byte[] bytes, int offset) {
        DecodeResult decodeResult = new DecodeResult();
        byte[] copy = Arrays.copyOfRange(bytes, offset, fieldLength);
        copy[0] &= (byte) 0x7f;
        long identifier = PrimitiveConverter.variableBytesToUint64(copy);
        decodeResult.setKind(ValueMappingKind.ReservedKind1);
        decodeResult.setIdentifier(identifier);
        return decodeResult;
    }

    private long getIdentifier(byte[] bytes, int offset) {
        byte[] copy = Arrays.copyOfRange(bytes, offset, fieldLength);
        copy[0] &= LOW_BITS_MASK;
        return PrimitiveConverter.variableBytesToUint64(copy);
    }

    /**
     * Decode an encoded floating point value from a byte array.
     *
     * <p>This method supports all the special value encodings from ST1201. If you only need basic
     * values (normal mapped values, positive and negative infinity) and can tolerate everything
     * else being mapped to {@code Double.NaN}, then you can use {@link #decode(byte[])} to get a
     * double value back directly.
     *
     * @param bytes The encoded array
     * @return the results of the decoding
     * @throws IllegalArgumentException if the array is invalid
     */
    public DecodeResult decodeSpecial(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != fieldLength) {
            throw new IllegalArgumentException("Array length does not match expected field length");
        }
        return decodeSpecial(bytes, 0);
    }

    private double decodeAsNormalMappedValue(byte[] bytes, int offset)
            throws IllegalArgumentException {
        double val;
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
                long lx = (long) wrapped.getInt() & 0xFFFFFFFFL;
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
                throw new IllegalArgumentException("Only field lengths of [1-8] are supported");
        }
        return val;
    }

    /**
     * Compute constants used for encoding and decoding.
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes
     */
    protected final void computeConstants(double min, double max, int length) {
        fieldLength = length;
        a = min;
        b = max;
        double bPow = Math.ceil(log2(b - a));
        double dPow = 8 * fieldLength - 1;
        sF = Math.pow(2, dPow - bPow);
        sR = Math.pow(2, bPow - dPow);
        zOffset = 0.0;
        if (a < 0 && b > 0) {
            zOffset = sF * a - Math.floor(sF * a);
        }
    }

    /**
     * Base-2 logarithm.
     *
     * @param val Input value
     * @return Log value
     */
    private static double log2(double val) {
        return Math.log(val) / logOf2;
    }

    private void fillBytes(byte[] encoded, long identifier) {
        if (identifier == 0L) {
            // Very common case
            return;
        }
        byte[] identifierBytes = PrimitiveConverter.uintToVariableBytes(identifier);
        int offset = this.fieldLength - identifierBytes.length;
        if (offset < 0) {
            throw new IllegalArgumentException(
                    "Identifier is too large to fit into specified ST1201 field length");
        }
        System.arraycopy(identifierBytes, 0, encoded, offset, identifierBytes.length);
    }

    private void setHighBits(byte[] encoded, byte highByteBits) {
        byte highByteEncoded = encoded[0];
        highByteEncoded = (byte) ((highByteEncoded & 0b00000111) | (highByteBits & HIGH_BITS_MASK));
        encoded[0] = highByteEncoded;
    }

    private void setHighBitsReservedKind1(byte[] encoded) {
        byte highByteEncoded = encoded[0];
        highByteEncoded = (byte) ((highByteEncoded & 0b00111111) | RESERVED_KIND1_HIGH_BYTE);
        encoded[0] = highByteEncoded;
    }
}
