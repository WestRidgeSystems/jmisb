package org.jmisb.api.klv.st0102;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * Object Country Coding Method Version Date (ST 0102 tag 24).
 *
 * <p>This metadata item provides the effective date (promulgation date) of the source (FIPS 10-4,
 * ISO 3166, GENC, or STANAG 1059) used for the Object Country Coding Method. As ISO 3166 is updated
 * by dated circulars, not by version revision, the ISO 8601 YYYY-MM-DD formatted date is used. The
 * valid country codes (and the corresponding meanings) can vary between versions of those coding
 * methods, so this metadata item can be used to identify the exact version of the coding method.
 */
public class OcmDate implements ISecurityMetadataValue {
    private LocalDate date;

    /**
     * Construct OcmDate from a LocalDate.
     *
     * @param date the date for the coding method version.
     */
    public OcmDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Construct OcmDate from encoded bytes.
     *
     * @param bytes byte array with encoded value for OcmDate (exactly 10 bytes, see ST0102)
     */
    public OcmDate(byte[] bytes) {
        if (bytes.length != 10) {
            throw new IllegalArgumentException(
                    "Object Country Coding Method Version Date must have the format YYYY-MM-DD");
        }

        // TODO: can we avoid the string allocation?
        String dateString = new String(bytes, StandardCharsets.US_ASCII);
        date = LocalDate.parse(dateString, ISO_LOCAL_DATE);
    }

    /**
     * The date value.
     *
     * <p>These dates are somewhat arbitrary, and do not have associated time zones. It is probably
     * safest to treat them as identifiers with ordering.
     *
     * @return the date of the Country Coding Method.
     */
    public LocalDate getValue() {
        return date;
    }

    @Override
    public byte[] getBytes() {
        return date.format(ISO_LOCAL_DATE).getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getDisplayableValue() {
        return date.format(ISO_LOCAL_DATE);
    }

    @Override
    public String getDisplayName() {
        return "Object Country Coding Method Date";
    }
}
