package org.jmisb.api.klv.st0805;

/** Represents a Platform Position CoT message. */
public class PlatformPosition extends CotMessage {
    public double sensorAzimuth;
    public double sensorFov;
    public double sensorVfov;
    public String sensorModel;
    public double sensorRange;

    /**
     * Get sensor azimuth.
     *
     * @return sensor azimuth in degrees
     */
    public double getSensorAzimuth() {
        return sensorAzimuth;
    }

    /**
     * Set sensor azimuth.
     *
     * @param sensorAzimuth sensor azimuth in degrees
     */
    public void setSensorAzimuth(double sensorAzimuth) {
        this.sensorAzimuth = sensorAzimuth;
    }

    /**
     * Get sensor field of view.
     *
     * @return field of view in degrees
     */
    public double getSensorFov() {
        return sensorFov;
    }

    /**
     * Set sensor field of view.
     *
     * @param sensorFov field of view in degrees
     */
    public void setSensorFov(double sensorFov) {
        this.sensorFov = sensorFov;
    }

    /**
     * Get sensor vertical field of view.
     *
     * @return vertical field of view in degrees
     */
    public double getSensorVfov() {
        return sensorVfov;
    }

    /**
     * Set sensor vertical field of view.
     *
     * @param sensorVfov vertical field of view in degrees
     */
    public void setSensorVfov(double sensorVfov) {
        this.sensorVfov = sensorVfov;
    }

    /**
     * Get sensor model (e.g., "EOW", "EON").
     *
     * @return currently active sensor
     */
    public String getSensorModel() {
        return sensorModel;
    }

    /**
     * Set sensor model.
     *
     * @param sensorModel currently active sensor
     */
    public void setSensorModel(String sensorModel) {
        this.sensorModel = sensorModel;
    }

    /**
     * Get sensor range.
     *
     * @return slant range in meters
     */
    public double getSensorRange() {
        return sensorRange;
    }

    /**
     * Set sensor range.
     *
     * @param sensorRange slant range in meters
     */
    public void setSensorRange(double sensorRange) {
        this.sensorRange = sensorRange;
    }
}
