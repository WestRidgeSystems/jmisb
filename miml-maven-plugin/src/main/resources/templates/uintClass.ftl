<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
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
<#if minValue?? && maxValue??>
     * The value must be in the range [${minValue}, ${maxValue}].
<#elseif minValue??>
     * The value must be at least ${minValue}.
</#if>
     * @param value the unsigned integer value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(long value) {
<#if minValue??>
        if (value < ${minValue}) {
            throw new IllegalArgumentException("Minimum value for ${nameSentenceCase} is ${minValue}");
        }
<#else>
        if (value < 0) {
            throw new IllegalArgumentException("Minimum value for ${nameSentenceCase} is 0");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new IllegalArgumentException("Maximum value for ${nameSentenceCase} is ${maxValue}");
        }
</#if>
        this.uintValue = value;
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${nameSentenceCase}(byte[] bytes) throws KlvParseException {
        try {
            this.uintValue = PrimitiveConverter.variableBytesToUint64(bytes);
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

    /**
     * Get the value of this ${nameSentenceCase}.
     *
     * @return the value as an unsigned long
     */
    public long getValue() {
        return this.uintValue;
    }
}
