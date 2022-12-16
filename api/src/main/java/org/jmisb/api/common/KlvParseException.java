package org.jmisb.api.common;

import java.util.Arrays;

/** Indicates an error occurred during metadata parsing. */
public class KlvParseException extends Exception {
    private final byte[] buffer;

    /**
     * Constructor.
     *
     * @param message the exception message, which should describe the problem in human readable
     *     terms
     */
    public KlvParseException(String message) {
        super(message);
        this.buffer = null;
    }

    /**
     * Constructor.
     *
     * @param exception the inner exception object
     * @param buffer the buffer corresponding to the inner exception
     */
    public KlvParseException(KlvParseException exception, byte[] buffer) {
        super(exception);
        this.buffer = Arrays.copyOf(buffer, buffer.length);
    }

    /** @return a copy of the {@code byte[]} corresponding to the wrapped exception. */
    public byte[] getBuffer() {
        return Arrays.copyOf(buffer, buffer.length);
    }
}
