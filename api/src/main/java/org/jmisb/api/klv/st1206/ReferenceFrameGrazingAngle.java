package org.jmisb.api.klv.st1206;

/**
 * Reference Frame Grazing Angle (ST1206 Tag 24).
 *
 * <p>The reference frame grazing angle is used in SAR coherent change products as SARMI.
 *
 * <p>The reference frame grazing angle is defined as the angle between the line-of-sight vector
 * from the SRP to the sensor and the ground plane at the SRP for the collected reference frame. The
 * complement for the current SAR image used in the creation of the SAR coherent change products is
 * defined in @link{GrazingAngle}.
 *
 * <p>See ST1206 Section 6.3 for more information.
 */
public class ReferenceFrameGrazingAngle extends Angle_0_90 {

    /**
     * Create from value.
     *
     * @param grazingAngle grazing angle in degrees.
     */
    public ReferenceFrameGrazingAngle(double grazingAngle) {
        super(grazingAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public ReferenceFrameGrazingAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Reference Frame Grazing Angle";
    }
}
