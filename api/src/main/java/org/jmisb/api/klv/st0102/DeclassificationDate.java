package org.jmisb.api.klv.st0102;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Declassification Date (ST 0102 tag 10)
 */
public class DeclassificationDate implements ISecurityMetadataValue
{
    private LocalDate date;

    public DeclassificationDate(LocalDate date)
    {
        this.date = date;
    }

    public DeclassificationDate(byte[] bytes)
    {
        if (bytes.length != 8)
        {
            throw new IllegalArgumentException("Declassification Date must have the format YYYYMMDD");
        }

        // TODO: can we avoid the string allocation?
        String dateString = new String(bytes, StandardCharsets.US_ASCII);
        date = LocalDate.parse(dateString, BASIC_ISO_DATE);
    }

    public LocalDate getValue()
    {
        return date;
    }

    @Override
    public byte[] getBytes()
    {
        return date.format(BASIC_ISO_DATE).getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue()
    {
        return date.format(BASIC_ISO_DATE);
    }
}
