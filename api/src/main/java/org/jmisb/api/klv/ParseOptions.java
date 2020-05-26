package org.jmisb.api.klv;

/**
 * Flag options for parsing.
 */
public enum ParseOptions
{
    /**
     * Whether to only log in the event of buffer overrun.
     * 
     * Not setting this flag means to throw in the event of buffer overrun.
     */
    LOG_ON_OVERRUN,
    
    /**
     * Whether to only log if the checksum is present but incorrect.
     * 
     * Not setting this flag means to throw in the event of invalid checksum.
     */
    LOG_ON_CHECKSUM_FAIL,
    
    /**
     * Whether to only log if the checksum is required but missing.
     * 
     * Not setting this flag means to throw if the checksum is required for
     * whatever if being parsed.
     * 
     * This flag has no effect if the checksum is optional.
     */
    LOG_ON_CHECKSUM_MISSING,
    
    /**
     * Whether to only log if an invalid field encoding is encountered.
     * 
     * This could be a field that has the wrong length, or has values that are
     * out of range.
     * 
     * Not setting this means to throw in the event of invalid field, which will
     * probably result in the whole message being ignored.
     */
    LOG_ON_INVALID_FIELD_ENCODING
}
