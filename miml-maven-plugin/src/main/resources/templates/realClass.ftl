<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ${name} MIMD Floating Point value.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final double doubleValue;

    /**
     * Construct from value.
     *
<#if maxValue??>
     * The value must be in the range [${minValue}, ${maxValue}].
     *
<#elseif minValue??>
     * The minimum value is ${minValue}.
     *
</#if>
     * @param value the floating point value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(double value) throws IllegalArgumentException{
<#if minValue??>
        if (value < ${minValue}) {
            throw new IllegalArgumentException("Minimum value for ${nameSentenceCase} is ${minValue}");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new IllegalArgumentException("Maximum value for ${nameSentenceCase} is ${maxValue}");
        }
</#if>
        this.doubleValue = value;
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array (4 or 8 bytes)
     * @throws KlvParseException if the array could not be parsed
     */
    public ${nameSentenceCase}(byte[] bytes) throws KlvParseException {
        // TODO: check if we're in IMAPA land
        try {
            this.doubleValue = PrimitiveConverter.toFloat64(bytes);
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
        // TODO: check if we're in IMAPA land
        // TODO: consider a version that allows selection of length 4 or 8 bytes.
        return PrimitiveConverter.float64ToBytes(doubleValue);
    }

    @Override
    public String getDisplayableValue() {
        <#if units??>
        return String.format("%.3f ${escapedUnits}", this.doubleValue);
        <#else>
        return String.format("%.3f", this.doubleValue);
        </#if>
    }

    /**
     * Get the value of this ${nameSentenceCase}.
     *
     * @return the value as a double
     */
    public double getValue() {
        return this.doubleValue;
    }
}
