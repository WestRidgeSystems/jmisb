package org.jmisb.api.klv.st0806;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.IMisbMessage;
import static org.jmisb.api.klv.KlvConstants.RvtLocalSetUl;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RvtLocalSet implements IMisbMessage
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RvtLocalSet.class);

    /**
     * Create a {@link IRvtMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public static IRvtMetadataValue createValue(RvtMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        // Unimplemented tags flagged with TODO
        switch (tag) {
            // No checksum, handled automatically
            case UserDefinedTimeStampMicroseconds:
                // TODO: implement
                break;
            case PlatformTrueAirspeed:
                return new RvtPlatformTrueAirspeed(bytes);
            case PlatformIndicatedAirspeed:
                return new RvtPlatformIndicatedAirspeed(bytes);
            case TelemetryAccuracyIndicator:
                // TODO: implement (maybe)
                break;
            case FragCircleRadius:
                return new FragCircleRadius(bytes);
            case FrameCode:
                return new FrameCode(bytes);
            case UASLSVersionNumber:
                return new ST0806Version(bytes);
            case VideoDataRate:
                return new VideoDataRate(bytes);
            case DigitalVideoFileFormat:
                return new DigitalVideoFileFormat(bytes);
            case UserDefinedLS:
                // TODO: implement - this can repeat
                break;
            case PointOfInterestLS:
                // TODO: implement - this can repeat
                break;
            case AreaOfInterestLS:
                // TODO: implement - this can repeat
                break;
            case MGRSZone:
                // TODO: implement
                break;
            case MGRSLatitudeBandAndGridSquare:
                return new AircraftMGRSLatitudeBandAndGridSquare(bytes);
            case MGRSEasting:
                // TODO: implement
                break;
            case MGRSNorthing:
                // TODO: implement
                break;
            case MGRSZoneSecondValue:
                // TODO: implement
                break;
            case MGRSLatitudeBandAndGridSquareSecondValue:
                return new FrameCentreMGRSLatitudeBandAndGridSquare(bytes);
            case MGRSEastingSecondValue:
                // TODO: implement
                break;
            case MGRSNorthingSecondValue:
                // TODO: implement
                break;
            default:
                LOGGER.info("Unknown Remote Video Terminal Metadata tag: {}", tag);
        }
        return null;
    }

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<RvtMetadataKey, IRvtMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public RvtLocalSet(Map<RvtMetadataKey, IRvtMetadataValue> values)
    {
        map.putAll(values);
    }

    /**
     * Build a RVT Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public RvtLocalSet(byte[] bytes) throws KlvParseException
    {
        int offset = 0;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields)
        {
            RvtMetadataKey key = RvtMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown RVT Metadata tag: {}", field.getTag());
                    break;
                case CRC32:
                    // TODO
                    break;
                default:
                    IRvtMetadataValue value = createValue(key, field.getData());
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
        for (RvtMetadataKey tag: getTags())
        {
            if (tag == RvtMetadataKey.CRC32)
            {
                continue;
            }
            chunks.add(new byte[]{(byte) tag.getTag()});
            len += 1;
            IRvtMetadataValue value = getField(tag);
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
            // Add Key and Length of checksum with placeholder for value - Checksum must be final element
            byte[] checksum = new byte[2];
            chunks.add(new byte[]{(byte)RvtMetadataKey.CRC32.getTag()});
            chunks.add(BerEncoder.encode(checksum.length, Ber.SHORT_FORM));
            chunks.add(checksum);
            valueLength += 4;

            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend UL since this is standalone message
            chunks.add(0, RvtLocalSetUl.getBytes());

            byte[] array = ArrayUtils.arrayFromChunks(chunks, keyLength + lengthField.length + valueLength);
            // TODO: Compute the checksum and replace the last two bytes of array
            // Checksum.compute(array, true);
            return array;
        }
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<RvtMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IRvtMetadataValue getField(RvtMetadataKey tag)
    {
        return map.get(tag);
    }

    @Override
    public UniversalLabel getUniversalLabel()
    {
        return RvtLocalSetUl;
    }

    @Override
    public String displayHeader()
    {
        return "ST0806 Remote Video Terminal";
    }

}
