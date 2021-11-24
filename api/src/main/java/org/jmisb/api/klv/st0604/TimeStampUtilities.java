package org.jmisb.api.klv.st0604;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st0603.TimeStatus;

/**
 * ST 0604 Time Stamp utilities.
 *
 * <p>This is a collection of mostly static utility functions for encoding and decoding time stamps
 * using ST 0604 conventions.
 */
public class TimeStampUtilities {

    private static final String TIME_STAMP_IDENTIFIER = "MISPmicrosectime";
    private static final byte[] TIME_STAMP_IDENTIFIER_BYTES =
            TIME_STAMP_IDENTIFIER.getBytes(StandardCharsets.US_ASCII);
    private static final byte[] H265_TIME_STAMP_IDENTIFIER_BYTES =
            new byte[] {
                (byte) 0xa8,
                (byte) 0x68,
                (byte) 0x7d,
                (byte) 0xd4,
                (byte) 0xd7,
                (byte) 0x59,
                (byte) 0x37,
                (byte) 0x58,
                (byte) 0xa5,
                (byte) 0xce,
                (byte) 0xf0,
                (byte) 0x33,
                (byte) 0x8b,
                (byte) 0x65,
                (byte) 0x45,
                (byte) 0xf1
            };
    private static final int EXPECTED_LENGTH = 28;
    private static final int IDENTIFIER_OFFSET = 0;
    private static final int IDENTIFIER_LENGTH = 16;
    private static final int TIMESTATUS_OFFSET = IDENTIFIER_OFFSET + IDENTIFIER_LENGTH;

    /* Note that ST 0604.6 uses counts (i.e. the first byte is 1, not 0), so it doesn't quite line up */
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE1_OFFSET = 17;
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE2_OFFSET = 18;
    // offset 19 (aka "Byte 20") is a pad byte for start code emulation prevention
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE3_OFFSET = 20;
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE4_OFFSET = 21;
    // offset 22 (aka "Byte 23") is a pad byte for start code emulation prevention
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE5_OFFSET = 23;
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE6_OFFSET = 24;
    // offset 25 (aka "Byte 26") is a pad byte for start code emulation prevention
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE7_OFFSET = 26;
    private static final int MODIFIED_PRECISION_TIME_STAMP_BYTE8_OFFSET = 27;

    private TimeStampUtilities() {};

    /**
     * Decode the ST 0604 precision time stamp from an encoded byte array.
     *
     * <p>The byte array includes the identifier, time status and time stamp. The time stamp is
     * assumed padded with 0xFF as described in ST 0604 Section 7.4. This provides start code
     * emulation protection.
     *
     * @param sideDataBytes the byte array of encoded data, beginning with the identifier.
     * @return time stamp object
     */
    public static ST0603TimeStamp decodePrecisionTimeStamp(byte[] sideDataBytes) {
        if (isRequiredLength(sideDataBytes) && isPrecisionTimeStampSEI(sideDataBytes)) {
            long precisionTimeStamp = getPrecisionTimeStamp(sideDataBytes);
            ST0603TimeStamp timestamp = new ST0603TimeStamp(precisionTimeStamp);
            return timestamp;
        } else {
            return null;
        }
    }

    /**
     * Decode the ST 0604 time status from an encoded byte array.
     *
     * <p>The byte array includes the identifier, time status and time stamp.
     *
     * @param sideDataBytes the byte array of encoded data, beginning with the identifier.
     * @return time status object
     */
    public static TimeStatus decodeTimeStatus(byte[] sideDataBytes) {
        if (isRequiredLength(sideDataBytes) && isPrecisionTimeStampSEI(sideDataBytes)) {
            TimeStatus timeStatus = new TimeStatus(sideDataBytes[TIMESTATUS_OFFSET]);
            return timeStatus;
        } else {
            return null;
        }
    }

    private static boolean isRequiredLength(byte[] sideDataBytes) {
        return sideDataBytes.length == EXPECTED_LENGTH;
    }

