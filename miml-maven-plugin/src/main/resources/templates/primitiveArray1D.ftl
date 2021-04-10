<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of ${typeDescription} 1D array.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue, INestedKlvValue {
    private final ${primitiveType}[] implementingType;

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
     * @param value ${typeDescription} array to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid.
     */
    public ${namespacedName}(${primitiveType}[] value) throws KlvParseException {
<#if arrayDimensionSize(0)??>
        if (value.length != ${arrayDimensionSize(0)}) {
            throw new KlvParseException("Required number of ${namespacedName} elements is ${arrayDimensionSize(0)}");
        }
</#if>
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] < ${minValue}) {
                throw new KlvParseException("Minimum value for ${namespacedName} elements is ${minValue}");
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] > ${maxValue}) {
                throw new KlvParseException("Maximum value for ${namespacedName} elements is ${maxValue}");
            }
        }
</#if>
        this.implementingType = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
<#if typeName=="Real">
        org.jmisb.api.klv.st1303.MDAPDecoder decoder = new org.jmisb.api.klv.st1303.MDAPDecoder();
        this.implementingType = decoder.decodeFloatingPoint1D(bytes, 0);
<#elseif ref>
        try {
            java.util.List<${primitiveType}> values = new java.util.ArrayList<>();
            for (int i = 0; i < bytes.length; ++i) {
                org.jmisb.api.klv.BerField serialField = org.jmisb.api.klv.BerDecoder.decode(bytes, i, true);
                i += serialField.getLength();
                org.jmisb.api.klv.BerField groupField = org.jmisb.api.klv.BerDecoder.decode(bytes, i, true);
                i += groupField.getLength();
                ${primitiveType} value = new ${primitiveType}(serialField.getValue(), groupField.getValue());
                values.add(value);
            }
            this.implementingType = values.toArray(new ${primitiveType}[0]);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
<#elseif typeName=="UInt">
        org.jmisb.api.klv.st1303.MDAPDecoder decoder = new org.jmisb.api.klv.st1303.MDAPDecoder();
        this.implementingType = decoder.decodeUInt1D(bytes, 0);
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
    public byte[] getBytes() {
<#if ref>
        org.jmisb.api.klv.ArrayBuilder builder = new org.jmisb.api.klv.ArrayBuilder();
        for (${primitiveType} ref: this.implementingType) {
            builder.appendAsOID(ref.getSerialNumber());
            builder.appendAsOID(ref.getGroupIdentifier());
        }
        return builder.toBytes();
<#else>
        try {
    <#if typeName=="Real">
        <#if resolution??>
            org.jmisb.api.klv.st1303.ElementProcessedEncoder encoder = new org.jmisb.api.klv.st1303.ElementProcessedEncoder(${minValue}, ${maxValue}, (double)${resolution});
        <#elseif minValue?? && maxValue??>
            org.jmisb.api.klv.st1303.ElementProcessedEncoder encoder = new org.jmisb.api.klv.st1303.ElementProcessedEncoder(${minValue}, ${maxValue}, Float.BYTES});
        <#else>
            org.jmisb.api.klv.st1303.NaturalFormatEncoder encoder = new org.jmisb.api.klv.st1303.NaturalFormatEncoder();
        </#if>
            return encoder.encode(this.implementingType);
    <#elseif typeName=="UInt">
        <#if minValue?? && maxValue?? && minValue==0.0 && maxValue=255.0>
            org.jmisb.api.klv.st1303.NaturalFormatEncoder encoder = new org.jmisb.api.klv.st1303.NaturalFormatEncoder();
            return encoder.encodeUnsigned(this.implementingType);
        <#else>
            org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder encoder = new org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder();
            return encoder.encode(this.implementingType);
        </#if>
    </#if>
        } catch (KlvParseException ex) {
            return new byte[0];
        }
</#if>
    }

    @Override
    public String getDisplayableValue() {
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as ${typeDescription} 1D array.
     */
    public ${primitiveType}[] getValue() {
        return this.implementingType.clone();
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        ${namespacedName}ItemKey key = (${namespacedName}ItemKey) tag;
        ${primitiveType} value = this.implementingType[key.getIdentifier()];
        IKlvValue field = new IKlvValue() {
            @Override
            public String getDisplayableValue() {
<#if ref>
                return String.format("(%d, %d)", value.getSerialNumber(), value.getGroupIdentifier());
<#else>
                return String.format("${displayFormatter}", value);
</#if>
            }

            @Override
            public String getDisplayName() {
                return String.format("%d", key.getIdentifier());
            }
        };
        return field;
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        SortedSet<${namespacedName}ItemKey> arrayIdentifiers = new TreeSet<>();
        for (int i = 0; i < implementingType.length; ++i) {
            arrayIdentifiers.add(new ${namespacedName}ItemKey(i));
        }
        return arrayIdentifiers;
    }
}
