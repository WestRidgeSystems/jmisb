package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st1201.FpEncoder;

/** Shard base class for horizontal and vertical field of view. */
public abstract class VmtiFieldOfView implements IVmtiMetadataValue {
    private static double MIN_VAL = 0;
    private static double MAX_VAL = 180;
    private static int NUM_BYTES = 2;
    private double value;

    /**
     * Create from value.
     *
     * @param fov field of view in degrees.
     */
    public VmtiFieldOfView(double fov) {
        if (fov < MIN_VAL || fov > MAX_VAL) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,180]");
        }
        this.value = fov;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public VmtiFieldOfView(byte[] bytes) {
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
        return String.format("%.1f\u00B0", value);
    }

    /**
     * Get the field of view.
     *
     * @return the value in degrees.
     */
    public double getFieldOfView() {
        return this.value;
    }
}
