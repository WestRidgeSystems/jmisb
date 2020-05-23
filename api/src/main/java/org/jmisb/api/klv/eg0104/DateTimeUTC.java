package org.jmisb.api.klv.eg0104;

import java.nio.charset.StandardCharsets;

/**
 * Date Time UTC, generic value for EG 0104.
 */
public class DateTimeUTC implements IPredatorMetadataValue
{
    private final String label;
    private final String value;

    public DateTimeUTC(byte[] bytes, String label)
    {
        this.value = new String(bytes, StandardCharsets.UTF_8);
        this.label = label;
    }

    @Override
    public String getDisplayableValue()
    {
        return value;
    }

    @Override
    public String getDisplayName()
    {
        return label;
    }

}
