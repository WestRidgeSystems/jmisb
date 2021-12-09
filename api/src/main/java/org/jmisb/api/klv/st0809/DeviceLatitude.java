package org.jmisb.api.klv.st0809;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Device Latitude (ST 0809 Local Set Item 4).
 *
 * <p>Latitude of the sensor/station reporting the data (see SMPTE RP 210)
 */
public class DeviceLatitude implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Device Latitude";
    private final double degrees;

    /**
     * Create from value.
     *
     * @param degrees Latitude, in degrees [-90,90].
     */
    public DeviceLatitude(double degrees) {
        if ((degrees < -90) || (degrees > 90)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-90,90]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Latitude, encoded as a 8-byte floating point value
     */
    public DeviceLatitude(byte[] bytes) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is an 8-byte floating point value");
        }

        this.degrees = PrimitiveConverter.toFloat64(bytes);
    }

    /**
     * Get the latitude in degrees.
     *
     * @return Degrees in range [-90,90]
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public final String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float64ToBytes(degrees);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.6f\u00B0", degrees);
    }
}
