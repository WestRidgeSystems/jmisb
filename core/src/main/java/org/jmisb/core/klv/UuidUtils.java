package org.jmisb.core.klv;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;

/** Internal utility methods for UUID conversions. */
public class UuidUtils {

    /**
     * Parse an ST1204 style identifier to a standard Java UUID.
     *
     * @param identifier the identifier to convert.
     * @return the corresponding UUID, or null if the identifier is not in the right format.
     */
    public static UUID parseUUID(String identifier) {
        String[] uuidParts = identifier.split("-");
        if (uuidParts.length != 8) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(uuidParts[0]);
        sb.append(uuidParts[1]);
        sb.append("-");
        sb.append(uuidParts[2]);
        sb.append("-");
        sb.append(uuidParts[3]);
        sb.append("-");
        sb.append(uuidParts[4]);
        sb.append("-");
        sb.append(uuidParts[5]);
        sb.append(uuidParts[6]);
        sb.append(uuidParts[7]);
        UUID uuid = UUID.fromString(sb.toString());
        return uuid;
    }

    /**
     * Get the content of a UUID as a byte array.
     *
     * @param uuid the UUID to convert
     * @return the equivalent value as a byte array.
     */
    public static byte[] uuidToArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    /**
     * Get the content of a UUID as an array.
     *
     * @param uuid the UUID to convert
     * @return the equivalent value as a long array.
     */
    public static long[] uuidToLongArray(UUID uuid) {
        byte[] bytes = uuidToArray(uuid);
        long[] longArray = new long[16];
        for (int i = 0; i < bytes.length; ++i) {
            longArray[i] = bytes[i] & 0xFF;
        }
        return longArray;
    }

    /**
     * Convert part of a byte array to a UUID.
     *
     * @param bytes the byte array
     * @param index the offset into the byte array where the UUID should be read from
     * @return UUID
     */
    public static UUID arrayToUuid(byte[] bytes, int index) {
        if (index + 16 > bytes.length) {
            throw new IllegalArgumentException("Too few bytes available to read UUID");
        }
        ByteBuffer bb = ByteBuffer.wrap(bytes, index, 16);
        return new UUID(bb.getLong(), bb.getLong());
    }

    /**
     * Convert a standard Java UUID to the ST1204 text representation.
     *
     * @param uuid the UUID to format as text
     * @return text equivalent to the UUID
     */
    public static String formatUUID(UUID uuid) {
        String standardFormatUUID = uuid.toString().toUpperCase();
        String misbFormatUUID =
                standardFormatUUID.substring(0, 4)
                        + "-"
                        + standardFormatUUID.substring(4, 28)
                        + "-"
                        + standardFormatUUID.substring(28, 32)
                        + "-"
                        + standardFormatUUID.substring(32);
        return misbFormatUUID;
    }

    public static UUID convertHashOutputToVersion5UUID(byte[] uuidBytes) {
        byte[] truncatedBytes = Arrays.copyOf(uuidBytes, 16);
        truncatedBytes[6] &= 15; // clear version
        truncatedBytes[6] |= 80; // set to version 5
        truncatedBytes[8] &= 63; // clear variant bits
        truncatedBytes[8] |= 128; // set to variant 2
        return UuidUtils.arrayToUuid(truncatedBytes, 0);
    }

    /**
     * Convert a hex String (in UUID format) to a byte array.
     *
     * <p>Per ST1204 algorithm, the separators are ignored.
     *
     * @param uuidString the string to convert
     * @return corresponding byte array.
     */
    public static byte[] uuidStringToByteArray(String uuidString) {
        return uuidString.replaceAll("-", "").toUpperCase().getBytes(Charset.forName("US-ASCII"));
    }
}
