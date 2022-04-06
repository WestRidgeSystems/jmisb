package org.jmisb.st0601;

/**
 * Differential Pressure (ST 0601 Item 49).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Differential Pressure. Differential pressure at aircraft location. Measured as the
 * Stagnation/impact/total pressure minus static pressure
 *
 * <p>Map 0..(2^16-1) to 0..5000
 *
 * <p>Resolution: ~0.08 millibar
 *
 * </blockquote>
 */
public class DifferentialPressure extends UasPressureMillibars {
    /**
     * Create from value.
     *
     * @param pressureMillibars The pressure in millibars, in range [0, 5000]
     */
    public DifferentialPressure(double pressureMillibars) {
        super(pressureMillibars);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public DifferentialPressure(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Differential Pressure";
    }
}
