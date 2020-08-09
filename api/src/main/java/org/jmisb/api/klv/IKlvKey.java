package org.jmisb.api.klv;

/** Base interface for KLV tag or metadata key implementations. */
public interface IKlvKey {
    /**
     * Get the integer code that corresponds to this key.
     *
     * <p>If the parent is a local set, this is the number that the tag uses in a local set. However
     * it can be used in a series as a straight identifier (essentially an index), or in a nested
     * structure as an offset. It is best treated as an opaque identifier.
     *
     * @return the tag or other identifier as an integer.
     */
    int getIdentifier();
}
