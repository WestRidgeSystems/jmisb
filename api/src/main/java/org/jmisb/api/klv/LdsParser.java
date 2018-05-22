package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parse fields from a Local Data Set (LDS)
 */
public class LdsParser
{
    private static Logger logger = LoggerFactory.getLogger(LdsParser.class);

    private LdsParser() {}

    /**
     * Parse {@link LdsField}s from a byte array
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     * @return List of parsed fields
     *
     * @throws KlvParseException If a parsing error occurs
     */
    public static List<LdsField> parseFields(byte[] bytes, int start, int length) throws KlvParseException
    {
        String dbgMessage = "Tags: ";

        List<LdsField> fields = new ArrayList<>();
        final int last = start + length;
        int offset = start;
        while (offset < last)
        {
            // Get the Key (tag)
            // TODO: according to ST 0601, tags are actually BER-OID encoded. Below we treat it as a single byte uint8,
            // which will work until 0601 starts including tags >127.
            int tag = bytes[offset];

            // Get the Length
            int lengthFieldOffset = offset + 1;
            LengthField lengthField = BerDecoder.decodeLengthField(bytes, lengthFieldOffset, false);

            // Get the Value
            int begin = lengthFieldOffset + lengthField.getSizeOfLength();
            int end = begin + lengthField.getSizeOfValue();
            if (end > bytes.length)
            {
                // TODO: we will probably need a non-strict option to return the fields that were actually parsed
                throw new KlvParseException("Overrun encountered while parsing LDS fields");
            }

            byte[] value = Arrays.copyOfRange(bytes, begin, end);
            fields.add(new LdsField(tag, value));
            offset = end;
            if (logger.isDebugEnabled())
                dbgMessage = dbgMessage + tag + " ";
        }

        if (logger.isDebugEnabled())
            logger.debug(dbgMessage);

        return fields;
    }
}
