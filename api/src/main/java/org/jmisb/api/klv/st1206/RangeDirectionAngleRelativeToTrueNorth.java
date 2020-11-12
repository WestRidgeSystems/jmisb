package org.jmisb.api.klv.st1206;

/**
 * Range Direction Angle Relative to True North (ST1206 Tag 11).
 *
 * <p>When estimating the height of objects based on the lengths of their shadows, it is
 * particularly important to measure the shadow in the direction of the sensor line-of-sight vector,
 * which does not necessarily align with any of the image axes. Inferring the shadow direction by
 * merely examining shadows in an image may be misleading, as complicated structures can create
 * unusual shadows. Note that for typical SARMI applications, whereby the SAR frames are rotating
 * due to sensor motion, the shadow direction is usually fixed. On the other hand, if image
 * post-processing were to fix the frame of orientation in the SARMI data for applications such as
 * chipping, or extracting an arbitrarily oriented chip from a larger frame because of bandwidth
 * constraints, the shadow angle would change from frame-to-frame and could rotate in a complete
 * circle.
 *
 * <p>See ST1206 Section 6.2.8 for more information.
 */
public class RangeDirectionAngleRelativeToTrueNorth extends Angle_360 {

    /**
     * Create from value.
     *
     * @param rangeDirectionAngle range direction angle relative to true north, in degrees.
     */
    public RangeDirectionAngleRelativeToTrueNorth(double rangeDirectionAngle) {
        super(rangeDirectionAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public RangeDirectionAngleRelativeToTrueNorth(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Range Direction Angle Relative to True North";
    }
}
