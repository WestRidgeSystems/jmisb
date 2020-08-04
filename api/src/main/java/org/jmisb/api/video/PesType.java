package org.jmisb.api.video;

import java.util.HashMap;
import java.util.Map;

/** Packetized Elementary Stream (PES) type. */
public enum PesType {
    UNKNOWN(-1),
    VIDEO(0),
    AUDIO(1),
    DATA(2),
    SUBTITLE(3),
    ATTACHMENT(4);

    private int code;

    private static final Map<Integer, PesType> lookupTable = new HashMap<>();

    static {
        for (PesType type : values()) {
            lookupTable.put(type.code, type);
        }
    }

    private PesType(int c) {
        code = c;
    }

    public int getCode() {
        return code;
    }

    public static PesType getType(int typeCode) {
        return lookupTable.containsKey(typeCode) ? lookupTable.get(typeCode) : UNKNOWN;
    }
}
