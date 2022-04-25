package org.jmisb.st0903;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;
import org.jmisb.st0903.shared.EncodingMode;

/** Shard base class for horizontal and vertical field of view. */
public abstract class VmtiFieldOfView implements IVmtiMetadataValue {
    private static final double MIN_VAL = 0;
    private static final double MAX_VAL = 180;
    private static final double LEGACY_INT_RANGE = 65535.0; // 2^16-1
    private static final int NUM_BYTES = 2;
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
     * <p>Note this constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @deprecated use {@link #VmtiFieldOfView(byte[], EncodingMode)} instead to specify the
     *     encoding mode
     */
    @Deprecated
    public VmtiFieldOfView(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 2-byte IMAPB in ST0903.4. Earlier versions used a two-byte
     * unsigned integer structure in the range [0, 2^16-1] that was then mapped into the range [0,
     * 180.0] degrees. Which formatting applies can only be determined from the ST0903 version in
     * this {@link org.jmisb.st0903.VmtiLocalSet}. The {@code encodingMode} parameter determines
     * whether to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public VmtiFieldOfView(byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            parseAsLegacy(bytes);
        } else {
            parseAsIMAPB(bytes);
        }
    }

    private void parseAsLegacy(byte[] bytes) throws IllegalArgumentException {
        int v = PrimitiveConverter.variableBytesToUint16(bytes);
        this.value = v * MAX_VAL / LEGACY_INT_RANGE;
    }

    private void parseAsIMAPB(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte IMAPB as of ST0903.4");
        }
        FpEncoder decoder =
                new FpEncoder(MIN_VAL, MAX_VAL, bytes.length, OutOfRangeBehaviour.Default);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES, OutOfRangeBehaviour.Default);
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
