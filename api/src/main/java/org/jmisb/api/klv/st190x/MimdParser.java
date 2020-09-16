package org.jmisb.api.klv.st190x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.CrcCcitt;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st1903.MIMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser for top level MIMD structures.
 *
 * <p>MIMD encoding (ST1902) is similar to, but incompatible with the normal approach used for local
 * set parsing (see {@link LdsParser}).
 */
public class MimdParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(MimdParser.class);
    private static final int LEN_CHECK_VALUE = 2;

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
        final int last = start + length - LEN_CHECK_VALUE;
        int offset = start + UniversalLabel.LENGTH;
        BerField reportedLength = BerDecoder.decode(bytes, offset, false);
        offset += reportedLength.getLength();
        final int valueStart = offset;
        while (offset < last) {
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
            if (end > last) {
                InvalidDataHandler idh = InvalidDataHandler.getInstance();
                idh.handleOverrun(LOGGER, "Overrun encountered while parsing MIMD fields");
            }

            byte[] value = Arrays.copyOfRange(bytes, begin, end);
            fields.add(new LdsField(tag, value));
            offset = end;
        }
        CrcCcitt crcCalc = new CrcCcitt();
        crcCalc.addData(Arrays.copyOfRange(bytes, start, start + UniversalLabel.LENGTH));
        crcCalc.addData(Arrays.copyOfRange(bytes, valueStart, last));
        byte[] expectedCheckValue = Arrays.copyOfRange(bytes, last, last + LEN_CHECK_VALUE);
        if (!Arrays.equals(expectedCheckValue, crcCalc.getCrc())) {
            InvalidDataHandler idh = InvalidDataHandler.getInstance();
            idh.handleInvalidChecksum(LOGGER, "Bad MIMD Check Value");
        }
        return fields;
    }
}
