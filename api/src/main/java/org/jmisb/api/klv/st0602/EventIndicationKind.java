package org.jmisb.api.klv.st0602;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvValue;

/**
 * Kind of annotation event.
 *
 * <p>ST 0602 allows five kinds of events on an annotation.
 */
public enum EventIndicationKind implements IKlvValue {
    /**
     * Unknown Indication.
     *
     * <p>This is not a valid item, and should not be created. It exists only to support parsing of
     * future entities.
     */
    UNKNOWN(0x00),
    /** New annotation. */
    NEW(0x31),
    /** Annotation movement. */
    MOVE(0x32),
    /** Annotation modification. */
    MODIFY(0x33),
    /** Annotation deletion. */
    DELETE(0x34),
    /** Annotation status. */
    STATUS(0x35);

    private static final Map<Byte, EventIndicationKind> lookupTable = new HashMap<>();

    static {
        for (EventIndicationKind c : values()) {
            lookupTable.put(c.getEncodedValue(), c);
        }
    }

    private EventIndicationKind(int v) {
        this.encodedValue = (byte) v;
    }

    private byte encodedValue;

    @Override
    public String getDisplayName() {
        return "Event Indication";
    }

    @Override
    public String getDisplayableValue() {
        return this.toString();
    }

    /**
     * The encoded representation of this event indication kind.
     *
     * @return integer equivalent to this kind.
     */
    public byte getEncodedValue() {
        return encodedValue;
    }

    /**
     * Look up the event indication kind for a given code.
     *
     * @param code the byte value for the EventIndicationKind value.
     * @return the corresponding EventIndicationKind ({@code UNKNOWN} if no match).
     */
    public static EventIndicationKind getEventIndicationKind(byte code) {
        return lookupTable.getOrDefault(code, UNKNOWN);
    }
}
