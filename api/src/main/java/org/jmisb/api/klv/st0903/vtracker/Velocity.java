package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Velocity (ST 0903 VTracker LS Tag 10).
 * <p>
 * From ST0903:
 * <blockquote>
 * The velocity of the entity at the time of last detection.
 * </blockquote>
 */
public class Velocity implements IVmtiMetadataValue
{
    private static final int VELOCITY_GROUP_LEN = 6;
    private static final int STANDARD_DEVIATIONS_GROUP_LEN = 6;
    private static final int CORRELATION_GROUP_LEN = 6;
    private static final FpEncoder VelocityEncoder = new FpEncoder(-900.0, 900.0, 2);
    private static final FpEncoder SigmaEncoder = new FpEncoder(0, 650.0, 2);
    private static final FpEncoder RhoEncoder = new FpEncoder(-1, 1, 2);
    private VelocityPack value;

    /**
     * Create from value
     *
     * @param velocity the velocity DLP (truncation) pack.
     */
    public Velocity(VelocityPack velocity)
    {
        this.value = velocity;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public Velocity(byte[] bytes)
    {
        switch (bytes.length)
        {
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
                value = new VelocityPack(east, north, up, sigEast, sigNorth, sigUp, rhoEastNorth, rhoEastUp, rhoNorthUp);
                break;
            }
            default:
               throw new IllegalArgumentException(this.getDisplayName() + " length must match one of 6, 12 or 18");
        }
    }

    @Override
    public final String getDisplayName()
    {
        return "Velocity";
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Velocity]";
    }

    /**
     * Get the Target Velocity.
     *
     * @return the velocity as a packed structure.
     */
    public VelocityPack getVelocity()
    {
        return value;
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        if ((value.getEast() != null) && (value.getNorth() != null) && (value.getUp() != null))
        {
            chunks.add(VelocityEncoder.encode(value.getEast()));
            chunks.add(VelocityEncoder.encode(value.getNorth()));
            chunks.add(VelocityEncoder.encode(value.getUp()));
            len += VELOCITY_GROUP_LEN;
            if ((value.getSigEast() != null) && (value.getSigNorth() != null) && (value.getSigUp() != null))
            {
                chunks.add(SigmaEncoder.encode(value.getSigEast()));
                chunks.add(SigmaEncoder.encode(value.getSigNorth()));
                chunks.add(SigmaEncoder.encode(value.getSigUp()));
                len += STANDARD_DEVIATIONS_GROUP_LEN;
                if ((value.getRhoEastNorth()!= null) && (value.getRhoEastUp()!= null) && (value.getRhoNorthUp()!= null))
                {
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
