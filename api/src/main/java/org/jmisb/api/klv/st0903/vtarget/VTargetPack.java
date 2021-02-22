package org.jmisb.api.klv.st0903.vtarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.AlgorithmId;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VMTI Target Pack.
 *
 * <p>The target pack represents a single VMTI target. It consists of a target id (integer value,
 * always required) and a set of variable values that can be considered as attributes of the target.
 */
public class VTargetPack implements IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(VTargetPack.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VTargetMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    private final int targetId;

    /**
     * Construct from target values.
     *
     * @param targetId the target identifier
     * @param values the VTarget metadata as a Map.
     */
    public VTargetPack(int targetId, Map<VTargetMetadataKey, IVmtiMetadataValue> values) {
        this.targetId = targetId;
        map.putAll(values);
    }

    /**
     * Construct from encoded bytes.
     *
     * <p>This is used to parse out a single VTargetPack from the KLV-encoded byte array.
     *
     * <p>This constructor only supports ST0903.4 and later.
     *
     * @param bytes the byte array
     * @param offset the offset into the {@code bytes} array to start parsing.
     * @param length the number of bytes to parse
     * @throws KlvParseException if there is a problem during parsing
     * @deprecated use {@link #VTargetPack(byte[], int, int, EncodingMode)} instead
     */
    @Deprecated
    public VTargetPack(byte[] bytes, int offset, int length) throws KlvParseException {
        this(bytes, offset, length, EncodingMode.IMAPB);
    }

    /**
     * Construct from encoded bytes.
     *
     * <p>This is used to parse out a single VTargetPack from the KLV-encoded byte array.
     *
     * <p>This constructor allows selection of which encoding rules (according to the ST903 version)
     * to apply for floating point data.
     *
     * @param bytes the byte array
     * @param offset the offset into the {@code bytes} array to start parsing.
     * @param length the number of bytes to parse
     * @param encodingMode which encoding mode the {@code bytes} parameter uses for floating point
     *     data
     * @throws KlvParseException if there is a problem during parsing
     */
    public VTargetPack(byte[] bytes, int offset, int length, EncodingMode encodingMode)
            throws KlvParseException {
        BerField targetIdField = BerDecoder.decode(bytes, offset, true);
        offset += targetIdField.getLength();
        targetId = targetIdField.getValue();
        List<LdsField> fields =
                LdsParser.parseFields(bytes, offset, length - targetIdField.getLength());
        for (LdsField field : fields) {
            VTargetMetadataKey key = VTargetMetadataKey.getKey(field.getTag());
            if (key == VTargetMetadataKey.Undefined) {
                LOGGER.info("Unknown VMTI VTarget Metadata tag: {}", field.getTag());
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
     * <p>This method only supports ST0903.4 and later.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     * @deprecated Use {@link #createValue(VTargetMetadataKey, byte[], EncodingMode)} to explicitly
     *     identify the encoding format.
     */
    @Deprecated
    public static IVmtiMetadataValue createValue(VTargetMetadataKey tag, byte[] bytes)
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
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IVmtiMetadataValue createValue(
            VTargetMetadataKey tag, byte[] bytes, EncodingMode encodingMode)
            throws KlvParseException {
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
                return new TargetLocationOffsetLat(bytes, encodingMode);
            case TargetLocationOffsetLon:
                return new TargetLocationOffsetLon(bytes, encodingMode);
            case TargetHAE:
                return new TargetHAE(bytes, encodingMode);
            case BoundaryTopLeftLatOffset:
                return new BoundaryTopLeftLatOffset(bytes, encodingMode);
            case BoundaryTopLeftLonOffset:
                return new BoundaryTopLeftLonOffset(bytes, encodingMode);
            case BoundaryBottomRightLatOffset:
                return new BoundaryBottomRightLatOffset(bytes, encodingMode);
            case BoundaryBottomRightLonOffset:
                return new BoundaryBottomRightLonOffset(bytes, encodingMode);
            case TargetLocation:
                return new TargetLocation(bytes, encodingMode);
            case TargetBoundarySeries:
                return new TargetBoundarySeries(bytes, encodingMode);
            case CentroidPixRow:
                return new CentroidPixelRow(bytes);
            case CentroidPixColumn:
                return new CentroidPixelColumn(bytes);
            case FPAIndex:
                return new FpaIndex(bytes);
            case AlgorithmId:
                return new AlgorithmId(bytes);
            case VMask:
                return new VMask(bytes);
            case VObject:
                return new VObject(bytes);
            case VFeature:
                return new VFeature(bytes);
            case VTracker:
                return new VTracker(bytes, encodingMode);
            case VChip:
                return new VChip(bytes);
            case VChipSeries:
                return new VChipSeries(bytes);
            case VObjectSeries:
                return new VObjectSeries(bytes);
            default:
                LOGGER.info("Unrecognized VTarget tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the target identifier.
     *
     * @return target identifier.
     */
    public int getTargetIdentifier() {
        return targetId;
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    public Set<VTargetMetadataKey> getTags() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VTargetMetadataKey tag) {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();

        byte[] targetIdBytes = BerEncoder.encode(targetId, Ber.OID);
        chunks.add(targetIdBytes);
        len += targetIdBytes.length;

        for (VTargetMetadataKey tag : getTags()) {
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

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return this.getField((VTargetMetadataKey) tag);
    }

    @Override
    public Set<VTargetMetadataKey> getIdentifiers() {
        return this.getTags();
    }

    @Override
    public String getDisplayName() {
        return "VTarget";
    }

    @Override
    public String getDisplayableValue() {
        return "Target " + targetId;
    }
}
