package org.jmisb.api.klv.st0601;

/**
 * Target Error Estimate - LE90 (ST 0601 tag 46)
 * <p>
 * From ST:
 * <blockquote>
 * Lateral error 90 (LE90) is the estimated error distance in the vertical (or lateral) direction.
 * <p>
 * Specifies the interval of 90% probability in the local vertical direction.
 * <p>
 * Map 0..(2^16-1) to 0..4095 metres
 * <p>
 * Resolution: ~0.0625 metres
 * </blockquote>
 */
public class TargetErrorEstimateLe90 extends UasDatalinkTargetErrorEstimate
{
    /**
     * Create from value
     *
     * @param metres The value in metres, in the range [0..4095]
     */
    public TargetErrorEstimateLe90(double metres)
    {
        super(metres);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public TargetErrorEstimateLe90(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Target Error LE90";
    }
}
