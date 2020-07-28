package org.jmisb.api.klv.st1909;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ST 1909 Metadata key/value pairs.
 *
 * <p>This is used to transfer metadata between the source format (e.g. ST0601) and the renderer.
 */
public class MetadataItems {
    private Map<MetadataKey, String> items = new HashMap<>();

    /**
     * The keys in the metadata set.
     *
     * @return set of MetadataKey that have some data set (which might be an empty string).
     */
    public Set<MetadataKey> getItemKeys() {
        return items.keySet();
    }

    /**
     * Get the value for a specified key.
     *
     * @param key the key
     * @return the string value, including any required prefix/suffix.
     */
    public String getValue(MetadataKey key) {
        return items.get(key);
    }

    /**
     * Add an item to the set of metadata key/value paris.
     *
     * @param key the key
     * @param value the string value, including any required prefix/suffix.
     */
    public void addItem(MetadataKey key, String value) {
        items.put(key, value);
    }

    /**
     * Basic validity check for the metadata.
     *
     * @return true if there is some valid data to be shown.
     */
    public boolean isValid() {
        // TODO: could be more sophisticated
        return (!items.isEmpty());
    }

    /** Clear the metadata. */
    public void clear() {
        items.clear();
    }
}
