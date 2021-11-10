package org.jmisb.api.klv.st0601;

/**
 * Target Error Estimate - CE90 (ST 0601 Item 45).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Circular error 90 (CE90) is the estimated error distance in the horizontal direction.
 *
 * <p>Specifies the radius of 90% probability on a plane tangent to the earth’s surface.
 *
 * <p>Map 0..(2^16-1) to 0..4095 meters
 *
 * <p>Resolution: ~0.0625 meters
 *
 * </blockquote>
 */
public class TargetErrorEstimateCe90 extends UasDatalinkTargetErrorEstimate {
    /**
     * Create from value.
     *
     * @param meters The value in meters, in the range [0..4095]
     */
    public TargetErrorEstimateCe90(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     */
    public TargetErrorEstimateCe90(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Error CE90";
    }
}
