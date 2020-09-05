// Generated file - changes will be lost on rebuild
package ${packageName};

import java.nio.charset.StandardCharsets;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;

/**
 * ${name} MIMD String.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final String stringValue;

    /**
     * Construct from value.
     *
     * @param value the string value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(String value) {
        <#if maxLength??>
        if (value.length() > ${maxLength}) {
            throw new IllegalArgumentException("${nameSentenceCase} maximum length is ${maxLength} characters");
        }
        </#if>
        this.stringValue = value;
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public ${nameSentenceCase}(byte[] bytes) {
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${nameSentenceCase} corresponding to the encoded byte array.
     */
    public static ${nameSentenceCase} fromBytes(byte[] bytes) {
        return new ${nameSentenceCase}(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes(){
        return this.stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue() {
        return this.stringValue;
    }

    /**
     * Get the value of this ${nameSentenceCase}.
     *
     * @return the value as a String
     */
    public String getValue() {
        return this.stringValue;
    }
}
