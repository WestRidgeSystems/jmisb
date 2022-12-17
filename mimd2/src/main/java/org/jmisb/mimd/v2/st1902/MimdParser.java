package org.jmisb.mimd.v2.st1902;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.CrcCcitt;
import org.jmisb.api.klv.CrcCcitt8;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.mimd.v2.st1903.MIMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser for top level MIMD structures.
 *
 * <p>MIMD encoding (ST1902.2) is similar to, but incompatible with the normal approach used for
 * local set parsing (see {@link LdsParser}), and also incompatible with the approach used in ST
 * 1902.1.
 */
public class MimdParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(MimdParser.class);
    private static final int KEY_LEN_CHECK_VALUE_LEN = 1;
    private static final int LOCAL_SET_CHECK_VALUE_LEN = 2;

    private MimdParser() {}

    /**
     * Parse {@link MIMD} local set from a byte array.
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     * @return List of parsed fields
     * @throws KlvParseException If a parsing error occurs
     */
    public static List<LdsField> parseFields(final byte[] bytes, final int start, final int length)
            throws KlvParseException {

        List<LdsField> fields = new ArrayList<>();
        final int localSetEnd = start + length - LOCAL_SET_CHECK_VALUE_LEN;
        int offset = start + UniversalLabel.LENGTH;
        BerField reportedLength = BerDecoder.decode(bytes, offset, false);
        int keyAndLengthBytesLen = UniversalLabel.LENGTH + reportedLength.getLength();
        byte[] keyAndLengthBytes = Arrays.copyOfRange(bytes, start, start + keyAndLengthBytesLen);
        offset += reportedLength.getLength();
        byte[] expectedCRC8 = CrcCcitt8.getCRC(keyAndLengthBytes);
        if (expectedCRC8[0] != bytes[offset]) {
            InvalidDataHandler idh = InvalidDataHandler.getInstance();
            idh.handleInvalidChecksum(LOGGER, "Bad MIMD Key/Len Check Value");
        }
        offset += KEY_LEN_CHECK_VALUE_LEN;
        final int valueStart = offset;
        while (offset < localSetEnd) {
            // Get the BER-OID encoded Key (tag)
            BerField tagField = BerDecoder.decode(bytes, offset, true);
            int tag = tagField.getValue();
            offset += tagField.getLength();

            // Get the Length (BER short or long form-encoded)
            int lengthFieldOffset = offset;
            BerField lengthField = BerDecoder.decode(bytes, lengthFieldOffset, false);
            // Get the Value
            int begin = lengthFieldOffset + lengthField.getLength();
            int end = begin + lengthField.getValue();
            if (end > localSetEnd) {
                InvalidDataHandler idh = InvalidDataHandler.getInstance();
                idh.handleOverrun(LOGGER, "Overrun encountered while parsing MIMD fields");
            }

            byte[] value = Arrays.copyOfRange(bytes, begin, end);
            fields.add(new LdsField(tag, value));
            offset = end;
        }
        CrcCcitt crcCalc = new CrcCcitt();
        crcCalc.addData(Arrays.copyOfRange(bytes, valueStart, localSetEnd));
        byte[] expectedCheckValue =
                Arrays.copyOfRange(bytes, localSetEnd, localSetEnd + LOCAL_SET_CHECK_VALUE_LEN);
        if (!Arrays.equals(expectedCheckValue, crcCalc.getCrc())) {
            InvalidDataHandler idh = InvalidDataHandler.getInstance();
            idh.handleInvalidChecksum(LOGGER, "Bad MIMD Check Value");
        }
        return fields;
    }
}
