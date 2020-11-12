package org.jmisb.api.klv.st1206;

/**
 * Grazing Angle (ST1206 Tag 1).
 *
 * <p>The grazing angle is needed to estimate the height of objects in the SAR imagery as SARMI
 * based on the lengths of shadows.
 *
 * <p>The grazing angle is defined as the angle between the line-of-sight vector from the Scene
 * Reference Point (SRP) to the sensor and the ground plane at the SRP. The SRP denotes the scene
 * center for the SAR imagery frames. Technically, the grazing angle from the sensor to each pixel
 * in a SAR image slightly differs. However, a good approximation is to assume the grazing angle to
 * the SRP represents that of the entire image. The grazing angle is not the same as the sensor
 * depression angle. For short slant ranges, the differences are negligible. However, for longer
 * slant ranges, the difference can be pronounced.
 *
 * <p>See ST1206 Section 6.2.1 for more information.
 */
public class GrazingAngle extends Angle_0_90 {

    /**
     * Create from value.
     *
     * @param grazingAngle grazing angle in degrees.
     */
    public GrazingAngle(double grazingAngle) {
        super(grazingAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public GrazingAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Grazing Angle";
    }
}
