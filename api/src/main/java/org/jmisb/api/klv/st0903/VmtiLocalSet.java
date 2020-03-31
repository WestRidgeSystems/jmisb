package org.jmisb.api.klv.st0903;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.IMisbMessage;
import static org.jmisb.api.klv.KlvConstants.VmtiLocalSetUl;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st0601.Checksum;

import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.core.klv.ArrayUtils;

public class VmtiLocalSet implements IMisbMessage {

    private static final Logger LOG = Logger.getLogger(VmtiLocalSet.class.getName());

    /**
     * Create a {@link IVmtiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public static IVmtiMetadataValue createValue(VmtiMetadataKey tag, byte[] bytes) throws KlvParseException
    {
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
                return new VmtiHorizontalFieldOfView(bytes);
            case VerticalFieldOfView:
                return new VmtiVerticalFieldOfView(bytes);
            case MiisId:
                return new MiisCoreIdentifier(bytes);
            case VTargetSeries:
                return new VTargetSeries(bytes);
            case AlgorithmSeries:
                return new AlgorithmSeries(bytes);
            case OntologySeries:
                return new OntologySeries(bytes);
            default:
                LOG.log(Level.INFO, "Unknown VMTI Metadata tag: {0}", tag);
        }
        return null;
    }

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<VmtiMetadataKey, IVmtiMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VmtiLocalSet(Map<VmtiMetadataKey, IVmtiMetadataValue> values)
    {
        map.putAll(values);
    }

    /**
     * Build a VMTI Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public VmtiLocalSet(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields)
        {
            VmtiMetadataKey key = VmtiMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOG.log(Level.INFO, "Unknown VMTI Metadata tag: {0}", field.getTag());
                    break;
                case Checksum:
                    byte[] expected = Checksum.compute(bytes, false);
                    byte[] actual = Arrays.copyOfRange(bytes, bytes.length-2, bytes.length);
                    if (!Arrays.equals(expected, actual))
                    {
                        throw new KlvParseException("Bad checksum");
                    }
                    break;
                default:
                    IVmtiMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
                    break;
            }
        }        
    }

    @Override
    public byte[] frameMessage(boolean isNested)
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (VmtiMetadataKey tag: getTags())
        {
            if (tag == VmtiMetadataKey.Checksum)
            {
                continue;
            }
            chunks.add(new byte[]{(byte) tag.getTag()});
            len += 1;
            IVmtiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(bytes);
            len += bytes.length;
        }

        // Figure out value length
        final int keyLength = UniversalLabel.LENGTH;
        int valueLength = 0;
        valueLength = chunks.stream().map((chunk) -> chunk.length).reduce(valueLength, Integer::sum);

        if (isNested)
        {
            return ArrayUtils.arrayFromChunks(chunks, valueLength);
        }
        else
        {
            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend UL since this is standalone message
            chunks.add(0, VmtiLocalSetUl.getBytes());

            // Add Key and Length of checksum with placeholder for value - Checksum must be final element
            byte[] checksum = new byte[2];
            chunks.add(new byte[]{(byte)VmtiMetadataKey.Checksum.getTag()});
            chunks.add(BerEncoder.encode(checksum.length, Ber.SHORT_FORM));
            chunks.add(checksum);

            byte[] array = ArrayUtils.arrayFromChunks(chunks, keyLength + lengthField.length + valueLength);
            // Compute the checksum and replace the last two bytes of array
            Checksum.compute(array, true);
            return array;
        }
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<VmtiMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVmtiMetadataValue getField(VmtiMetadataKey tag)
    {
        return map.get(tag);
    }

    @Override
    public UniversalLabel getUniversalLabel()
    {
        return VmtiLocalSetUl;
    }

    @Override
    public String displayHeader()
    {
        return "ST0903 VMTI";
    }

}
