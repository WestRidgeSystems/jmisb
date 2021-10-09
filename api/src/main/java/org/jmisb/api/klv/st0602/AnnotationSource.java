package org.jmisb.api.klv.st0602;

import org.jmisb.core.klv.PrimitiveConverter;

public class AnnotationSource implements IAnnotationMetadataValue {

    private final long flags;

    /**
     * Create from value.
     *
     * @param flags bitmask of flag values per ST 0602.
     */
    public AnnotationSource(long flags) {
        this.flags = flags;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     */
    public AnnotationSource(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    "Annotation Source encoding is a four byte bit mask");
        }
        flags = PrimitiveConverter.toUint32(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(flags);
    }

    @Override
    public String getDisplayName() {
        return "Annotation Source";
    }

    @Override
    public String getDisplayableValue() {
        // TODO:
        return String.format("0x%08x", flags);
    }
}
