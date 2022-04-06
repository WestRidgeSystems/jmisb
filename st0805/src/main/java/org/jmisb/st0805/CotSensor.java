package org.jmisb.st0805;

/**
 * CoT Sensor element.
 *
 * <p>This represents the data in a Sensor detail element.
 */
public class CotSensor extends CotElement {
    private Double sensorAzimuth;
    private Double sensorFov;
    private Double sensorVfov;
    private String sensorModel;
    private Double sensorRange;

    /**
     * Get sensor azimuth.
     *
     * @return sensor azimuth in degrees, or null if not set
     */
    public Double getAzimuth() {
        return sensorAzimuth;
    }

    /**
     * Set sensor azimuth.
     *
     * @param azimuth sensor azimuth in degrees
     */
    public void setAzimuth(double azimuth) {
        this.sensorAzimuth = azimuth;
    }

    /**
     * Get sensor field of view.
     *
     * <p>This is the horizontal direction.
     *
     * @return field of view in degrees, or null if not set
     */
    public Double getFov() {
        return sensorFov;
    }

    /**
     * Set sensor field of view.
     *
     * @param sensorFov field of view in degrees
     */
    public void setFov(double sensorFov) {
        this.sensorFov = sensorFov;
    }

    /**
     * Get sensor vertical field of view.
     *
     * @return vertical field of view in degrees, or null if not set
     */
    public Double getVerticalFov() {
        return sensorVfov;
    }

    /**
     * Set sensor vertical field of view.
     *
     * @param sensorVfov vertical field of view in degrees
     */
    public void setVerticalFov(double sensorVfov) {
        this.sensorVfov = sensorVfov;
    }

    /**
     * Get sensor model (e.g., "EOW", "EON").
     *
     * @return currently active sensor
     */
    public String getModel() {
        return sensorModel;
    }

    /**
     * Set sensor model.
     *
     * @param model currently active sensor
     */
    public void setModel(String model) {
        this.sensorModel = model;
    }

    /**
     * Get sensor range.
     *
     * @return slant range in meters, or null if not set
     */
    public Double getRange() {
        return sensorRange;
    }

    /**
     * Set sensor range.
     *
     * @param range slant range in meters
     */
    public void setRange(double range) {
        this.sensorRange = range;
    }

    @Override
    void writeAsXML(StringBuilder sb) {
        sb.append("<sensor");
        if (getAzimuth() != null) {
            writeAttribute(sb, "azimuth", getAzimuth());
        }
        if (getFov() != null) {
            writeAttribute(sb, "fov", getFov());
        }
        if (getVerticalFov() != null) {
            writeAttribute(sb, "vfov", getVerticalFov());
        }
        if (getModel() != null) {
            writeAttribute(sb, "model", getModel());
        }
        if (getRange() != null) {
            writeAttribute(sb, "range", getRange());
        }
        sb.append("/>");
    }
}
