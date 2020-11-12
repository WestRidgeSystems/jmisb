package org.jmisb.api.klv.st1206;

/**
 * Ground Track Angle (ST1206 Tag 16).
 *
 * <p>The ground track angle is the heading over ground the sensor travels. It is important to note
 * that the ground track angle is assumed to be with respect to the SRP rather than the sensor
 * location. While this differentiation may seem insignificant, the difference between the
 * coordinate system at the SRP and the platform becomes pronounced near the poles.
 *
 * <p>The ground track angle may either be the desired, ideal ground track angle or the actual
 * ground track angle, depending on the sensor.
 *
 * <p>See ST1206 Section 6.2.13 for more information.
 */
public class GroundTrackAngle extends Angle_360 {

    /**
     * Create from value.
     *
     * @param groundTrackAngle ground track angle in degrees.
     */
    public GroundTrackAngle(double groundTrackAngle) {
        super(groundTrackAngle);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public GroundTrackAngle(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Ground Track Angle";
    }
}
