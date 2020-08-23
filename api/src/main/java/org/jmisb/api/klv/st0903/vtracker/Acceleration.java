package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Acceleration (ST 0903 VTracker LS Tag 11).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * The acceleration of the entity at the time of last detection.
 *
 * </blockquote>
 */
public class Acceleration implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    private static final int ACCELERATION_GROUP_LEN = 6;
    private static final int STANDARD_DEVIATIONS_GROUP_LEN = 6;
    private static final int CORRELATION_GROUP_LEN = 6;
    private static final int NUM_BYTES = 2;
    private static final double MIN_VAL = -900.0;
    private static final double MAX_VAL = 900.0;
    private static final double MIN_SIGMA_VAL = 0.0;
    private static final double MAX_SIGMA_VAL = 650.0;
    private static final double MIN_RHO_VAL = -1.0;
    private static final double MAX_RHO_VAL = 1.0;
    private static final int LEGACY_INT_RANGE = 65535;
    private static final FpEncoder AccelerationEncoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
    private static final FpEncoder SigmaEncoder =
            new FpEncoder(MIN_SIGMA_VAL, MAX_SIGMA_VAL, NUM_BYTES);
    private static final FpEncoder RhoEncoder = new FpEncoder(MIN_RHO_VAL, MAX_RHO_VAL, NUM_BYTES);
    private AccelerationPack value;

    /**
     * Create from value.
     *
     * @param acceleration the acceleration DLP (truncation) pack.
     */
    public Acceleration(AccelerationPack acceleration) {
        this.value = acceleration;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note: this constructor only supports ST0903.4 and later.
     *
     * @param bytes The byte array of length 6, 12 or 18.
     * @deprecated use {@link #Acceleration(byte[], EncodingMode)} to explicitly specify the
     *     encoding used.
     */
    @Deprecated
    public Acceleration(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding for each element to 2-byte IMAPB in ST0903.4. Earlier versions
     * used a two-byte unsigned integer structure in the range [0, 2^16-1]that was then mapped into
     * the same ranges that the IMAPB encoding uses. Which formatting applies can only be determined
     * from the ST0903 version in the parent {@link org.jmisb.api.klv.st0903.VmtiLocalSet}. The
     * {@code compatibilityMode} parameter determines whether to parse using the legacy encoding or
     * current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes The byte array of length 6, 12 or 18.
     * @param encodingMode which encoding mode the {@code bytes} parameter uses
     */
    public Acceleration(byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            parseAsLegacy(bytes);
        } else {
            parseAsIMAPB(bytes);
        }
    }

    private void parseAsLegacy(byte[] bytes) throws IllegalArgumentException {
        switch (bytes.length) {
            case ACCELERATION_GROUP_LEN:
                {
                    double east = legacyAccelerationDecoder(bytes, 0);
                    double north = legacyAccelerationDecoder(bytes, 2);
                    double up = legacyAccelerationDecoder(bytes, 4);
                    value = new AccelerationPack(east, north, up);
                    break;
                }
            case ACCELERATION_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double east = legacyAccelerationDecoder(bytes, 0);
                    double north = legacyAccelerationDecoder(bytes, 2);
                    double up = legacyAccelerationDecoder(bytes, 4);
                    double sigEast = legacySigmaDecoder(bytes, 6);
                    double sigNorth = legacySigmaDecoder(bytes, 8);
                    double sigUp = legacySigmaDecoder(bytes, 10);
                    value = new AccelerationPack(east, north, up, sigEast, sigNorth, sigUp);
                    break;
                }
            case ACCELERATION_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double east = legacyAccelerationDecoder(bytes, 0);
                    double north = legacyAccelerationDecoder(bytes, 2);
                    double up = legacyAccelerationDecoder(bytes, 4);
                    double sigEast = legacySigmaDecoder(bytes, 6);
                    double sigNorth = legacySigmaDecoder(bytes, 8);
                    double sigUp = legacySigmaDecoder(bytes, 10);
                    double rhoEastNorth = legacyRhoDecoder(bytes, 12);
                    double rhoEastUp = legacyRhoDecoder(bytes, 14);
                    double rhoNorthUp = legacyRhoDecoder(bytes, 16);
                    value =
                            new AccelerationPack(
                                    east,
                                    north,
                                    up,
                                    sigEast,
                                    sigNorth,
                                    sigUp,
                                    rhoEastNorth,
                                    rhoEastUp,
                                    rhoNorthUp);
                    break;
                }
            default:
                throw new IllegalArgumentException(
                        this.getDisplayName() + " length must match one of 6, 12 or 18");
        }
    }

    private double legacyAccelerationDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.variableBytesToUint16(byteSubset);
        return MIN_VAL + (v * (MAX_VAL - MIN_VAL) / LEGACY_INT_RANGE);
    }

    private double legacySigmaDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.variableBytesToUint16(byteSubset);
        return MIN_SIGMA_VAL + (v * (MAX_SIGMA_VAL - MIN_SIGMA_VAL) / LEGACY_INT_RANGE);
    }

    private double legacyRhoDecoder(byte[] bytes, int offset) {
        byte[] byteSubset = new byte[] {bytes[offset], bytes[offset + 1]};
        int v = PrimitiveConverter.variableBytesToUint16(byteSubset);
        return MIN_RHO_VAL + (v * (MAX_RHO_VAL - MIN_RHO_VAL) / LEGACY_INT_RANGE);
    }

    private void parseAsIMAPB(byte[] bytes) throws IllegalArgumentException {
        switch (bytes.length) {
            case ACCELERATION_GROUP_LEN:
                {
                    double east = AccelerationEncoder.decode(bytes, 0);
                    double north = AccelerationEncoder.decode(bytes, 2);
                    double up = AccelerationEncoder.decode(bytes, 4);
                    value = new AccelerationPack(east, north, up);
                    break;
                }
            case ACCELERATION_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double east = AccelerationEncoder.decode(bytes, 0);
                    double north = AccelerationEncoder.decode(bytes, 2);
                    double up = AccelerationEncoder.decode(bytes, 4);
                    double sigEast = SigmaEncoder.decode(bytes, 6);
                    double sigNorth = SigmaEncoder.decode(bytes, 8);
                    double sigUp = SigmaEncoder.decode(bytes, 10);
                    value = new AccelerationPack(east, north, up, sigEast, sigNorth, sigUp);
                    break;
                }
            case ACCELERATION_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double east = AccelerationEncoder.decode(bytes, 0);
                    double north = AccelerationEncoder.decode(bytes, 2);
                    double up = AccelerationEncoder.decode(bytes, 4);
                    double sigEast = SigmaEncoder.decode(bytes, 6);
                    double sigNorth = SigmaEncoder.decode(bytes, 8);
                    double sigUp = SigmaEncoder.decode(bytes, 10);
                    double rhoEastNorth = RhoEncoder.decode(bytes, 12);
                    double rhoEastUp = RhoEncoder.decode(bytes, 14);
                    double rhoNorthUp = RhoEncoder.decode(bytes, 16);
                    value =
                            new AccelerationPack(
                                    east,
                                    north,
                                    up,
                                    sigEast,
                                    sigNorth,
                                    sigUp,
                                    rhoEastNorth,
                                    rhoEastUp,
                                    rhoNorthUp);
                    break;
                }
            default:
                throw new IllegalArgumentException(
                        this.getDisplayName() + " length must match one of 6, 12 or 18");
        }
    }

    @Override
    public final String getDisplayName() {
        return "Acceleration";
    }

    @Override
    public String getDisplayableValue() {
        return "[Acceleration]";
    }

    /**
     * Get the Target Acceleration.
     *
     * @return the acceleration as a packed structure.
     */
    public AccelerationPack getAcceleration() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        if ((value.getEast() != null) && (value.getNorth() != null) && (value.getUp() != null)) {
            chunks.add(AccelerationEncoder.encode(value.getEast()));
            chunks.add(AccelerationEncoder.encode(value.getNorth()));
            chunks.add(AccelerationEncoder.encode(value.getUp()));
            len += ACCELERATION_GROUP_LEN;
            if ((value.getSigEast() != null)
                    && (value.getSigNorth() != null)
                    && (value.getSigUp() != null)) {
                chunks.add(SigmaEncoder.encode(value.getSigEast()));
                chunks.add(SigmaEncoder.encode(value.getSigNorth()));
                chunks.add(SigmaEncoder.encode(value.getSigUp()));
                len += STANDARD_DEVIATIONS_GROUP_LEN;
                if ((value.getRhoEastNorth() != null)
                        && (value.getRhoEastUp() != null)
                        && (value.getRhoNorthUp() != null)) {
                    chunks.add(RhoEncoder.encode(value.getRhoEastNorth()));
                    chunks.add(RhoEncoder.encode(value.getRhoEastUp()));
                    chunks.add(RhoEncoder.encode(value.getRhoNorthUp()));
                    len += CORRELATION_GROUP_LEN;
                }
            }
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }
}
