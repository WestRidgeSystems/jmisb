package org.jmisb.api.klv.st1201;

/**
 * Kind of value to be mapped using ST1201.
 *
 * <p>ST1201 can encode values outside of the normal range of a double. This enumeration is used to
 * represent the type of value that is encoded / decoded, as described in ST 1201.5 Table 1 "Bit
 * Patterns" and Table 2 "Special Value Bit Patterns".
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
     * <p>Up to and including ST 1201.4, there were two parts of reserved bit space. This kind
     * represents the part where the high bit is 1, and the next most significant bit is 0.
     *
     * <p>Note that this kind is not valid with an encoded value of 0, since that would result in a
     * normal value mapping (max value).
     */
    ReservedKind1,
    /** The value is positive infinity. */
    PositiveInfinity,
    /** The value is negative infinity. */
    NegativeInfinity,
    /**
     * The value is a positive Quiet Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which need to
     * be coordinated with the MISB.
     */
    PositiveQuietNaN,
    /**
     * The value is a negative Quiet Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which need to
     * be coordinated with the MISB.
     */
    NegativeQuietNaN,
    /**
     * The value is a positive Signaling Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which need to
     * be coordinated with the MISB.
     */
    PositiveSignalNaN,
    /**
     * The value is a negative Signaling Not a Number (NaN) value.
     *
     * <p>MISB allows this to be combined with a set of low bits (the @code{NaN Id}) which need to
     * be coordinated with the MISB.
     */
    NegativeSignalNaN,
    /**
     * The value is a MISB defined special value.
     *
     * <p>Prior to ST 1201.5, this was reserved bit space.
     */
    MISBDefined,
    /**
     * The value is a user defined signaling value.
     *
     * <p>While ST1201.5 does not make it explicit, this is conceptually a NaN value.
     *
     * <p>MISB allows this to be combined with a set of low bits which are user defined. In this
     * context, "user defined" means by an invoking specification.
     */
    UserDefined;

    /**
     * Value is in the second reserved bit space (ST 1201.4 legacy).
     *
     * <p>This provides an alias for ST 1201.4 and earlier for the second reserved bit space (most
     * significant bits are @code{0b11100}). This was reallocated in ST 1201.5 to be MISB defined.
     */
    public static final ValueMappingKind ReservedKind2 = MISBDefined;
}
