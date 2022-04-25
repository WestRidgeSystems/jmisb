package org.jmisb.st0602;

import java.nio.charset.StandardCharsets;
import org.jmisb.api.common.KlvParseException;

/**
 * Identification of most recent significant event’s author.
 *
 * <p>The Modification History contains information identifying the author of the most recent
 * significant event such as {@code NEW}, {@code MODIFY} and {@code DELETE}. The specific contents
 * are user definable.
 */
public class ModificationHistory implements IAnnotationMetadataValue {

    private final String history;

    /**
     * Create from value.
     *
     * @param author the most recent significant event's author.
     * @throws KlvParseException if the string is greater than 127 bytes in ASCII encoding.
     */
    public ModificationHistory(String author) throws KlvParseException {
        if (author.getBytes(StandardCharsets.US_ASCII).length > 127) {
            throw new KlvParseException("Modification history maximum length is 127 bytes");
        }
        history = author;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of ASCII encoded text, maximum length 127 bytes.
     * @throws KlvParseException if the string is greater than 127 bytes in ASCII encoding.
     */
    public ModificationHistory(byte[] bytes) throws KlvParseException {
        if (bytes.length > 127) {
            throw new KlvParseException(
                    "Modification history encoding is maximum length 127 bytes");
        }
        history = new String(bytes, StandardCharsets.US_ASCII);
    }

    @Override
    public byte[] getBytes() {
        return history.getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayName() {
        return "Modification History";
    }

    @Override
    public String getDisplayableValue() {
        return history;
    }
}
