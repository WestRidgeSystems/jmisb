package org.jmisb.api.video;

import java.util.HashMap;
import java.util.Map;

/**
 * Packetized Elementary Stream (PES) type.
 *
 * <p>A transport stream can contain different types (kinds) of elementary streams. This enumeration
 * provides the known PES types.
 */
public enum PesType {
    UNKNOWN(-1),
    VIDEO(0),
    AUDIO(1),
    DATA(2),
    SUBTITLE(3),
    ATTACHMENT(4);

    private final int code;

    private static final Map<Integer, PesType> lookupTable = new HashMap<>();

    static {
        for (PesType type : values()) {
            lookupTable.put(type.code, type);
        }
    }

    PesType(int c) {
        code = c;
    }

    /**
     * Get the identifier code for the given PES type.
     *
     * @return PES identifier as an integer value.
     */
    public int getCode() {
        return code;
    }

    /**
     * Look up an PES type for a given identifier code.
     *
     * @param typeCode the integer PES identifier
     * @return the corresponding PES type.
     */
    public static PesType getType(int typeCode) {
        return lookupTable.getOrDefault(typeCode, UNKNOWN);
    }
}
