package org.jmisb.api.klv.st0903.vtarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

public class VTargetPack {

    private static final Logger LOG = Logger.getLogger(VTargetPack.class.getName());

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<VTargetMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    private final int targetId;

    public VTargetPack(int targetId, Map<VTargetMetadataKey, IVmtiMetadataValue> values)
    {
        this.targetId = targetId;
        map.putAll(values);
    }

    // TODO consider refactoring to pass in the original array instead of a copy
    public VTargetPack(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        BerField targetIdField = BerDecoder.decode(bytes, offset, true);
        offset += targetIdField.getLength();
        targetId = targetIdField.getValue();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields)
        {
            VTargetMetadataKey key = VTargetMetadataKey.getKey(field.getTag());
            if (key == VTargetMetadataKey.Undefined)
            {
                LOG.log(Level.INFO, "Unknown VMTI VTarget Metadata tag: {0}", field.getTag());
            }
            else
            {
                IVmtiMetadataValue value = createValue(key, field.getData());
                map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IVmtiMetadataValue createValue(VTargetMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented.
        // Mark all unimplemented tags with TODO.
        switch (tag) {
            case TargetCentroid:
                return new TargetCentroid(bytes);
            case BoundaryTopLeft:
                return new BoundaryTopLeft(bytes);
            case BoundaryBottomRight:
                return new BoundaryBottomRight(bytes);
            case TargetPriority:
                return new TargetPriority(bytes);
            case TargetConfidenceLevel:
                return new TargetConfidenceLevel(bytes);
            case TargetHistory:
                return new TargetHistory(bytes);
            case PercentageOfTargetPixels:
                return new PercentageOfTargetPixels(bytes);
            case TargetColor:
                return new TargetColor(bytes);
            case TargetIntensity:
                return new TargetIntensity(bytes);
            case TargetLocationOffsetLat:
                return new TargetLocationOffsetLat(bytes);
            case TargetLocationOffsetLon:
                return new TargetLocationOffsetLon(bytes);
            case TargetHAE:
                return new TargetHAE(bytes);
            case BoundaryTopLeftLatOffset:
                return new BoundaryTopLeftLatOffset(bytes);
            case BoundaryTopLeftLonOffset:
                return new BoundaryTopLeftLonOffset(bytes);
            case BoundaryBottomRightLatOffset:
                return new BoundaryBottomRightLatOffset(bytes);
            case BoundaryBottomRightLonOffset:
                return new BoundaryBottomRightLonOffset(bytes);
            case TargetLocation:
                return new TargetLocation(bytes);
            case TargetBoundarySeries:
                return new TargetBoundarySeries(bytes);
            case CentroidPixRow:
                return new CentroidPixelRow(bytes);
            case CentroidPixColumn:
                return new CentroidPixelColumn(bytes);
            case FPAIndex:
                return new FpaIndex(bytes);
            case AlgorithmId:
                // TODO
                return null;
            case VMask:
                // TODO
                return null;
            case VObject:
                return new VObject(bytes);
            case VFeature:
                return new VFeature(bytes);
            case VTracker:
                return new VTracker(bytes);
            case VChip:
                return new VChip(bytes);
            case VChipSeries:
                return new VChipSeries(bytes);
            case VObjectSeries:
                return new VObjectSeries(bytes);
            default:
                System.out.println("Unrecognized VTarget tag: " + tag);
        }
        return null;
    }

    /**
     * Get the target identifier.
     *
     * @return target identifier.
     */
    public int getTargetIdentifier()
    {
        return targetId;
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<VTargetMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VTargetMetadataKey tag)
    {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     * @throws IOException if there is a problem during conversion.
     */
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(BerEncoder.encode(targetId));
        for (VTargetMetadataKey tag: getTags())
        {
            outputStream.write(new byte[]{(byte) tag.getTag()});
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            outputStream.write(BerEncoder.encode(bytes.length));
            outputStream.write(bytes);
        }
        return outputStream.toByteArray();
    }
}
