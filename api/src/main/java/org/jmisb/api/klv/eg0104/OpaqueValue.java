package org.jmisb.api.klv.eg0104;

import org.jmisb.api.klv.st0601.*;

/**
 * Represents a value that is not interpreted by the library yet
 */
public class OpaqueValue implements IPredatorMetadataValue
{
    private String displayName;

    /**
     * Create from encoded bytes
     * @param bytes The byte array
     * @param label display label
     */
    public OpaqueValue(byte[] bytes, String label)
    {
        this.displayName = label;
    }

    @Override
    public String getDisplayableValue()
    {
        return "N/A";
    }

    @Override
    public final String getDisplayName()
    {
        return displayName;
    }

}
