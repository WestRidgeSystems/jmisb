package org.jmisb.api.klv.st0601;

/**
 * Target Error Estimate - LE90 (ST 0601 Item 46).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Lateral error 90 (LE90) is the estimated error distance in the vertical (or lateral) direction.
 *
 * <p>Specifies the interval of 90% probability in the local vertical direction.
 *
 * <p>Map 0..(2^16-1) to 0..4095 meters
 *
 * <p>Resolution: ~0.0625 meters
 *
 * </blockquote>
 */
public class TargetErrorEstimateLe90 extends UasDatalinkTargetErrorEstimate {
    /**
     * Create from value.
     *
     * @param meters The value in meters, in the range [0..4095]
     */
    public TargetErrorEstimateLe90(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public TargetErrorEstimateLe90(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Error LE90";
    }
}
