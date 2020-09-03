// Generated file - changes will be lost on rebuild
package ${packageName};

import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ${name} MIMD Signed Integer.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final long intValue;

    /**
     * Construct from value.
     *
     * @param value the signed integer value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(long value) {
        this.intValue = value;
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public ${nameSentenceCase}(byte[] bytes) {
        this.intValue = PrimitiveConverter.variableBytesToInt64(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes(){
        return PrimitiveConverter.int64ToVariableBytes(intValue);
    }

    @Override
    public String getDisplayableValue() {
        <#if units??>
        return String.format("%d ${units}", this.intValue);
        <#else>
        return String.format("%d", this.intValue);
        </#if>
    }
}
