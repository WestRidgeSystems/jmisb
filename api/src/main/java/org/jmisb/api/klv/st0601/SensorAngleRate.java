package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Shared implementation of Sensor Angle Rate.
 * <p>
 * This is used by Sensor Azimuth Rate, Sensor Elevation Rate, and Sensor Roll
 * Rate (ST 0601 tag 117, 118 and 119).
 */
public abstract class SensorAngleRate implements IUasDatalinkValue
{
    private static double MIN_VAL = -1000.0;
    private static double MAX_VAL = 1000.0;
    private static int RECOMMENDED_BYTES = 3;
    private static int MAX_BYTES = 4;
    private double angleRate;

    /**
     * Create from value
     *
     * @param rate Sensor angle rate in degrees per second. Valid range is [-1000,1000]
     */
    public SensorAngleRate(double rate)
    {
        if (rate < MIN_VAL || rate > MAX_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [-1000,1000]");
        }
        this.angleRate = rate;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public SensorAngleRate(byte[] bytes)
    {
        if (bytes.length > MAX_BYTES)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.angleRate = decoder.decode(bytes);
    }

    /**
     * Get the sensor angle rate
     * @return The density altitude in meters
     */
    public double getAngleRate()
    {
        return this.angleRate;
    }

    @Override
    public byte[] getBytes()
    {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES);
        return encoder.encode(this.angleRate);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.3f dps", this.angleRate);
    }
}
