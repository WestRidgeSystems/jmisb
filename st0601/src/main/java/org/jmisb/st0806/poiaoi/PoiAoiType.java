package org.jmisb.st0806.poiaoi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * POI/AOI Type (ST 0806 POI Local Set Tag 5, AOI Local Set Tag 6)
 *
 * <p>Target Identifier.
 */
public class PoiAoiType implements IRvtPoiAoiMetadataValue {
    /** Friendly. */
    static final PoiAoiType FRIENDLY;
    /** Hostile. */
    static final PoiAoiType HOSTILE;
    /** Target. */
    static final PoiAoiType TARGET;
    /** Unknown. */
    static final PoiAoiType UNKNOWN;

    static final Map<Integer, String> DISPLAY_VALUES =
            Arrays.stream(
                            new Object[][] {
                                {1, "Friendly"},
                                {2, "Hostile"},
                                {3, "Target"},
                                {4, "Unknown"}
                            })
                    .collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    static {
        FRIENDLY = new PoiAoiType((byte) 0x01);
        HOSTILE = new PoiAoiType((byte) 0x02);
        TARGET = new PoiAoiType((byte) 0x03);
        UNKNOWN = new PoiAoiType((byte) 0x04);
    }

    private byte value;

    /**
     * Create from value.
     *
     * @param type The value of the POI / AOI type enumeration.
     */
    public PoiAoiType(byte type) {
        if (type < Collections.min(DISPLAY_VALUES.keySet())
                || type > Collections.max(DISPLAY_VALUES.keySet())) {
            throw new IllegalArgumentException(
                    getDisplayName()
                            + " must be in range ["
                            + Collections.min(DISPLAY_VALUES.keySet())
                            + ", "
                            + Collections.max(DISPLAY_VALUES.keySet())
                            + "]");
        }
        value = type;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public PoiAoiType(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 1-byte enumeration");
        }
        value = bytes[0];
    }

    @Override
    public final String getDisplayName() {
        return "POI/AOI Type";
    }

    @Override
    public byte[] getBytes() {
        return new byte[] {value};
    }

    @Override
    public String getDisplayableValue() {
        return DISPLAY_VALUES.getOrDefault((int) value, "Unknown");
    }

    /**
     * Get the target identifier value.
     *
     * @return target identifier as an integer value.
     */
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.value;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PoiAoiType other = (PoiAoiType) obj;
        return this.value == other.value;
    }
}
