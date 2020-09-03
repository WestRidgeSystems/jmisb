// Generated file - changes will be lost on rebuild
package ${packageName};

import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ${name} MIMD Unsigned Integer.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final long uintValue;

    /**
     * Construct from value.
     *
     * @param value the unsigned integer value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(long value) {
        this.uintValue = value;
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public ${nameSentenceCase}(byte[] bytes) {
        this.uintValue = PrimitiveConverter.variableBytesToUint64(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes(){
        return PrimitiveConverter.uintToVariableBytes(uintValue);
    }

    @Override
    public String getDisplayableValue() {
        <#if units??>
        return String.format("%d ${units}", this.uintValue);
        <#else>
        return String.format("%d", this.uintValue);
        </#if>
    }
}
