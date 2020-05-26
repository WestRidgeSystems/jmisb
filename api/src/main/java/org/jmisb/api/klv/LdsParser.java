package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Parse fields from a Local Data Set (LDS)
 */
public class LdsParser
{
    private static Logger logger = LoggerFactory.getLogger(LdsParser.class);

    private LdsParser() {}

    /**
     * Parse {@link LdsField}s from a byte array.
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     * @param parseOptions any special parsing options
     * @return List of parsed fields
     *
     * @throws KlvParseException If a parsing error occurs
     */
    public static List<LdsField> parseFields(byte[] bytes, int start, int length, EnumSet<ParseOptions> parseOptions) throws KlvParseException {
        StringBuilder debugMessageStringBuilder = new StringBuilder();
        if (logger.isDebugEnabled())
        {
            debugMessageStringBuilder.append("Tags: ");
        }

        List<LdsField> fields = new ArrayList<>();
        final int last = start + length;
        int offset = start;
        while (offset < last)
        {
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
            if (end > bytes.length)
            {
                if (parseOptions.contains(ParseOptions.LOG_ON_OVERRUN))
                {
                    logger.warn("Overrun encountered while parsing LDS fields");
                    break;
                }
                else
                {
                    throw new KlvParseException("Overrun encountered while parsing LDS fields");
                }
            }

            byte[] value = Arrays.copyOfRange(bytes, begin, end);
            fields.add(new LdsField(tag, value));
            offset = end;
            if (logger.isDebugEnabled())
            {
                debugMessageStringBuilder.append(tag);
                debugMessageStringBuilder.append(" ");
            }
        }

        if (logger.isDebugEnabled())
            logger.debug(debugMessageStringBuilder.toString());

        return fields;
    }
}
