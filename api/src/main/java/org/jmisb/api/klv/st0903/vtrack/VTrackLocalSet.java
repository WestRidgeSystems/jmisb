package org.jmisb.api.klv.st0903.vtrack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st0601.Checksum;
import org.jmisb.api.klv.st0903.OntologySeries;
import org.jmisb.api.klv.st0903.PrecisionTimeStamp;
import org.jmisb.api.klv.st0903.ST0903Version;
import org.jmisb.api.klv.st0903.VmtiMetadataConstants;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.vtracker.BoundarySeries;
import org.jmisb.api.klv.st0903.vtracker.DetectionStatus;
import org.jmisb.api.klv.st0903.vtracker.EndTime;
import org.jmisb.api.klv.st0903.vtracker.StartTime;
import org.jmisb.api.klv.st0903.vtracker.TrackConfidence;
import org.jmisb.api.klv.st0903.vtracker.TrackId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** VTrack Local Set. */
public class VTrackLocalSet implements IMisbMessage {

    /** Universal Label for VTrack Local Set. */
    public static final UniversalLabel VTrackLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x1E, 0x00, 0x00, 0x00
                    });

    private static final Logger LOGGER = LoggerFactory.getLogger(VTrackLocalSet.class);

    /**
     * Create a {@link IVTrackMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     * @throws KlvParseException if the byte array could not be parsed.
     * @return the new instance
     */
    public static IVTrackMetadataValue createValue(
            VTrackMetadataKey tag, byte[] bytes, EncodingMode encodingMode)
            throws KlvParseException {
        switch (tag) {
            case TrackTimeStamp:
                return new PrecisionTimeStamp(bytes);
            case TrackId:
                return new TrackId(bytes);
            case TrackStatus:
                return new DetectionStatus(bytes);
            case TrackStartTime:
                return new StartTime(bytes);
            case TrackEndTime:
                return new EndTime(bytes);
            case TrackBoundarySeries:
                return new BoundarySeries(bytes, encodingMode);
            case TrackAlgorithm:
                return new VmtiTextString(VmtiTextString.ALGORITHM, bytes);
            case TrackConfidence:
                return new TrackConfidence(bytes);
            case SystemName:
                return new VmtiTextString(VmtiTextString.SYSTEM_NAME, bytes);
            case VersionNumber:
                return new ST0903Version(bytes);
            case SourceSensor:
                return new VmtiTextString(VmtiTextString.SOURCE_SENSOR, bytes);
            case NumTrackPoints:
                return new NumTrackPoints(bytes);
            case VTrackItemSeries:
                return new VTrackItemSeries(bytes, encodingMode);
            case OntologySeries:
                return new OntologySeries(bytes);
            default:
                LOGGER.info("Unknown VTrack Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all data elements in the message. */
    private final SortedMap<VTrackMetadataKey, IVTrackMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public VTrackLocalSet(Map<VTrackMetadataKey, IVTrackMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build a VTrack Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public VTrackLocalSet(byte[] bytes) throws KlvParseException {
        int offset = 0;
        EncodingMode encodingMode = EncodingMode.IMAPB;
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, bytes.length);
        for (LdsField field : fields) {
            VTrackMetadataKey key = VTrackMetadataKey.getKey(field.getTag());
            if (key.equals(VTrackMetadataKey.VersionNumber)) {
                ST0903Version version = new ST0903Version(field.getData());
                if (version.getVersion() < 4) {
                    encodingMode = EncodingMode.LEGACY;
                }
                break;
            }
        }
        for (LdsField field : fields) {
            VTrackMetadataKey key = VTrackMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown VTrack Metadata tag: {}", field.getTag());
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
                        IVTrackMetadataValue value =
                                createValue(key, field.getData(), encodingMode);
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
        updateNumTrackPoints();
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (VTrackMetadataKey tag : getIdentifiers()) {
            if (tag == VTrackMetadataKey.Checksum) {
                continue;
            }
            // add T/L/V for this item
            arrayBuilder.appendAsOID(tag.getIdentifier());
            IVTrackMetadataValue value = getField(tag);
            byte[] bytes = value.getBytes();
            arrayBuilder.appendAsBerLength(bytes.length);
            arrayBuilder.append(bytes);
        }
        if (isNested) {
            return arrayBuilder.toBytes();
        } else {
            // put checksum placeholder on the end
            arrayBuilder.appendAsOID(VTrackMetadataKey.Checksum.getIdentifier());
            byte[] checksum = new byte[2]; // will be filled in later
            arrayBuilder.appendAsBerLength(checksum.length);
            arrayBuilder.append(checksum);
            // put length of pack on the front
            arrayBuilder.prependLength();
            // put UL on the front of that
            arrayBuilder.prepend(VTrackLocalSetUl);
            byte[] bytes = arrayBuilder.toBytes();
            // Compute the checksum and replace the last two bytes of array
            Checksum.compute(bytes, true);
            return bytes;
        }
    }

    /**
     * Get the set of tags with populated values.
     *
     * @return The set of tags for which values have been set
     */
    @Override
    public Set<VTrackMetadataKey> getIdentifiers() {
        return map.keySet();
    }

    /**
     * Get the value of a given tag.
     *
     * @param tag Tag of the value to retrieve
     * @return The value, or null if no value was set
     */
    public IVTrackMetadataValue getField(VTrackMetadataKey tag) {
        return map.get(tag);
    }

    @Override
    public IVTrackMetadataValue getField(IKlvKey key) {
        return this.getField((VTrackMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return VTrackLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST0903 VTrack";
    }

    private void updateVersion() {
        ST0903Version version = (ST0903Version) getField(VTrackMetadataKey.VersionNumber);
        // If we're missing a version, or it's too old, update it to current. Otherwise, leave it
        // alone.
        if ((version == null) || (version.getVersion() < 4)) {
            version = new ST0903Version(VmtiMetadataConstants.ST_VERSION_NUMBER);
            map.put(VTrackMetadataKey.VersionNumber, version);
        }
    }

    private void updateNumTrackPoints() {
        VTrackItemSeries trackItemSeries =
                (VTrackItemSeries) getField(VTrackMetadataKey.VTrackItemSeries);
        int numPoints;
        if (trackItemSeries == null) {
            // No track item points.
            numPoints = 0;
        } else {
            numPoints = trackItemSeries.getIdentifiers().size();
        }
        NumTrackPoints numTrackPoints = new NumTrackPoints(numPoints);
        map.put(VTrackMetadataKey.NumTrackPoints, numTrackPoints);
    }
}
