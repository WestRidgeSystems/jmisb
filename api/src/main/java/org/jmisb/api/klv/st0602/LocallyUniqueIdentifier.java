package org.jmisb.api.klv.st0602;

import org.jmisb.core.klv.PrimitiveConverter;

/** ST 0602 Annotation Identifier. */
public class LocallyUniqueIdentifier implements IAnnotationMetadataValue {
    private long id;

    /**
     * Create from value.
     *
     * @param id The identifier
     */
    public LocallyUniqueIdentifier(long id) {
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     */
    public LocallyUniqueIdentifier(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    "Locally Unique Identifier encoding is a four-byte unsigned integer");
        }
        id = PrimitiveConverter.toUint32(bytes);
    }

    /**
     * Get the unique identifier.
     *
     * @return The identifier value
     */
    public long getIdentifier() {
        return id;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(getIdentifier());
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", getIdentifier());
    }

    @Override
    public String getDisplayName() {
        return "Locally Unique Identifier";
    }
}
