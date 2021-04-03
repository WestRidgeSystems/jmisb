package org.jmisb.core.klv;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

/** Utility methods to convert primitive types to/from byte arrays */
public class PrimitiveConverter {
    // These temporary buffers are wrapped in thread local variables to ensure thread safety. An
    // alternative
    // would be to make clients create their own instances of this class. May want to change to use
    // that if it
    // turns out ThreadLocal.get() is expensive.

    private static final ThreadLocal<ByteBuffer> shortBuffer =
            ThreadLocal.withInitial(() -> ByteBuffer.allocate(Short.BYTES));
    private static final ThreadLocal<ByteBuffer> intBuffer =
            ThreadLocal.withInitial(() -> ByteBuffer.allocate(Integer.BYTES));
    private static final ThreadLocal<ByteBuffer> longBuffer =
            ThreadLocal.withInitial(() -> ByteBuffer.allocate(Long.BYTES));
    private static final ThreadLocal<ByteBuffer> floatBuffer =
            ThreadLocal.withInitial(() -> ByteBuffer.allocate(Float.BYTES));
    private static final ThreadLocal<ByteBuffer> doubleBuffer =
            ThreadLocal.withInitial(() -> ByteBuffer.allocate(Double.BYTES));

    static long arrayToUnsignedLongInternal(byte[] bytes) {
        long res = 0;
        for (byte b : bytes) {
            int i = b & 0xFF;
            res = (res << 8) + i;
        }
        return res;
    }

    static long arrayToUnsignedLongInternal(byte[] bytes, int offset, int length) {
        long res = 0;
        for (int idx = offset; idx < offset + length; idx++) {
            int i = bytes[idx] & 0xFF;
            res = (res << 8) + i;
        }
        return res;
    }

    static long arrayToSignedLongInternal(byte[] bytes, int offset, int length) {
        // There is a BigInteger constructor to do this without copying, but only in JDK 9+
        byte[] slice = Arrays.copyOfRange(bytes, offset, length);
        return new BigInteger(slice).longValue();
    }

    /**
     * Convert an unsigned 32-bit unsigned integer (long with range of uint32) to a byte array.
     *
     * <p>This is similar to uint32ToBytes, except that it only uses the minimum required number of
     * bytes to represent the value. So if the value will fit into two bytes, the results will be
     * only two bytes.
     *
     * @param longValue The unsigned 32-bit integer as long
     * @return The array of length 1-4 bytes.
     */
    public static byte[] uint32ToVariableBytes(long longValue) {
        if (longValue > 65535) {
            byte[] bytes = PrimitiveConverter.uint32ToBytes(longValue);
            if (bytes[0] == 0x00) {
                return new byte[] {bytes[1], bytes[2], bytes[3]};
            }
            return bytes;
        } else if (longValue > 255) {
            return PrimitiveConverter.uint16ToBytes((int) longValue);
        } else {
            return PrimitiveConverter.uint8ToBytes((short) longValue);
        }
    }

