package org.jmisb.api.klv.st1206;

/**
 * Reference Frame Range Direction Angle Relative to True North (ST 1206 Tag 26).
 *
 * <p>The reference frame range direction angle relative to True North is the down range direction
 * angle of the reference SAR image used in the creation of the SAR coherent change products. The
 * compliment for the current SAR image used in the creation of the SAR coherent change products is
 * defined in @link{RangeDirectionAngleRelativeToTrueNorth}.
 *
 * <p>See ST 1206 Section 6.3 for more information.
 */
public class ReferenceFrameRangeDirectionAngleRelativeToTrueNorth extends Angle_360 {

    /**
     * Create from value.
     *
     * @param rangeDirectionAngle range direction angle relative to true north, in degrees.
     */
    public ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(double rangeDirectionAngle) {
        super(rangeDirectionAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Reference Frame Range Direction Angle Relative to True North";
    }
}
