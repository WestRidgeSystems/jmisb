package org.jmisb.api.klv.st0903.vtracker;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * VTracker Local Set.
 */
public class VTrackerLS {

    private static final Logger LOG = Logger.getLogger(VTrackerLS.class.getName());

    // TODO consider refactoring to pass in the original array instead of a copy
    public VTrackerLS(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length - offset);
        for (LdsField field : fields) {
            VTrackerMetadataKey key = VTrackerMetadataKey.getKey(field.getTag());
            if (key == VTrackerMetadataKey.Undefined) {
                LOG.log(Level.INFO, "Unknown VMTI VTarget Metadata tag: {0}", field.getTag());
            } else {
                IVmtiMetadataValue value = createValue(key, field.getData());
                setField(key, value);
            }
        }
    }

    private void setField(VTrackerMetadataKey key, IVmtiMetadataValue value)
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
    public static IVmtiMetadataValue createValue(VTrackerMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        // Keep the case statements in enum ordinal order so we can keep track of what is implemented.
        // Mark all unimplemented tags with TODO.
        switch (tag) {
            case trackId:
                // TODO
                return null;
            case detectionStatus:
                // TODO
                return null;
            case startTime:
                return new StartTime(bytes);
            case endTime:
                return new EndTime(bytes);
            case boundarySeries:
                // TODO
                return null;
            case algorithm:
                // TODO
                return null;
            case confidence:
                return new TrackConfidence(bytes);
            case numTrackPoints:
                // TODO
                return null;
            case trackHistorySeries:
                // TODO
                return null;
            case velocity:
                // TODO
                return null;
            case acceleration:
                // TODO
                return null;
            case algorithmId:
                // TODO
                return null;
            default:
                System.out.println("Unrecognized VTracker tag: " + tag);
        }
        return null;
    }

}
