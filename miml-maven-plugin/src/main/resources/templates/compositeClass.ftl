<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.Beta;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
<#if topLevel>
import org.jmisb.api.klv.CrcCcitt;
</#if>
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
<#if topLevel>
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
</#if>
import org.jmisb.api.klv.LdsField;
<#if topLevel>
import org.jmisb.api.klv.UniversalLabel;
<#else>
import org.jmisb.api.klv.LdsParser;
</#if>
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
<#list entries as entry>
    <#if entry.name == "mimdId">
import org.jmisb.api.klv.st1902.MimdId;
    <#break>
    </#if>
</#list>
<#list entries as entry>
    <#if entry.ref>
import org.jmisb.api.klv.st1902.MimdIdReference;
    <#break>
    </#if>
</#list>
<#if topLevel>
import org.jmisb.api.klv.st1902.MimdParser;
</#if>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${name} Local Set.
 *
 * See ${document} for more information on this data type.
 */
@Beta
public class ${name} implements <#if topLevel>IMisbMessage, </#if>IMimdMetadataValue, INestedKlvValue {
    private static final Logger LOGGER = LoggerFactory.getLogger(${name}.class);

<#list entries as entry>
    <#if entry.ref && entry.array>
    private ${entry.namespacedQualifiedName} ${entry.name};
    <#elseif entry.ref>
    private MimdIdReference ${entry.name};
    <#elseif entry.list>
    private ${entry.namespacedName} ${entry.name};
    <#elseif entry.name == "mimdId">
    private MimdId ${entry.name};
    <#elseif entry.primitive>
    private ${entry.namespacedQualifiedName} ${entry.name};
    <#else>
    private ${entry.qualifiedTypeName} ${entry.name};
    </#if>
