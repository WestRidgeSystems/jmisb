package org.jmisb.api.klv;

/**
 * The methods for representing KLV lengths and some values, known as Basic Encoding Rules (BER).
 *
 * <p>BER is described in ST0107.4 "KLV Metadata in Motion Imagery" and the Motion Imagery Handbook.
 */
public enum Ber {
    /** The value is encoded in single-byte "Short Form". */
    SHORT_FORM,
    /** The value is encoded in multiple-byte "Long Form". */
    LONG_FORM,
    /** The value is encoded as an OID value. */
    OID
}
