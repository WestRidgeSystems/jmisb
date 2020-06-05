package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * Classifying Country and Releasing Instructions Country Coding Method Version Date (ST 0102 tag 23)
 */
public class CcmDate implements ISecurityMetadataValue
{
    private LocalDate date;

    public CcmDate(LocalDate date)
    {
        this.date = date;
    }

    public CcmDate(byte[] bytes)
    {
        if (bytes.length != 10)
        {
            throw new IllegalArgumentException("Classifying Country Coding Method Version Date must have the format YYYY-MM-DD");
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

    @Override
    public String getDisplayName()
    {
        return "Country Coding Method Version Date";
    }
}
