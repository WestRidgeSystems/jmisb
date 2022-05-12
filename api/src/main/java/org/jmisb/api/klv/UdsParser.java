package org.jmisb.api.klv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;

/** Parse fields from a Universal Data Set (UDS). */
public class UdsParser {
    private UdsParser() {}

    /**
     * Parse {@link UdsField}s from a byte array.
     *
     * @param bytes Array representing encoded UDS bytes
     * @param start Offset location to start parsing (must be at the start of a 16-byte UL)
     * @param length Number of bytes to parse
     * @return List of fields
     * @throws KlvParseException If a parsing error occurs
     */
    public static List<UdsField> parseFields(byte[] bytes, int start, int length)
            throws KlvParseException {
        List<UdsField> fields = new ArrayList<>();

        int offset = start;
        while (offset < start + length) {
            // Get the Key (UL)
            UniversalLabel key =
                    new UniversalLabel(
                            Arrays.copyOfRange(bytes, offset, offset + UniversalLabel.LENGTH));
            // increment offset by the number of bytes in the UL key.
            offset += UniversalLabel.LENGTH;

            // Get the length
            if (offset >= bytes.length) {
                // TODO: we will probably need a non-strict option to return the fields that were
                // actually parsed
                throw new KlvParseException("Overrun encountered while parsing UDS fields");
            }
            BerField lengthField = BerDecoder.decode(bytes, offset, false);
            // increment offset by the number of bytes in the length
            offset += lengthField.getLength();

            // Get the value
            int end = offset + lengthField.getValue();
            byte[] value = Arrays.copyOfRange(bytes, offset, end);
            if (end > bytes.length) {
                // TODO: we will probably need a non-strict option to return the fields that were
                // actually parsed
                throw new KlvParseException("Overrun encountered while parsing UDS fields");
            }

            // Add to fields
            fields.add(new UdsField(key, value));
            // Increment offset by the number of bytes in the value.
            offset += value.length;
        }

        return fields;
    }
}
