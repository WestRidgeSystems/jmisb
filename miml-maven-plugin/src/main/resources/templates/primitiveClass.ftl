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
 * <p>This is a specialisation of ${typeDescription}.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final ${primitiveType} implementingValue;

    /**
     * Construct from value.
     *
<#if minValue?? && maxValue??>
     * <p>The value must be in the range [${minValue}, ${maxValue}].
     *
<#elseif minValue??>
     * <p>The minimum value is ${minValue}.
     *
</#if>
<#if maxLength??>
     * <p>The maximum length is ${maxLength}.
     * 
</#if>
<#if units?has_content>
     * <p>The value is in units of ${units}.
     * 
</#if>
     * @param value ${typeDescription} value to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid (e.g. outside of the valid range).
     */
    public ${namespacedName}(${primitiveType} value) throws KlvParseException {
<#if minValue??>
        if (value < ${minValue}) {
            throw new KlvParseException("Minimum value for ${namespacedName} is ${minValue}");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new KlvParseException("Maximum value for ${namespacedName} is ${maxValue}");
        }
</#if>
<#if maxLength??>
        if (value.length() > ${maxLength}) {
            throw new KlvParseException("${namespacedName} maximum length is ${maxLength}");
        }
</#if>
        this.implementingValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
<#if typeName=="Real">
    <#if minValue?? && maxValue??>
            org.jmisb.api.klv.st1201.FpEncoder decoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, bytes.length);
            this.implementingValue = decoder.decode(bytes);
    <#else>
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.toFloat64(bytes);
    </#if>
<#elseif typeName=="Integer">
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToInt64(bytes);
<#elseif typeName=="String">
            this.implementingValue = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
<#elseif typeName=="UInt">
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToUint64(bytes);
</#if>
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
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

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * <p>This version allows parsing of a specific number of bytes from a given offset.
     *
     * @param bytes Encoded byte array.
     * @param offset the offset into the byte array to start decoding.
     * @param length the number of bytes to decode.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public ${namespacedName}(byte[] bytes, int offset, int length) throws KlvParseException {
        try {
<#if typeName=="Real">
    <#if minValue?? && maxValue??>
            org.jmisb.api.klv.st1201.FpEncoder decoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, length);
            this.implementingValue = decoder.decode(bytes, offset);
    <#else>
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.toFloat64(bytes, offset, length);
    </#if>
<#elseif typeName=="Integer">
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToInt64(bytes, offset, length);
<#elseif typeName=="String">
            this.implementingValue = new String(bytes, offset, length, java.nio.charset.StandardCharsets.UTF_8);
<#elseif typeName=="UInt">
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToUint64(bytes, offset, length);
</#if>
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes() {
<#if typeName=="Real">
    <#if minValue?? && maxValue??>
        <#if resolution??>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, (double)${resolution});
        <#else>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, Float.BYTES);
        </#if>
        return encoder.encode(implementingValue);
    <#else>
        // TODO: consider a version that allows selection of length 4 or 8 bytes.
        return org.jmisb.core.klv.PrimitiveConverter.float64ToBytes(implementingValue);
    </#if>
<#elseif typeName=="Integer">
        return org.jmisb.core.klv.PrimitiveConverter.int64ToVariableBytes(implementingValue);
<#elseif typeName=="String">
        return this.implementingValue.getBytes(java.nio.charset.StandardCharsets.UTF_8);
<#elseif typeName=="UInt">
        return org.jmisb.core.klv.PrimitiveConverter.uintToVariableBytes(implementingValue);
</#if>
    }

    @Override
    public String getDisplayableValue() {
<#if units?has_content>
        return String.format("${displayFormatter} ${escapedUnits}", this.implementingValue);
<#else>
        return String.format("${displayFormatter}", this.implementingValue);
</#if>
    }

    /**
     * Get the value of this ${namespacedName}.
     *
<#if units?has_content>
     * @return The value as ${typeDescription}, in units of ${units}.
<#else>
     * @return The value as ${typeDescription}.
</#if>
     */
    public ${primitiveType} getValue() {
        return this.implementingValue;
    }
}
