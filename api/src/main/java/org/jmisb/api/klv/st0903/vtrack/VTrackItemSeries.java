package org.jmisb.api.klv.st0903.vtrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.api.klv.st0903.vtarget.TargetIdentifierKey;

/**
 * VTrack LS VTrackSeries (ST 0903 VTrack Local Set Item 101).
 *
 * <p>Track metadata values, each of which is a VTrackItem Pack.
 */
public class VTrackItemSeries implements IVTrackMetadataValue, INestedKlvValue {
    private final Map<Integer, VTrackItem> trackItems = new HashMap<>();

    /**
     * Create the message from a list of VTrackItems.
     *
     * @param values the track item packs to include in the series.
     */
    public VTrackItemSeries(List<VTrackItem> values) {
        values.forEach(
                (trackItem) -> trackItems.put(trackItem.getTargetIdentifier(), trackItem));
    }

    /**
     * Create from encoded bytes.
     *
     * <p>This constructor allows selection of which encoding rules (according to the ST0903
     * version) to apply.
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     * @throws KlvParseException if there is a parsing error on the byte array.
     */
    public VTrackItemSeries(byte[] bytes, EncodingMode encodingMode) throws KlvParseException {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            VTrackItem trackItem =
                    new VTrackItem(bytes, index, lengthField.getValue(), encodingMode);
            trackItems.put(trackItem.getTargetIdentifier(), trackItem);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (VTrackItem vTrackItem : trackItems.values()) {
            byte[] trackItemBytes = vTrackItem.getBytes();
            arrayBuilder.appendAsBerLength(trackItemBytes.length);
            arrayBuilder.append(trackItemBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[" + trackItems.size() + " Points]";
    }

    @Override
    public String getDisplayName() {
        return "Track Points";
    }

    /**
     * Get the VTrackItems in the series.
     *
     * @return the VTrackItems as a List
     */
    public List<VTrackItem> getTrackItems() {
        return new ArrayList<>(trackItems.values());
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        int identifier = tag.getIdentifier();
        return trackItems.get(identifier);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<TargetIdentifierKey> identifiers = new TreeSet<>();
        trackItems
                .keySet()
                .forEach(
                        (Integer ident) -> identifiers.add(new TargetIdentifierKey(ident)));
        return identifiers;
    }
}
