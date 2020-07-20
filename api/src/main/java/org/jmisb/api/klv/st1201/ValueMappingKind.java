package org.jmisb.api.klv.st1201;

/**
 * Kind of value to be mapped using ST1201.
 *
 * <p>ST1201 can encode values outside of the normal range of a double. This enumeration is used to
 * represent the type of value that is encoded / decoded.
 */
public enum ValueMappingKind {
    /**
     * The value represents a normal number.
     *
     * <p>This is the usual case, where the high bit is 0, or where all the bits are 0 except for
     * the high bit which is 1.
     */
    NormalMappedValue,
    /**
     * This part of the bit space is reserved.
     *
     * <p>There are two parts of reserved bit space. This represents the part where the high bit is
     * zero, and the next most significant bit is 0.
     *
     * <p>Note that is not valid with an encoded value of 0, since that would result in a normal
     * value mapping (max value).
     */
    ReservedKind1,
    /** The value is positive infinity. */
    PositiveInfinity,
    /** The value is negative infinity. */
    NegativeInfinity,
    /**
     * The value is a positive Quiet Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which needs to
     * be coordinated with the MISB.
     */
    PostiveQuietNaN,
    /**
     * The value is a negative Quiet Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which needs to
     * be coordinated with the MISB.
     */
    NegativeQuietNaN,
    /**
     * The value is a positive Signaling Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which needs to
     * be coordinated with the MISB.
     */
    PositiveSignalNaN,
    /**
     * The value is a negative Signaling Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which needs to
     * be coordinated with the MISB.
     */
    NegativeSignalNaN,
    /**
     * This part of the bit space is reserved.
     *
     * <p>There are two parts of reserved bit space. This represents the part where the most
     * significant bits are @code{0b11100}.
     */
    ReservedKind2,
    /**
     * The value is a user defined signaling value.
     *
     * <p>While ST1201.4 does not make it explicit, this is conceptually a NaN value.
     *
     * <p>MISB allows this to be combined with a set of low bits which are user defined.
     */
    UserDefined
}
