package org.jmisb.st1602;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Z-Order (ST 1602 Composite Imaging Local Set Tag 18).
 *
 * <p>Z-Order defines the order of images (or stack) along the Z-axis (the axis perpendicular to the
 * horizontal (left/right) axis, and the vertical (up/down) axis. A value of zero (0) is reserved
 * for the bottom-most image in the stack. Different images cannot have the same Z-order.
 *
 * <p>This item is mandatory within the Composite Imaging Local Set.
 */
public class ZOrder implements ICompositeImagingValue {
    private final int z;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * @param zOrder The Z-Order value in the valid range 0 to 255.
     */
    public ZOrder(int zOrder) {
        if ((zOrder < MIN_VALUE) || (zOrder > MAX_VALUE)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in the range [0,255]");
        }
        this.z = zOrder;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes byte encoding of the unsigned integer value (single byte)
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public ZOrder(byte[] bytes) throws KlvParseException {
        try {
            this.z = PrimitiveConverter.toUint8(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException("Unable to deserialise Z-Order: " + ex.getMessage());
        }
    }

    /**
     * Get the Z-Order value.
     *
     * @return The Z-Order
     */
    public int getValue() {
        return z;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes((short) z);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", z);
    }

    @Override
    public final String getDisplayName() {
        return "Z-Order";
    }
}
