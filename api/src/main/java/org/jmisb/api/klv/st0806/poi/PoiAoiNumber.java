package org.jmisb.api.klv.st0806.poi;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Point of Interest Number (ST 0806 POI Local Set Tag 1).
 */
public class PoiAoiNumber implements IRvtPoiMetadataValue
{
    private final int number;

    private static long MIN_VALUE = 0;
    private static long MAX_VALUE = 65535;
    private final static int REQUIRED_BYTE_LENGTH = 2;

    /**
     * Create from value
     *
     * @param poi the point of interest number, in the range [0, 65535].
     */
    public PoiAoiNumber(int poi)
    {
        if (poi > MAX_VALUE || poi < MIN_VALUE)
        {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0, 65535]");
        }
        this.number = poi;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes two bytes representing 16-bit unsigned integer value.
     */
    public PoiAoiNumber(byte[] bytes)
    {
        if (bytes.length != REQUIRED_BYTE_LENGTH)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is a two byte unsigned integer");
        }
        this.number = PrimitiveConverter.toUint16(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint16ToBytes(number);
    }

    /**
     * Get the POI number.
     * @return the value of this POI/AOI number as an integer value.
     */
    public int getNumber()
    {
        return number;
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%d", number);
    }

    @Override
    public final String getDisplayName()
    {
        return "POI/AOI Number";
    }

}
