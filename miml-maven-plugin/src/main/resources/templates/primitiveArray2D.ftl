<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of ${typeDescription} 2D array.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final ${primitiveType}[][] implementingType;

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
     * @param value ${typeDescription} 2D array to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid.
     */
    public ${namespacedName}(${primitiveType}[][] value) throws KlvParseException {
<#if arrayDimensionSize(0)??>
        if (value.length != ${arrayDimensionSize(0)}) {
            throw new KlvParseException("Required number of ${namespacedName} rows is ${arrayDimensionSize(0)}");
        }
</#if>
<#if arrayDimensionSize(1)??>
        if (value[0].length != ${arrayDimensionSize(1)}) {
            throw new KlvParseException("Required number of ${namespacedName} columns is ${arrayDimensionSize(1)}");
        }
</#if>
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] < ${minValue?string["0.000"]}) {
                    throw new KlvParseException("Minimum value for ${namespacedName} elements is ${minValue}");
                }
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] > ${maxValue?string["0.000"]}) {
                    throw new KlvParseException("Maximum value for ${namespacedName} elements is ${maxValue}");
                }
            }
        }
</#if>
        this.implementingType = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        org.jmisb.api.klv.st1303.MDAPDecoder decoder = new org.jmisb.api.klv.st1303.MDAPDecoder();
<#if typeName=="Boolean">
        this.implementingType = decoder.decodeBoolean2D(bytes, 0);
<#elseif typeName=="Integer">
        this.implementingType = decoder.decodeInt2D(bytes, 0);
<#elseif typeName=="Real">
        this.implementingType = decoder.decodeFloatingPoint2D(bytes, 0);
<#elseif typeName=="UInt">
        this.implementingType = decoder.decodeUInt2D(bytes, 0);
</#if>
<#if arrayDimensionSize(0)??>
        if (this.implementingType.length != ${arrayDimensionSize(0)}) {
            throw new KlvParseException("Required number of ${namespacedName} rows is ${arrayDimensionSize(0)}");
        }
</#if>
<#if arrayDimensionSize(1)??>
        if (this.implementingType[0].length != ${arrayDimensionSize(1)}) {
            throw new KlvParseException("Required number of ${namespacedName} columns is ${arrayDimensionSize(1)}");
        }
</#if>
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @return new ${namespacedName} corresponding to the encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public static ${namespacedName} fromBytes(byte[] bytes) throws KlvParseException {
        return new ${namespacedName}(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes(){
        try {
<#if typeName=="Boolean">
            org.jmisb.api.klv.st1303.BooleanArrayEncoder encoder = new org.jmisb.api.klv.st1303.BooleanArrayEncoder();
<#elseif typeName=="Integer">
            org.jmisb.api.klv.st1303.NaturalFormatEncoder encoder = new org.jmisb.api.klv.st1303.NaturalFormatEncoder();
<#elseif typeName=="Real">
    <#if resolution??>
            org.jmisb.api.klv.st1303.ElementProcessedEncoder encoder = new org.jmisb.api.klv.st1303.ElementProcessedEncoder(${minValue}, ${maxValue}, (double)${resolution});
    <#else>
            org.jmisb.api.klv.st1303.ElementProcessedEncoder encoder = new org.jmisb.api.klv.st1303.ElementProcessedEncoder(${minValue}, ${maxValue}, Float.BYTES);
    </#if>
<#elseif typeName=="UInt">
            org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder encoder = new org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder();
</#if>
            return encoder.encode(this.implementingType);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public String getDisplayableValue() {
        // TODO: see if we can return something useful here
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as ${typeDescription} 2D array.
     */
    public ${primitiveType}[][] getValue() {
        return this.implementingType.clone();
    }
}
