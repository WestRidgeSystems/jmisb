package org.jmisb.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Platform Fuel Remaining (ST 0601 Item 58).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Remaining fuel on airborne platform. Metered as fuel weight remaining.
 *
 * <p>Platform Fuel Remaining indicates the current weight of fuel present on the platform.
 *
 * <p>Map 0..(2^16)-1 to 0..10000 Kilograms
 *
 * <p>1 kilogram = 2.20462262 pounds
 *
 * <p>Resolution: ~0.16 kilograms
 *
 * </blockquote>
 */
public class PlatformFuelRemaining implements IUasDatalinkValue {
    private final double kilograms;
    private static final double FLOAT_RANGE = 10000.0;
    private static final double INT_RANGE = 65535.0;

    /**
     * Create from value.
     *
     * @param mass The value in kilograms.
     */
    public PlatformFuelRemaining(double mass) {
        if ((mass < 0.0) || (mass > 10000.0)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [0,10000]");
        }

        this.kilograms = mass;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public PlatformFuelRemaining(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.kilograms = (intVal / INT_RANGE) * FLOAT_RANGE;
    }

    /**
     * Get the value in kilograms.
     *
     * @return The value in kilograms
     */
    public double getKilograms() {
        return kilograms;
    }

    @Override
    public byte[] getBytes() {
        int intVal = (int) Math.round((kilograms / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2fkg", this.kilograms);
    }

    @Override
    public String getDisplayName() {
        return "Platform Fuel Remaining";
    }
}
