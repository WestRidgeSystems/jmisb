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
     * @param data the bytes to compute over.
     */
    public void addData(byte[] data) {
        addData(data, data.length);
    }

    private void addData(byte[] data, int length) {
        for (int i = 0; i < length; i++) {
            tVal = TABLE[lookUp];
            aVal = (tVal >> 8);
            bVal = (tVal & 255);
            lookUp = aVal ^ lastBVal ^ data[i] & 0xFF;
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

    /**
     * Verify CRC-16 checksum for byte array.
     *
     * <p>This uses the CRC-16 algorithm specified by the Motion Imagery Handbook (October 2019)
     *
     * <p>This is not the same checksum as is used in ST0601.
     *
     * @param fullMessage Byte array of the full message packet
     * @param expected expected checksum value
     * @return true if expected checksum validates against data.
     */
    public static boolean verify(byte[] fullMessage, byte[] expected) {
        if (fullMessage.length < 2) {
            return false;
        }
        if (expected.length != 2) {
            return false;
        }
        CrcCcitt crc16 = new CrcCcitt();
        crc16.addData(fullMessage, fullMessage.length - 2);
        byte[] result = crc16.getCrc();
        boolean matches = (result[0] == expected[0]) && (result[1] == expected[1]);
        return matches;
    }
}
