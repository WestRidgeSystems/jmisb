package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.st1204.CoreIdentifier;

/**
 * MIIS Core Identifier (Tag 94)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Use according to the rules and requirements defined in ST 1204.
 *
 * <p>The MIIS Core Identifier allows users to include the MIIS Core Identifier (MISB ST 1204)
 * Binary Value (opposed to the text-based representation) within MISB ST 0601. Tag 94's value does
 * not include MISB ST 1204's 16-byte Key or length, only the value portion. See MISB ST 1204 for
 * generation and usage requirements.
 *
 * </blockquote>
 */
public class MiisCoreIdentifier implements IUasDatalinkValue {

    private CoreIdentifier coreIdentifier;

    /**
     * Create from value
     *
     * @param identifier a valid ST1204 Core Identifier
     */
    public MiisCoreIdentifier(CoreIdentifier identifier) {
        coreIdentifier = identifier;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array containing the raw values.
     */
    public MiisCoreIdentifier(byte[] bytes) {
        coreIdentifier = CoreIdentifier.fromBytes(bytes);
    }

    /**
     * Get the identifier.
     *
     * @return The identifier (which can be null if the parsing failed).
     */
    public CoreIdentifier getCoreIdentifier() {
        return coreIdentifier;
    }

    @Override
    public byte[] getBytes() {
        if (coreIdentifier != null) {
            return coreIdentifier.getRawBytesRepresentation();
        } else {
            return null;
        }
    }

    @Override
    public String getDisplayableValue() {
        if (coreIdentifier != null) {
            return coreIdentifier.getTextRepresentation();
        } else {
            return "[NULL]";
        }
    }

    @Override
    public String getDisplayName() {
        return "MIIS Core Identifier";
    }
}
