package org.jmisb.identicalpropertiesshader;

/**
 * Model for a single properties file.
 *
 * <p>This file is an implementation detail, and is not part of the public API.
 */
class PropertiesFile {
    private final long propertiesChecksum;
    private final byte[] content;
    private final long timestamp;

    public PropertiesFile(byte[] content, long propertiesChecksum, long timestamp) {
        this.content = content;
        this.propertiesChecksum = propertiesChecksum;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getPropertiesChecksum() {
        return propertiesChecksum;
    }

    public byte[] getContent() {
        return content;
    }
}
