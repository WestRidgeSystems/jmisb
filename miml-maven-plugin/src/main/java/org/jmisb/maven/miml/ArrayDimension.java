package org.jmisb.maven.miml;

/** POJO class for an array dimension. */
public class ArrayDimension {
    private Integer maxLength = null;

    /**
     * The maximum length for the array.
     *
     * @return the maximum length.
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * Set the maximum length for the array.
     *
     * @param maxLength the maximum length.
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
