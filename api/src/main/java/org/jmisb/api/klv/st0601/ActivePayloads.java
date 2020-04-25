package org.jmisb.api.klv.st0601;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Active Payloads (Tag 138).
 * <p>
 * From ST0601:
 * <blockquote>
 * List of currently active payloads from the payload list (Tag 138).
 * <p>
 * The Active Payloads item is a list of the subset of payloads from the Payload
 * List which are currently in use. The list is a series of Payload Identifiers
 * which map into the Payload List allowing receivers to determine the Active
 * Payload Names.
 * <p>
 * The list is a series of bits which represent which payloads are active. A bit
 * value of one (1) means the payload is active, a bit value of zero (0) means
 * the payload is not active. Using the example from the Payload List (Tag 138),
 * if payloads 0, 1, and 3 are active, bits 0, 1, and 3 will be set in the
 * Active Payloads Value. The result for this example is a single byte with the
 * value of 0x0B.
 * </blockquote>
 */
public class ActivePayloads implements IUasDatalinkValue
{
    private BigInteger payloads;

    /**
     * Create from encoded bytes.
     * @param bytes The byte array containing the bit list corresponding to active payloads.
     */
    public ActivePayloads(byte[] bytes)
    {
        byte[] extendedBytes = new byte[bytes.length + 1];
        extendedBytes[0] = 0x00;
        System.arraycopy(bytes, 0, extendedBytes, 1, bytes.length);
        payloads = new BigInteger(extendedBytes);
    }

    /**
     * Create from values.
     *
     * @param values List of payload identifiers (per corresponding Tag 138).
     */
    public ActivePayloads(List<Integer> values)
    {
        payloads = BigInteger.valueOf(0);
        values.forEach((i) -> {
            payloads = payloads.setBit(i);
        });
    }

    @Override
    public byte[] getBytes()
    {
        byte[] bytes = payloads.toByteArray();
        if ((bytes[0] == 0x00) && (bytes.length > 1))
        {
            return Arrays.copyOfRange(bytes, 1, bytes.length);
        }
        else
        {
            return bytes;
        }
    }

    @Override
    public String getDisplayableValue()
    {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < payloads.bitLength(); ++i)
        {
            if (payloads.testBit(i)) {
                strings.add("" + i);
            }
        }
        return String.join(",", strings);
    }

    @Override
    public String getDisplayName()
    {
        return "Active Payloads";
    }
}
