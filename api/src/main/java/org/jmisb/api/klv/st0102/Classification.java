package org.jmisb.api.klv.st0102;

import java.util.HashMap;
import java.util.Map;

/**
 * Allowed classification levels in ST 0102
 */
public enum Classification
{
    UNKNOWN((byte)0x00),
    UNCLASSIFIED((byte)0x01),
    RESTRICTED((byte)0x02),
    CONFIDENTIAL((byte)0x03),
    SECRET((byte)0x04),
    TOP_SECRET((byte)0x05);

    private byte code;

    private static final Map<Byte, Classification> lookupTable = new HashMap<>();

    static
    {
        for (Classification c : values())
        {
            lookupTable.put(c.code, c);
        }
    }

    Classification(byte c)
    {
        code = c;
    }

    public byte getCode()
    {
        return code;
    }

    public static Classification getClassification(byte code)
    {
        if (lookupTable.containsKey(code))
        {
            return lookupTable.get(code);
        }
        throw new IllegalArgumentException("Invalid classification code: " + code);
    }
}
