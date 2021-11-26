package org.jmisb.api.klv.st0602;

import org.jmisb.api.common.KlvParseException;

/** ST 0602 Event Indication. */
public class EventIndication implements IAnnotationMetadataValue {
    private EventIndicationKind id;

    /**
     * Create from value.
     *
     * @param id The identifier
     */
    public EventIndication(EventIndicationKind id) {
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1
     * @throws KlvParseException if {@code bytes} is not the correct length
     */
    public EventIndication(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException("Event indication encoding is a one-byte character");
        }
        id = EventIndicationKind.getEventIndicationKind(bytes[0]);
    }

    /**
     * Get the event indication.
     *
     * @return The event indication value
     */
    public EventIndicationKind getEventIndicationKind() {
        return id;
    }

    @Override
    public byte[] getBytes() {
        return new byte[] {id.getEncodedValue()};
    }

    @Override
    public String getDisplayableValue() {
        return id.getDisplayableValue();
    }

    @Override
    public String getDisplayName() {
        return id.getDisplayName();
    }
}
