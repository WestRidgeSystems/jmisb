package org.jmisb.api.klv.st0903;

import static org.jmisb.api.klv.KlvConstants.VmtiLocalSetUl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st0601.Checksum;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VmtiLocalSet implements IMisbMessage {
    private static final Logger LOGGER = LoggerFactory.getLogger(VmtiLocalSet.class);

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes.
     *
     * <p>This method only supports ST0903.4 and later for values that are (or nest) floating point
     * values.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return the new instance
     * @throws KlvParseException if the byte array could not be parsed.
     * @deprecated Use {@link #createValue(VmtiMetadataKey, byte[], EncodingMode)} to explicitly
     *     identify what encoding to use.
     */
    @Deprecated
    public static org.jmisb.api.klv.st0903.IVmtiMetadataValue createValue(
            VmtiMetadataKey tag, byte[] bytes) throws org.jmisb.api.common.KlvParseException {
        return createValue(tag, bytes, EncodingMode.IMAPB);
    }

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes.
     *
     * <p>This constructor allows selection of which encoding rules (according to the ST903 version)
     * to apply.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     * @throws KlvParseException if the byte array could not be parsed.
     * @return the new instance
     */
    public static IVmtiMetadataValue createValue(
            VmtiMetadataKey tag, byte[] bytes, EncodingMode encodingMode) throws KlvParseException {
        // This is fully implemented as of ST0903.5
        switch (tag) {
                // No Checksum - handled automatically
            case PrecisionTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case SystemName:
                return new VmtiTextString(VmtiTextString.SYSTEM_NAME, bytes);
            case VersionNumber:
                return new ST0903Version(bytes);
            case TotalTargetsInFrame:
                return new VmtiTotalTargetCount(bytes);
            case NumberOfReportedTargets:
                return new VmtiReportedTargetCount(bytes);
            case FrameNumber:
                return new FrameNumber(bytes);
            case FrameWidth:
                return new FrameWidth(bytes);
            case FrameHeight:
                return new FrameHeight(bytes);
            case SourceSensor:
                return new VmtiTextString(VmtiTextString.SOURCE_SENSOR, bytes);
            case HorizontalFieldOfView:
                return new VmtiHorizontalFieldOfView(bytes, encodingMode);
            case VerticalFieldOfView:
                return new VmtiVerticalFieldOfView(bytes, encodingMode);
            case MiisId:
                return new MiisCoreIdentifier(bytes);
            case VTargetSeries:
                return new VTargetSeries(bytes, encodingMode);
            case AlgorithmSeries:
                return new AlgorithmSeries(bytes);
            case OntologySeries:
                return new OntologySeries(bytes);
            default:
                LOGGER.info("Unknown VMTI Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all data elements in the message. */
    private final SortedMap<VmtiMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VmtiLocalSet(Map<VmtiMetadataKey, IVmtiMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build a VMTI Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public VmtiLocalSet(byte[] bytes) throws KlvParseException {
        int offset = 0;
        EncodingMode encodingMode = EncodingMode.IMAPB;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields) {
            VmtiMetadataKey key = VmtiMetadataKey.getKey(field.getTag());
            if (key.equals(VmtiMetadataKey.VersionNumber)) {
                ST0903Version version = new ST0903Version(field.getData());
                if (version.getVersion() < 4) {
                    encodingMode = EncodingMode.LEGACY;
                }
                break;
            }
        }
        for (LdsField field : fields) {
            VmtiMetadataKey key = VmtiMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown VMTI Metadata tag: {}", field.getTag());
                    break;
                case Checksum:
                    byte[] expected = Checksum.compute(bytes, false);
                    byte[] actual = Arrays.copyOfRange(bytes, bytes.length - 2, bytes.length);
                    if (!Arrays.equals(expected, actual)) {
                        InvalidDataHandler.getInstance()
                                .handleInvalidChecksum(LOGGER, "Bad checksum");
                    }
                    break;
                default:
                    try {
                        IVmtiMetadataValue value = createValue(key, field.getData(), encodingMode);
                        map.put(key, value);
                    } catch (KlvParseException | IllegalArgumentException ex) {
                        InvalidDataHandler.getInstance()
                                .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                    }
                    break;
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        updateVersion();
        List<byte[]> chunks = new ArrayList<>();
        for (VmtiMetadataKey tag : getIdentifiers()) {
            if (tag == VmtiMetadataKey.Checksum) {
                continue;
            }
            chunks.add(new byte[] {(byte) tag.getIdentifier()});
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            chunks.add(bytes);
        }

        // Figure out value length
        final int keyLength = UniversalLabel.LENGTH;
        int valueLength = 0;
        valueLength =
                chunks.stream().map((chunk) -> chunk.length).reduce(valueLength, Integer::sum);

        if (isNested) {
            return ArrayUtils.arrayFromChunks(chunks, valueLength);
        } else {
            // Add Key and Length of checksum with placeholder for value - Checksum must be final
            // element
            byte[] checksum = new byte[2];
            chunks.add(new byte[] {(byte) VmtiMetadataKey.Checksum.getIdentifier()});
            chunks.add(BerEncoder.encode(checksum.length, Ber.SHORT_FORM));
            chunks.add(checksum);
            valueLength += 4;

            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend UL since this is standalone message
            chunks.add(0, VmtiLocalSetUl.getBytes());

            byte[] array =
                    ArrayUtils.arrayFromChunks(
                            chunks, keyLength + lengthField.length + valueLength);
            // Compute the checksum and replace the last two bytes of array
            Checksum.compute(array, true);
            return array;
        }
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    @Override
    public Set<VmtiMetadataKey> getIdentifiers() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VmtiMetadataKey tag) {
        return map.get(tag);
    }

    @Override
    public IVmtiMetadataValue getField(IKlvKey key) {
        return this.getField((VmtiMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return VmtiLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST0903 VMTI";
    }

    private void updateVersion() {
        ST0903Version version = (ST0903Version) getField(VmtiMetadataKey.VersionNumber);
        // If we're missing a version, or it's too old, update it to current. Otherwise, leave it
        // alone.
        if ((version == null) || (version.getVersion() < 4)) {
            version = new ST0903Version(VmtiMetadataConstants.ST_VERSION_NUMBER);
            map.put(VmtiMetadataKey.VersionNumber, version);
        }
    }
}
