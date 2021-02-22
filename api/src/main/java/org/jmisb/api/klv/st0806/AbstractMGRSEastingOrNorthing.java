package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared implementation for AircraftMGRSEasting, AircraftMGRSNorthing, FrameCentreMGRSEasting and
 * FrameCentreMGRSNorthing.
 */
abstract class AbstractMGRSEastingOrNorthing implements IRvtMetadataValue {
    private static final int REQUIRED_NUM_BYTES = 3;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99999;

    private final String label;
    private final int v;

    /**
     * Create from value.
     *
     * @param name the display name for the specific implementation
     * @param value integer value, in the range 0 to 99999.
     */
    public AbstractMGRSEastingOrNorthing(String name, int value) {
        if ((value > MAX_VALUE) || (value < MIN_VALUE)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " valid range is [0,99999].");
        }
        label = name;
        v = value;
    }

    /**
     * Create from encoded bytes.
     *
     * @param name the display name for the specific implementation
     * @param bytes Encoded byte array, of length 3 bytes.
     */
    public AbstractMGRSEastingOrNorthing(String name, byte[] bytes) {
        if (bytes.length != REQUIRED_NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a three byte unsigned integer.");
        }
        label = name;
        v = PrimitiveConverter.toUint24(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint24ToBytes(v);
    }

    @Override
    public String getDisplayableValue() {
        return "" + v;
    }

    @Override
    public final String getDisplayName() {
        return label;
    }

    /**
     * Get the MGRS Easting or Northing Value.
     *
     * @return MGRS Easting or Northign as an integer.
     */
    public final int getValue() {
        return v;
    }
}
