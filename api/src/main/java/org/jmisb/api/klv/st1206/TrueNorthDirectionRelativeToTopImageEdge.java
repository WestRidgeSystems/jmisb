package org.jmisb.api.klv.st1206;

/**
 * True North Direction Relative to Top Image Edge (ST1206 Tag 12).
 *
 * <p>The True North direction relative to top image edge angle may be computed by knowing the down
 * range direction relative to True North based on sensor telemetry and the True North direction
 * with respect to pixels in the image. True North direction with respect to pixels in the image is
 * defined relative to the top edge of the image as measured in a counter-clockwise fashion assuming
 * the origin (0,0) is the top-left corner of the image. Hence, regardless of the image orientation,
 * the range direction with respect to True North may be computed from the ground track and ground
 * squint angles. It is important that these quantities be defined at the SRP rather than at the
 * sensor location as the difference between True North at the SRP and sensor location increases
 * closer to the poles. The angle between True North and the image itself will always be the same
 * value if the SAR motion imagery orientation is fixed or will vary from frame-to-frame if the
 * orientation varies from frame-to-frame.
 *
 * <p>See ST1206 Section 6.2.9 for more information.
 */
public class TrueNorthDirectionRelativeToTopImageEdge extends Angle_360 {

    /**
     * Create from value.
     *
     * @param direction true north direction relative to top image edge, in degrees.
     */
    public TrueNorthDirectionRelativeToTopImageEdge(double direction) {
        super(direction);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public TrueNorthDirectionRelativeToTopImageEdge(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "True North Direction Relative to Top Image Edge";
    }
}
