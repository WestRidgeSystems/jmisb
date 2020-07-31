package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Transmission Frequency (Item 132).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Radio frequency used to transmit the Motion Imagery
 *
 * <p>Resolution: 2 bytes = 4 MHz, 3 bytes = 15.625 KHz
 *
 * <p>The Radio Frequency used to transmit the UAS Motion Imagery from the platform to the ground
 * station or satellite uplink.
 *
 * </blockquote>
 */
public class TransmissionFrequency implements IUasDatalinkValue {
    private static double MIN_VAL = 1.0;
    private static double MAX_VAL = 99999.0;
    private static int RECOMMENDED_BYTES = 3;
    private static int MAX_BYTES = 4;
    private double frequency;

    /**
     * Create from value.
     *
     * @param frequency transmission frequency in MHz. Valid range is [1,99999]
     */
    public TransmissionFrequency(double frequency) {
        if (frequency < MIN_VAL || frequency > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [1,99999]");
        }
        this.frequency = frequency;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes IMAPB Encoded byte array
     */
    public TransmissionFrequency(byte[] bytes) {
        if (bytes.length > MAX_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " cannot be longer than " + MAX_BYTES + " bytes");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.frequency = decoder.decode(bytes);
    }

    /**
     * Get frequency.
     *
     * @return the transmission frequency in MHz
     */
    public double getFrequency() {
        return this.frequency;
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, RECOMMENDED_BYTES);
        return encoder.encode(this.frequency);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.2fMHz", this.frequency);
    }

    @Override
    public String getDisplayName() {
        return "Transmission Frequency";
    }
}