</#list>

    /** Map containing all data elements in the message. */
    private final SortedMap<${name}MetadataKey, IMimdMetadataValue> map = new TreeMap<>();

    /**
     * Create a new empty ${name} local set.
     */
    public ${name}() {
    }

    /**
     * Create the ${name} local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public ${name}(Map<${name}MetadataKey, IMimdMetadataValue> values) {
        map.putAll(values);
        propagateValueMap(map);
    }

    private void propagateValueMap(SortedMap<${name}MetadataKey, IMimdMetadataValue> map) throws IllegalArgumentException {
        map.forEach((${name}MetadataKey key, IMimdMetadataValue value) -> {
            switch (key) {
<#list entries as entry>
            case ${entry.name}:
    <#if entry.ref && entry.array>
                if (!(value instanceof ${entry.namespacedQualifiedName})) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be ${entry.namespacedQualifiedName}");
                }
                this.${entry.name} = (${entry.namespacedQualifiedName}) value;
                break;
    <#elseif entry.ref>
                if (!(value instanceof MimdIdReference)) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be MimdIdReference");
                }
                this.${entry.name} = (MimdIdReference) value;
                break;
    <#elseif entry.list>
                if (!(value instanceof ${entry.namespacedName})) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be ${entry.namespacedName}");
                }
                this.${entry.name} = (${entry.namespacedName}) value;
                break;
    <#elseif entry.name == "mimdId">
                if (!(value instanceof MimdId)) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be MimdId");
                }
                this.${entry.name} = (MimdId) value;
                break;
    <#elseif entry.primitive>
                if (!(value instanceof ${entry.namespacedQualifiedName})) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be ${entry.namespacedQualifiedName}");
                }
                this.${entry.name} = (${entry.namespacedQualifiedName}) value;
                break;
    <#else>
                if (!(value instanceof ${entry.qualifiedTypeName})) {
                    throw new IllegalArgumentException("Value of ${entry.name} should be ${entry.qualifiedTypeName}");
                }
                this.${entry.name} = (${entry.qualifiedTypeName}) value;
                break;
    </#if>
</#list>
            default:
                throw new IllegalArgumentException(key.name() + " should not be present in ${name} type");
            }
        });
    }
<#list entries as entry>
    <#if entry.ref && entry.array>

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a ${entry.namespacedQualifiedName} value, or null if not set.
     */
    public ${entry.namespacedQualifiedName} get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the ${entry.namespacedQualifiedName} value to set.
     */
    public void set${entry.nameSentenceCase}(${entry.namespacedQualifiedName} ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    <#elseif entry.ref>

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a {@code MimdIdReference} value, or null if not set.
     */
    public MimdIdReference get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the MimdIdReference value to set.
     */
    public void set${entry.nameSentenceCase}(MimdIdReference ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    <#elseif entry.list>

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a ${entry.namespacedName} value, or null if not set.
     */
    public ${entry.namespacedName} get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the ${entry.namespacedName} value to set.
     */
    public void set${entry.nameSentenceCase}(${entry.namespacedName} ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    <#elseif entry.name == "mimdId">

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a MimdId value, or null if not set.
     */
    public MimdId get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the MimdId value to set.
     */
    public void set${entry.nameSentenceCase}(MimdId ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    <#elseif entry.primitive>

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a ${entry.namespacedQualifiedName} value, or null if not set.
     */
    public ${entry.namespacedQualifiedName} get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the ${entry.namespacedQualifiedName} value to set.
     */
    public void set${entry.nameSentenceCase}(${entry.namespacedQualifiedName} ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    <#else>

    /**
     * Get the {@code ${entry.name}} attribute value.
     *
     * @return a ${entry.qualifiedTypeName} value, or null if not set.
     */
    public ${entry.qualifiedTypeName} get${entry.nameSentenceCase}() {
        return ${entry.name};
    }

    /**
     * Set the {@code ${entry.name}} attribute value.
     *
     * @param ${entry.name} the ${entry.nameSentenceCase} value to set.
     */
    public void set${entry.nameSentenceCase}(${entry.qualifiedTypeName} ${entry.name}) {
        this.${entry.name} = ${entry.name};
    }
    </#if>
</#list>

<#if topLevel>
    /**
     * Build a ${name} Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public ${name}(byte[] bytes) throws KlvParseException {
        List<LdsField> fields = MimdParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            ${name}MetadataKey key = ${name}MetadataKey.getKey(field.getTag());
            try {
                switch (key) {
    <#list entries as entry>
                    case ${entry.name}:
        <#if entry.ref && entry.array>
                        this.${entry.name} = ${entry.namespacedQualifiedName}.fromBytes(field.getData());
                        break;
        <#elseif entry.ref>
                        this.${entry.name} = MimdIdReference.fromBytes(field.getData(), "${entry.nameSentenceCase}", "${entry.typeName}");
                        break;
        <#elseif entry.list>
                        this.${entry.name} = ${entry.namespacedName}.fromBytes(field.getData());
                        break;
        <#elseif entry.name == "mimdId">
                        this.${entry.name} = MimdId.fromBytes(field.getData());
                        break;
        <#elseif entry.primitive>
                        this.${entry.name} = ${entry.namespacedQualifiedName}.fromBytes(field.getData());
                        break;
        <#else>
                        this.${entry.name} = ${entry.qualifiedTypeName}.fromBytes(field.getData());
                        break;
        </#if>
    </#list>
                    default:
                        LOGGER.info("Unknown ${name} Metadata id: {}", field.getTag());
                        break;
                    }
            } catch (KlvParseException | IllegalArgumentException ex) {
                        InvalidDataHandler idh = InvalidDataHandler.getInstance();
                        String msg = ex.getMessage();
                        idh.handleInvalidFieldEncoding(LOGGER, msg);
            }
        }
    }

    @Override
    public byte[] getBytes(){
        return frameMessage(false);
    }
<#else>
    /**
     * Build a ${name} Local Set from encoded bytes.
     *
     * @param data the bytes to build from.
     * @param offset the offset into {@code bytes} to start parsing from.
     * @param numBytes the number of bytes to parse.
     * @throws KlvParseException if parsing fails (depending on InvalidDataHandler strategy)
     */
    public ${name}(byte[] data, int offset, int numBytes) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(data, offset, numBytes);
        for (LdsField field : fields) {
            ${name}MetadataKey key = ${name}MetadataKey.getKey(field.getTag());
            try {
                switch (key) {
    <#list entries as entry>
                    case ${entry.name}:
        <#if entry.ref && entry.array>
                        this.${entry.name} = ${entry.namespacedQualifiedName}.fromBytes(field.getData());
                        break;
        <#elseif entry.ref>
                        this.${entry.name} = MimdIdReference.fromBytes(field.getData(), "${entry.nameSentenceCase}", "${entry.typeName}");
                        break;
        <#elseif entry.list>
                        this.${entry.name} = ${entry.namespacedName}.fromBytes(field.getData());
                        break;
        <#elseif entry.name == "mimdId">
                        this.${entry.name} = MimdId.fromBytes(field.getData());
                        break;
        <#elseif entry.primitive>
                        this.${entry.name} = ${entry.namespacedQualifiedName}.fromBytes(field.getData());
                        break;
        <#else>
                        this.${entry.name} = ${entry.qualifiedTypeName}.fromBytes(field.getData());
                        break;
        </#if>
    </#list>
                    default:
                        LOGGER.info("Unknown MIMD ${name} Metadata tag: {}", field.getTag());
                        break;
                    }
            } catch (KlvParseException | IllegalArgumentException ex) {
                InvalidDataHandler idh = InvalidDataHandler.getInstance();
                String msg = ex.getMessage();
                idh.handleInvalidFieldEncoding(LOGGER, msg);
            }
        }
    }

    /**
     * Construct ${name} Local Set from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the array could not be parsed
     */
    public ${name}(byte[] bytes) throws KlvParseException {
        this(bytes, 0, bytes.length);
    }

    /**
     * Create ${name} Local Set from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${name} corresponding to the encoded byte array.
     * @throws KlvParseException if the array could not be parsed
     */
    public static ${name} fromBytes(byte[] bytes) throws KlvParseException {
        return new ${name}(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes(){
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (IKlvKey key: getIdentifiers()) {
            <#if hasDeprecatedAttribute>
            if (key.isDeprecated()) {
                // ST1902-01
                LOGGER.info("Omitting deprecated ${name} Metadata: {}", key.toString());
                continue;
            }
            </#if>
            arrayBuilder.appendAsOID(key.getIdentifier());
            byte[] valueBytes = ((IMimdMetadataValue)getField(key)).getBytes();
            arrayBuilder.appendAsBerLength(valueBytes.length);
            arrayBuilder.append(valueBytes);
        }
        return arrayBuilder.toBytes();
    }
</#if>

    @Override
    public String getDisplayName() {
        return "${name}";
    }

    @Override
    public String getDisplayableValue() {
        return "[${name}]";
    }

<#if topLevel>
    @Override
    public UniversalLabel getUniversalLabel() {
        return KlvConstants.${name}LocalSetUl;
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (IKlvKey key: getIdentifiers()) {
            <#if hasDeprecatedAttribute>
            if (key.isDeprecated()) {
                // ST1902-01
                LOGGER.info("Omitting deprecated ${name} Metadata: {}", key.toString());
                continue;
            }
            </#if>
            arrayBuilder.appendAsOID(key.getIdentifier());
            byte[] valueBytes = ((IMimdMetadataValue)getField(key)).getBytes();
            arrayBuilder.appendAsBerLength(valueBytes.length);
            arrayBuilder.append(valueBytes);
        }
        // Nesting is highly unlikely, but is supported.
        if (!isNested) {
            CrcCcitt crc = new CrcCcitt();
            crc.addData(getUniversalLabel().getBytes());
            crc.addData(arrayBuilder.toBytes());
            arrayBuilder.append(crc.getCrc());
            arrayBuilder.prependLength();
            arrayBuilder.prepend(getUniversalLabel());
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String displayHeader() {
        return "${name}";
    }

</#if>
    static IMimdMetadataValue createValue(${name}MetadataKey key, byte[] data)
            throws KlvParseException {
        switch (key) {
<#list entries as entry>
            case ${entry.name}:
<#if entry.ref && entry.array>
                return ${entry.namespacedQualifiedName}.fromBytes(data);
<#elseif entry.ref>
                return MimdIdReference.fromBytes(data, "${entry.nameSentenceCase}", "${entry.typeName}");
<#elseif entry.list>
                return ${entry.namespacedName}.fromBytes(data);
<#elseif entry.name == "mimdId">
                return MimdId.fromBytes(data);
<#elseif entry.primitive>
                return ${entry.namespacedQualifiedName}.fromBytes(data);
<#else>
                return ${entry.qualifiedTypeName}.fromBytes(data);
</#if>
</#list>
            default:
                LOGGER.info("Unknown ${name} Metadata tag: {}", key.name());
                return null;
        }
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        switch ((${name}MetadataKey)tag) {
<#list entries as entry>
        case ${entry.name}:
            return ${entry.name};
</#list>
        default:
            LOGGER.info("Unknown ${name} Metadata tag: {}", tag.getIdentifier());
            return null;
        }
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<${name}MetadataKey> identifiers = EnumSet.noneOf(${name}MetadataKey.class);
<#list entries as entry>
        if (${entry.name} != null) {
            identifiers.add(${name}MetadataKey.${entry.name});
        }
</#list>
        return identifiers;
    }
}
