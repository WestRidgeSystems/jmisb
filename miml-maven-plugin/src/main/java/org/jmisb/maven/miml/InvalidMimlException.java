package org.jmisb.maven.miml;

/**
 * Invalid MIML Exception.
 *
 * <p>This indicates that the parsing introduced some context that is not supported by the grammar.
 */
public class InvalidMimlException extends RuntimeException {

    public InvalidMimlException(String message) {
        super(message);
    }
}
