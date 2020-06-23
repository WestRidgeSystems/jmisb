package org.jmisb.api.klv;

/**
 * The methods for representing KLV lengths and some values, known as Basic Encoding Rules (BER).
 *
 * <p>BER is described in ST0107.4 "KLV Metadata in Motion Imagery" and the Motion Imagery Handbook.
 */
public enum Ber {
    SHORT_FORM,
    LONG_FORM,
    OID
}
