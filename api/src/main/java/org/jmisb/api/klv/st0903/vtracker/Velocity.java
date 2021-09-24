package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.LocVelAccPackKey;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Velocity (ST 0903 VTracker LS Tag 10).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * The velocity of the entity at the time of last detection.
 *
 * </blockquote>
 */
public class Velocity implements IVmtiMetadataValue, IVTrackItemMetadataValue, INestedKlvValue {
    private static final int VELOCITY_GROUP_LEN = 6;
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
    private static final FpEncoder VelocityEncoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
    private static final FpEncoder SigmaEncoder =
            new FpEncoder(MIN_SIGMA_VAL, MAX_SIGMA_VAL, NUM_BYTES);
    private static final FpEncoder RhoEncoder = new FpEncoder(MIN_RHO_VAL, MAX_RHO_VAL, NUM_BYTES);
    private VelocityPack value;

    /**
     * Create from value.
     *
     * @param velocity the velocity DLP (truncation) pack.
     */
    public Velocity(VelocityPack velocity) {
        this.value = velocity;
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note: this constructor only supports ST0903.4 and later.
     *
     * @param bytes The byte array of length 6, 12 or 18.
     * @deprecated use {@link #Velocity(byte[], EncodingMode)} to explicitly specify the encoding
     *     used.
     */
    @Deprecated
    public Velocity(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding for each element to 2-byte IMAPB in ST0903.4. Earlier versions
     * used a two-byte unsigned integer structure in the range [0, 2^16-1] that was then mapped into
     * the same ranges that the IMAPB encoding uses. Which formatting applies can only be determined
     * from the ST0903 version in the parent {@link org.jmisb.api.klv.st0903.VmtiLocalSet}. The
     * {@code encodingMode} parameter determines whether to parse using the legacy encoding or
     * current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes The byte array of length 6, 12 or 18.
     * @param encodingMode which encoding mode the {@code bytes} parameter uses
     */
    public Velocity(byte[] bytes, EncodingMode encodingMode) {
        if (encodingMode.equals(EncodingMode.LEGACY)) {
            parseAsLegacy(bytes);
        } else {
            parseAsIMAP(bytes);
        }
    }

    private void parseAsLegacy(byte[] bytes) {
        switch (bytes.length) {
            case VELOCITY_GROUP_LEN:
                {
                    double east = legacyVelocityDecoder(bytes, 0);
                    double north = legacyVelocityDecoder(bytes, 2);
                    double up = legacyVelocityDecoder(bytes, 4);
                    value = new VelocityPack(east, north, up);
                    break;
                }
            case VELOCITY_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double east = legacyVelocityDecoder(bytes, 0);
                    double north = legacyVelocityDecoder(bytes, 2);
                    double up = legacyVelocityDecoder(bytes, 4);
                    double sigEast = legacySigmaDecoder(bytes, 6);
                    double sigNorth = legacySigmaDecoder(bytes, 8);
                    double sigUp = legacySigmaDecoder(bytes, 10);
                    value = new VelocityPack(east, north, up, sigEast, sigNorth, sigUp);
                    break;
                }
            case VELOCITY_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double east = legacyVelocityDecoder(bytes, 0);
                    double north = legacyVelocityDecoder(bytes, 2);
                    double up = legacyVelocityDecoder(bytes, 4);
                    double sigEast = legacySigmaDecoder(bytes, 6);
                    double sigNorth = legacySigmaDecoder(bytes, 8);
                    double sigUp = legacySigmaDecoder(bytes, 10);
                    double rhoEastNorth = legacyRhoDecoder(bytes, 12);
                    double rhoEastUp = legacyRhoDecoder(bytes, 14);
                    double rhoNorthUp = legacyRhoDecoder(bytes, 16);
                    value =
                            new VelocityPack(
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

    private double legacyVelocityDecoder(byte[] bytes, int offset) {
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

    private void parseAsIMAP(byte[] bytes) throws IllegalArgumentException {
        switch (bytes.length) {
            case VELOCITY_GROUP_LEN:
                {
                    double east = VelocityEncoder.decode(bytes, 0);
                    double north = VelocityEncoder.decode(bytes, 2);
                    double up = VelocityEncoder.decode(bytes, 4);
                    value = new VelocityPack(east, north, up);
                    break;
                }
            case VELOCITY_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
                {
                    double east = VelocityEncoder.decode(bytes, 0);
                    double north = VelocityEncoder.decode(bytes, 2);
                    double up = VelocityEncoder.decode(bytes, 4);
                    double sigEast = SigmaEncoder.decode(bytes, 6);
                    double sigNorth = SigmaEncoder.decode(bytes, 8);
                    double sigUp = SigmaEncoder.decode(bytes, 10);
                    value = new VelocityPack(east, north, up, sigEast, sigNorth, sigUp);
                    break;
                }
            case VELOCITY_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
                {
                    double east = VelocityEncoder.decode(bytes, 0);
                    double north = VelocityEncoder.decode(bytes, 2);
                    double up = VelocityEncoder.decode(bytes, 4);
                    double sigEast = SigmaEncoder.decode(bytes, 6);
                    double sigNorth = SigmaEncoder.decode(bytes, 8);
                    double sigUp = SigmaEncoder.decode(bytes, 10);
                    double rhoEastNorth = RhoEncoder.decode(bytes, 12);
                    double rhoEastUp = RhoEncoder.decode(bytes, 14);
                    double rhoNorthUp = RhoEncoder.decode(bytes, 16);
                    value =
                            new VelocityPack(
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
        return "Velocity";
    }

    @Override
    public String getDisplayableValue() {
        return "[Velocity]";
    }

    /**
     * Get the Target Velocity.
     *
     * @return the velocity as a packed structure.
     */
    public VelocityPack getVelocity() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        if (hasRequiredValues()) {
            chunks.add(VelocityEncoder.encode(value.getEast()));
            chunks.add(VelocityEncoder.encode(value.getNorth()));
            chunks.add(VelocityEncoder.encode(value.getUp()));
            len += VELOCITY_GROUP_LEN;
            if (hasStandardDeviations()) {
                chunks.add(SigmaEncoder.encode(value.getSigEast()));
                chunks.add(SigmaEncoder.encode(value.getSigNorth()));
                chunks.add(SigmaEncoder.encode(value.getSigUp()));
                len += STANDARD_DEVIATIONS_GROUP_LEN;
                if (hasCorrelations()) {
                    chunks.add(RhoEncoder.encode(value.getRhoEastNorth()));
                    chunks.add(RhoEncoder.encode(value.getRhoEastUp()));
                    chunks.add(RhoEncoder.encode(value.getRhoNorthUp()));
                    len += CORRELATION_GROUP_LEN;
                }
            }
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    private boolean hasRequiredValues() {
        return (value.getEast() != null) && (value.getNorth() != null) && (value.getUp() != null);
    }

    private boolean hasCorrelations() {
        return (value.getRhoEastNorth() != null)
                && (value.getRhoEastUp() != null)
                && (value.getRhoNorthUp() != null);
    }

    private boolean hasStandardDeviations() {
        return (value.getSigEast() != null)
                && (value.getSigNorth() != null)
                && (value.getSigUp() != null);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<LocVelAccPackKey> keys = EnumSet.noneOf(LocVelAccPackKey.class);
        if (hasRequiredValues()) {
            keys.add(LocVelAccPackKey.east);
            keys.add(LocVelAccPackKey.north);
            keys.add(LocVelAccPackKey.up);
            if (hasStandardDeviations()) {
                keys.add(LocVelAccPackKey.sigEast);
                keys.add(LocVelAccPackKey.sigNorth);
                keys.add(LocVelAccPackKey.sigUp);
                if (hasCorrelations()) {
                    keys.add(LocVelAccPackKey.rhoEastNorth);
                    keys.add(LocVelAccPackKey.rhoEastUp);
                    keys.add(LocVelAccPackKey.rhoNorthUp);
                }
            }
        }
        return keys;
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        LocVelAccPackKey key = (LocVelAccPackKey) tag;
        switch (key) {
            case east:
                return new VmtiTextString("East", String.format("%.1fm/s", value.getEast()));
            case north:
                return new VmtiTextString("North", String.format("%.1fm/s", value.getNorth()));
            case up:
                return new VmtiTextString("Up", String.format("%.1fm/s", value.getUp()));
            case sigEast:
                return new VmtiTextString(
                        "Standard Deviation East", String.format("%.1fm/s", value.getSigEast()));
            case sigNorth:
                return new VmtiTextString(
                        "Standard Deviation North", String.format("%.1fm/s", value.getSigNorth()));
            case sigUp:
                return new VmtiTextString(
                        "Standard Deviation Up", String.format("%.1fm/s", value.getSigUp()));
            case rhoEastNorth:
                return new VmtiTextString(
                        "Cross Correlation East North",
                        String.format("%.2f", value.getRhoEastNorth()));
            case rhoEastUp:
                return new VmtiTextString(
                        "Cross Correlation East Up", String.format("%.2f", value.getRhoEastUp()));
            case rhoNorthUp:
                return new VmtiTextString(
                        "Cross Correlation North Up", String.format("%.2f", value.getRhoNorthUp()));
        }
        return null;
    }
}
