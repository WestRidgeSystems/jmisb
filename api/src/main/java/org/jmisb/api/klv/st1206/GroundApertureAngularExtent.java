package org.jmisb.api.klv.st1206;

/**
 * Ground Aperture Angular Extent (ST1206 Tag 15).
 *
 * <p>The aperture angular extent, Δθ, is the angle swept in cross-range as the sensor traverses the
 * synthetic aperture used to generate a single SAR image. It may be defined in the slant plane, in
 * which case it is the angle between the line-of-sight vector at the first pulse and the
 * line-of-sight vector at the last pulse forming the aperture. The aperture angular extent may also
 * be defined in the ground plane, Δθ<sub>g</sub>, giving a sense of the physical geometry and
 * angular distance flown to cover a single SAR image.
 *
 * <p>See ST1206 Section 6.2.11 for more information.
 */
public class GroundApertureAngularExtent extends Angle_0_90 {

    /**
     * Create from value.
     *
     * @param groundApertureAngle ground aperture angle in degrees.
     */
    public GroundApertureAngularExtent(double groundApertureAngle) {
        super(groundApertureAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public GroundApertureAngularExtent(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Ground Aperture Angular Extent";
    }
}
