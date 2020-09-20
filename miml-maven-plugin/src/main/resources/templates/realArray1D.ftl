<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
<#if minValue?? && maxValue??>
import org.jmisb.api.klv.st1201.FpEncoder;
</#if>
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
<#if !(minValue?? && maxValue??)>
import org.jmisb.core.klv.PrimitiveConverter;
</#if>

/**
 * ${name} MIMD Floating Point Array.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final double[] doubleArray;

    /**
     * Construct from value.
     *
<#if maxValue??>
     * Each array value must be in the range [${minValue}, ${maxValue}].
     *
<#elseif minValue??>
     * The minimum value for each element in the array is ${minValue}.
     *
</#if>
     * @param value the floating point values to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(double[] value) throws IllegalArgumentException{
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] < ${minValue}) {
                throw new IllegalArgumentException("Minimum value for ${nameSentenceCase} elements is ${minValue}");
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] > ${maxValue}) {
                throw new IllegalArgumentException("Maximum value for ${nameSentenceCase} elements is ${maxValue}");
            }
        }
</#if>
        this.doubleArray = value.clone();
    }

    /**
     * Create ${nameSentenceCase} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${nameSentenceCase}(byte[] bytes) throws KlvParseException {
        // TODO: decode using ST1303 rules
        this.doubleArray = new double[0];
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
        // TODO: encode using ST1303 rules
        return null;
    }

    @Override
    public String getDisplayableValue() {
        // TODO: see if we can return something useful here
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${nameSentenceCase}.
     *
     * @return the value as a double array
     */
    public double[] getValue() {
        return this.doubleArray.clone();
    }
}
