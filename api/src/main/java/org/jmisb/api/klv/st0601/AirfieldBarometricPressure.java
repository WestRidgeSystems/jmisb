package org.jmisb.api.klv.st0601;

/**
 * Airfield Barometric Pressure (ST 0601 tag 53)
 * <p>
 * From ST:
 * <blockquote>
 * Airfield Barometric Pressure. Local pressure at airfield of known height.
 * <p>
 * Map 0..(2^16-1) to 0..5000
 * <p>
 * Resolution: ~0.08 millibar
 * </blockquote>
 */
public class AirfieldBarometricPressure extends UasPressureMillibars
{
    public AirfieldBarometricPressure(double pressureMillibars)
    {
        super(pressureMillibars);
    }

    public AirfieldBarometricPressure(byte[] bytes)
    {
        super(bytes);
    }
}
