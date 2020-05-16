package org.jmisb.api.klv.st0806.poi;

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
 * ST0806 Remove Video Terminal Point of Interest (POI) Local Set.
 *
 * Any number of POI Local Sets (including none) can be embedded in a parent
 * RvtLocalSet instance.
 */
public class RvtPoiLocalSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RvtPoiLocalSet.class);

    /**
     * Map containing all data elements in the message
     */
    private final SortedMap<RvtPoiMetadataKey, IRvtPoiMetadataValue> map = new TreeMap<>();

    /**
     * Create the message from the given key/value pairs
     *
     * @param values Tag/value pairs to be included in the local set/
     */
    public RvtPoiLocalSet(Map<RvtPoiMetadataKey, IRvtPoiMetadataValue> values)
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
    public RvtPoiLocalSet(byte[] bytes, int start, int length) throws KlvParseException
    {
        List<LdsField> fields = LdsParser.parseFields(bytes, start, length);
        for (LdsField field : fields)
        {
            RvtPoiMetadataKey key = RvtPoiMetadataKey.getKey(field.getTag());
            if (key == RvtPoiMetadataKey.Undefined)
            {
                LOGGER.info("Unknown RVT POI Metadata tag: {}", field.getTag());
            }
            else
            {
                IRvtPoiMetadataValue value = createValue(key, field.getData());
                map.put(key, value);
            }
        }
    }

    /**
     * Create a {@link IRvtPoiMetadataValue} instance from encoded bytes
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the bytes could not be parsed.
     */
    public static IRvtPoiMetadataValue createValue(RvtPoiMetadataKey tag, byte[] bytes) throws KlvParseException
    {
        switch (tag)
        {
            case PoiAoiNumber:
                return new PoiAoiNumber(bytes);
            case PoiLatitude:
                // TODO
                break;
            case PoiLongitude:
                // TODO
                break;
            case PoiAltitude:
                return new PoiAltitude(bytes);
            case PoiAoiType:
                return new PoiAoiType(bytes);
            case PoiAoiText:
                return new RvtPoiTextString(RvtPoiTextString.POI_AOI_TEXT, bytes);
            case PoiSourceIcon:
                return new RvtPoiTextString(RvtPoiTextString.POI_SOURCE_ICON, bytes);
            case PoiAoiSourceId:
                return new RvtPoiTextString(RvtPoiTextString.POI_AOI_SOURCE_ID, bytes);
            case PoiAoiLabel:
                return new RvtPoiTextString(RvtPoiTextString.POI_AOI_LABEL, bytes);
            case OperationId:
                return new RvtPoiTextString(RvtPoiTextString.OPERATION_ID, bytes);
            default:
                LOGGER.info("Unrecognized RVT POI tag: {}", tag);
        }
        return null;
    }

    /**
     * Get the set of tags with populated values
     *
     * @return The set of tags for which values have been set
     */
    public Set<RvtPoiMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IRvtPoiMetadataValue getField(RvtPoiMetadataKey tag)
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
        for (RvtPoiMetadataKey tag: getTags())
        {
            chunks.add(new byte[]{(byte) tag.getTag()});
            len += 1;
            IRvtPoiMetadataValue value = getField(tag);
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
