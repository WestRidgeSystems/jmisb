package org.jmisb.api.klv;

/** Local Data Set field, comprised of a tag (the key) and its value */
public class LdsField {
    private final int tag;

    private final byte[] data;

    /**
     * Create an LDS field
     *
     * @param tag The integer tag
     * @param data Byte array containing the value
     */
    public LdsField(int tag, byte[] data) {
        this.tag = tag;
        this.data = data.clone();
    }

    /**
     * Get the tag
     *
     * @return The integer tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * Get the value
     *
     * @return The value stored as a byte array
     */
    public byte[] getData() {
        return data.clone();
    }
}
