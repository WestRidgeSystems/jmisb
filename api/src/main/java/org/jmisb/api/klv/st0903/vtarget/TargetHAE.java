package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Target HAE (ST0903 VTarget Pack Tag 12).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Height of the target expressed as height in meters above the WGS84 ellipsoid (HAE). Conversion:
 * IMAPB(-900, 19000, 2).
 *
 * </blockquote>
 */
public class TargetHAE implements IVmtiMetadataValue {
    protected static final double MIN_VAL = -900;
    protected static final double MAX_VAL = 19000;
    protected static final int NUM_BYTES = 2;
    protected double value;

    /**
     * Create from value
     *
     * @param altitude height above ellipsoid in metres [-900, 19000]
     */
    public TargetHAE(double altitude) {
        if (altitude < MIN_VAL || altitude > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [-19.2,19.2]");
        }
        this.value = altitude;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetHAE(byte[] bytes) {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte IMAPB as of ST0903.4");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
        return encoder.encode(this.value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f", value);
    }

    /**
     * Get the height above ellipsoid.
     *
     * @return the value in metres.
     */
    public double getHAE() {
        return this.value;
    }

    @Override
    public final String getDisplayName() {
        return "Target HAE";
    }
}
