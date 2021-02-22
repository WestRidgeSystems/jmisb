package org.jmisb.api.video;

/**
 * Supported KLV multiplexing options.
 *
 * <p>See ST1402 Section 9.4 for a discussion of this.
 */
public enum KlvFormat {
    /** KLV is not included in the multiplexing. */
    NoKlv,
    /** KLV is multiplexed using the SMPTE RP 217 Asynchronous method. */
    Asynchronous,
    /** KLV is multiplexed using the ISO/IEC 13818-1 Synchronous method. */
    Synchronous;
}
