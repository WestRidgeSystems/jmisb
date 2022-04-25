package org.jmisb.st0601;

/**
 * Airfield Barometric Pressure (ST 0601 Item 53).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Airfield Barometric Pressure. Local pressure at airfield of known height.
 *
 * <p>Map 0..(2^16-1) to 0..5000
 *
 * <p>Resolution: ~0.08 millibar
 *
 * </blockquote>
 */
public class AirfieldBarometricPressure extends UasPressureMillibars {
    /**
     * Create from value.
     *
     * @param pressureMillibars pressure in millibars
     */
    public AirfieldBarometricPressure(double pressureMillibars) {
        super(pressureMillibars);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 2
     */
    public AirfieldBarometricPressure(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Airfield Barometric Pressure";
    }
}
