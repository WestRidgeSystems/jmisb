package org.jmisb.api.klv.st0805;

/**
 * Represents a Platform Position CoT message
 */
public class PlatformPosition extends CotMessage
{
    public double sensorAzimuth;
    public double sensorFov;
    public double sensorVfov;
    public String sensorModel;
    public double sensorRange;

    public double getSensorAzimuth()
    {
        return sensorAzimuth;
    }

    public void setSensorAzimuth(double sensorAzimuth)
    {
        this.sensorAzimuth = sensorAzimuth;
    }

    public double getSensorFov()
    {
        return sensorFov;
    }

    public void setSensorFov(double sensorFov)
    {
        this.sensorFov = sensorFov;
    }

    public double getSensorVfov()
    {
        return sensorVfov;
    }

    public void setSensorVfov(double sensorVfov)
    {
        this.sensorVfov = sensorVfov;
    }

    public String getSensorModel()
    {
        return sensorModel;
    }

    public void setSensorModel(String sensorModel)
    {
        this.sensorModel = sensorModel;
    }

    public double getSensorRange()
    {
        return sensorRange;
    }

    public void setSensorRange(double sensorRange)
    {
        this.sensorRange = sensorRange;
    }
}
