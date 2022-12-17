package org.jmisb.api.klv;

/**
 * CRC-8 CCITT variant implementation.
 *
 * <p>There are many implementations of CRC-8. This is the CCITT version, with polynomial of {@code
 * 0x07} and initialisation of {@code 0x00}.
 *
 * <p>No copyright is claimed over the code in this source file.
 */
public class CrcCcitt8 {

    private static final int LOOKUP_SIZE_IN_BITS = 8;
    private static final byte POLYNOMIAL = (byte) 0x07;
    private static final int TOP_BIT = 0x80;
    private static final int MASK = 0xFF;
    private static final byte[] TABLE = createTable();

    private byte result = 0;

    private static byte[] createTable() {
        int size = 1 << LOOKUP_SIZE_IN_BITS;
        byte[] table = new byte[size];
        for (int i = 0; i < size; i++) {
            table[i] = createTableElement(i);
        }
        return table;
    }

    private static byte createTableElement(int i) {
        byte elem = (byte) i;
        for (int j = 0; j < LOOKUP_SIZE_IN_BITS; j++) {
            if ((elem & TOP_BIT) != 0) {
                elem = (byte) (elem << 1);
                elem ^= POLYNOMIAL;
            } else {
                elem <<= 1;
            }
            elem &= MASK;
        }
        return elem;
    }

    /**
     * Calculate a CRC-8 from a byte array.
     *
     * @param key the bytes to compute over.
     * @return the one byte checksum.
     */
    public static byte[] getCRC(byte[] key) {
        CrcCcitt8 crcCcitt = new CrcCcitt8();
        crcCcitt.addData(key);
        return crcCcitt.getCrc();
    }

    /** Constructor. */
    public CrcCcitt8() {}

    /**
     * Add byte array data to the CRC-8 calculation.
     *
     * @param data the bytes to compute over.
     */
    public void addData(byte[] data) {
        addData(data, 0, data.length);
    }

    /**
     * Add part of byte array data to the CRC-8 calculation.
     *
     * @param data the bytes to compute over.
     * @param start the index into {@code data} to start reading from
     * @param length the number of byte to read from {@code data}
     */
    public void addData(byte[] data, int start, int length) {
        for (var i = start; i < data.length && i < start + length; i++) {
            int aVal = (data[i] ^ result) & 0xFF;
            result = TABLE[aVal];
        }
    }

    /**
     * Finalise the CRC-8 calculation.
     *
     * @return the one byte checksum.
     */
    public byte[] getCrc() {
        return new byte[] {(byte) result};
    }
}
