package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.jmisb.core.klv.ArrayUtils;

/**
 * Target Boundary Series (ST0903 VTarget Pack Tag 18).
 * <p>
 * From ST0903:
 * <blockquote>
 * Provides detailed geo-positioning information for the boundary around an area
 * or volume of interest. An arbitrary number of vertices defines the boundary.
 * Each vertex is an element of type Location. Typical boundary are the
 * bounding boxes defined by two or four vertices. Location type captures
 * geo-positioning data about a specific location on or near the surface of the
 * Earth. The contents of these packs fall into three groups, namely, geospatial
 * location (Latitude, Longitude, and Height), standard deviations for these
 * values, and correlation coefficients among them. Location elements are
 * Defined-Length Truncation Packs, omitting unknown or less important data from
 * the end of the Pack. Use of TargetBoundarySeries is preferred over Target
 * Bounding Box (Tags 13 through 16) when accuracy and correlation information
 * is available and needed. Such information aids fusion with other moving
 * object indicators, such as, radar based GMTI, to support track identification
 * and tracking.
 * </blockquote>
 */
public class TargetBoundarySeries implements IVmtiMetadataValue
{
    private final List<LocationPack> boundary = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param locations the Location Packs to add.
     */
    public TargetBoundarySeries(List<LocationPack> locations)
    {
        boundary.addAll(locations);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the TargetBoundarySeries
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public TargetBoundarySeries(byte[] bytes) throws KlvParseException
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
        return "Target Boundary";
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
