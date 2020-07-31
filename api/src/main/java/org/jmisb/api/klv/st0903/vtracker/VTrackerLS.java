package org.jmisb.api.klv.st0903.vtracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VTracker Local Set. */
public class VTrackerLS {

    private static final Logger LOGGER = LoggerFactory.getLogger(VTrackerLS.class);

    /** Map containing all data elements in the message */
    private final SortedMap<VTrackerMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VTrackerLS(Map<VTrackerMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    public VTrackerLS(byte[] bytes) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            VTrackerMetadataKey key = VTrackerMetadataKey.getKey(field.getTag());
            if (key == VTrackerMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VTracker Metadata tag: {}", field.getTag());
            } else {
                try {
                    IVmtiMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                }
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
    public static IVmtiMetadataValue createValue(VTrackerMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case trackId:
                return new TrackId(bytes);
            case detectionStatus:
                return new DetectionStatus(bytes);
            case startTime:
                return new StartTime(bytes);
            case endTime:
                return new EndTime(bytes);
            case boundarySeries:
                return new BoundarySeries(bytes);
            case algorithm:
                return new VmtiTextString(VmtiTextString.ALGORITHM, bytes);
            case confidence:
                return new TrackConfidence(bytes);
            case numTrackPoints:
                return new NumTrackPoints(bytes);
            case trackHistorySeries:
                return new TrackHistorySeries(bytes);
            case velocity:
                return new Velocity(bytes);
            case acceleration:
                return new Acceleration(bytes);
            case algorithmId:
                return new AlgorithmId(bytes);
            default:
                LOGGER.info("Unrecognized VTracker tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VTrackerMetadataKey tag : getTags()) {
            chunks.add(new byte[] {(byte) tag.getTag()});
            len += 1;
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(bytes);
            len += bytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<VTrackerMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VTrackerMetadataKey tag) {
        return map.get(tag);
    }
}
