package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vtarget.dto.TargetLocationPack;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Target Location (ST0903 VTarget Pack Tag 17).
 * <p>
 * From ST0903:
 * <blockquote>
 * Provides detailed geo-positioning information for a target, optionally
 * including the standard deviation and correlation coefficients. This item is
 * of type Location which is a Defined-Length Truncation Pack. To specify the
 * geographic coordinates for a target when the VMTI LS is not embedded within a
 * MISB ST 0601 LS, Target Location VTarget Pack Tag 17 must be used in lieu of
 * Target Location Latitude Offset VTarget Pack Tag 10 and Target Location
 * Longitude Offset VTarget Pack Tag 11, since ST 0601 Target Latitude and
 * Target Longitude (Frame Center Coordinates) will not be specified. However,
 * even embedding the VMTI LS within a ST 0601 LS, Target Location may still be
 * used
 * </blockquote>
 */
public class TargetLocation implements IVmtiMetadataValue
{
    private TargetLocationPack value;
    private final int COORDINATES_GROUP_LEN = 10;
    private final int STANDARD_DEVIATIONS_GROUP_LEN = 6;
    private final int CORRELATION_GROUP_LEN = 6;
    private final FpEncoder LatEncoder = new FpEncoder(-90.0, 90.0, 4);
    private final FpEncoder LonEncoder = new FpEncoder(-180.0, 180.0, 4);
    private final FpEncoder HaeEncoder = new FpEncoder(-900.0, 19000.0, 2);
    private final FpEncoder SigmaEncoder = new FpEncoder(0, 650.0, 2);
    private final FpEncoder RhoEncoder = new FpEncoder(-1, 1, 2);

    /**
     * Create from value.
     *
     * @param targetLocation the packed target location structure.
     */
    public TargetLocation(TargetLocationPack targetLocation)
    {
        value = targetLocation;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array.
     */
    public TargetLocation(byte[] bytes)
    {
        switch (bytes.length) {
            case COORDINATES_GROUP_LEN:
            {
                double lat = LatEncoder.decode(bytes, 0);
                double lon = LonEncoder.decode(bytes, 4);
                double hae = HaeEncoder.decode(bytes, 8);
                value = new TargetLocationPack(lat, lon, hae);
            }
                break;
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN:
            {
                double lat = LatEncoder.decode(bytes, 0);
                double lon = LonEncoder.decode(bytes, 4);
                double hae = HaeEncoder.decode(bytes, 8);
                double sigEast = SigmaEncoder.decode(bytes, 10);
                double sigNorth = SigmaEncoder.decode(bytes, 12);
                double sigUp = SigmaEncoder.decode(bytes, 14);
                value = new TargetLocationPack(lat, lon, hae, sigEast, sigNorth, sigUp);
            }
                break;
            case COORDINATES_GROUP_LEN + STANDARD_DEVIATIONS_GROUP_LEN + CORRELATION_GROUP_LEN:
            {
                double lat = LatEncoder.decode(bytes, 0);
                double lon = LonEncoder.decode(bytes, 4);
                double hae = HaeEncoder.decode(bytes, 8);
                double sigEast = SigmaEncoder.decode(bytes, 10);
                double sigNorth = SigmaEncoder.decode(bytes, 12);
                double sigUp = SigmaEncoder.decode(bytes, 14);
                double rhoEastNorth = RhoEncoder.decode(bytes, 16);
                double rhoEastUp = RhoEncoder.decode(bytes, 18);
                double rhoNorthUp = RhoEncoder.decode(bytes, 20);
                value = new TargetLocationPack(lat, lon, hae, sigEast, sigNorth, sigUp, rhoEastNorth, rhoEastUp, rhoNorthUp);
            }
                break;
            default:
                throw new IllegalArgumentException(this.getDisplayName() + " length must match one of 10, 16 or 22");
        }
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        if ((value.getLat() != null) && (value.getLon() != null) && (value.getHae() != null))
        {
            chunks.add(LatEncoder.encode(value.getLat()));
            chunks.add(LonEncoder.encode(value.getLon()));
            chunks.add(HaeEncoder.encode(value.getHae()));
            len += COORDINATES_GROUP_LEN;
        }
        if ((value.getSigEast() != null) && (value.getSigNorth() != null) && (value.getSigUp() != null))
        {
            chunks.add(SigmaEncoder.encode(value.getSigEast()));
            chunks.add(SigmaEncoder.encode(value.getSigNorth()));
            chunks.add(SigmaEncoder.encode(value.getSigUp()));
            len += STANDARD_DEVIATIONS_GROUP_LEN;
        }
        if ((value.getRhoEastNorth()!= null) && (value.getRhoEastUp()!= null) && (value.getRhoNorthUp()!= null))
        {
            chunks.add(RhoEncoder.encode(value.getRhoEastNorth()));
            chunks.add(RhoEncoder.encode(value.getRhoEastUp()));
            chunks.add(RhoEncoder.encode(value.getRhoNorthUp()));
            len += CORRELATION_GROUP_LEN;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Location]";
    }

    @Override
    public final String getDisplayName()
    {
        return "Target Location";
    }

    /**
     * Get the Target Location.
     *
     * @return the target location as a packed structure.
     */
    public TargetLocationPack getTargetLocation()
    {
        return value;
    }
}
