package org.jmisb.api.klv.st0806;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import org.jmisb.api.klv.st0806.poiaoi.PoiAoiNumber;
import org.jmisb.api.klv.st0806.poiaoi.RvtAoiLocalSet;
import org.jmisb.api.klv.st0806.poiaoi.RvtAoiMetadataKey;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiLocalSet;
import org.jmisb.api.klv.st0806.poiaoi.RvtPoiMetadataKey;
import org.jmisb.api.klv.st0806.userdefined.RvtNumericId;
import org.jmisb.api.klv.st0806.userdefined.RvtUserDefinedLocalSet;
import org.jmisb.api.klv.st0806.userdefined.RvtUserDefinedMetadataKey;
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
                return new RvtUserDefinedLocalSet(bytes, 0, bytes.length);
            case PointOfInterestLS:
                return new RvtPoiLocalSet(bytes, 0, bytes.length);
            case AreaOfInterestLS:
                return new RvtAoiLocalSet(bytes, 0, bytes.length);
            case MGRSZone:
                return new AircraftMGRSZone(bytes);
            case MGRSLatitudeBandAndGridSquare:
                return new AircraftMGRSLatitudeBandAndGridSquare(bytes);
            case MGRSEasting:
                return new AircraftMGRSEasting(bytes);
            case MGRSNorthing:
                return new AircraftMGRSNorthing(bytes);
            case MGRSZoneSecondValue:
                return new FrameCentreMGRSZone(bytes);
            case MGRSLatitudeBandAndGridSquareSecondValue:
                return new FrameCentreMGRSLatitudeBandAndGridSquare(bytes);
            case MGRSEastingSecondValue:
                return new FrameCentreMGRSEasting(bytes);
            case MGRSNorthingSecondValue:
                return new FrameCentreMGRSNorthing(bytes);
            default:
                LOGGER.info("Unknown Remote Video Terminal Metadata tag: {}", tag);
        }
        return null;
    }

    /**
     * Map containing all non-repeating elements in the message
     */
    private final SortedMap<RvtMetadataKey, IRvtMetadataValue> map = new TreeMap<>();

    /**
     * Map containing User Defined Local sets
     */
    private final Map<Integer, RvtUserDefinedLocalSet> userDefined = new TreeMap<>();

    /**
     * Map containing POI Local sets
     */
    private final Map<Integer, RvtPoiLocalSet> pois = new TreeMap<>();

    /**
     * Map containing AOI Local sets
     */
    private final Map<Integer, RvtAoiLocalSet> aois = new TreeMap<>();

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
                case UserDefinedLS:
                    RvtUserDefinedLocalSet userDefinedLocalSet = (RvtUserDefinedLocalSet)createValue(key, field.getData());
                    if (userDefinedLocalSet.getTags().contains(RvtUserDefinedMetadataKey.NumericId))
                    {
                        RvtNumericId rvtNumericId = (RvtNumericId)userDefinedLocalSet.getField(RvtUserDefinedMetadataKey.NumericId);
                        userDefined.put(rvtNumericId.getValue(), userDefinedLocalSet);
                    }
                    break;
                case PointOfInterestLS:
                    RvtPoiLocalSet poi = (RvtPoiLocalSet)createValue(key, field.getData());
                    if (poi.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber))
                    {
                        PoiAoiNumber poiNumber = (PoiAoiNumber)poi.getField(RvtPoiMetadataKey.PoiAoiNumber);
                        pois.put(poiNumber.getNumber(), poi);
                    }
                    break;
                case AreaOfInterestLS:
                    RvtAoiLocalSet aoi = (RvtAoiLocalSet)createValue(key, field.getData());
                    if (aoi.getTags().contains(RvtAoiMetadataKey.PoiAoiNumber))
                    {
                        PoiAoiNumber aoiNumber = (PoiAoiNumber)aoi.getField(RvtAoiMetadataKey.PoiAoiNumber);
                        aois.put(aoiNumber.getNumber(), aoi);
                    }
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
        for (Integer numericId: getUserDefinedIndexes())
        {
            RvtUserDefinedLocalSet userDefinedLocalSet = getUserDefinedLocalSet(numericId);
            chunks.add(new byte[]{(byte)(RvtMetadataKey.UserDefinedLS.getTag())});
            len += 1;
            byte[] localSetBytes = userDefinedLocalSet.getBytes();
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        for (Integer poiNumber: getPOIIndexes())
        {
            RvtPoiLocalSet poiLocalSet = getPOI(poiNumber);
            chunks.add(new byte[]{(byte)(RvtMetadataKey.PointOfInterestLS.getTag())});
            len += 1;
            byte[] localSetBytes = poiLocalSet.getBytes();
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
        }
        for (Integer aoiNumber: getAOIIndexes())
        {
            RvtAoiLocalSet aoiLocalSet = getAOI(aoiNumber);
            chunks.add(new byte[]{(byte)(RvtMetadataKey.AreaOfInterestLS.getTag())});
            len += 1;
            byte[] localSetBytes = aoiLocalSet.getBytes();
            byte[] lengthBytes = BerEncoder.encode(localSetBytes.length);
            chunks.add(lengthBytes);
            len += lengthBytes.length;
            chunks.add(localSetBytes);
            len += localSetBytes.length;
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
            // Add Key and Length of CRC-32 with placeholder for value - must be final element if used
            byte[] checksum = new byte[4];
            chunks.add(new byte[]{(byte)RvtMetadataKey.CRC32.getTag()});
            valueLength += 1;
            byte[] checksumLengthBytes = BerEncoder.encode(checksum.length, Ber.SHORT_FORM);
            chunks.add(checksumLengthBytes);
            valueLength += checksumLengthBytes.length;
            chunks.add(checksum);
            valueLength += 4;

            // Prepend length field into front of the list
            byte[] lengthField = BerEncoder.encode(valueLength);
            chunks.add(0, lengthField);

            // Prepend UL since this is standalone message
            chunks.add(0, RvtLocalSetUl.getBytes());

            byte[] array = ArrayUtils.arrayFromChunks(chunks, keyLength + lengthField.length + valueLength);
            // TODO: Compute the CRC-32 and replace the last four bytes of array
            // Checksum.compute(array, true);
            return array;
        }
    }

    /**
     * Get the set of tags with populated values.
     *
     * This doesn't include the POI Local Sets, AOI Local Sets or User Defined
     * Local Sets (if any).
     *
     * @return The set of tags for which values have been set
     */
    public Set<RvtMetadataKey> getTags()
    {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     * 
     * This cannot be used to access the POI Local Sets, AOI Local Sets or User
     * Defined Local Sets. There are specific accessors for those.
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

    public void addUserDefinedLocalSet(RvtUserDefinedLocalSet localset)
    {
        if (localset == null)
        {
            throw new IllegalArgumentException("Cannot add null User Defined local set to RVT parent");
        }
        if (localset.getTags() == null)
        {
            throw new IllegalArgumentException("Cannot add null User Defined  local set tags to RVT parent");
        }
        if (!localset.getTags().contains(RvtUserDefinedMetadataKey.NumericId))
        {
            throw new IllegalArgumentException("User Defined local set must contain NumericId");
        }
        RvtNumericId numericId = (RvtNumericId)localset.getField(RvtUserDefinedMetadataKey.NumericId);
        userDefined.put(numericId.getValue(), localset);
    }

    /**
     * Get the available User Defined Local Set indexes.
     * @return set of NumericIds.
     */
    public Set<Integer> getUserDefinedIndexes()
    {
        return userDefined.keySet();
    }

    /**
     * Get the User Defined Local Set by index.
     * @param index the index (NumericId)
     * @return User Defined Set corresponding to the index.
     */
    public RvtUserDefinedLocalSet getUserDefinedLocalSet(int index)
    {
        return userDefined.get(index);
    }

    public void addPointOfInterestLocalSet(RvtPoiLocalSet localset)
    {
        if (localset == null)
        {
            throw new IllegalArgumentException("Cannot add null POI local set to RVT parent");
        }
        if (localset.getTags() == null)
        {
            throw new IllegalArgumentException("Cannot add null POI local set tags to RVT parent");
        }
        if (!localset.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber))
        {
            throw new IllegalArgumentException("POI local set must contain POI/AOI Number");
        }
        PoiAoiNumber poiNumber = (PoiAoiNumber)localset.getField(RvtPoiMetadataKey.PoiAoiNumber);
        pois.put(poiNumber.getNumber(), localset);
    }

    /**
     * Get the available POI Local Set indexes.
     * @return set of POI/AOI Numbers.
     */
    public Set<Integer> getPOIIndexes()
    {
        return pois.keySet();
    }

    /**
     * Get the POI Local Set by index.
     * @param index the index (POI/AOI Number)
     * @return POI corresponding to the index.
     */
    public RvtPoiLocalSet getPOI(int index)
    {
        return pois.get(index);
    }

    public void addAreaOfInterestLocalSet(RvtAoiLocalSet localset)
    {
        if (localset == null)
        {
            throw new IllegalArgumentException("Cannot add null AOI local set to RVT parent");
        }
        if (localset.getTags() == null)
        {
            throw new IllegalArgumentException("Cannot add null AOI local set tags to RVT parent");
        }
        if (!localset.getTags().contains(RvtAoiMetadataKey.PoiAoiNumber))
        {
            throw new IllegalArgumentException("AOI local set must contain POI/AOI Number");
        }
        PoiAoiNumber aoiNumber = (PoiAoiNumber)localset.getField(RvtAoiMetadataKey.PoiAoiNumber);
        aois.put(aoiNumber.getNumber(), localset);
    }

    /**
     * Get the available AOI Local Set indexes.
     * @return set of POI/AOI Numbers.
     */
    public Set<Integer> getAOIIndexes()
    {
        return aois.keySet();
    }

    /**
     * Get the AOI Local Set by index.
     * @param index the index (POI/AOI Number)
     * @return AOI corresponding to the index.
     */
    public RvtAoiLocalSet getAOI(int index)
    {
        return aois.get(index);
    }

}
