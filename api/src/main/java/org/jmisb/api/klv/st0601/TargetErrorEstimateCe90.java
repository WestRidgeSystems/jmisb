package org.jmisb.api.klv.st0601;

/**
 * Target Error Estimate - CE90 (ST 0601 tag 45)
 * <p>
 * From ST:
 * <blockquote>
 * Circular error 90 (CE90) is the estimated error distance in the horizontal direction.
 * <p>
 * Specifies the radius of 90% probability on a plane tangent to the earth’s surface.
 * <p>
 * Map 0..(2^16-1) to 0..4095 metres
 * <p>
 * Resolution: ~0.0625 metres
 * </blockquote>
 */
public class TargetErrorEstimateCe90 extends UasDatalinkTargetErrorEstimate
{
    /**
     * Create from value
     *
     * @param metres The value in metres, in the range [0..4095]
     */
    public TargetErrorEstimateCe90(double metres)
    {
        super(metres);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public TargetErrorEstimateCe90(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Target Error CE90";
    }
}
