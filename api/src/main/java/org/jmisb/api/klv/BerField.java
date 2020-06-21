package org.jmisb.api.klv;

/** Represents a BER-encoded field (length and integer value) */
public class BerField {
    /** Size of the field in bytes */
    private int length;

    /** The encoded value */
    private int value;

    /**
     * Constructor
     *
     * @param length Size of the field in bytes
     * @param value The (non-negative) integer value
     * @throws IllegalArgumentException If a negative value is specified
     */
    public BerField(int length, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
        this.length = length;
        this.value = value;
    }

    /**
     * Get the length
     *
     * @return Size of the field in bytes
     */
    public int getLength() {
        return length;
    }

    /**
     * Get the value
     *
     * @return The value as an integer
     */
    public int getValue() {
        return value;
    }
}
