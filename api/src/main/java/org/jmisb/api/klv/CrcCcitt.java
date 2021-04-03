package org.jmisb.api.klv;

/**
 * CRC-16 CCITT variant implementation.
 *
 * <p>There are many implementations of CRC-16. This version is directly from Appendix D of the
 * Motion Imagery Handbook (October 2019), Appendix D.
 *
 * <p>No copyright is claimed over the code in this source file.
 *
 * <p>Note that this is not the same as the ST 0601 Checksum.
 */
public class CrcCcitt {

    private static final int[] TABLE = createTable(4129, 16, 8);

    private int tVal;
    private int aVal;
    private int lastBVal;
    private int lookUp;
    private int bVal;

    private static int[] createTable(int poly, int width, int lookUpSize) {
        int size = (int) Math.pow(2, lookUpSize);
        int[] table = new int[size];
        for (int i = 0; i < size; i++) {
            table[i] = tableElement(i, poly, width, lookUpSize);
        }
        return table;
    }

    private static int tableElement(int ival, int poly, int width, int lookUpSize) {
        int topBit = (int) Math.pow(2, (width - 1));
        int index = ival * ((int) Math.pow(2, lookUpSize));
        int mask = (int) Math.pow(2, width) - 1;
        for (int i = 0; i < lookUpSize; i++) {
            if ((topBit & index) != 0) {
                index = (index * 2) ^ poly;
            } else {
                index *= 2;
            }
            index &= mask;
        }
        return index;
    }

    /**
     * Calculate a CRC-16 from a byte array.
     *
     * @param key the bytes to compute over.
     * @return the two byte checksum.
     */
    public static byte[] getCRC(byte[] key) {
        CrcCcitt crcCcitt = new CrcCcitt();
        crcCcitt.addData(key);
        return crcCcitt.getCrc();
    }

    /** Constructor. */
    public CrcCcitt() {
        tVal = TABLE[255];
        aVal = (tVal >> 8);
        lastBVal = (tVal & 255);
        lookUp = (aVal ^ 255);
    }

    /**
     * Add byte array data to the CRC-16 calculation.
     *
     * @param key the bytes to compute over.
     */
    public void addData(byte[] key) {
        for (int i = 0; i < key.length; i++) {
            tVal = TABLE[lookUp];
            aVal = (tVal >> 8);
            bVal = (tVal & 255);
            lookUp = aVal ^ lastBVal ^ key[i] & 0xFF;
            lastBVal = bVal;
        }
    }

    /**
     * Finalise the CRC-16 calculation.
     *
     * @return the two byte checksum.
     */
    public byte[] getCrc() {
        tVal = TABLE[lookUp];
        aVal = (tVal >> 8);
        bVal = (tVal & 255);
        lookUp = (aVal ^ lastBVal);
        int returnVal = ((lookUp * 256) ^ bVal);
        byte highByte = (byte) (returnVal >>> 8);
        byte lowByte = (byte) (returnVal & 0xFF);
        return new byte[] {highByte, lowByte};
    }
}
