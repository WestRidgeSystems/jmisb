package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/** Shared implementation for AircraftMGRSZone and FrameCentreMGRSZone. */
abstract class AbstractMGRSZone implements IRvtMetadataValue {
    private static final int REQUIRED_NUM_BYTES = 1;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 60;

    private final String label;
    private final short v;

    /**
     * Create from value.
     *
     * @param name the display name for the specific implementation
     * @param value integer value, in the range 1 to 60.
     */
    public AbstractMGRSZone(String name, int value) {
        if ((value > MAX_VALUE) || (value < MIN_VALUE)) {
            throw new IllegalArgumentException(this.getDisplayName() + " valid range is [1,60].");
        }
        label = name;
        v = (short) value;
    }

    /**
     * Create from encoded bytes.
     *
     * @param name the display name for the specific implementation
     * @param bytes Encoded byte array, of length 1 byte.
     */
    public AbstractMGRSZone(String name, byte[] bytes) {
        if (bytes.length != REQUIRED_NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a one byte unsigned integer.");
        }
        label = name;
        v = (short) PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes(v);
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
     * Get the MGRS Zone.
     *
     * @return MGRS Zone as an integer.
     */
    public final int getZone() {
        return v;
    }
}
