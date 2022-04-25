package org.jmisb.st0903.vtrack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.st0903.FrameHeight;
import org.jmisb.st0903.FrameNumber;
import org.jmisb.st0903.FrameWidth;
import org.jmisb.st0903.MiisCoreIdentifier;
import org.jmisb.st0903.PrecisionTimeStamp;
import org.jmisb.st0903.VmtiHorizontalFieldOfView;
import org.jmisb.st0903.VmtiVerticalFieldOfView;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.st0903.shared.VmtiTextString;
import org.jmisb.st0903.vtarget.BoundaryBottomRight;
import org.jmisb.st0903.vtarget.BoundaryTopLeft;
import org.jmisb.st0903.vtarget.CentroidPixelColumn;
import org.jmisb.st0903.vtarget.CentroidPixelRow;
import org.jmisb.st0903.vtarget.FpaIndex;
import org.jmisb.st0903.vtarget.PercentageOfTargetPixels;
import org.jmisb.st0903.vtarget.TargetBoundarySeries;
import org.jmisb.st0903.vtarget.TargetCentroid;
import org.jmisb.st0903.vtarget.TargetColor;
import org.jmisb.st0903.vtarget.TargetConfidenceLevel;
import org.jmisb.st0903.vtarget.TargetHistory;
import org.jmisb.st0903.vtarget.TargetIntensity;
import org.jmisb.st0903.vtarget.TargetLocation;
import org.jmisb.st0903.vtarget.TargetPriority;
import org.jmisb.st0903.vtarget.VChip;
import org.jmisb.st0903.vtarget.VChipSeries;
import org.jmisb.st0903.vtarget.VFeature;
import org.jmisb.st0903.vtarget.VMask;
import org.jmisb.st0903.vtarget.VObject;
import org.jmisb.st0903.vtarget.VObjectSeries;
import org.jmisb.st0903.vtracker.Acceleration;
import org.jmisb.st0903.vtracker.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VMTI Target Pack.
 *
 * <p>The target pack represents a single VMTI target. It consists of a target id (integer value,
 * always required) and a set of variable values that can be considered as attributes of the target.
 */
public class VTrackItem implements IKlvValue, INestedKlvValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(VTrackItem.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<VTrackItemMetadataKey, IVTrackItemMetadataValue> map = new TreeMap<>();

    private final int targetId;

    /**
     * Construct from target values.
     *
     * @param targetId the target identifier
     * @param values the VTarget metadata as a Map.
     */
    public VTrackItem(int targetId, Map<VTrackItemMetadataKey, IVTrackItemMetadataValue> values) {
        this.targetId = targetId;
        map.putAll(values);
    }

    /**
     * Construct from encoded bytes.
     *
     * <p>This is used to parse out a single VTargetPack from the KLV-encoded byte array.
     *
     * <p>This constructor allows selection of which encoding rules (according to the ST0903
     * version) to apply for floating point data.
     *
     * @param bytes the byte array
     * @param offset the offset into the {@code bytes} array to start parsing.
     * @param length the number of bytes to parse
     * @param encodingMode which encoding mode the {@code bytes} parameter uses for floating point
     *     data
     * @throws KlvParseException if there is a problem during parsing
     */
    public VTrackItem(byte[] bytes, int offset, int length, EncodingMode encodingMode)
            throws KlvParseException {
        BerField targetIdField = BerDecoder.decode(bytes, offset, true);
        offset += targetIdField.getLength();
        targetId = targetIdField.getValue();
        List<LdsField> fields =
                LdsParser.parseFields(bytes, offset, length - targetIdField.getLength());
        for (LdsField field : fields) {
            VTrackItemMetadataKey key = VTrackItemMetadataKey.getKey(field.getTag());
            if (key == VTrackItemMetadataKey.Undefined) {
                LOGGER.info("Unknown VTrackItem Metadata tag: {}", field.getTag());
            } else {
                try {
                    IVTrackItemMetadataValue value =
                            createValue(key, field.getData(), encodingMode);
                    map.put(key, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                }
            }
        }
    }

    /**
     * Create a {@link IVTrackItemMetadataValue} instance from encoded bytes.
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
    public static IVTrackItemMetadataValue createValue(
            VTrackItemMetadataKey tag, byte[] bytes, EncodingMode encodingMode)
            throws KlvParseException {
        switch (tag) {
            case TargetTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case TargetCentroidPixNum:
                return new TargetCentroid(bytes);
            case TargetCentroidPixRow:
                return new CentroidPixelRow(bytes);
            case TargetCentroidPixCol:
                return new CentroidPixelColumn(bytes);
            case BoundaryTopLeftPixNum:
                return new BoundaryTopLeft(bytes);
            case BoundaryBottomRightPixNum:
                return new BoundaryBottomRight(bytes);
            case TargetPriority:
                return new TargetPriority(bytes);
            case TargetConfidenceLevel:
                return new TargetConfidenceLevel(bytes);
            case TargetHistory:
                return new TargetHistory(bytes);
            case PercentTargetPixels:
                return new PercentageOfTargetPixels(bytes);
            case TargetColor:
                return new TargetColor(bytes);
            case TargetIntensity:
                return new TargetIntensity(bytes);
            case TargetLocation:
                return new TargetLocation(bytes, encodingMode);
            case TargetBoundarySeries:
                return new TargetBoundarySeries(bytes, encodingMode);
            case Velocity:
                return new Velocity(bytes, encodingMode);
            case Acceleration:
                return new Acceleration(bytes, encodingMode);
            case FpaIndex:
                return new FpaIndex(bytes);
            case VideoFrameNumber:
                return new FrameNumber(bytes);
            case MiisId:
                return new MiisCoreIdentifier(bytes);
            case FrameWidth:
                return new FrameWidth(bytes);
            case FrameHeight:
                return new FrameHeight(bytes);
            case SensorHorizontalFov:
                return new VmtiHorizontalFieldOfView(bytes, encodingMode);
            case SensorVerticalFov:
                return new VmtiVerticalFieldOfView(bytes, encodingMode);
            case MotionImageryUrl:
                return new VmtiTextString(VmtiTextString.MOTION_IMAGERY_URL, bytes);
            case VMask:
                return new VMask(bytes);
            case VObject:
                return new VObject(bytes);
            case VFeature:
                return new VFeature(bytes);
            case VChip:
                return new VChip(bytes);
            case VChipSeries:
                return new VChipSeries(bytes);
            case VObjectSeries:
                return new VObjectSeries(bytes);
            default:
                LOGGER.info("Unrecognized VTrackItem tag: {}", tag);
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
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVTrackItemMetadataValue getField(VTrackItemMetadataKey tag) {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     *
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.appendAsOID(targetId);
        for (VTrackItemMetadataKey tag : getIdentifiers()) {
            arrayBuilder.appendAsOID(tag.getIdentifier());
            IVTrackItemMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            arrayBuilder.appendAsBerLength(bytes.length);
            arrayBuilder.append(bytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return this.getField((VTrackItemMetadataKey) tag);
    }

    @Override
    public Set<VTrackItemMetadataKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public String getDisplayName() {
        return "VTrackItem";
    }

    @Override
    public String getDisplayableValue() {
        return "Track Item " + targetId;
    }
}
