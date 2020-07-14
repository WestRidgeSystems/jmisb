package org.jmisb.api.klv.st0601;

/**
 * Static Pressure (ST 0601 Item 37).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Static Pressure. Static pressure at aircraft location. The static pressure is the pressure of the
 * air that surrounds the aircraft. Static pressure is measured by a sensor mounted out of the air
 * stream on the side of the fuselage. This is used with impact pressure to compute indicated
 * airspeed, true airspeed, and density altitude.
 *
 * <p>Map 0..(2^16-1) to 0..5000
 *
 * <p>Resolution: ~0.08 millibar
 *
 * </blockquote>
 */
public class StaticPressure extends UasPressureMillibars {
    public StaticPressure(double pressureMillibars) {
        super(pressureMillibars);
    }

    public StaticPressure(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Static Pressure";
    }
}
