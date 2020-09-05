// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import ${listItemTypePackage}.${listItemType};
import org.jmisb.api.klv.st190x.IMimdMetadataValue;

/**
 * MIMD LIST&lt;${listItemType}&gt; implementation.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final List<${listItemType}> listValues = new ArrayList<>();

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
        while (index < data.length - 1) {
            BerField lengthField = BerDecoder.decode(data, index, false);
            // TODO: handle lengthField == 0, which is a special case - ZLE.
            // Zero-Length-Element (ZLE) as a filler element to mark an element as unchanged since the last Packet
            index += lengthField.getLength();
            ${listItemType} listItem = new ${listItemType}(data, index, lengthField.getValue());
            listValues.add(listItem);
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
        for (${listItemType} value : listValues) {
            byte[] listItemBytes = value.getBytes();
            arrayBuilder.appendAsBerLength(listItemBytes.length);
            arrayBuilder.append(listItemBytes);
        }
        arrayBuilder.prependLength();
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[${nameSentenceCase}]";
    }
}
