package org.jmisb.api.klv.st0602;

/** ST 0602 Event Indication. */
public class EventIndication implements IAnnotationMetadataValue {
    private EventIndicationKind id;

    /**
     * Create from value.
     *
     * @param id The identifier
     */
    public EventIndication(EventIndicationKind id) {
        // TODO this might be better as a smart enumeration
        this.id = id;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1
     */
    public EventIndication(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Event indication encoding is a one-byte character");
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
