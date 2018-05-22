package org.jmisb.api.common;

/**
 * Indicates an error occurred during metadata parsing
 */
public class KlvParseException extends Exception
{
    public KlvParseException(String message)
    {
        super(message);
    }
}
