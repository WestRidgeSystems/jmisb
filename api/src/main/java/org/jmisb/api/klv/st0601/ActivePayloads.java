package org.jmisb.api.klv.st0601;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Active Payloads (Item 139).
 *
 * <p>From ST0601:
 *
 * <blockquote>
 *
 * List of currently active payloads from the payload list (Item 138).
 *
 * <p>The Active Payloads item is a list of the subset of payloads from the Payload List which are
 * currently in use. The list is a series of Payload Identifiers which map into the Payload List
 * allowing receivers to determine the Active Payload Names.
 *
 * <p>The list is a series of bits which represent which payloads are active. A bit value of one (1)
 * means the payload is active, a bit value of zero (0) means the payload is not active. Using the
 * example from the Payload List (Item 138), if payloads 0, 1, and 3 are active, bits 0, 1, and 3
 * will be set in the Active Payloads Value. The result for this example is a single byte with the
 * value of 0x0B.
 *
 * </blockquote>
 */
public class ActivePayloads implements IUasDatalinkValue {
    private BigInteger payloads;

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array containing the bit list corresponding to active payloads.
     */
    public ActivePayloads(byte[] bytes) {
        byte[] extendedBytes = new byte[bytes.length + 1];
        extendedBytes[0] = 0x00;
        System.arraycopy(bytes, 0, extendedBytes, 1, bytes.length);
        payloads = new BigInteger(extendedBytes);
    }

    /**
     * Create from values.
     *
     * @param values List of payload identifiers (per corresponding Item 138).
     */
    public ActivePayloads(List<Integer> values) {
        payloads = BigInteger.valueOf(0);
        values.forEach((i) -> payloads = payloads.setBit(i));
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = payloads.toByteArray();
        if ((bytes[0] == 0x00) && (bytes.length > 1)) {
            return Arrays.copyOfRange(bytes, 1, bytes.length);
        } else {
            return bytes;
        }
    }

    @Override
    public String getDisplayableValue() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < payloads.bitLength(); ++i) {
            if (payloads.testBit(i)) {
                strings.add("" + i);
            }
        }
        return String.join(",", strings);
    }

    /**
     * Get the list of identifiers for active payloads.
     *
     * <p>The identifiers match the corresponding Item 138 (PayloadList) identifiers.
     *
     * @return list of integer identifiers.
     */
    public List<Integer> getIdentifiers() {
        List<Integer> identifiers = new ArrayList<>();
        for (int i = 0; i < payloads.bitLength(); ++i) {
            if (payloads.testBit(i)) {
                identifiers.add(i);
            }
        }
        return identifiers;
    }

    /**
     * Mark a payload as active.
     *
     * @param identifier the payload identifier (per Item 138 - PayloadList)
     */
    public void setPayloadActive(final int identifier) {
        payloads = payloads.setBit(identifier);
    }

    /**
     * Mark a payload as inactive.
     *
     * @param identifier the payload identifier (per Item 138 - PayloadList)
     */
    public void setPayloadInactive(final int identifier) {
        payloads = payloads.clearBit(identifier);
    }

    /**
     * Check if a payload is currently marked active.
     *
     * @param identifier the payload identifier (per Item 138 - PayloadList)
     * @return true if the payload is active, otherwise false.
     */
    public boolean payloadIsActive(final int identifier) {
        return payloads.testBit(identifier);
    }

    @Override
    public String getDisplayName() {
        return "Active Payloads";
    }
}
