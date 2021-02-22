package org.jmisb.api.common;

/** Indicates an error occurred during metadata parsing. */
public class KlvParseException extends Exception {
    /**
     * Constructor.
     *
     * @param message the exception message, which should describe the problem in human readable
     *     terms
     */
    public KlvParseException(String message) {
        super(message);
    }
}
