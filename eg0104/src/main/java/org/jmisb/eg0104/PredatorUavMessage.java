package org.jmisb.eg0104;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.UdsField;
import org.jmisb.api.klv.UdsParser;
import org.jmisb.api.klv.UniversalLabel;

/** Represents a Predator UAV Metadata Message (EG0104.5). */
public class PredatorUavMessage implements IMisbMessage {

    /** Universal label for obsolete Predator UAV Basic Universal Metadata Set (EG0104.5). */
    public static final UniversalLabel PredatorMetadataLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x01, 0x02,
                        0x01, 0x01, 0x00, 0x00
                    });

    /** Map containing all data elements in the message. */
    protected SortedMap<PredatorMetadataKey, IPredatorMetadataValue> map = new TreeMap<>();

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing the EG0104-encoded data
     * @throws KlvParseException if a parsing error occurs
     */
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
        return PredatorMetadataLocalSetUl;
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

    /**
     * Get all keys present in the message.
     *
     * @return Set containing all EG0104 keys contained in the message
     */
    @Override
    public Set<PredatorMetadataKey> getIdentifiers() {
        return map.keySet();
    }

    /**
     * Get the value of a specified key.
     *
     * @param key The key to retrieve
     * @return The value, or null if not present
     */
    public IPredatorMetadataValue getField(PredatorMetadataKey key) {
        return map.get(key);
    }

    @Override
    public IPredatorMetadataValue getField(IKlvKey tag) {
        return this.getField((PredatorMetadataKey) tag);
    }
}
