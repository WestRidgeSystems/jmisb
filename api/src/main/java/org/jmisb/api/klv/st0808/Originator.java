package org.jmisb.api.klv.st0808;

/**
 * Text identifier for the creator of a message (ST 0808 Tag 1).
 *
 * <p>The Ancillary Text Creator element contains the Natural Text identifying the creator of the
 * message, for example by system name or username
 *
 * <p>This element is free text,encoded with the UTF 8-Bit Coded Character Set.
 *
 * <p>The Ancillary Text Originator is an optional element in the ancillary text metadata set.
 */
public class Originator extends NaturalText implements IAncillaryTextMetadataValue {

    private static final String ORIGINATOR = "Originator";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the Natural Text subset of UTF-8.
     */
    public Originator(String value) {
        super(ORIGINATOR, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public Originator(byte[] bytes) {
        super(ORIGINATOR, bytes);
    }
}
