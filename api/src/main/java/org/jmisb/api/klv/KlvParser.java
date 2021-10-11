package org.jmisb.api.klv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Parse metadata to extract individual {@link IMisbMessage} packets. */
public class KlvParser {
    private static Logger logger = LoggerFactory.getLogger(KlvParser.class);

    private KlvParser() {}

    /**
     * Parse a byte array containing one or more {@link IMisbMessage}s.
     *
     * <p>This is the main interface for parsing KLV metadata. It assumes that {@code bytes}
     * contains one or more top-level messages, i.e., byte sequences starting with a Universal Label
     * (UL). If a particular UL is unsupported it will be returned as a {@link RawMisbMessage}.
     *
     * <p>The supported UL are determined by the {@link MisbMessageFactory} singleton.
     *
     * @param bytes The byte array
     * @return List of {@link IMisbMessage}s
     * @throws KlvParseException if a parsing exception occurs
     */
    public static List<IMisbMessage> parseBytes(byte[] bytes) throws KlvParseException {
        List<IMisbMessage> messages = new ArrayList<>();

        if (logger.isDebugEnabled()) logger.debug("len: " + bytes.length);

        int pos = 0;

        while (pos < bytes.length) {
            // Get the next full message including UL (key), length, and value
            byte[] nextMessage = getNextMessage(bytes, pos);
            pos += nextMessage.length;

            try {
                IMisbMessage message = MisbMessageFactory.getInstance().handleMessage(nextMessage);
                if (logger.isDebugEnabled()) logger.debug("Parsed as " + message.displayHeader());
                messages.add(message);
            } catch (IllegalArgumentException ex) {
                logger.error("Exception thrown by parser", ex);
                throw new KlvParseException(ex.getMessage());
            }
        }

        return messages;
    }

    /**
     * Extract the next top-level message.
     *
     * @param bytes The original byte array, assumed to begin with 16-byte UL
     * @param pos the offset into the byte array to start parsing from
     * @return Byte array containing the full top-level message, including UL key, length, and value
     * @throws KlvParseException if a parsing error occurs
     */
    private static byte[] getNextMessage(byte[] bytes, int pos) throws KlvParseException {
        // Length of the key field (UL)
        final int keyLength = UniversalLabel.LENGTH;
        BerField lengthField = BerDecoder.decode(bytes, pos + keyLength, false);
        final int totalLength = keyLength + lengthField.getLength() + lengthField.getValue();

        if (pos + totalLength > bytes.length) {
            throw new KlvParseException("Length exceeds available bytes");
        }
        // If the lengths are equal, just return the original array; otherwise copy a subrange.
        if ((pos == 0) && (totalLength == bytes.length)) {
            return bytes;
        } else {
            return Arrays.copyOfRange(bytes, pos, pos + totalLength);
        }
    }
}
