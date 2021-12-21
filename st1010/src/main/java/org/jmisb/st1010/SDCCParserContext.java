package org.jmisb.st1010;

/**
 * Context for {@link SDCC} Parsing.
 *
 * <p>This is used by the {@link SDCCParser} implementation, and is an internal implementation
 * detail.
 */
class SDCCParserContext {

    private boolean sparseMode;
    private SDCC sdcc;

    /**
     * Get whether the byte array being parsed uses sparse mode.
     *
     * @return true if sparse mode is used, false if sparse mode is not used.
     */
    public boolean isSparseMode() {
        return sparseMode;
    }

    /**
     * Set whether the byte array being parsed uses spare mode.
     *
     * @param sparseMode true if sparse mode is used, false if sparse mode is not used.
     */
    public void setSparseMode(boolean sparseMode) {
        this.sparseMode = sparseMode;
    }

    /**
     * Get the SDCC matrix.
     *
     * @return the SDCC matrix.
     */
    public SDCC getSdcc() {
        return sdcc;
    }

    /**
     * Set the SDCC matrix.
     *
     * @param sdcc the matrix.
     */
    public void setSdcc(SDCC sdcc) {
        this.sdcc = sdcc;
    }
}
