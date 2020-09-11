<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
package ${packageName};

import org.jmisb.api.common.KlvParseException;
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
     * @throws KlvParseException if the array could not be parsed
     */
    public ${nameSentenceCase}(byte[] bytes) throws KlvParseException {
        try {
            this.intValue = PrimitiveConverter.variableBytesToInt64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${nameSentenceCase} corresponding to the encoded byte array.
     * @throws KlvParseException if the array could not be parsed
     */
    public static ${nameSentenceCase} fromBytes(byte[] bytes) throws KlvParseException {
        return new ${nameSentenceCase}(bytes);
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

    /**
     * Get the value of this ${nameSentenceCase}.
     *
     * @return the value as a signed long
     */
    public long getValue() {
        return this.intValue;
    }
}
