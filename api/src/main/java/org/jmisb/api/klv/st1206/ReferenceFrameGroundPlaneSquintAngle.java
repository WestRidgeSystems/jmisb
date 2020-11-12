package org.jmisb.api.klv.st1206;

/**
 * Reference Frame Ground Plane Squint Angle (ST1206 Tag 25).
 *
 * <p>The reference frame squint angle may be expressed as the Doppler cone angle between the ground
 * track vector and the radar’s line-of-sight vector or as a ground squint angle, which is the
 * corresponding angle projected onto the ground plane. Here, the ground plane is the geodetic plane
 * orthogonal to the ellipsoid normal at the SRP of the reference plane although it may also be
 * defined as the geocentric plane. The compliment for the current SAR image used in the creation of
 * the SAR coherent change products is defined in @link{GroundPlaneSquintAngle}.
 *
 * <p>See ST1206 Section 6.3 for more information.
 */
public class ReferenceFrameGroundPlaneSquintAngle extends Angle_90 {

    /**
     * Create from value.
     *
     * @param squintAngle squint angle in degrees.
     */
    public ReferenceFrameGroundPlaneSquintAngle(double squintAngle) {
        super(squintAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public ReferenceFrameGroundPlaneSquintAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Reference Frame Ground Plane Squint Angle";
    }
}
