package org.jmisb.api.klv.eg0104;

import java.nio.charset.StandardCharsets;

/**
 * Image Source Device value for EG 0104.
 * 
 * This was Sensor Name in the NIMA-MIPO memo.
 */
public class ImageSourceDevice implements IPredatorMetadataValue
{
    private final String value;

    public ImageSourceDevice(byte[] bytes)
    {
        value = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue()
    {
        return value;
    }

    @Override
    public String getDisplayName()
    {
        return "Image Source Device";
    }
}
