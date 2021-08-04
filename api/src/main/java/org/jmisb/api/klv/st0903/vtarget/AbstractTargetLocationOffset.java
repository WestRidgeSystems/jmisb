package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared superclass for TargetLocationOffsetLatitude (Tag 10) and TargetLocationOffsetLongitude
 * (Tag 11).
 */
public abstract class AbstractTargetLocationOffset implements IVmtiMetadataValue {

    protected static final double MIN_VAL = -19.2;
    protected static final double MAX_VAL = 19.2;
    protected static final int NUM_BYTES = 3;
    private static final int LEGACY_ERROR_INDICATOR = -8388608;
    private static final int LEGACY_INT_RANGE = 8388607; // 2^23 - 1
    protected double value;

    /**
     * Create from value.
     *
     * @param offset location offset in degrees. Valid range is [-19.2, 19.2]
     */
    public AbstractTargetLocationOffset(double offset) {
        if (offset < MIN_VAL || offset > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [-19.2,19.2]");
        }
        this.value = offset;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>This constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @deprecated use {@link #AbstractTargetLocationOffset(byte[], EncodingMode)} to explicitly
     *     specify the encoding used in {@code bytes}.
     */
    @Deprecated
    public AbstractTargetLocationOffset(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 3-byte IMAPB in ST0903.4. Earlier versions used a variable
     * length (1-3 bytes) byte array to represent an integer in the range [-2^23-1, 2^23-1]that was
     * then mapped into the range [-19.2,19.2]. The three byte case could potentially represent
     * either kind of formatting, and which formatting applies can only be determined from the
     * version in the parent {@link org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code
     * compatibilityMode} parameter determines whether to parse using the legacy encoding or current
     * encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public AbstractTargetLocationOffset(byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            parseAsLegacy(bytes);
        } else {
            parseAsIMAPB(bytes);
        }
    }

    private void parseAsLegacy(byte[] bytes) {
        int v = PrimitiveConverter.toInt32(bytes);
        if (v == LEGACY_ERROR_INDICATOR) {
            this.value = Double.NaN;
        } else {
            this.value = v * MAX_VAL / LEGACY_INT_RANGE;
        }
    }

    private void parseAsIMAPB(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 3) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is three byte IMAPB as of ST0903.4");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
        return encoder.encode(this.value, OutOfRangeBehaviour.Clamp);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.5f\u00B0", value);
    }

    /**
     * Get the value.
     *
     * @return the value in offset degrees.
     */
    public double getValue() {
        return this.value;
    }
}
