package org.jmisb.api.klv.st1206;

/**
 * Range Layover Angle Relative to True North (ST1206 Tag 13).
 *
 * <p>The range layover relative to True North is in the direction perpendicular to the sensor
 * ground track angle at the aperture center. Hence, for a left-looking sensor, the layover angle is
 * the ground track angle plus ninety degrees. Conversely, for a right-looking sensor, the range
 * layover angle is the ground track angle minus ninety degrees.
 *
 * <p>See ST1206 Section 6.2.10 for more information.
 */
public class RangeLayoverAngleRelativeToTrueNorth extends Angle_360 {

    /**
     * Create from value.
     *
     * @param layoverAngle range layover angle relative to true north, in degrees.
     */
    public RangeLayoverAngleRelativeToTrueNorth(double layoverAngle) {
        super(layoverAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public RangeLayoverAngleRelativeToTrueNorth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Range Layover Angle Relative to True North";
    }
}
