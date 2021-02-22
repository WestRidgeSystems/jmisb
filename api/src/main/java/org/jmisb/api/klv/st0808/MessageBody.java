package org.jmisb.api.klv.st0808;

/**
 * Text of the message body (ST 0808 Tag 3).
 *
 * <p>The Ancillary Text Message Body element contains the Natural Text of the message to be
 * encoded. This element is free text,encoded with the UTF 8-Bit Coded Character Set.
 *
 * <p>The Ancillary Text Message Body is a mandatory element in the ancillary text metadata set.
 */
public class MessageBody extends NaturalText implements IAncillaryTextMetadataValue {

    public static final String MESSAGE_BODY = "Message Body";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the Natural Text subset of UTF-8.
     */
    public MessageBody(String value) {
        super(MESSAGE_BODY, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public MessageBody(byte[] bytes) {
        super(MESSAGE_BODY, bytes);
    }
}
