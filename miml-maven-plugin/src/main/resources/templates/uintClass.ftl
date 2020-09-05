// Generated file - changes will be lost on rebuild
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
     * @param value the unsigned integer value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(long value) {
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
}
