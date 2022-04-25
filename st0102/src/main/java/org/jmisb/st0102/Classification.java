package org.jmisb.st0102;

import java.util.HashMap;
import java.util.Map;

/** Allowed classification levels in ST 0102. */
public enum Classification {
    /**
     * The Classification is unknown.
     *
     * <p>This value is not valid, and should not be used. It is an implementation detail.
     */
    UNKNOWN((byte) 0x00),
    /**
     * Unclassified.
     *
     * <p>Motion Imagery data that is unclassified needs to be explicitly marked (see ST 0102.10-54
     * and ST 0102.10-51).
     */
    UNCLASSIFIED((byte) 0x01),
    /**
     * Restricted.
     *
     * <p>This is a NATO marking, and is not a valid US marking.
     */
    RESTRICTED((byte) 0x02),
    /** Confidential. */
    CONFIDENTIAL((byte) 0x03),
    /** Secret. */
    SECRET((byte) 0x04),
    /** Top Secret. */
    TOP_SECRET((byte) 0x05);

    private final byte code;

    private static final Map<Byte, Classification> lookupTable = new HashMap<>();

    static {
        for (Classification c : values()) {
            lookupTable.put(c.code, c);
        }
    }

    Classification(byte c) {
        code = c;
    }

    /**
     * Get the code for this classification.
     *
     * @return the associated byte value (e.g. {@code 0x03 for Confidential}.
     */
    public byte getCode() {
        return code;
    }

    /**
     * Look up the classification for a given code.
     *
     * @param code the byte value for the Classification value.
     * @return the corresponding Classification.
     */
    public static Classification getClassification(byte code) {
        if (lookupTable.containsKey(code)) {
            return lookupTable.get(code);
        }
        throw new IllegalArgumentException("Invalid classification code: " + code);
    }
}
