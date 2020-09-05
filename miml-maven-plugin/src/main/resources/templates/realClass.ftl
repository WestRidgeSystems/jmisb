// Generated file - changes will be lost on rebuild
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ${name} MIMD Floating Point.
 *
 * See ${document} for more information on this data type.
 */
public class ${nameSentenceCase} implements IMimdMetadataValue {
    private final double doubleValue;

    /**
     * Construct from value.
     *
     * @param value the floating point value to initialise this ${nameSentenceCase} with.
     */
    public ${nameSentenceCase}(double value) {
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
        return String.format("%.3f ${units}", this.doubleValue);
        <#else>
        return String.format("%.3f", this.doubleValue);
        </#if>
    }
}
