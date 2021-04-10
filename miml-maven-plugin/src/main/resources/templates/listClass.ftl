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
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import ${qualifiedTypeName};

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of a MIMD LIST&lt;${typeName}&gt;.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue, INestedKlvValue {
    private final Map<${namespacedName}ItemKey, ${typeName}> listValues = new HashMap<>();

    /**
     * Create a ${namespacedName} from values.
     *
     * @param values the values to construct from.
     */
    public ${namespacedName} (List<${typeName}> values) {
        for (int i = 0; i < values.size(); i++) {
            ${namespacedName}ItemKey identifier = new ${namespacedName}ItemKey(i);
            listValues.put(identifier, values.get(i));
        }
    }

    /**
     * Build a ${namespacedName} from encoded bytes.
     *
     * @param data the bytes to build from.
     * @param offset the offset into {@code bytes} to start parsing from.
     * @param numBytes the number of bytes to parse.
     * @throws KlvParseException if parsing fails
     */
    public ${namespacedName}(byte[] data, int offset, int numBytes) throws KlvParseException {
        int index = offset;
        int itemCount = 0;
        while (index < data.length - 1) {
            BerField lengthField = BerDecoder.decode(data, index, false);
            // TODO: handle lengthField == 0, which is a special case - ZLE.
            // Zero-Length-Element (ZLE) as a filler element to mark an element as unchanged since the last Packet
            index += lengthField.getLength();
            ${typeName} listItem = new ${typeName}(data, index, lengthField.getValue());
            listValues.put(new ${namespacedName}ItemKey(itemCount), listItem);
            index += lengthField.getValue();
            itemCount++;
        }
    }

    /**
     * Build a ${namespacedName} from encoded bytes.
     *
     * @param data the bytes to build from.
     * @return new ${namespacedName} corresponding to the encoded byte array.
     * @throws KlvParseException if parsing fails
     */
    public static ${namespacedName} fromBytes(byte[] data) throws KlvParseException {
        return new ${namespacedName}(data, 0, data.length);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (${typeName} value : listValues.values()) {
            byte[] listItemBytes = value.getBytes();
            arrayBuilder.appendAsBerLength(listItemBytes.length);
            arrayBuilder.append(listItemBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "LIST[${typeName}]";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return listValues.get((${namespacedName}ItemKey) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return listValues.keySet();
    }
}
