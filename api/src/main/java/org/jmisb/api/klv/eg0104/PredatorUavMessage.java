package org.jmisb.api.klv.eg0104;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.UdsField;
import org.jmisb.api.klv.UdsParser;
import org.jmisb.api.klv.UniversalLabel;

/** Represents a Predator UAV Metadata Message (EG0104.5). */
public class PredatorUavMessage implements IMisbMessage {
    /** Map containing all data elements in the message */
    protected SortedMap<PredatorMetadataKey, IPredatorMetadataValue> map = new TreeMap<>();

    public PredatorUavMessage(byte[] bytes) throws KlvParseException {
        // Parse the length field
        int offset = UniversalLabel.LENGTH;
        BerField messageLength = BerDecoder.decode(bytes, offset, false);
        offset += messageLength.getLength();

        // Parse fields out of the array
        List<UdsField> fields = UdsParser.parseFields(bytes, offset, messageLength.getValue());

        // Convert field data based on ST 0104
        for (UdsField field : fields) {
            PredatorMetadataKey key = PredatorMetadataKey.getKey(field.getKey());
            IPredatorMetadataValue value =
                    PredatorUniversalSetFactory.createValue(
                            PredatorMetadataKey.getKey(field.getKey()), field.getValue());
            map.put(key, value);
        }
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.PredatorMetadataLocalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        throw new UnsupportedOperationException(
                "No support for generating EG0104 messages. Its an obsolete format.");
    }

    @Override
    public String displayHeader() {
        return "Predator EG0104.5";
    }

    public Set<PredatorMetadataKey> getKeys() {
        return map.keySet();
    }

    public IPredatorMetadataValue getField(PredatorMetadataKey key) {
        return map.get(key);
    }
}
