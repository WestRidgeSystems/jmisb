package org.jmisb.st1909;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Date / time format utilities for ST 1909.
 *
 * <p>This matches the expected format, per ST 1909-46.
 */
public class ST1909DateTimeFormatter {
    private static final java.time.format.DateTimeFormatter DATE_TIME_FORMATTER;

    static {
        DATE_TIME_FORMATTER =
                new DateTimeFormatterBuilder()
                        .append(java.time.format.DateTimeFormatter.ISO_DATE)
                        .appendLiteral('T')
                        .appendPattern("HH:mm:ss.S")
                        .appendLiteral('Z')
                        .toFormatter();
    }

    private ST1909DateTimeFormatter() {};

    /**
     * Format a given date and time in the ST 1909 specified format.
     *
     * @param dateTime the date / time to format
     * @return string containing the the formatted result
     */
    public static String format(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }
}