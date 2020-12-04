package org.jmisb.api.klv.st0102;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Declassification Date (ST 0102 tag 10).
 *
 * <p>The Declassification Date metadata element provides a date when the classified material may be
 * automatically declassified.
 */
public class DeclassificationDate implements ISecurityMetadataValue {
    private LocalDate date;

    /**
     * Construct DeclassificationDate from a LocalDate.
     *
     * @param date the date for the declassification date.
     */
    public DeclassificationDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Construct DeclassificationDate from encoded bytes.
     *
     * @param bytes byte array with encoded value for DeclassificationDate (exactly 8 bytes, see
     *     ST0102).
     */
    public DeclassificationDate(byte[] bytes) {
        if (bytes.length != 8) {
            throw new IllegalArgumentException(
                    "Declassification Date must have the format YYYYMMDD");
        }

        String dateString = new String(bytes, StandardCharsets.US_ASCII);
        try {
            date = LocalDate.parse(dateString, BASIC_ISO_DATE);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * The date value.
     *
     * <p>These dates are somewhat arbitrary (defined in years from classification), and do not have
     * associated time zones.
     *
     * @return the declassification date.
     */
    public LocalDate getValue() {
        return date;
    }

    @Override
    public byte[] getBytes() {
        return date.format(BASIC_ISO_DATE).getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue() {
        return date.format(BASIC_ISO_DATE);
    }

    @Override
    public String getDisplayName() {
        return "Declassification Date";
    }
}
