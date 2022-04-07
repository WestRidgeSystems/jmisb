package org.jmisb.st1301;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;

/**
 * ST 1204 MIIS Core Identifier (ST 1301 Local Set Item 4).
 *
 * <p>ST 1204 Core Identifier value. This class wraps the ST 1204 MIIS implementation to adapt it to
 * the MIIS Local Set.
 */
public class ST1301CoreIdentifier implements IMiisMetadataValue {
    private final CoreIdentifier value;

    /**
     * Create from value.
     *
     * <p>The current version is available as {@link MiisMetadataConstants#ST_VERSION_NUMBER}.
     *
     * @param identifier the core identifier
     */
    public ST1301CoreIdentifier(CoreIdentifier identifier) {
        this.value = identifier;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array in the ST 1204 Core Identifier encoding
     * @throws KlvParseException if {@code bytes} does not correspond with a valid Core Identifier
     */
    public ST1301CoreIdentifier(byte[] bytes) throws KlvParseException {
        this.value = CoreIdentifier.fromBytes(bytes);
    }

    /**
     * Get the core identifier.
     *
     * @return The ST 1204 Core Identifier
     */
    public CoreIdentifier getCoreIdentifier() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return value.getRawBytesRepresentation();
    }

    @Override
    public String getDisplayableValue() {
        return value.getTextRepresentation();
    }

    @Override
    public final String getDisplayName() {
        return "Core Identifier";
    }
}
