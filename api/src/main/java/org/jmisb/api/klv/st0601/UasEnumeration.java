package org.jmisb.api.klv.st0601;

import java.util.Collections;
import java.util.Map;
import static org.jmisb.api.klv.st0601.IcingDetected.DISPLAY_VALUES;

public abstract class UasEnumeration implements IUasDatalinkValue {

    protected byte value;

    abstract Map<Integer, String> getDisplayValues();

    abstract String getDisplayName();

    /**
     * Create from value
     *
     * @param enumeratedValue The value of the enumeration
     */
    public UasEnumeration(byte enumeratedValue) {
        if (enumeratedValue < Collections.min(getDisplayValues().keySet()) || enumeratedValue > Collections.max(getDisplayValues().keySet())) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [" + Collections.min(getDisplayValues().keySet()) + ", " + Collections.max(getDisplayValues().keySet()) + "]");
        }
        value = enumeratedValue;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public UasEnumeration(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 1-byte enumeration");
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
