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
        chunks.add(bytes);
        numBytesInChunks += bytes.length;
        return this;
    }

    /**
     * Append an unsigned integer encoded as a BER-OID value.
     *
     * @param value the unsigned integer value.
     * @return this instance, to support method chaining.
     */
    public ArrayBuilder appendAsOID(int value) {
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
        byte[] encodedBytes = universalLabel.getBytes();
        chunks.add(encodedBytes);
        numBytesInChunks += encodedBytes.length;
        return this;
    }

    /**
     * Build the byte array from the appended parts.
     *
     * @return the byte array.
     */
    public byte[] toBytes() {
        return ArrayUtils.arrayFromChunks(chunks, numBytesInChunks);
    }
}
