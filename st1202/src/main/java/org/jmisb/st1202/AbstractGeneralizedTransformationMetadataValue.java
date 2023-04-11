package org.jmisb.st1202;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Abstract shared representation for 4 byte float values.
 *
 * <p>Since so much of the local set is common, this is a single representation.
 */
public abstract class AbstractGeneralizedTransformationMetadataValue
        implements IGeneralizedTransformationMetadataValue {

    private final float value;
    private final String displayName;

    /**
     * Create from value.
     *
     * @param value the value as a float.
     * @param displayName the human-readable display name for the value
     */
    protected AbstractGeneralizedTransformationMetadataValue(
            final float value, final String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @param displayName the human-readable display name for the value
     * @throws KlvParseException if the byte array is not of the correct length
     */
    protected AbstractGeneralizedTransformationMetadataValue(byte[] bytes, final String displayName)
            throws KlvParseException {
        try {
            this.value = PrimitiveConverter.toFloat32(bytes);
            this.displayName = displayName;
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(getDisplayName() + " is of length 4 bytes");
        }
    }

    @Override
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Get the value.
     *
     * @return value as a floating point value.
     */
    public float getValue() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f", this.value);
    }
}
