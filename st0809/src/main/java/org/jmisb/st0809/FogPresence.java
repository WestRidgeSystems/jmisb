package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Fog Presence (ST 0809 Local Set Item 29).
 *
 * <p>Boolean flag for presence of fog.
 */
public class FogPresence implements IMeteorologicalMetadataValue {

    private boolean value;

    /**
     * Create from value.
     *
     * @param fog presence of fog (true) or absence of fog (false)
     */
    public FogPresence(boolean fog) {
        this.value = fog;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 1
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public FogPresence(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 1-byte boolean");
        }
        this.value = (bytes[0] != 0x00);
    }

    @Override
    public final String getDisplayName() {
        return "Fog Presence";
    }

    /**
     * Get the value.
     *
     * @return true for fog presence, false for fog not present.
     */
    public boolean getValue() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        // TODO: there is no definition for what the encoding should be.
        return (value ? new byte[] {0x01} : new byte[] {0x00});
    }

    @Override
    public String getDisplayableValue() {
        return (value ? "Present" : "Not Present");
    }
}
