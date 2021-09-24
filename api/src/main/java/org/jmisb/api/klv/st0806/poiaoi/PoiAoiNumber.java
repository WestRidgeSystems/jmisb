package org.jmisb.api.klv.st0806.poiaoi;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Point of Interest / Area of InterestNumber (ST 0806 POI Local Set Tag 1 and AOI Local Set Tag 1).
 */
public class PoiAoiNumber implements IRvtPoiAoiMetadataValue {
    private final int number;
    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = 65535;
    private static final int REQUIRED_BYTE_LENGTH = 2;

    /**
     * Create from value.
     *
     * @param poiaoi the point of interest or area of interest number, in the range [0, 65535].
     */
    public PoiAoiNumber(int poiaoi) {
        if (poiaoi > MAX_VALUE || poiaoi < MIN_VALUE) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0, 65535]");
        }
        this.number = poiaoi;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes two bytes representing 16-bit unsigned integer value.
     */
    public PoiAoiNumber(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTE_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a two byte unsigned integer");
        }
        this.number = PrimitiveConverter.toUint16(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(number);
    }

    /**
     * Get the POI / AOI number.
     *
     * @return the value of this POI/AOI number as an integer value.
     */
    public int getNumber() {
        return number;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", number);
    }

    @Override
    public final String getDisplayName() {
        return "POI/AOI Number";
    }
}
