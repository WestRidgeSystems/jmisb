package org.jmisb.api.klv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.eg0104.PredatorUavMessage;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0102.universalset.SecurityMetadataUniversalSet;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0903.vtrack.VTrackLocalSet;
import org.jmisb.api.klv.st1903.MIMD;
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
            byte[] nextMessage = getNextMessage(bytes);
            pos += nextMessage.length;

            try {
                // Extract a 16-byte universal label from the beginning of the message
                UniversalLabel ul =
                        new UniversalLabel(
                                Arrays.copyOfRange(nextMessage, 0, UniversalLabel.LENGTH));

                // Parse the message if it is one of the types we support
                if (ul.equals(KlvConstants.UasDatalinkLocalUl)) {
                    if (logger.isDebugEnabled()) logger.debug("UAS Datalink message");

                    UasDatalinkMessage message = new UasDatalinkMessage(nextMessage);
                    messages.add(message);
                } else if (ul.equals(KlvConstants.SecurityMetadataUniversalSetUl)) {
                    if (logger.isDebugEnabled())
                        logger.debug("Security Metadata Universal Set message");
                    SecurityMetadataUniversalSet message =
                            new SecurityMetadataUniversalSet(nextMessage);
                    messages.add(message);
                } else if (ul.equals(KlvConstants.SecurityMetadataLocalSetUl)) {
                    if (logger.isDebugEnabled())
                        logger.debug("Security Metadata Local Set message");
                    SecurityMetadataLocalSet message =
                            new SecurityMetadataLocalSet(nextMessage, true);
                    messages.add(message);
                } else if (ul.equals(KlvConstants.PredatorMetadataLocalSetUl)) {
                    if (logger.isDebugEnabled())
                        logger.debug("Predator UAV Metadata Local Set message");
                    PredatorUavMessage message = new PredatorUavMessage(nextMessage);
                    messages.add(message);
                } else if (ul.equals(KlvConstants.VTrackLocalSetUl)) {
                    if (logger.isDebugEnabled()) logger.debug("VTrack Local Set message");
                    VTrackLocalSet message = new VTrackLocalSet(nextMessage);
                    messages.add(message);
                } else if (ul.equals(KlvConstants.MIMDLocalSetUl)) {
                    if (logger.isDebugEnabled()) logger.debug("MIMD Local Set message");
                    MIMD message = new MIMD(nextMessage);
                    messages.add(message);
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Unsupported message type; wrapping as a raw message");
                    RawMisbMessage message = new RawMisbMessage(ul, nextMessage);
                    messages.add(message);
                }
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
     * @return Byte array containing the full top-level message, including UL key, length, and value
     * @throws KlvParseException if a parsing error occurs
     */
    private static byte[] getNextMessage(byte[] bytes) throws KlvParseException {
        // Length of the key field (UL)
        final int keyLength = UniversalLabel.LENGTH;
        // Length of the length field
        int lengthLength;
        // Length of the value field
        int payloadLength;

        // TODO: can we use BerDecode here?

        // Check for short form vs. long form
        if ((bytes[keyLength] & 0x80) == 0) {
            // short form
            lengthLength = 1;
            payloadLength = bytes[keyLength] & 0x7f;
            if (logger.isDebugEnabled()) logger.debug("Short-form length = " + payloadLength);
        } else {
            // long form
            int berLength = bytes[keyLength] & 0x7f;
            int len = 0;
            for (int i = 0; i < berLength; ++i) {
                int b = 0x00ff & bytes[keyLength + i + 1];
                len = (len << 8) | b;
            }
            lengthLength = berLength + 1;
            payloadLength = len;
            if (logger.isDebugEnabled())
                logger.debug(
                        "Long-form; ber-length = "
                                + berLength
                                + "; payload length = "
                                + payloadLength);
        }

        final int totalLength = keyLength + lengthLength + payloadLength;

        // If the lengths are equal, just return the original array; otherwise copy a subrange.
        if (totalLength > bytes.length) {
            throw new KlvParseException("Length exceeds available bytes");
        } else if (totalLength == bytes.length) {
            return bytes;
        } else {
            return Arrays.copyOfRange(bytes, 0, totalLength);
        }
    }
}
