package org.jmisb.api.klv.st2101;

import java.util.Arrays;
import org.jmisb.api.klv.st1204.CoreIdentifier;

/**
 * Utility class for ST 2101 conversion of MIIS Core Identifier.
 *
 * <p>This class is not intended to be instantiated. Instead, use the required static methods
 * directly.
 */
public class ST2101Converter {

    private static final byte[] ST2101_UUID_BYTES =
            new byte[] {
                (byte) 0xa5,
                (byte) 0x50,
                (byte) 0x52,
                (byte) 0xaf,
                (byte) 0x52,
                (byte) 0x16,
                (byte) 0x5f,
                (byte) 0x45,
                (byte) 0xa3,
                (byte) 0x18,
                (byte) 0x1c,
                (byte) 0xfc,
                (byte) 0x7a,
                (byte) 0xbb,
                (byte) 0xc2,
                (byte) 0x67
            };

    private static final int IDENTIFIER_OFFSET = 0;
    private static final int IDENTIFIER_LENGTH = ST2101_UUID_BYTES.length;
    private static final int MINIMUM_CORE_ID_LENGTH = 18;
    private static final int MINIMUM_LENGTH = IDENTIFIER_LENGTH + MINIMUM_CORE_ID_LENGTH;

    private ST2101Converter() {}

    /**
     * Convert a MIIS Core Identifier to ST 2101 SEI format.
     *
     * @param miisCoreId the Core Identifier to convert.
     * @return encoded UUID and Core Identifier as a byte array.
     */
    public static byte[] coreIdToSideDataBytes(CoreIdentifier miisCoreId) {
        byte[] st2101value = miisCoreId.getRawBytesRepresentation();
        byte[] bytes = new byte[ST2101_UUID_BYTES.length + st2101value.length];
        System.arraycopy(ST2101_UUID_BYTES, 0, bytes, 0, ST2101_UUID_BYTES.length);
        System.arraycopy(st2101value, 0, bytes, ST2101_UUID_BYTES.length, st2101value.length);
        return bytes;
    }

    /**
     * Decode the MIIS Core Identifier from an encoded byte array.
     *
     * <p>To be valid, this will be the User Data Unregistered SEI entry, consisting of the UUID
     * provided in ST 2101, followed by a MIIS Core Identifier.
     *
     * @param sideDataBytes the byte array of encoded data, beginning with the ST 2101 UUID.
     * @return MIIS Core Identifier, or null if the byte array does not match the expected structure
     *     or content.
     */
    public static CoreIdentifier decodeCoreId(byte[] sideDataBytes) {
        if (isRequiredLength(sideDataBytes) && isCoreIdentifierSEI(sideDataBytes)) {
            byte[] coreIdentifierBytes =
                    Arrays.copyOfRange(sideDataBytes, IDENTIFIER_LENGTH, sideDataBytes.length);
            return CoreIdentifier.fromBytes(coreIdentifierBytes);
        } else {
            return null;
        }
    }

    private static boolean isRequiredLength(byte[] sideDataBytes) {
        return sideDataBytes.length >= MINIMUM_LENGTH;
    }

    private static boolean isCoreIdentifierSEI(byte[] sideDataBytes) {
        return sideDataBytesStartsWithIdentifier(sideDataBytes, ST2101_UUID_BYTES);
    }

    private static boolean sideDataBytesStartsWithIdentifier(
            byte[] sideDataBytes, byte[] identifier) {
        return Arrays.equals(
                sideDataBytes,
                IDENTIFIER_OFFSET,
                IDENTIFIER_LENGTH,
                identifier,
                IDENTIFIER_OFFSET,
                IDENTIFIER_LENGTH);
    }
}