    /**
     * Convert an unsigned 6 byte unsigned integer (long with range of uint48) to a byte array.
     *
     * <p>This only uses the minimum required number of bytes to represent the value. So if the
     * value will fit into two bytes, the results will be only two bytes.
     *
     * @param longValue The unsigned integer as long
     * @return The array of length 1-6 bytes.
     */
    public static byte[] uintToVariableBytesV6(long longValue) {
        if (longValue > 1099511627775L) {
            byte[] bytes = PrimitiveConverter.int64ToBytes(longValue);
            return new byte[] {bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]};
        } else if (longValue > 4294967295L) {
            byte[] bytes = PrimitiveConverter.int64ToBytes(longValue);
            return new byte[] {bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]};
        } else {
            return uint32ToVariableBytes(longValue);
        }
    }

    /**
     * Convert an unsigned 8 byte unsigned integer (long with range of uint64) to a byte array.
     *
     * <p>This only uses the minimum required number of bytes to represent the value. So if the
     * value will fit into two bytes, the results will be only two bytes.
     *
     * @param longValue The unsigned integer as long
     * @return The array of length 1-8 bytes.
     */
    public static byte[] uintToVariableBytes(long longValue) {
        if (Long.compareUnsigned(longValue, 72057594037927935L) > 0) {
            return PrimitiveConverter.int64ToBytes(longValue);
        } else if (Long.compareUnsigned(longValue, 281474976710655L) > 0) {
            byte[] bytes = PrimitiveConverter.int64ToBytes(longValue);
            return new byte[] {
                bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]
            };
        } else {
            return uintToVariableBytesV6(longValue);
        }
    }

    private PrimitiveConverter() {}

    /**
     * Convert a byte array to a signed 32-bit integer
     *
     * @param bytes The array of length 1 - 4
     * @return The signed 32-bit integer
     */
    public static int toInt32(byte[] bytes) {
        if (bytes.length == 4) {
            return ByteBuffer.wrap(bytes).getInt();
        } else if (bytes.length == 3) {
            int res = ByteBuffer.wrap(bytes, 0, 2).getShort();
            return (res << 8) + (bytes[2] & 0xFF);
        } else if (bytes.length == 2) {
            return ByteBuffer.wrap(bytes).getShort();
        } else if (bytes.length == 1) {
            return ByteBuffer.wrap(bytes).get();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert part of a byte array to a signed 32-bit integer
     *
     * @param bytes The array
     * @param offset the offset into the array where the conversion should start
     * @return The signed 32-bit integer
     */
    public static int toInt32(byte[] bytes, int offset) {
        if (offset + Integer.BYTES <= bytes.length) {
            return ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
        }
        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert a byte array to a signed 16-bit integer
     *
     * @param bytes The array of length 2
     * @return The signed 16-bit integer
     */
    public static short toInt16(byte[] bytes) {
        if (bytes.length == 2) {
            return ByteBuffer.wrap(bytes).getShort();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert part of a byte array to an signed 16-bit integer
     *
     * @param bytes The array
     * @param offset the offset into the array where the conversion should start
     * @return The signed 16-bit integer
     */
    public static short toInt16(byte[] bytes, int offset) {
        if (offset + Short.BYTES <= bytes.length) {
            return ByteBuffer.wrap(bytes, offset, Short.BYTES).getShort();
        }

        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert a signed 16-bit integer to a byte array
     *
     * @param val The short value (16-byte signed integer)
     * @return The array of length 2
     */
    public static byte[] int16ToBytes(short val) {
        shortBuffer.get().putShort(0, val);
        return shortBuffer.get().array().clone();
    }

    /**
     * Convert a signed 32-bit integer to a byte array
     *
     * @param val The int value (32-byte signed integer)
     * @return The array of length 4
     */
    public static byte[] int32ToBytes(int val) {
        intBuffer.get().putInt(0, val);
        return intBuffer.get().array().clone();
    }

    /**
     * Convert an signed 32-bit integer to a byte array.
     *
     * <p>This is similar to int32ToBytes, except that it only uses the minimum required number of
     * bytes to represent the value. So if the value will fit into two bytes, the results will be
     * only two bytes.
     *
     * @param intValue The signed 32-bit integer
     * @return The array of length 1-4 bytes.
     */
    public static byte[] int32ToVariableBytes(int intValue) {
        if ((intValue > 32767) || (intValue < -32768)) {
            return PrimitiveConverter.int32ToBytes(intValue);
        } else if (((short) intValue > 127) || ((short) intValue < -128)) {
            return PrimitiveConverter.int16ToBytes((short) intValue);
        } else {
            return new byte[] {(byte) intValue};
        }
    }

    /**
     * Convert a byte array to an unsigned 24-bit integer (int with range of uint24)
     *
     * @param bytes The array of length 3
     * @return The unsigned 24-bit integer as an int
     */
    public static int toUint24(byte[] bytes) {
        if (bytes.length == 3) {
            int res = 0;
            for (byte b : bytes) {
                int i = b & 0xFF;
                res = (res << 8) + i;
            }
            return res;
        }
        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert an unsigned 24-bit value to byte array.
     *
     * @param intValue The integer value (24-byte unsigned integer)
     * @return The array of length 3
     */
    public static byte[] uint24ToBytes(int intValue) {
        if (intValue < 0 || intValue > 16777215) {
            throw new IllegalArgumentException("Value out of range");
        }
        byte[] bytes = PrimitiveConverter.uint32ToBytes(intValue);
        return new byte[] {bytes[1], bytes[2], bytes[3]};
    }

    /**
     * Convert a byte array to an unsigned 32-bit integer (long with range of uint32)
     *
     * @param bytes The array of length 4
     * @return The unsigned 32-bit integer as a long
     */
    public static long toUint32(byte[] bytes) {
        if (bytes.length == 4) {
            return Integer.toUnsignedLong(ByteBuffer.wrap(bytes).getInt());
        }
        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert a byte array to an unsigned 32-bit integer (long with range of uint32)
     *
     * @param bytes The array
     * @param offset the offset to convert at
     * @return The unsigned 32-bit integer as a long
     */
    public static long toUint32(byte[] bytes, int offset) {
        return Integer.toUnsignedLong(ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt());
    }

    /**
     * Convert a variable length byte array to an unsigned 32-bit integer (long with range of
     * uint32)
     *
     * @param bytes The array of length 1-4
     * @return The unsigned 32-bit integer as a long
     */
    public static long variableBytesToUint32(byte[] bytes) {
        switch (bytes.length) {
            case 4:
                return PrimitiveConverter.toUint32(bytes);
            case 3:
                return PrimitiveConverter.arrayToUnsignedLongInternal(bytes);
            case 2:
                return PrimitiveConverter.toUint16(bytes);
            case 1:
                return PrimitiveConverter.toUint8(bytes);
            default:
                throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert a variable length byte array to an unsigned 16-bit integer (int with range of uint16)
     *
     * @param bytes The array of length 1-2
     * @return The unsigned 16-bit integer as a int
     */
    public static int variableBytesToUint16(byte[] bytes) {
        switch (bytes.length) {
            case 2:
                return PrimitiveConverter.toUint16(bytes);
            case 1:
                return PrimitiveConverter.toUint8(bytes);
            default:
                throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert an unsigned 32-bit unsigned integer (long with range of uint32) to a byte array
     *
     * @param val The unsigned 32-bit integer as long
     * @return The array of length 4
     */
    public static byte[] uint32ToBytes(long val) {
        if (val < 0 || val > 4_294_967_295L) {
            throw new IllegalArgumentException("Value out of range");
        }
        longBuffer.get().putLong(0, val);
        return Arrays.copyOfRange(longBuffer.get().array(), 4, 8);
    }

    /**
     * Convert a byte array to an unsigned 16-bit integer (int with range of uint16)
     *
     * @param bytes The array of length 2
     * @return The unsigned 16-bit integer as an int
     */
    public static int toUint16(byte[] bytes) {
        if (bytes.length == 2) {
            return Short.toUnsignedInt(ByteBuffer.wrap(bytes).getShort());
        }

        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert a byte array to an unsigned 16-bit integer (int with range of uint16)
     *
     * @param bytes The array of data
     * @param offset the offset to convert at
     * @return The unsigned 16-bit integer as an int
     */
    public static int toUint16(byte[] bytes, int offset) {
        return Short.toUnsignedInt(ByteBuffer.wrap(bytes, offset, Short.BYTES).getShort());
    }

    /**
     * Convert an unsigned 16-bit unsigned integer (int with range of uint16) to a byte array
     *
     * @param val The unsigned 16-bit integer as int
     * @return The array of length 2
     */
    public static byte[] uint16ToBytes(int val) {
        if (val < 0 || val > 65_535) {
            throw new IllegalArgumentException("Value out of range");
        }
        intBuffer.get().putInt(0, val);
        return Arrays.copyOfRange(intBuffer.get().array(), 2, 4);
    }

    /**
     * Convert a byte array to an unsigned 8-bit integer (int with range of uint8)
     *
     * @param bytes The array of length 1
     * @return The unsigned 8-bit integer as an int
     */
    public static int toUint8(byte[] bytes) {
        if (bytes.length == 1) {
            return Byte.toUnsignedInt(bytes[0]);
        }
        throw new IllegalArgumentException("Invalid buffer length");
    }

    /**
     * Convert a byte array to an unsigned 8-bit integer (int with range of uint8)
     *
     * @param bytes The array of data
     * @param offset the offset to convert at
     * @return The unsigned 8-bit integer as an int
     */
    public static int toUint8(byte[] bytes, int offset) {
        return Byte.toUnsignedInt(bytes[offset]);
    }

    /**
     * Convert an unsigned 8-bit unsigned integer (short with range of uint8) to a byte array
     *
     * @param val The unsigned 8-bit integer as short
     * @return The array of length 1
     */
    public static byte[] uint8ToBytes(short val) {
        if (val < 0 || val > 255) {
            throw new IllegalArgumentException("Value out of range");
        }
        shortBuffer.get().putShort(0, val);
        return Arrays.copyOfRange(shortBuffer.get().array(), 1, 2);
    }

    /**
     * Convert a byte array to a signed 64-bit integer.
     *
     * @param bytes The array of length 8
     * @return The signed 64-bit integer
     */
    public static long toInt64(byte[] bytes) {
        return toInt64(bytes, 0);
    }

    /**
     * Convert a byte array with offset to a signed 64-bit integer.
     *
     * @param bytes The array of length 8
     * @param offset the offset into the array where the conversion should start
     * @return The signed 64-bit integer
     */
    public static long toInt64(byte[] bytes, int offset) {
        if (offset + Long.BYTES <= bytes.length) {
            return ByteBuffer.wrap(bytes, offset, Long.BYTES).getLong();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert a variable length byte array to an signed 64-bit integer (long)
     *
     * @param bytes The array of length 1-8
     * @return The signed 64-bit integer as a long
     */
    public static long variableBytesToInt64(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }

    /**
     * Convert a variable length byte array to an signed 64-bit integer (long with range of int64)
     *
     * @param bytes The array
     * @param offset the starting offset into the array to start extracting
     * @param length the number of bytes to extract (1-8)
     * @return The signed integer as a long
     */
    public static long variableBytesToInt64(byte[] bytes, int offset, int length) {
        switch (length) {
            case 8:
                return PrimitiveConverter.toInt64(bytes, offset);
            case 7:
                return arrayToSignedLongInternal(bytes, offset, length);
            case 6:
                return arrayToSignedLongInternal(bytes, offset, length);
            case 5:
                return arrayToSignedLongInternal(bytes, offset, length);
            case 4:
                return PrimitiveConverter.toInt32(bytes, offset);
            case 3:
                return arrayToSignedLongInternal(bytes, offset, length);
            case 2:
                return PrimitiveConverter.toInt16(bytes, offset);
            case 1:
                return bytes[offset];
            default:
                throw new IllegalArgumentException("Invalid length");
        }
    }

    /**
     * Convert a variable length byte array to an unsigned 64-bit integer (long with range of
     * uint64)
     *
     * @param bytes The array of length 1-8
     * @return The unsigned 64-bit integer as a long
     */
    public static long variableBytesToUint64(byte[] bytes) {
        switch (bytes.length) {
            case 8:
                return ByteBuffer.wrap(bytes, 0, Long.BYTES).getLong();
            case 7:
                return arrayToUnsignedLongInternal(bytes);
            case 6:
                return arrayToUnsignedLongInternal(bytes);
            case 5:
                return arrayToUnsignedLongInternal(bytes);
            case 4:
                return PrimitiveConverter.toUint32(bytes);
            case 3:
                return arrayToUnsignedLongInternal(bytes);
            case 2:
                return PrimitiveConverter.toUint16(bytes);
            case 1:
                return PrimitiveConverter.toUint8(bytes);
            default:
                throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert a variable length byte array to an unsigned 64-bit integer (long with range of
     * uint64)
     *
     * @param bytes The array
     * @param offset the starting offset into the array to start extracting
     * @param length the number of bytes to extract (1-8)
     * @return The unsigned 64-bit integer as a long
     */
    public static long variableBytesToUint64(byte[] bytes, int offset, int length) {
        switch (length) {
            case 8:
                return ByteBuffer.wrap(bytes, offset, Long.BYTES).getLong();
            case 7:
                return arrayToUnsignedLongInternal(bytes, offset, length);
            case 6:
                return arrayToUnsignedLongInternal(bytes, offset, length);
            case 5:
                return arrayToUnsignedLongInternal(bytes, offset, length);
            case 4:
                return PrimitiveConverter.toUint32(bytes, offset);
            case 3:
                return arrayToUnsignedLongInternal(bytes, offset, length);
            case 2:
                return PrimitiveConverter.toUint16(bytes, offset);
            case 1:
                return PrimitiveConverter.toUint8(bytes, offset);
            default:
                throw new IllegalArgumentException("Invalid length");
        }
    }

    /**
     * Convert a signed 64-byte integer to a byte array
     *
     * @param val The long value
     * @return The array of length 8
     */
    public static byte[] int64ToBytes(long val) {
        longBuffer.get().putLong(0, val);
        return longBuffer.get().array().clone();
    }

    /**
     * Convert an signed 64-bit integer to a byte array.
     *
     * <p>This is similar to int64ToBytes, except that it only uses the minimum required number of
     * bytes to represent the value. So if the value will fit into two bytes, the results will be
     * only two bytes.
     *
     * @param val The signed 64-bit integer
     * @return The array of length 1-8 bytes.
     */
    public static byte[] int64ToVariableBytes(long val) {
        return BigInteger.valueOf(val).toByteArray();
    }

    /**
     * Convert a byte array to a 32-bit floating point number
     *
     * @param bytes The array of length 4
     * @return The float value
     */
    public static float toFloat32(byte[] bytes) {
        if (bytes.length == 4) {
            return ByteBuffer.wrap(bytes).getFloat();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Extract a float (4 byte floating point value) from a byte array.
     *
     * @param bytes The array
     * @param offset the offset to start the extraction from
     * @return The floating point value
     */
    public static float toFloat32(byte[] bytes, int offset) {
        if (bytes.length >= offset + Float.BYTES) {
            return ByteBuffer.wrap(bytes, offset, Float.BYTES).getFloat();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert a 32-bit floating point number to a byte array
     *
     * @param val The float value
     * @return The array of length 4
     */
    public static byte[] float32ToBytes(float val) {
        floatBuffer.get().putFloat(0, val);
        return floatBuffer.get().array().clone();
    }

    /**
     * Convert a byte array to a 64-bit floating point number
     *
     * @param bytes The array of length 8 or 4
     * @return The double value
     */
    public static double toFloat64(byte[] bytes) {
        if (bytes.length == 8) {
            return ByteBuffer.wrap(bytes).getDouble();
        } else if (bytes.length == 4) {
            return ByteBuffer.wrap(bytes).getFloat();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Extract a double (8 byte floating point value) from a byte array.
     *
     * @param bytes The array
     * @param offset the offset to start the extraction from
     * @return The double value
     */
    public static double toFloat64(byte[] bytes, int offset) {
        if (bytes.length >= offset + Double.BYTES) {
            return ByteBuffer.wrap(bytes, offset, Double.BYTES).getDouble();
        } else {
            throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Extract a double (8 byte floating point value) from a byte array.
     *
     * @param bytes The array
     * @param offset the offset to start the extraction from
     * @param len the number of bytes to convert (4 or 8)
     * @return The double value
     */
    public static double toFloat64(byte[] bytes, int offset, int len) {
        switch (len) {
            case Float.BYTES:
                return toFloat32(bytes, offset);
            case Double.BYTES:
                return toFloat64(bytes, offset);
            default:
                throw new IllegalArgumentException("Invalid buffer length");
        }
    }

    /**
     * Convert a 64-bit floating point number to a byte array
     *
     * @param val The double value
     * @return The array of length 8
     */
    public static byte[] float64ToBytes(double val) {
        doubleBuffer.get().putDouble(0, val);
        return doubleBuffer.get().array().clone();
    }
}
