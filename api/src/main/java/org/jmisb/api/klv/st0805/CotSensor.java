/*
 * The MIT License
 *
 * Copyright 2021 West Ridge Systems.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jmisb.api.klv.st0805;

/**
 * CoT Sensor element.
 *
 * <p>This represents the data in a Sensor detail element.
 */
public class CotSensor extends CotElement {
    public Double sensorAzimuth;
    public Double sensorFov;
    public Double sensorVfov;
    public String sensorModel;
    public Double sensorRange;

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
