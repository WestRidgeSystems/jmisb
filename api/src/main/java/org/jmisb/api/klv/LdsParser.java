package org.jmisb.api.klv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Parse fields from a Local Data Set (LDS). */
public class LdsParser {
    private static Logger logger = LoggerFactory.getLogger(LdsParser.class);

    private LdsParser() {}

    /**
     * Parse {@link LdsField}s from a byte array.
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     * @return List of parsed fields
     * @throws KlvParseException If a parsing error occurs
     */
    public static List<LdsField> parseFields(byte[] bytes, int start, int length)
            throws KlvParseException {
        StringBuilder debugMessageStringBuilder = new StringBuilder();
        if (logger.isDebugEnabled()) {
            debugMessageStringBuilder.append("Tags: ");
        }

        List<LdsField> fields = new ArrayList<>();
        final int last = start + length;
        int offset = start;
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
            if (end > bytes.length) {
                // TODO: we will probably need a non-strict option to return the fields that were
                // actually parsed
                throw new KlvParseException("Overrun encountered while parsing LDS fields");
            }

            byte[] value = Arrays.copyOfRange(bytes, begin, end);
            fields.add(new LdsField(tag, value));
            offset = end;
            if (logger.isDebugEnabled()) {
                debugMessageStringBuilder.append(tag);
                debugMessageStringBuilder.append(" ");
            }
        }

        if (logger.isDebugEnabled()) logger.debug(debugMessageStringBuilder.toString());

        return fields;
    }
}
