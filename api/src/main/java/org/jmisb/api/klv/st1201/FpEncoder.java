package org.jmisb.api.klv.st1201;

import java.nio.ByteBuffer;

/**
 * Encoding and decoding of floating point values per ST 1201
 */
public class FpEncoder
{
    private double a, b;
    private double bPow, dPow;
    private double sF, sR;
    private double zOffset;
    private int fieldLength;
    private static double logOf2 = Math.log(2.0);

    /**
     * Construct an encoder with the desired field length
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param length The field length, in bytes (1, 2, 4, or 8)
     *
     * @throws IllegalArgumentException if the length is not supported
     */
    public FpEncoder(double min, double max, int length)
    {
        if (length == 1 || length == 2 || length == 4 || length == 8)
            computeConstants(min, max, length);
        else
            throw new IllegalArgumentException("Only 1, 2, 4, and 8 are valid field lengths");
    }

    /**
     * Construct an encoder with the desired precision, automatically selecting field length
     *
     * @param min The minimum floating point value to be encoded
     * @param max The maximum floating point value to be encoded
     * @param precision The required precision
     *
     * @throws IllegalArgumentException if the range/precision is too large to represent within 64 bytes
     */
    public FpEncoder(double min, double max, double precision)
    {
        double bits = Math.ceil(log2((max - min)/precision)+1);
        int length = (int) Math.ceil(bits / 8);

        // Only support length of 1/2/4/8
        if (length <= 2)
            computeConstants(min, max, length);
        else if (length <= 4)
            computeConstants(min, max, 4);
        else if (length <= 8)
            computeConstants(min, max, 8);
        else
            throw new IllegalArgumentException(("The specified range and precision cannot be represented using a 64-bit integer"));
    }

    /**
     * Get the length of the encoded byte array
     *
     * @return The length, in bytes
     */
    public int getFieldLength()
    {
        return fieldLength;
    }

    /**
     * Encode a floating point value as a byte array
     *
     * Note: Positive and negative infinity and NaN will be encoded by setting special flags defined by the ST.
     * To send other special value bit patterns, use the encodeSpecial method.
     *
     * @param val The value to encode
     * @return The encoded byte array
     *
     * @throws IllegalArgumentException if the value is not within the specified range
     */
    public byte[] encode(double val)
    {
        byte[] encoded = null;

        // Special values defined by the ST
        //
        if (val == Double.POSITIVE_INFINITY)
        {
            encoded = new byte[fieldLength];
            encoded[0] = (byte)0xc8;
        }
        else if (val == Double.NEGATIVE_INFINITY)
        {
            encoded = new byte[fieldLength];
            encoded[0] = (byte)0xe8;
        }
        else if (Double.isNaN(val))
        {
            // Send +QNaN as defined by the ST; to send other NaN values defined by the ST, use encodeSpecial method
            //
            encoded = new byte[fieldLength];
            encoded[0] = (byte)0xd0;
        }
        else if (val < a || val > b)
        {
            throw new IllegalArgumentException("Value must be in range [" + a + "," + b + "]");
        }
        else
        {
            // Value is normal and in range
            double d = Math.floor(sF * (val - a) + zOffset);
            switch (fieldLength)
            {
                case 1:
                    char c = (char) d;
                    encoded = ByteBuffer.allocate(1).putChar(c).array();
                    break;
                case 2:
                    short s = (short) d;
                    encoded = ByteBuffer.allocate(2).putShort(s).array();
                    break;
                case 4:
                    int i = (int) d;
                    encoded = ByteBuffer.allocate(4).putInt(i).array();
                    break;
                case 8:
                    long l = (long) d;
                    encoded = ByteBuffer.allocate(8).putLong(l).array();
                    break;
            }
        }
        return encoded;
    }

    /**
     * Decode a byte array containing an encoded floating point value
     *
     * @param bytes The encoded array
     * @return The floating point value
     *
     * @throws IllegalArgumentException if the array is invalid
     */
    public double decode(byte[] bytes)
    {
        double val = 0.0;

        if (bytes.length != fieldLength)
        {
            throw new IllegalArgumentException("Array length does not match expected field length");
        }
        else if (bytes[0] == (byte)0xc8)
        {
            val = Double.POSITIVE_INFINITY;
        }
        else if (bytes[0] == (byte)0xe8)
        {
            val = Double.NEGATIVE_INFINITY;
        }
        else if (bytes[0] == (byte)0xd0)
        {
            val = Double.NaN;
        }
        else
        {
            // Normal floating point value
            ByteBuffer wrapped = ByteBuffer.wrap(bytes);
            switch (fieldLength)
            {
                case 1:
                    byte b = wrapped.get();
                    val = sR * (b - zOffset) + a;
                    break;
                case 2:
                    short s = wrapped.getShort();
                    val = sR * (s - zOffset) + a;
                    break;
                case 4:
                    int i = wrapped.getInt();
                    val = sR * (i - zOffset) + a;
                    break;
                case 8:
                    long l = wrapped.getLong();
                    val = sR * (l - zOffset) + a;
                    break;
            }

            if (val < a || val > b)
            {
                throw new IllegalArgumentException("Error decoding floating point value; out of range");
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
    private void computeConstants(double min, double max, int length)
    {
        fieldLength = length;
        a = min;
        b = max;
        bPow = Math.ceil(log2(b - a));
        dPow = 8 * fieldLength - 1;
        sF = Math.pow(2, dPow - bPow);
        sR = Math.pow(2, bPow - dPow);
        zOffset = 0.0;
        if (a < 0 && b > 0)
        {
            zOffset = sF * a - Math.floor(sF * a);
        }
    }

    /**
     * Base-2 logarithm
     *
     * @param val Input value
     * @return Log value
     */
    private static double log2(double val)
    {
        return Math.log(val) / logOf2;
    }
}