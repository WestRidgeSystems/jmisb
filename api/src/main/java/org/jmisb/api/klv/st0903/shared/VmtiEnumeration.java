package org.jmisb.api.klv.st0903.shared;

import java.util.Collections;
import java.util.Map;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/** Abstract base class for single-byte enumeration values in ST 0903. */
public abstract class VmtiEnumeration implements IVmtiMetadataValue {
    /** Implementing value. */
    protected byte value;

    /**
     * Get a map of all possible values.
     *
     * @return Map of all enumeration values
     */
    public abstract Map<Integer, String> getDisplayValues();

    /**
     * Create from value.
     *
     * @param enumeratedValue The value of the enumeration
     */
    public VmtiEnumeration(byte enumeratedValue) {
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
    public VmtiEnumeration(byte[] bytes) {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.value;
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
        final VmtiEnumeration other = (VmtiEnumeration) obj;
        return this.value == other.value;
    }
}
