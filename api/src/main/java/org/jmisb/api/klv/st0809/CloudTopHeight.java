package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Cloud Top Height (ST 0809 Local Set Item 12).
 *
 * <p>Mean Sea Level (MSL) elevation of cloud deck in meters.
 */
public class CloudTopHeight implements IMeteorologicalMetadataValue {

    private float value;

    /**
     * Create from value.
     *
     * @param height height in meters relative to MSL.
     */
    public CloudTopHeight(float height) {
        this.value = height;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public CloudTopHeight(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Cloud Top Height";
    }

    /**
     * Get the height value.
     *
     * @return height in meters relative to MSL.
     */
    public float getHeight() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float32ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f m", this.value);
    }
}
