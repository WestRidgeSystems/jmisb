package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * On-board MI Storage Percent Full (Item 120).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Amount of on-board Motion Imagery storage used as a percentage of the total storage
 *
 * <p>Used with "On-board MI Storage Capacity" (Item 133), if available, to determine remaining
 * recording storage space.
 *
 * <p>Resolution: 2 bytes = 0.004 percent, 3 bytes = 1.5E-5 percent
 *
 * </blockquote>
 */
public class OnBoardMiStoragePercentFull implements IUasDatalinkValue {
    private static double MIN_VAL = 0.0;
    private static double MAX_VAL = 100.0;
    private static int RECOMMENDED_BYTES = 2;
    private static int MAX_BYTES = 3;
    private double percentage;

    /**
     * Create from value
     *
     * @param percentage MI storage level. Valid range is [0,100]
     */
    public OnBoardMiStoragePercentFull(double percentage) {
        if (percentage < MIN_VAL || percentage > MAX_VAL) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,100]");
        }
        this.percentage = percentage;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes IMAPB Encoded byte array, max length three bytes
     */
    public OnBoardMiStoragePercentFull(byte[] bytes) {
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
     * @return the percentage of storage that has been used
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
        return String.format("%.001f%%", this.percentage);
    }

    @Override
    public final String getDisplayName() {
        return "On-board MI Storage Percent Full";
    }
}
