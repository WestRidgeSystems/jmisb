package org.jmisb.st0903.vtracker;

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
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.AlgorithmId;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.VmtiTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VTracker Local Set. */
public class VTrackerLS {

    private static final Logger LOGGER = LoggerFactory.getLogger(VTrackerLS.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VTrackerMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VTrackerLS(Map<VTrackerMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VTracker local set
     * @throws KlvParseException if the byte array could not be parsed.
     * @deprecated use {@link #VTrackerLS(byte[], EncodingMode)} to specify the encoding mode.
     */
    @Deprecated
    public VTrackerLS(byte[] bytes) throws KlvParseException {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the VTracker local set
     * @param encodingMode the encoding mode for floating point data in the {@code bytes} array
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public VTrackerLS(byte[] bytes, EncodingMode encodingMode) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            VTrackerMetadataKey key = VTrackerMetadataKey.getKey(field.getTag());
            if (key == VTrackerMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VTracker Metadata tag: {}", field.getTag());
            } else {
                try {
                    IVmtiMetadataValue value = createValue(key, field.getData(), encodingMode);
                    map.put(key, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                }
            }
        }
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes.
     *
     * <p>For values using (or including as nested values) floating point, this method only works
     * correctly for ST0903.4 and later.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     * @deprecated use {@link #createValue(VTrackerMetadataKey, byte[], EncodingMode)} to specify
     *     the encoding
     */
    @Deprecated
    public static IVmtiMetadataValue createValue(VTrackerMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        return createValue(tag, bytes, EncodingMode.IMAPB);
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes.
     *
     * <p>This method allows selection of which encoding rules (according to the ST903 version) to
     * apply.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @param encodingMode the encoding mode for floating point data in the {@code bytes} array
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IVmtiMetadataValue createValue(
            VTrackerMetadataKey tag, byte[] bytes, EncodingMode encodingMode)
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
                return new BoundarySeries(bytes, encodingMode);
            case algorithm:
                return new VmtiTextString(VmtiTextString.ALGORITHM, bytes);
            case confidence:
                return new TrackConfidence(bytes);
            case numTrackPoints:
                return new NumTrackPoints(bytes);
            case trackHistorySeries:
                return new TrackHistorySeries(bytes, encodingMode);
            case velocity:
                return new Velocity(bytes, encodingMode);
            case acceleration:
                return new Acceleration(bytes, encodingMode);
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
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<VTrackerMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VTrackerMetadataKey tag) {
        return map.get(tag);
    }
}
