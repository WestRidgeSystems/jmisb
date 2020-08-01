package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Frag Circle Radius (ST 0806 Tag 6).
 *
 * <p>Size of fragmentation circle selected by the aircrew.
 *
 * <p>Units are meters.
 *
 * <p>Resolution: 1 meter.
 */
public class FragCircleRadius implements IRvtMetadataValue {
    private final int radius;

    private static long MIN_VALUE = 0;
    private static long MAX_VALUE = 65535;
    private static final int REQUIRED_BYTE_LENGTH = 2;

    /**
     * Create from value.
     *
     * @param radius radius in meters/second. Legal values are in [0, 65535].
     */
    public FragCircleRadius(int radius) {
        if (radius > MAX_VALUE || radius < MIN_VALUE) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [0, 65535]");
        }
        this.radius = radius;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes two bytes representing unsigned integer value.
     */
    public FragCircleRadius(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTE_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a two byte unsigned integer");
        }
        radius = PrimitiveConverter.toUint16(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(radius);
    }

    /**
     * Get the radius.
     *
     * @return The radius in meters
     */
    public int getRadius() {
        return radius;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dm", radius);
    }

    @Override
    public final String getDisplayName() {
        return "Frag Circle Radius";
    }
}
