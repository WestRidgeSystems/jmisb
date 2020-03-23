package org.jmisb.api.klv.st0903.vtarget;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

public class VTargetPack {

    private static final Logger LOG = Logger.getLogger(VTargetPack.class.getName());

    private int targetId;

    // TODO consider refactoring to pass in the original array instead of a copy
    public VTargetPack(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        BerField targetIdField = BerDecoder.decode(bytes, offset, true);
        offset += targetIdField.getLength();
        targetId = targetIdField.getValue();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VTargetMetadataKey key = VTargetMetadataKey.getKey(field.getTag());
            if (key == VTargetMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI VTarget Metadata tag: {0}", field.getTag());
            } else {
                IVmtiMetadataValue value = createValue(key, field.getData());
                setField(key, value);
            }
        }
    }

    private void setField(VTargetMetadataKey key, IVmtiMetadataValue value)
    {

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
                // TODO
                return null;
            case TargetIntensity:
                // TODO
                return null;
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
                // TODO
                return null;
            case TargetBoundarySeries:
                // TODO
                return null;
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
                // TODO
                return null;
            case VTracker:
                // TODO
                return null;
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

}
