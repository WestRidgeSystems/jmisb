package org.jmisb.api.klv.st0808;

/**
 * Text identifier for the source of a message (ST 0808 Tag 4).
 *
 * <p>The Ancillary Text Creator element contains the Natural Text identifying the source of the
 * message, for example the name of a chat room or the type of the text message
 *
 * <p>This element is free text,encoded with the UTF 8-Bit Coded Character Set.
 *
 * <p>The Ancillary Text Creator is an optional element in the ancillary text metadata set.
 */
public class Source extends NaturalText implements IAncillaryTextMetadataValue {

    public static final String SOURCE = "Source";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the Natural Text subset of UTF-8.
     */
    public Source(String value) {
        super(SOURCE, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public Source(byte[] bytes) {
        super(SOURCE, bytes);
    }
}