    private static boolean isPrecisionTimeStampSEI(byte[] sideDataBytes) {
        return isH264PrecisionTimeStampSEI(sideDataBytes)
                || isH265PrecisionTimeStampSEI(sideDataBytes);
    }

    private static boolean isH264PrecisionTimeStampSEI(byte[] sideDataBytes) {
        return sideDataBytesStartsWithIdentifier(sideDataBytes, TIME_STAMP_IDENTIFIER_BYTES);
    }

    private static boolean isH265PrecisionTimeStampSEI(byte[] sideDataBytes) {
        return sideDataBytesStartsWithIdentifier(sideDataBytes, H265_TIME_STAMP_IDENTIFIER_BYTES);
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

    static long getPrecisionTimeStamp(byte[] side_data_bytes) {
        long timestamp = side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE1_OFFSET] & 0xFF;
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE2_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE3_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE4_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE5_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE6_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE7_OFFSET] & 0xFF);
        timestamp = timestamp << 8;
        timestamp += (side_data_bytes[MODIFIED_PRECISION_TIME_STAMP_BYTE8_OFFSET] & 0xFF);
        return timestamp;
    }

    /**
     * Create an ST 0604 encoded Precision Time Stamp Information byte array.
     *
     * <p>This consists of a Precision Time Stamp Identifier (16 bytes), which is used for H.264 and
     * MPEG 2; a Time Status (1 byte); and a Modified Precision Time Stamp (11 bytes).
     *
     * <p>The Modified Precision Time Stamp is an 8 byte ST 0603 Time Stamp, with 0xFF bytes
     * inserted to provide Start Code Emulation Prevention.
     *
     * @param timeStatus the time status value to encode
     * @param timeStamp the timestamp value to encode
     * @return 28 byte array.
     */
    public static byte[] encodeH264(TimeStatus timeStatus, ST0603TimeStamp timeStamp) {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(TIME_STAMP_IDENTIFIER_BYTES);
        arrayBuilder.append(new byte[] {timeStatus.getEncodedValue()});
        arrayBuilder.append(getModifiedPrecisionTimeStamp(timeStamp));
        return arrayBuilder.toBytes();
    }

    /**
     * Create an ST 0604 encoded H.265 Precision Time Stamp Information byte array.
     *
     * <p>This consists of a Precision Time Stamp Identifier (16 bytes), which is used for H.265; a
     * Time Status (1 byte); and a Modified Precision Time Stamp (11 bytes).
     *
     * <p>The Modified Precision Time Stamp is an 8 byte ST 0603 Time Stamp, with 0xFF bytes
     * inserted to provide Start Code Emulation Prevention.
     *
     * @param timeStatus the time status value to encode
     * @param timeStamp the timestamp value to encode
     * @return 28 byte array.
     */
    public static byte[] encodeH265(TimeStatus timeStatus, ST0603TimeStamp timeStamp) {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(H265_TIME_STAMP_IDENTIFIER_BYTES);
        arrayBuilder.append(new byte[] {timeStatus.getEncodedValue()});
        arrayBuilder.append(getModifiedPrecisionTimeStamp(timeStamp));
        return arrayBuilder.toBytes();
    }

    static byte[] getModifiedPrecisionTimeStamp(ST0603TimeStamp timeStamp) {
        byte[] unencodedBytes = timeStamp.getBytesFull();
        byte[] modifiedPrecisionTimeStamp = new byte[11];
        int offset = 0;
        for (int i = 0; i < unencodedBytes.length; ++i) {
            modifiedPrecisionTimeStamp[offset] = unencodedBytes[i];
            offset++;
            if ((offset == 2) || (offset == 5) || (offset == 8)) {
                modifiedPrecisionTimeStamp[offset] = (byte) 0xFF;
                offset++;
            }
        }
        return modifiedPrecisionTimeStamp;
    }
}
