package org.jmisb.st0903.shared;

/**
 * The encoding approach used for floating point data.
 *
 * <p>There are two incompatible encoding approaches used in ST0903. ST0903.3 and earlier (including
 * the original EG0903 baseline version, and RP0903.2) mapped integer values to floating point
 * values with a direct mapping. ST0903.4 and later use the ST1201 IMAPB mapping approach.
 *
 * <p>This enumeration specifies which encoding is being used.
 */
public enum EncodingMode {
    /**
     * IMAPB encoding.
     *
     * <p>This is the 0903.4 and later format.
     */
    IMAPB,

    /**
     * Legacy encoding.
     *
     * <p>This is the 0903.3 and earlier format.
     */
    LEGACY
}
