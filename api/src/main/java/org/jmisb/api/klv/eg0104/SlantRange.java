package org.jmisb.api.klv.eg0104;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Slant Range value for EG 0104.
 */
public class SlantRange implements IPredatorMetadataValue
{
    private final float value;

    public SlantRange(byte[] bytes)
    {
        value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.1f m", value);
    }

    @Override
    public String getDisplayName()
    {
        return "Slant Range";
    }

    /**
     * Get the slant range value.
     * @return slant range in meters.
     */
    public float getRange()
    {
        return value;
    }

    
}
