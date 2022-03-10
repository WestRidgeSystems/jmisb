package org.jmisb.api.klv;

import java.util.Arrays;

/** Represents a 16-byte Universal Label (UL). */
public class UniversalLabel {
    /** Length of a UniversalLabel in bytes. */
    public static final int LENGTH = 16;

    private final byte[] bytes;
    private static final int OID_FIELD = 0;
    private static final int UL_LENGTH_FIELD = 1;
    private static final int UL_CODE_FIELD = 2;
    private static final int SMPTE_DESIGNATOR_FIELD = 3;

    /**
     * Construct a UL from a byte array.
     *
     * @param bytes The UL byte array
     * @throws IllegalArgumentException if the array does not contain a valid UL
     */
    public UniversalLabel(byte[] bytes) {
        testValidity(bytes);
        this.bytes = bytes.clone();
    }

    /**
     * Get the byte array.
     *
     * @return The 16-byte array
     */
    public byte[] getBytes() {
        return bytes.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UniversalLabel other = (UniversalLabel) obj;

        return Arrays.equals(this.bytes, other.bytes);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bytes == null) ? 0 : Arrays.hashCode(bytes));
        return result;
    }

    /**
     * Test whether an array of bytes contains a valid UL.
     *
     * @param bytes The byte array
     * @throws IllegalArgumentException if the array does not contain a valid UL
     */
    private static void testValidity(byte[] bytes) {
        if (bytes.length != LENGTH) {
            throw new IllegalArgumentException(
                    "Invalid UL length (expected 16; got " + bytes.length);
        }

        if (bytes[OID_FIELD] != 0x06) {
            throw new IllegalArgumentException("Invalid OID field");
        }

        if (bytes[UL_LENGTH_FIELD] != 0x0e) {
            throw new IllegalArgumentException("Invalid UL length field");
        }

        if (bytes[UL_CODE_FIELD] != 0x2b) {
            throw new IllegalArgumentException("Invalid UL Code field");
        }

        if (bytes[SMPTE_DESIGNATOR_FIELD] != 0x34) {
            throw new IllegalArgumentException("Invalid SMPTE Designator field");
        }
    }
}
