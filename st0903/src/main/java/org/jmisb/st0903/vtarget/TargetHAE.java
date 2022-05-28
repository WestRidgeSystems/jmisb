package org.jmisb.st0903.vtarget;

import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.EncodingMode;

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
    private static final double MIN_VAL = -900;
    private static final double MAX_VAL = 19000;
    private static final int NUM_BYTES = 2;
    private static final double LEGACY_INT_RANGE = 65535.0; // 2^16-1
    private double value;

    /**
     * Create from value.
     *
     * @param altitude height above ellipsoid in meters [-900, 19000]
     */
    public TargetHAE(double altitude) {
        if (altitude < MIN_VAL || altitude > MAX_VAL) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [-900,19000]");
        }
        this.value = altitude;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note: this constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @deprecated use {@link #TargetHAE(byte[], EncodingMode)} to explicitly specify the encoding
     *     mode.
     */
    @Deprecated
    public TargetHAE(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 2-byte IMAPB in ST0903.4. Earlier versions used a variable
     * length (1-2 bytes) byte array to represent an integer in the range [0, 2^16-1] that was then
     * mapped into the range [-900.0,19000.0]. The two byte case could potentially represent either
     * kind of formatting, and which formatting applies can only be determined from the version in
     * the parent {@link org.jmisb.st0903.VmtiLocalSet}. The {@code encodingMode} parameter
     * determines whether to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public TargetHAE(byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            parseAsLegacy(bytes);
        } else {
            parseAsIMAP(bytes);
        }
    }

    private void parseAsLegacy(byte[] bytes) {
        int v = PrimitiveConverter.variableBytesToUint16(bytes);
        this.value = MIN_VAL + (v * (MAX_VAL - MIN_VAL) / LEGACY_INT_RANGE);
    }

    private void parseAsIMAP(byte[] bytes) throws IllegalArgumentException {
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
        return String.format("%.1f", value);
    }

    /**
     * Get the height above ellipsoid.
     *
     * @return the value in meters.
     */
    public double getHAE() {
        return this.value;
    }

    @Override
    public final String getDisplayName() {
        return "Target HAE";
    }
}
