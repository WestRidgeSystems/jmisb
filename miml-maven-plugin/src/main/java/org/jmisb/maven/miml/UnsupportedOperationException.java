package org.jmisb.maven.miml;

/**
 * Unsupported operation Exception.
 *
 * <p>This indicates that the required operation or type is not yet supported.
 */
public class UnsupportedOperationException extends RuntimeException {

    public UnsupportedOperationException(String message) {
        super(message);
    }
}
