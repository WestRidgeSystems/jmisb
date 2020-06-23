package org.jmisb.api.klv.st0102;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.IMisbMessage;

/** Abstract base class for Security Metadata Local and Universal sets */
public abstract class SecurityMetadataMessage implements IMisbMessage {
    /** Map containing all data elements in the message */
    protected SortedMap<SecurityMetadataKey, ISecurityMetadataValue> map = new TreeMap<>();

    /**
     * Set a message field
     *
     * @param key The key
     * @param value The value
     */
    public void setField(SecurityMetadataKey key, ISecurityMetadataValue value) {
        map.put(key, value);
    }

    /**
     * Get a message field
     *
     * @param key The key
     * @return The value
     */
    public ISecurityMetadataValue getField(SecurityMetadataKey key) {
        return map.get(key);
    }

    /**
     * Get the available message keys
     *
     * @return Collection of the keys in the security metadata message.
     */
    public Collection<SecurityMetadataKey> getKeys() {
        return map.keySet();
    }
}
