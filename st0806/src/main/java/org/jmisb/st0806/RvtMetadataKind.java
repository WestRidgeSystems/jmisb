package org.jmisb.st0806;

/**
 * The kind of RVT Metadata Identifier.
 *
 * <p>This allows us to differentiate between repeating entries (e.g. a POI and an AOI that have the
 * same identifier number).
 */
public enum RvtMetadataKind {
    /**
     * A plain non-repeating identifier.
     *
     * <p>This is anything other than AOI Local Set, POI Local Set or User Defined Local Set.
     */
    PLAIN,
    /** An Area of Interest. */
    AOI,
    /** A Point of Interest. */
    POI,
    /** User Defined Local Set. */
    USER_DEFINED
}
