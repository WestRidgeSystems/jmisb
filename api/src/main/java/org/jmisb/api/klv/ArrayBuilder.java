package org.jmisb.api.klv;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Builder for linear byte arrays.
 *
 * <p>This class provides a Builder pattern to allow generating a byte array from other byte arrays,
 * or from some common KLV types.
 *
 * <p>It supports method chaining to make the code more fluent.
 */
public class ArrayBuilder {
    private final List<byte[]> chunks;
    private int numBytesInChunks = 0;
    private byte bitBuffer = 0;
    private int bitPosition = 0;

    /** Constructor. */
    public ArrayBuilder() {
        this.chunks = new ArrayList<>();
    }

    /**
     * Append a byte array.
     *
     * @param bytes the byte array to append.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder append(byte[] bytes) {
        flushBitBuffer();
        chunks.add(bytes);
        numBytesInChunks += bytes.length;
        return this;
    }

    /**
     * Append a single byte.
     *
     * @param b the byte to append.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendByte(byte b) {
        return this.append(new byte[] {b});
    }

    /**
     * Append a Boolean value as a single bit.
     *
     * @param b the boolean value to append.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsBit(boolean b) {
        bitBuffer = (byte) (bitBuffer << 1);
        if (b) {
            bitBuffer |= 1;
        }
        bitPosition++;
        if (bitPosition == Byte.SIZE) {
            appendBitBuffer();
        }
        return this;
    }

    /**
     * Flush any stored bits to the array.
     *
     * <p>If there are not enough bits to fill a byte, the last byte will be zero-bit filled.
     *
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder flushBitBuffer() {
        if (bitPosition != 0) {
            bitBuffer = (byte) (bitBuffer << (Byte.SIZE - bitPosition));
            appendBitBuffer();
        }
        return this;
    }

    /** Append a complete "bit buffer" byte to the chunks and reset position. */
    private void appendBitBuffer() {
        chunks.add(new byte[] {(byte) bitBuffer});
        numBytesInChunks += Byte.BYTES;
        bitBuffer = 0x00;
        bitPosition = 0;
    }
    /**
     * Append an unsigned integer encoded as a BER-OID value.
     *
     * @param value the unsigned integer value.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsOID(int value) {
        flushBitBuffer();
        byte[] encodedBytes = BerEncoder.encode(value, Ber.OID);
        numBytesInChunks += encodedBytes.length;
        chunks.add(encodedBytes);
        return this;
    }

    /**
     * Append an unsigned integer length encoded as a BER value.
     *
     * <p>This is usually what you want for a length field.
     *
     * <p>If the encoding says "BER-OID", use {@link #appendAsOID(int)} instead.
     *
     * @param length the unsigned integer value.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsBerLength(int length) {
        flushBitBuffer();
        byte[] encodedBytes = BerEncoder.encode(length);
        numBytesInChunks += encodedBytes.length;
        chunks.add(encodedBytes);
        return this;
    }

    /**
     * Append a 64-bit floating point ({@code double}) value.
     *
     * @param value the value to append
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsFloat64Primitive(double value) {
        flushBitBuffer();
        byte[] encodedBytes = PrimitiveConverter.float64ToBytes(value);
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Append a 32-bit floating point ({@code float}) value.
     *
     * @param value the value to append
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsFloat32Primitive(float value) {
        flushBitBuffer();
        byte[] encodedBytes = PrimitiveConverter.float32ToBytes(value);
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Append a 32-bit signed integer ({@code int}) value.
     *
     * @param value the value to append
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsInt32Primitive(int value) {
        flushBitBuffer();
        byte[] encodedBytes = PrimitiveConverter.int32ToBytes(value);
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Append a 32-bit unsigned integer ({@code long} with range of {@code uint32}) value.
     *
     * @param value the value to append
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsUInt32Primitive(long value) {
        flushBitBuffer();
        byte[] encodedBytes = PrimitiveConverter.uint32ToBytes(value);
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Append a Universal Label.
     *
     * @param universalLabel the value to append
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder append(UniversalLabel universalLabel) {
        flushBitBuffer();
        byte[] encodedBytes = universalLabel.getBytes();
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Prepend a Universal Label to the start of the array.
     *
     * @param universalLabel the value to prepend
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder prepend(UniversalLabel universalLabel) {
        flushBitBuffer();
        byte[] encodedBytes = universalLabel.getBytes();
        chunks.add(0, encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Prepend a BER encoded length of the following bytes to the byte array.
     *
     * <p>This is the type of representation used for Variable Length Packs. Be careful about
     * subsequent operations that append to the array, because this length field will not be
     * updated.
     *
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder prependLength() {
        flushBitBuffer();
        byte[] encodedBytes = BerEncoder.encode(numBytesInChunks);
        numBytesInChunks += encodedBytes.length;
        chunks.add(0, encodedBytes);
        return this;
    }

    /**
     * Build the byte array from the appended parts.
     *
     * @return the byte array.
     */
    public byte[] toBytes() {
        flushBitBuffer();
        return ArrayUtils.arrayFromChunks(chunks, numBytesInChunks);
    }
}
