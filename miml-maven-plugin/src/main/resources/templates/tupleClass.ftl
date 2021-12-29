// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
 * MIMD {@link ${parentName}} ${name} attribute.
 *
 * <p>This is a specialisation of a Tuple.
 *
 * <p>The Tuple Type is typically a short sequence of Unsigned Integers (UInts) for use within the
 * MIMD Model’s addressing method (for Directed Association) along with other uses. The Tuple Type
 * is the same as a one-dimensional array of UInt, but because of its short length and the
 * restriction to one dimension, it is a separate type to enable more efficient transmutations.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {

    private final List<Integer> values = new ArrayList<>();
    /**
     * Construct from value.
     *
     * @param value the array of (unsigned) integer values to initialise this ${namespacedName} with
     */
    public ${namespacedName}(int[] value) {
        this.values.addAll(Arrays.stream(value).boxed().collect(Collectors.toList()));
    }

    /**
     * Create a ${namespacedName} from encoded bytes.
     *
     * @param data the byte array to parse the ${namespacedName} from.
     * @throws KlvParseException if the parsing fails
     */
    public ${namespacedName}(byte[] data) throws KlvParseException {
        for (int offset = 0; offset < data.length; ) {
            BerField tupleField = BerDecoder.decode(data, offset, true);
            offset += tupleField.getLength();
            values.add(tupleField.getValue());
        }
    }

    /**
     * Parse a ${namespacedName} out of a byte array.
     *
     * @param data the byte array to parse the ${namespacedName} from.
     * @return ${namespacedName} equivalent to the byte array
     * @throws KlvParseException if the parsing fails
     */
    public static ${namespacedName} fromBytes(byte[] data) throws KlvParseException {
        return new ${namespacedName}(data);
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        this.values.forEach(
                (value) -> {
                    arrayBuilder.appendAsOID(value);
                });
        return arrayBuilder.toBytes();
    }

    /**
     * Get the values for this ${namespacedName}.
     *
     * @return the values as an (unsigned) integer array.
     */
    public int[] getValues() {
        return this.values.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public String getDisplayableValue() {
        return "("
                + this.values.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ")";
    }
}
