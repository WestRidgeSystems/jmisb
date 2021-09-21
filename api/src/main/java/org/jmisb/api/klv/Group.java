package org.jmisb.api.klv;

/** Group (Sets and Packs). */
public enum Group {
    /** Universal Data Sets are composed of elements that use a full 16-byte UL as the key. */
    UNIVERSAL_SET,

    /** Global Data Sets are composed of elements that have the same high-order key values. */
    GLOBAL_SET,

    /** Local Data Sets specify a mapping of integer tags to data elements. */
    LOCAL_DATA_SET,

    /**
     * Variable Length Packs eliminate the key field and instead use a defined sequence of data
     * elements.
     */
    VARIABLE_LENGTH_PACK,

    /**
     * Defined Length Packs eliminate both key and length fields and use pre-defined lengths for all
     * elements.
     */
    DEFINED_LENGTH_PACK
}
