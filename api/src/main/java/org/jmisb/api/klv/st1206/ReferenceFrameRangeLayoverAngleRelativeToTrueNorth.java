package org.jmisb.api.klv.st1206;

/**
 * Reference Frame Range Layover Angle Relative to True North (ST1206 Tag 27).
 *
 * <p>The reference frame range layover angle relative to True North is the direction angle
 * perpendicular to the sensor ground track angle at the aperture center of the reference SAR image
 * used in the creation of the SAR coherent products. The compliment for the current SAR image used
 * in the creation of the SAR coherent change products is defined
 * in @link{RangeLayoverAngleRelativeToTrueNorth}. .
 *
 * <p>See ST1206 Section 6.3 for more information.
 */
public class ReferenceFrameRangeLayoverAngleRelativeToTrueNorth extends Angle_360 {

    /**
     * Create from value.
     *
     * @param layoverAngle range layover angle relative to true north, in degrees.
     */
    public ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(double layoverAngle) {
        super(layoverAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Reference Frame Range Layover Angle Relative to True North";
    }
}
