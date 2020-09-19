<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
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
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.api.klv.st190x.MimdId;
import org.jmisb.api.klv.st190x.MimdIdReference;
<#if topLevel>
import org.jmisb.api.klv.st190x.MimdParser;
</#if>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${name} Local Set.
 *
 * See ${document} for more information on this data type.
 */
public class ${name} implements <#if topLevel>IMisbMessage, </#if>IMimdMetadataValue, INestedKlvValue {
    private static final Logger LOGGER = LoggerFactory.getLogger(${name}.class);

    /** Map containing all data elements in the message. */
    private final SortedMap<${name}MetadataKey, IMimdMetadataValue> map = new TreeMap<>();

    /**
     * Create the ${name} local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public ${name}(Map<${name}MetadataKey, IMimdMetadataValue> values) {
        map.putAll(values);
    }

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
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown ${name} Metadata id: {}", field.getTag());
                    break;
                default:
                    try {
                        IMimdMetadataValue value = createValue(key, field.getData());
                        map.put(key, value);
                    } catch (KlvParseException | IllegalArgumentException ex) {
                        InvalidDataHandler idh = InvalidDataHandler.getInstance();
                        String msg = ex.getMessage();
                        idh.handleInvalidFieldEncoding(LOGGER, msg);
                    }
                    break;
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
     * @param data the bytes to build from
     * @param offset the offset into {@code bytes} to start parsing from
     * @param numBytes the number of bytes to parse
     * @throws KlvParseException if parsing fails (depending on InvalidDataHandler strategy)
     */
    public ${name}(byte[] data, int offset, int numBytes) throws KlvParseException {
        List<LdsField> fields = LdsParser.parseFields(data, offset, numBytes);
        for (LdsField field : fields) {
            ${name}MetadataKey key = ${name}MetadataKey.getKey(field.getTag());
            if (key == ${name}MetadataKey.Undefined) {
                LOGGER.info("Unknown MIMD ${name} Metadata tag: {}", field.getTag());
            } else {
                try {
                    IMimdMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
                } catch (KlvParseException | IllegalArgumentException ex) {
                    InvalidDataHandler idh = InvalidDataHandler.getInstance();
                    String msg = ex.getMessage();
                    idh.handleInvalidFieldEncoding(LOGGER, msg);
                }
            }
        }
    }

    /**
     * Create ${name} Local Set from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new  ${name} corresponding to the encoded byte array.
     * @throws KlvParseException if the array could not be parsed
     */
    public static  ${name} fromBytes(byte[] bytes) throws KlvParseException {
        return new  ${name}(bytes, 0, bytes.length);
    }

    @Override
    public byte[] getBytes(){
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (Map.Entry<${name}MetadataKey, IMimdMetadataValue> entry: map.entrySet()) {
            <#if hasDeprecatedAttribute>
            if (entry.getKey().isDeprecated()) {
                // ST1902-01
                LOGGER.info("Omitting deprecated ${name} Metadata: {}", entry.getKey().toString());
                continue;
            }
            </#if>
            arrayBuilder.appendAsOID(entry.getKey().getIdentifier());
            byte[] valueBytes = entry.getValue().getBytes();
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
        for (Map.Entry<${name}MetadataKey, IMimdMetadataValue> entry: map.entrySet()) {
            <#if hasDeprecatedAttribute>
            if (entry.getKey().isDeprecated()) {
                // ST1902-01
                LOGGER.info("Omitting deprecated ${name} Metadata: {}", entry.getKey().toString());
                continue;
            }
            </#if>
            arrayBuilder.appendAsOID(entry.getKey().getIdentifier());
            byte[] valueBytes = entry.getValue().getBytes();
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
<#if entry.typeName?starts_with("REF\l")>
                return MimdIdReference.fromBytes(data, "${entry.nameSentenceCase}", "${entry.refItemType}");
<#elseif entry.typeName?starts_with("LIST\l")>
                return ${entry.nameSentenceCase}.fromBytes(data);
<#elseif entry.primitiveType>
                return ${entry.nameSentenceCase}.fromBytes(data);
<#elseif entry.name == "mimdId">
                return MimdId.fromBytes(data);
<#else>
                return ${entry.typeName}.fromBytes(data);
</#if>
</#list>
            default:
                LOGGER.info("Unknown ${name} Metadata tag: {}", key.name());
                return null;
        }
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return map.get((${name}MetadataKey)tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }
}
