// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import ${listItemTypePackage}.${listItemType};
import org.jmisb.api.klv.st190x.IMimdMetadataValue;

/**
 * MIMD LIST&lt;${listItemType}&gt; implementation.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue, INestedKlvValue {
    private final Map<${listItemType}Identifier, ${listItemType}> listValues = new HashMap<>();

    /**
     * Create a $LIST&lt;${listItemType}&gt; from values.
     *
     * @param values the values to construct from
     */
    public ${nameSentenceCase}(Map<${listItemType}Identifier, ${listItemType}> values) {
        listValues.putAll(values);
    }

    /**
     * Build a $LIST&lt;${listItemType}&gt; from encoded bytes.
     *
     * @param data the bytes to build from
     * @param offset the offset into {@code bytes} to start parsing from
     * @param numBytes the number of bytes to parse
     * @throws KlvParseException if parsing fails
     */
    public ${nameSentenceCase}(byte[] data, int offset, int numBytes) throws KlvParseException {
        int index = offset;
        int itemCount = 0;
        while (index < data.length - 1) {
            BerField lengthField = BerDecoder.decode(data, index, false);
            // TODO: handle lengthField == 0, which is a special case - ZLE.
            // Zero-Length-Element (ZLE) as a filler element to mark an element as unchanged since the last Packet
            index += lengthField.getLength();
            ${listItemType} listItem = new ${listItemType}(data, index, lengthField.getValue());
            listValues.put(new ${listItemType}Identifier(itemCount), listItem);
            index += lengthField.getValue();
        }
    }

    /**
     * Build a LIST&lt;${listItemType}&gt; from encoded bytes.
     *
     * @param data the bytes to build from
     * @return new ${nameSentenceCase} corresponding to the encoded byte array.
     * @throws KlvParseException if parsing fails
     */
    public static ${nameSentenceCase} fromBytes(byte[] data) throws KlvParseException {
        return new ${nameSentenceCase}(data, 0, data.length);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (${listItemType} value : listValues.values()) {
            byte[] listItemBytes = value.getBytes();
            arrayBuilder.appendAsBerLength(listItemBytes.length);
            arrayBuilder.append(listItemBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[${nameSentenceCase}]";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return listValues.get((${listItemType}Identifier) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return listValues.keySet();
    }
}
