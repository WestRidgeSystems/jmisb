package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Zoom Percentage (Tag 134).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * For a variable zoom system, the percentage of zoom.
 *
 * <p>Resolution: 1 byte = 1%, 2 bytes = .0039%
 *
 * <p>Percentage of Zoom of the sensor system.
 *
 * <p>Includes both digital and optical zoom
 *
 * <p>0% means no zoom, 100% means fully zoomed
 *
 * </blockquote>
 */
public class ZoomPercentage implements IUasDatalinkValue {
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 100.0;
    private static int RECOMMENDED_BYTES = 2;
    private static int MAX_BYTES = 4;
    private double percentage;

    /**
     * Create from value
     *
     * @param percentage Zoom percentage. Valid range is [0,100]
     */
    public ZoomPercentage(double percentage) {
        if (percentage < MIN_VAL || percentage > MAX_VAL) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,100]");
        }
        this.percentage = percentage;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, 4 bytes maximum
     */
    public ZoomPercentage(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.percentage = decoder.decode(bytes);
    }

    /**
     * Get percentage.
     *
     * @return the zoom percentage
     */
    public double getPercentage() {
        return this.percentage;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES);
        return encoder.encode(this.percentage);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f%%", this.percentage);
    }

    @Override
    public final String getDisplayName() {
        return "Zoom Percentage";
    }
}
