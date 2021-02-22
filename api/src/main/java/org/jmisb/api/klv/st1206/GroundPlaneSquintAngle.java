package org.jmisb.api.klv.st1206;

/**
 * Ground Plane Squint Angle (ST1206 Tag 2).
 *
 * <p>From a collection perspective the squint angle may be expressed as the Doppler cone angle
 * between the ground track vector and the radar’s line-of-sight vector or as a ground squint angle,
 * which is the corresponding angle projected onto the ground plane. Here, the ground plane is the
 * geodetic plane orthogonal to the ellipsoid normal at the SRP, although it may also be defined as
 * the geocentric plane. Most often, the squint angle is defined relative to the sensor velocity
 * vector. However, it is technically defined as the ground-track angle to which the SAR imagery is
 * formed. It can be interpreted as the average velocity vector along the synthetic aperture, or
 * approximated from a single velocity measurement at some position in the aperture, typically the
 * SRP, barring the availability of the ground-track angle.
 *
 * <p>For the ground plane squint angle, forward of broadside is positive and aft of broadside is
 * negative.
 *
 * <p>*
 *
 * <p>The Look Direction metadata element and the Ground Plane Squint Angle metadata element shall
 * be present at all times.
 *
 * <p>See ST1206 Section 6.2.2 for more information.
 */
public class GroundPlaneSquintAngle extends Angle_90 {

    /**
     * Create from value.
     *
     * @param squintAngle squint angle in degrees.
     */
    public GroundPlaneSquintAngle(double squintAngle) {
        super(squintAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public GroundPlaneSquintAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Ground Plane Squint Angle";
    }
}
