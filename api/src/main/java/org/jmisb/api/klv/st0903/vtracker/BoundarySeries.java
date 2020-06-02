package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vtarget.TargetLocation;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Boundary Series (ST0903 VTracker Local Set Tag 5).
 * <p>
 * From ST0903:
 * <blockquote>
 * BoundarySeries is of type BoundarySeries that specifies a bounding area or
 * volume, which encloses the full extent of VMTI detections for the entity. For
 * a simple, planar bounding box, the area will generally lie on the surface of
 * the Earth (although not necessarily, depending upon the Height values
 * provided) defining the “footprint” of the track. By specifying additional
 * vertices, enables describing complex, multifaceted volumes.
 * </blockquote>
 */
public class BoundarySeries implements IVmtiMetadataValue
{
    private final List<LocationPack> boundary = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param locations the Location Packs to add.
     */
    public BoundarySeries(List<LocationPack> locations)
    {
        boundary.addAll(locations);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the BoundarySeries
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public BoundarySeries(byte[] bytes) throws KlvParseException
    {
        int index = 0;
        while (index < bytes.length - 1)
        {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            byte[] packBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            LocationPack location = TargetLocation.targetLocationPackFromBytes(packBytes);
            boundary.add(location);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (LocationPack location: getLocations())
        {
            byte[] localSetBytes = TargetLocation.serialiseLocationPack(location);
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue()
    {
        return "[Location Series]";
    }

    @Override
    public String getDisplayName()
    {
        return "Boundary";
    }

    /**
     * Get the TargetLocation structure.
     *
     * @return the list of Target Locations.
     */
    public List<LocationPack> getLocations()
    {
        return boundary;
    }
}
