package org.jmisb.cumulo;

/**
 * Coordinate frame definitions
 * <p>
 * EcefFrame is centered at the Earth's center of mass, where X intersects lat/lon (0, 0),
 * Y intersects lat/lon (0, 90), and the Z axis intersects the north pole, lat/lon (90, 0).
 * <p>
 * InertialFrame is centered at the position of the sensor and is oriented identically to
 * the EcefFrame.
 * <p>
 * NedFrame is a local North-East-Down coordinate system centered at the position of the
 * sensor.
 * <p>
 * CameraFrame is centered at the camera's optical center, with X pointing to the right on
 * the focal plane, Y pointing down, and Z pointing outward along the optical axis.
 * <p>
 * ImageFrame is centered at the upper-left corner of the image frame, with X pointed to
 * the right and Y pointed down in the image, and Z pointed out along the optical axis.
 * The units are in pixels.
 */
public enum CoordinateFrame {

    /** Earth-centered, Earth-fixed coordinate frame */
    EcefFrame(0),
    InertialFrame(1),
    NedFrame(2),
    CameraFrame(3),
    ImageFrame(4);

    private int code;

    private CoordinateFrame(int c)
    {
        code = c;
    }

    public int getCode()
    {
        return code;
    }
}
