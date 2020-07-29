package org.jmisb.api.klv.st0102.localset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.CountryCodingMethodUtilities;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;

/** Object Country Coding Method (ST 0102 tag 12). */
public class OcMethod implements ISecurityMetadataValue {
    private byte method;
    private static Set<Byte> legal =
            new HashSet<>(
                    Arrays.asList(
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x05,
                            (byte) 0x06,
                            (byte) 0x07,
                            (byte) 0x08,
                            (byte) 0x09,
                            (byte) 0x0a,
                            (byte) 0x0b,
                            (byte) 0x0c,
                            (byte) 0x0d,
                            (byte) 0x0e,
                            (byte) 0x0f,
                            (byte) 0x40));

    /**
     * Create from value.
     *
     * @param method Country coding method
     */
    public OcMethod(CountryCodingMethod method) {
        this.method = CountryCodingMethodUtilities.getValueForCodingMethod(method);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public OcMethod(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Country coding method must be one byte");
        }

        if (legal.contains(bytes[0])) {
            this.method = bytes[0];
        } else {
            throw new IllegalArgumentException("Invalid object country coding method: " + bytes[0]);
        }
    }

    /**
     * Get the country coding method.
     *
     * @return The country coding method
     */
    public CountryCodingMethod getMethod() {
        return CountryCodingMethodUtilities.getMethodForValue(this.method);
    }

    @Override
    public byte[] getBytes() {
        return new byte[] {method};
    }

    @Override
    public String getDisplayableValue() {
        return getMethod().toString();
    }

    @Override
    public String getDisplayName() {
        return "Object Country Coding Method";
    }
}
