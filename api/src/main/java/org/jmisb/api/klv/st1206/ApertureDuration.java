package org.jmisb.api.klv.st1206;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * SARMI Aperture Duration (ST 1206 Item 15).
 *
 * <p>The synthetic aperture duration is the length of the coherent processing period or the time
 * interval the radar beam illuminates the scene. This time is a function of both flight geometry
 * and RF carrier frequency. The aperture duration is the dominant contributor to latency due to
 * physically flying the aperture. It, however, accounts for neither the SAR image formation time,
 * which is small for real-time SARMI, nor the data transmission time from the sensor to the
 * end-user, which may be large when utilizing BLOS data links.
 */
public class ApertureDuration implements ISARMIMetadataValue {
    private long duration;
    private static long MIN_VALUE = 0;
    private static long MAX_VALUE = 4294967295L;

    /**
     * Create from value.
     *
     * @param apertureDuration The aperture duration in microseconds
     */
    public ApertureDuration(long apertureDuration) {
        if (apertureDuration < MIN_VALUE || apertureDuration > MAX_VALUE) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s value must be in range [%d, %d]",
                            this.getDisplayName(), MIN_VALUE, MAX_VALUE));
        }
        this.duration = apertureDuration;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4 bytes
     */
    public ApertureDuration(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is four byte unsigned integer");
        }
        duration = PrimitiveConverter.toUint32(bytes);
    }

    /**
     * Get the aperture duration.
     *
     * @return The aperture duration in microseconds.
     */
    public long getApertureDuration() {
        return duration;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(duration);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%dμs", this.duration);
    }

    @Override
    public final String getDisplayName() {
        return "Aperture Duration";
    }
}
