package org.jmisb.api.klv;

/**
 * Base interface for KLV tag (metadata key) implementations.
 */
public interface IKlvTag {

    /**
     * Get the integer code that corresponds to this tag.
     * <p>
     * This is the number that the tag uses in a local set.
     * <p>
     * @return the tag as an integer.
     */
    int getTagCode();
}
