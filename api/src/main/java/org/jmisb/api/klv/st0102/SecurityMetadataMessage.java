package org.jmisb.api.klv.st0102;

import org.jmisb.api.klv.IMisbMessage;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Abstract base class for Security Metadata Local and Universal sets
 */
public abstract class SecurityMetadataMessage implements IMisbMessage
{
    /** Map containing all data elements in the message */
    protected SortedMap<SecurityMetadataKey, ISecurityMetadataValue> map = new TreeMap<>();

    /**
     * Set a message field
     * @param key The key
     * @param value The value
     */
    public void setField(SecurityMetadataKey key, ISecurityMetadataValue value)
    {
        map.put(key, value);
    }

    /**
     * Get a message field
     * @param key The key
     * @return The value
     */
    public ISecurityMetadataValue getField(SecurityMetadataKey key)
    {
        return map.get(key);
    }
}
