package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/** ST 0602 Annotation Identifier. */
public class LocallyUniqueIdentifier implements IAnnotationMetadataValue {
    private long id;

    /**
     * Create from value.
     *
     * @param id The identifier in the range [0, 4294967295].
     * @throws KlvParseException if the length is not in range.
     */
    public LocallyUniqueIdentifier(long id) throws KlvParseException {
        if ((id < 0) || (id > 4294967295l)) {
            throw new KlvParseException(
                    "Valid range for Locally Unique Identifier is a four byte unsigned integer, got "
                            + id);
        }
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     * @throws KlvParseException if the length is not valid.
     */
    public LocallyUniqueIdentifier(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(
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
