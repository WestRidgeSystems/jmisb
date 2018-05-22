package org.jmisb.api.klv;

/**
 * Encode data using Basic Encoding Rules (BER)
 */
public class BerEncoder
{
    private static int SHORT_FORM_MAX_LENGTH = 127;

    private BerEncoder() {}

    /**
     * Encode a KLV length field
     *
     * @param length The length to encode
     * @param ber Encoding type
     * @return The encoded length field
     * @throws IllegalArgumentException If an illegal length value is specified
     * @throws UnsupportedOperationException If the encoding type is not supported
     */
    public static byte[] encodeLengthField(int length, Ber ber)
    {
        if (length < 0)
        {
            throw new IllegalArgumentException("Length cannot be negative");
        }

        byte[] bytes;

        if (ber == Ber.SHORT_FORM)
        {
            if (length > SHORT_FORM_MAX_LENGTH)
            {
                throw new IllegalArgumentException("BER short form can only represent the range [0,127]");
            }
            bytes = new byte[1];
            bytes[0] = (byte)length;
        }
        else if (ber == Ber.LONG_FORM)
        {
            if (length <= 127)
            {
                // 1 byte
                bytes = new byte[2];
                bytes[0] = (byte)0x81;
                bytes[1] = (byte)length;
            }
            else if (length <= 65535)
            {
                // 2 bytes
                bytes = new byte[3];
                bytes[0] = (byte)0x82;
                bytes[1] = (byte)(length >>> 8);
                bytes[2] = (byte)(length);
            }
            else if (length <= 16777215)
            {
                // 3 bytes
                bytes = new byte[4];
                bytes[0] = (byte)0x83;
                bytes[1] = (byte)(length >>> 16);
                bytes[2] = (byte)(length >>> 8);
                bytes[3] = (byte)(length);
            }
            else
            {
                // 4 bytes
                bytes = new byte[5];
                bytes[0] = (byte)0x84;
                bytes[1] = (byte)(length >>> 24);
                bytes[2] = (byte)(length >>> 16);
                bytes[3] = (byte)(length >>> 8);
                bytes[4] = (byte)(length);
            }
        }
        else
        {
            throw new UnsupportedOperationException("BER-OID is not yet supported");
        }

        return bytes;
    }

    /**
     * Encode a KLV length field using short or long form, whichever is more compact
     *
     * @param length The length to encode
     * @return The encoded length field
     * @throws IllegalArgumentException If an illegal length value is specified
     */
    public static byte[] encodeLengthField(int length)
    {
        Ber ber = (length <= SHORT_FORM_MAX_LENGTH) ? Ber.SHORT_FORM : Ber.LONG_FORM;
        return encodeLengthField(length, ber);
    }
}
