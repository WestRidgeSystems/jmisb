package org.jmisb.api.klv;

/** Universal Data Set field, comprised of its Universal Label (key) and its value. */
public class UdsField {
    private final UniversalLabel key;

    private final byte[] value;

    /**
     * Constructor.
     *
     * @param key the Universal Label for the Universal Data Set
     * @param value the value associated with the Universal Data Set metadata item
     */
    public UdsField(UniversalLabel key, byte[] value) {
        this.key = key;
        this.value = value.clone();
    }

    /**
     * Universal Label for the Universal Data Set.
     *
     * @return universal label identifying the metadata item.
     */
    public UniversalLabel getKey() {
        return key;
    }

    /**
     * Value associated with the Universal Data Set metadata item.
     *
     * @return value of the metadata item
     */
    public byte[] getValue() {
        return value.clone();
    }
}
