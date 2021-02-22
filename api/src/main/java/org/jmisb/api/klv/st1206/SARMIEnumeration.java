package org.jmisb.api.klv.st1206;

import java.util.Collections;
import java.util.Map;

/** Abstract base class for single-byte enumeration values in ST 1206. */
public abstract class SARMIEnumeration implements ISARMIMetadataValue {

    protected byte value;

    /**
     * Get a map of all possible values.
     *
     * @return Map of all enumeration values
     */
    abstract Map<Integer, String> getDisplayValues();

    /**
     * Create from value.
     *
     * @param enumeratedValue The value of the enumeration
     */
    public SARMIEnumeration(byte enumeratedValue) {
        if (enumeratedValue < Collections.min(getDisplayValues().keySet())
                || enumeratedValue > Collections.max(getDisplayValues().keySet())) {
            throw new IllegalArgumentException(
                    getDisplayName()
                            + " must be in range ["
                            + Collections.min(getDisplayValues().keySet())
                            + ", "
                            + Collections.max(getDisplayValues().keySet())
                            + "]");
        }
        value = enumeratedValue;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public SARMIEnumeration(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 1-byte enumeration");
        }

        value = bytes[0];
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[Byte.BYTES];
        bytes[0] = value;
        return bytes;
    }

    @Override
    public String getDisplayableValue() {
        return getDisplayValues().getOrDefault((int) value, "Unknown");
    }
}
