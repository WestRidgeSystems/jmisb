package org.jmisb.st0809;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Device Longitude (ST 0809 Local Set Item 5).
 *
 * <p>Longitude of the sensor/station reporting the data (see SMPTE RP 210)
 */
public class DeviceLongitude implements IMeteorologicalMetadataValue {
    private static final String DISPLAY_NAME = "Device Longitude";
    private final double degrees;

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180].
     */
    public DeviceLongitude(double degrees) {
        if ((degrees < -180) || (degrees > 180)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 8-byte floating point value
     */
    public DeviceLongitude(byte[] bytes) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is an 8-byte floating point value");
        }

        this.degrees = PrimitiveConverter.toFloat64(bytes);
    }

    /**
     * Get the longitude in degrees.
     *
     * @return Degrees in range [-180,180]
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
