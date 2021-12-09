package org.jmisb.api.klv.st0809;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Meteorological Metadata Local Set.
 *
 * <p>This local set supports encoding of meteorological data in KLV.
 */
public class MeteorologicalMetadataLocalSet implements IMisbMessage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MeteorologicalMetadataLocalSet.class);

    /**
     * Create a {@link IMeteorologicalMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the parsing fails
     */
    static IMeteorologicalMetadataValue createValue(MeteorologicalMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case PrecisionTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case Version:
                return new ST0809Version(bytes);
            case ReportingID:
                return new ReportingID(bytes);
            case DeviceLatitude:
                return new DeviceLatitude(bytes);
            case DeviceLongitude:
                return new DeviceLongitude(bytes);
            case BarometricPressure:
                return new BarometricPressure(bytes);
            case BlackGlobeTemperature:
                return new BlackGlobeTemperature(bytes);
            case WetBulbTemperatureHeatIndex:
                return new WetBulbTemperatureHeatIndex(bytes);
            case CloudCoverPercentage:
                return new CloudCoverPercentage(bytes);
            case CloudSkyCoverType:
                return new CloudSkyCoverType(bytes);
            case CloudBaseHeight:
                return new CloudBaseHeight(bytes);
            case CloudTopHeight:
                return new CloudTopHeight(bytes);
            case CloudThickness:
                return new CloudThickness(bytes);
            case CarbonDioxideConcentration:
                return new CarbonDioxideConcentration(bytes);
            case OzoneConcentration:
                return new OzoneConcentration(bytes);
            case Temperature:
                return new Temperature(bytes);
            case DryBulbTemperature:
                return new DryBulbTemperature(bytes);
            case WetBulbTemperature:
                return new WetBulbTemperature(bytes);
            case RelativeHumidity:
                return new RelativeHumidity(bytes);
            case DewPoint:
                return new DewPoint(bytes);
            case TemperatureLapseRate:
                return new TemperatureLapseRate(bytes);
            case LightingConditions:
                return new LightingConditions(bytes);
            case AmbientLightColorTemperature:
                return new AmbientLightColorTemperature(bytes);
            case VisibilityConditions:
                return new VisibilityConditions(bytes);
            case Visibility:
                return new Visibility(bytes);
            case SolarIlluminationDiffuse:
                return new SolarIlluminationDiffuse(bytes);
            case SolarIlluminationDirect:
                return new SolarIlluminationDirect(bytes);
            case TotalIlluminationDirect:
                return new TotalIlluminationDirect(bytes);
            case FogPresence:
                return new FogPresence(bytes);
            case FogThickness:
                return new FogThickness(bytes);
            case FogCoverPercentage:
                return new FogCoverPercentage(bytes);
            case FogExtinctionCoefficient:
                return new FogExtinctionCoefficient(bytes);
            case PrecipitationType:
                return new PrecipitationType(bytes);
            case PrecipitationRate:
                return new PrecipitationRate(bytes);
            case RNaught:
                return new RNaught(bytes);
            case ThetaNaught:
                return new ThetaNaught(bytes);
            case GreenwoodFrequency:
                return new GreenwoodFrequency(bytes);
            case RytovParameter:
                return new RytovParameter(bytes);
            case TylerTrackingFrequency:
                return new TylerTrackingFrequency(bytes);
            default:
                LOGGER.info("Unknown Meteorological Metadata tag: {}", tag);
        }
        return null;
    }

    /**
     * Map containing all elements in the message.
     *
     * <p>This map provides conformance with ST 0809.1-4.
     */
    private final SortedMap<MeteorologicalMetadataKey, IMeteorologicalMetadataValue> map =
            new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public MeteorologicalMetadataLocalSet(
            Map<MeteorologicalMetadataKey, IMeteorologicalMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build an Meteorological Metadata Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public MeteorologicalMetadataLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        for (LdsField field : fields) {
            MeteorologicalMetadataKey key = MeteorologicalMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown Meteorological Metadata tag: {}", field.getTag());
                    break;
                default:
                    IMeteorologicalMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException(
                    "ST 0809 Meteorological Metadata Local Set cannot be nested.");
        }
        if (!map.containsKey(MeteorologicalMetadataKey.PrecisionTimeStamp)) {
            throw new IllegalArgumentException("Cannot frame message without Precision Time Stamp");
        }
        if (!map.containsKey(MeteorologicalMetadataKey.Version)) {
            map.put(
                    MeteorologicalMetadataKey.Version,
                    new ST0809Version(MeteorologicalMetadataConstants.ST_VERSION_NUMBER));
        }
        ArrayBuilder builder = new ArrayBuilder();
        // ST 0809.1-02
        addItemToBuilder(MeteorologicalMetadataKey.PrecisionTimeStamp, builder);
        // ST 0809.1-03
        addItemToBuilder(MeteorologicalMetadataKey.Version, builder);
        for (MeteorologicalMetadataKey tag : map.keySet()) {
            if (tag.equals(MeteorologicalMetadataKey.PrecisionTimeStamp)) {
                continue;
            }
            if (tag.equals(MeteorologicalMetadataKey.Version)) {
                continue;
            }
            addItemToBuilder(tag, builder);
        }
        builder.prependLength();
        builder.prepend(KlvConstants.MeteorologicalMetadataLocalSetUl);
        return builder.toBytes();
    }

    private void addItemToBuilder(MeteorologicalMetadataKey tag, ArrayBuilder builder) {
        builder.appendAsOID(tag.getIdentifier());
        byte[] valueBytes = getField(tag).getBytes();
        builder.appendAsBerLength(valueBytes.length);
        builder.append(valueBytes);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IMeteorologicalMetadataValue getField(IKlvKey key) {
        return map.get((MeteorologicalMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.MeteorologicalMetadataLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 0809 Meteorological Metadata";
    }
}
