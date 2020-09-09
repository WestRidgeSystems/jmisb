// Generated file - changes will be lost on rebuild
package ${packageName};

import java.util.List;
import java.util.Map;
<#if topLevel>
import java.util.Set;
</#if>
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
<#if topLevel>
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.UniversalLabel;
<#else>
import org.jmisb.api.klv.LdsField;
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
public class ${name} implements <#if topLevel>IMisbMessage, </#if>IMimdMetadataValue {
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
                        InvalidDataHandler.getInstance()
                                .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
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
                    InvalidDataHandler.getInstance()
                            .handleInvalidFieldEncoding(LOGGER, ex.getMessage());
                }
            }
        }
    }

    @Override
    public byte[] getBytes(){
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (Map.Entry<${name}MetadataKey, IMimdMetadataValue> entry: map.entrySet()) {
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
        // TODO: ArrayBuilder when its available.
        return new byte[] {};
    }

    @Override
    public String displayHeader() {
        return "${name}";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return map.get(tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

</#if>
    static IMimdMetadataValue createValue(${name}MetadataKey key, byte[] data)
            throws KlvParseException {
        switch (key) {
<#list entries as entry>
            case ${entry.name}:
<#if entry.typeName?starts_with("REF\l")>
                return MimdIdReference.fromBytes(data);
<#else>
                return ${entry.nameSentenceCase}.fromBytes(data);
</#if>
</#list>
            default:
                LOGGER.info("Unknown ${name} Metadata tag: {}", key.name());
                return null;
        }
    }
}

