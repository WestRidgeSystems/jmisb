package org.jmisb.core.klv;

import java.util.Collection;
import java.util.Formatter;

/** Utility methods for arrays */
public class ArrayUtils {
    private ArrayUtils() {}

    /**
     * Format a byte array as a string of hexadecimal values
     *
     * @param bytes The input array
     * @return The formatted string
     */
    public static String toHexString(byte[] bytes) {
        return internalToHex(bytes, 16, false);
    }

    /**
     * Format a byte array as a string of hexadecimal values
     *
     * @param bytes The input array
     * @param columns The number of values per row
     * @return The formatted string
     */
    public static String toHexString(byte[] bytes, int columns) {
        return internalToHex(bytes, columns, false);
    }

    /**
     * Format a byte array as a string of hexadecimal values
     *
     * @param bytes The input array
     * @param columns The number of values per row
     * @param decorate If true, prepend "0x" to each byte value
     * @return The formatted string
     */
    public static String toHexString(byte[] bytes, int columns, boolean decorate) {
        return internalToHex(bytes, columns, decorate);
    }

    private static String internalToHex(byte[] bytes, int columns, boolean decorate) {
        Formatter formatter = new Formatter();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0 && i % columns == 0) {
                formatter.format(System.lineSeparator());
            }
            if (decorate) {
                formatter.format("0x%02x, ", bytes[i]);
            } else {
                formatter.format("%02x ", bytes[i]);
            }
        }
        return formatter.toString();
    }

    /**
     * Concatenate a collection of byte arrays (chunks) sequentially into one big array
     *
     * @param chunks The collection of chunks
     * @param totalLength The total number of bytes in all chunks
     * @return New array concatenating all chunks
     */
    public static byte[] arrayFromChunks(Collection<byte[]> chunks, int totalLength) {
        byte[] array = new byte[totalLength];
        int i = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, array, i, chunk.length);
            i += chunk.length;
        }
        return array;
    }
}
