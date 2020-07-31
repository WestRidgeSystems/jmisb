package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;

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
public class Acceleration implements IVmtiMetadataValue {
    private static final int ACCELERATION_GROUP_LEN = 6;
    private static final int STANDARD_DEVIATIONS_GROUP_LEN = 6;
    private static final int CORRELATION_GROUP_LEN = 6;
    private static final FpEncoder AccelerationEncoder = new FpEncoder(-900.0, 900.0, 2);
    private static final FpEncoder SigmaEncoder = new FpEncoder(0, 650.0, 2);
    private static final FpEncoder RhoEncoder = new FpEncoder(-1, 1, 2);
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
     * @param bytes The byte array of length 1
     */
    public Acceleration(byte[] bytes) {
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
