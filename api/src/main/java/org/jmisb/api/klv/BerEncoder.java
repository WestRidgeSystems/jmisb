package org.jmisb.api.klv;

import java.util.Arrays;

/** Encode data using Basic Encoding Rules (BER). */
public class BerEncoder {
    private static final int SHORT_FORM_MAX_LENGTH = 127;

    private BerEncoder() {}

    /**
     * Encode an integer using Basic Encoding Rules (BER).
     *
     * @param value The value to encode (must be non-negative)
     * @param ber Encoding type
     * @return The encoded value
     * @throws IllegalArgumentException If a negative value is specified
     */
    public static byte[] encode(int value, Ber ber) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }

        byte[] bytes;

        if (ber == Ber.SHORT_FORM) {
            if (value > SHORT_FORM_MAX_LENGTH) {
                throw new IllegalArgumentException(
                        "BER short form can only represent the range [0,127]");
            }
            bytes = new byte[1];
            bytes[0] = (byte) value;
        } else if (ber == Ber.LONG_FORM) {
            if (value <= 255) {
                // 1 byte
                bytes = new byte[2];
                bytes[0] = (byte) 0x81;
                bytes[1] = (byte) value;
            } else if (value <= 65535) {
                // 2 bytes
                bytes = new byte[3];
                bytes[0] = (byte) 0x82;
                bytes[1] = (byte) (value >>> 8);
                bytes[2] = (byte) (value);
            } else if (value <= 16777215) {
                // 3 bytes
                bytes = new byte[4];
                bytes[0] = (byte) 0x83;
                bytes[1] = (byte) (value >>> 16);
                bytes[2] = (byte) (value >>> 8);
                bytes[3] = (byte) (value);
            } else {
                // 4 bytes
                bytes = new byte[5];
                bytes[0] = (byte) 0x84;
                bytes[1] = (byte) (value >>> 24);
                bytes[2] = (byte) (value >>> 16);
                bytes[3] = (byte) (value >>> 8);
                bytes[4] = (byte) (value);
            }
        } else {
            // BER-OID
            byte[] encoded = new byte[] {0, 0, 0, 0, 0};
            int i;
            for (i = 4; i >= 0; i--) {
                encoded[i] = (byte) (value & 0x7f);
                if (i != 4) encoded[i] |= 0x80;
                value >>= 7;
                if (value == 0) break;
            }

            bytes = Arrays.copyOfRange(encoded, i, 5);
        }

        return bytes;
    }

    /**
     * Encode an integer using short or long form, whichever is more compact.
     *
     * @param value The value to encode (must be non-negative)
     * @return The encoded value
     * @throws IllegalArgumentException If a negative value is specified
     */
    public static byte[] encode(int value) {
        Ber ber = (value <= SHORT_FORM_MAX_LENGTH) ? Ber.SHORT_FORM : Ber.LONG_FORM;
        return encode(value, ber);
    }
}
