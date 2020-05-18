package org.jmisb.api.klv.st0806.poiaoi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.core.klv.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ST0806 Remove Video Terminal Area of Interest (AOI) Local Set.
 *
 * Any number of AOI Local Sets (including none) can be embedded in a parent
 * RvtLocalSet instance.
 */
public class RvtAoiLocalSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RvtAoiLocalSet.class);

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set/
     */
    public RvtAoiLocalSet(Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values)
    {
        map.putAll(values);
    }

    /**
     * Parse {@link LdsField}s from a byte array
     *
     * @param bytes Byte array to parse
     * @param start Index of the first byte to parse
     * @param length Number of bytes to parse
     *
     * @throws KlvParseException If a parsing error occurs
     */
    public RvtAoiLocalSet(byte[] bytes, int start, int length) throws KlvParseException
    {
        List<LdsField> fields = LdsParser.parseFields(bytes, start, length);
        for (LdsField field : fields)
        {
            RvtAoiMetadataKey key = RvtAoiMetadataKey.getKey(field.getTag());
            if (key == RvtAoiMetadataKey.Undefined)
            {
                LOGGER.info("Unknown RVT AOI Metadata tag: {}", field.getTag());
            }
            else
            {
                IRvtPoiAoiMetadataValue value = createValue(key, field.getData());
                map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IRvtPoiAoiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IRvtPoiAoiMetadataValue createValue(RvtAoiMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        switch (tag)
        {
            case PoiAoiNumber:
                return new PoiAoiNumber(bytes);
            case CornerLatitudePoint1:
                return new CornerLatitudePoint1(bytes);
            case CornerLongitudePoint1:
                return new CornerLongitudePoint1(bytes);
            case CornerLatitudePoint3:
                return new CornerLatitudePoint3(bytes);
            case CornerLongitudePoint3:
                return new CornerLongitudePoint3(bytes);
            case PoiAoiType:
                return new PoiAoiType(bytes);
            case PoiAoiText:
                return new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_TEXT, bytes);
            case PoiAoiSourceId:
                return new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_SOURCE_ID, bytes);
            case PoiAoiLabel:
                return new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, bytes);
            case OperationId:
                return new RvtPoiAoiTextString(RvtPoiAoiTextString.OPERATION_ID, bytes);
            default:
                LOGGER.info("Unrecognized RVT AOI tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<RvtAoiMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IRvtPoiAoiMetadataValue getField(RvtAoiMetadataKey tag)
    {
        return map.get(tag);
    }

    /**
     * Get the byte array corresponding to the value for this Local Set.
     * @return byte array with the encoded local set.
     */
    public byte[] getBytes()
    {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (RvtAoiMetadataKey tag: getTags())
        {
            chunks.add(new byte[]{(byte) tag.getTag()});
            len += 1;
            IRvtPoiAoiMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            byte[] lengthBytes = BerEncoder.encode(bytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(bytes);
            len += bytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }
}
