package org.jmisb.api.klv.st0102;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.IKlvTag;
import org.jmisb.api.klv.IMisbMessage;

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

    @Override
    public ISecurityMetadataValue getField(IKlvTag key)
    {
        return map.get((SecurityMetadataKey)key);
    }

    @Override
    public Set<SecurityMetadataKey> getTags()
    {
        return map.keySet();
    }
}
