package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * Object Country Coding Method Version Date (ST 0102 tag 24)
 */
public class OcmDate implements ISecurityMetadataValue
{
    private LocalDate date;

    public OcmDate(LocalDate date)
    {
        this.date = date;
    }

    public OcmDate(byte[] bytes)
    {
        if (bytes.length != 10)
        {
            throw new IllegalArgumentException("Object Country Coding Method Version Date must have the format YYYY-MM-DD");
        }

        // TODO: can we avoid the string allocation?
        String dateString = new String(bytes, StandardCharsets.US_ASCII);
        date = LocalDate.parse(dateString, ISO_LOCAL_DATE);
    }

    public LocalDate getValue()
    {
        return date;
    }

    @Override
    public byte[] getBytes()
    {
        return date.format(ISO_LOCAL_DATE).getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue()
    {
        return date.format(ISO_LOCAL_DATE);
    }
}
