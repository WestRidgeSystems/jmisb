package org.jmisb.api.klv.st0107;

import java.nio.charset.StandardCharsets;

/**
 * Standard UTF-8 encoded String.
 *
 * <p>This corresponds to a metadata item of type {@code utf8}.
 *
 * <p>From ST 0107.4:
 *
 * <blockquote>
 *
 * <p>Items with the type {@code utf8} are strings of characters based on the ISO/IEC 10646:2014
 * Universal Character Set (UCS) standard for interpretation, as discussed in the Motion Imagery
 * Handbook. Unless a Local Set item description states otherwise, all strings are variable length.
 * The Local Set item’s length determines the length of the string with the one exception of the
 * "empty string".
 *
 * <p>The empty string uses a single null character ({@code 0x00}) with no other characters to
 * indicate the string is blank. The length of the empty string within KLV is one (1), for the null
 * character, but the decoding software converts the empty string to the software language’s
 * equivalent of an empty string (e.g., Java interprets an empty string as a String of length zero).
 * The empty string is different than an "Unknown" string (see MISB ST 0601), where the string is
 * undefined (e.g., Java interprets an Unknown string as a String variable set to "null"). A string
 * item with a length of zero (0) signifies an "Unknown" string.
 *
 * <p>Strings can easily consume a large amount of bandwidth; the recommended practice is to use the
 * fewest characters needed to provide the intended meaning. With exception of the single null
 * character for an empty string, or if an item definition allows it, do not use the characters in
 * the hex ranges (inclusive) of {@code 0x00} through {@code 0x08}, {@code 0x0B} through {@code
 * 0x0C}, {@code 0x0E} through {@code 0x1F}, and character {@code 0x7F}. Furthermore, remove any
 * leading or trailing null, tab, line feed, carriage return, and space characters ({@code 0x00},
 * {@code 0x09}, {@code 0x0A}, {@code 0x0D}, and {@code 0x20}, respectively) in the Value unless
 * specified by the defining document.
 *
 * </blockquote>
 *
 * <p>This implementation handles the empty and null cases as defined in ST 0107, along with
 * standard conversions.
 */
public class Utf8String {
    private final String value;

    /**
     * Construct from a String value.
     *
     * @param string the value to construct from
     */
    public Utf8String(final String string) {
        this.value = string;
    }

    /**
     * Construct from a byte array.
     *
     * <p>This assumes the byte array is encoded in UTF-8 or something compatible (e.g. the ASCII
     * subset); or otherwise conforms to the ST 0107 requirements for empty and unknown strings.
     *
     * @param bytes the byte array
     */
    public Utf8String(final byte[] bytes) {
        if (bytes.length == 0) {
            this.value = null;
        } else if ((bytes.length == 1) && (bytes[0] == 0x00)) {
            this.value = "";
        } else {
            this.value = new String(bytes, StandardCharsets.UTF_8);
        }
    }

    /**
     * Get the cleaned-up version of the value that was passed in.
     *
     * <p>This has leading and trailing white-space characters removed, and special characters
     * removed in accordance with ST 0107.
     *
     * @return String containing ST 0107 compliant version of input value.
     */
    public String getValue() {
        if (this.value == null) {
            return null;
        }
        return trimValue();
    }

    /**
     * Get the value that was passed in.
     *
     * <p>This does not have additional white-space trimmed off, or special characters removed.
     *
     * @return the untrimmed string value
     * @see getValue() for a version that does the standard character cleanups.
     */
    public String getValueUntrimmed() {
        return this.value;
    }

    /**
     * Test if this is an empty string value.
     *
     * <p>This is conceptually a "known to be empty" ({@code ""}) string value, and would be encoded
     * as a single null byte in accordance with ST 0107.
     *
     * @return true if the string is Empty, otherwise false. An Unknown sting is not considered
     *     empty.
     * @see isUnknown() for a way to check for Unknown.
     */
    public boolean isEmpty() {
        if (this.value == null) {
            return false;
        }
        return trimValue().isEmpty();
    }

    /**
     * Test if this is an "Unknown" string value.
     *
     * <p>This is conceptually undefined (null), and would be encoded as a zero-length string in
     * accordance with ST 0107.
     *
     * @return true if the string is Unknown, otherwise false.
     */
    public boolean isUnknown() {
        return (this.value == null);
    }

    /**
     * Get the encoded bytes for this string.
     *
     * <p>This handles the special case encoding per ST 0107.
     *
     * @return byte array corresponding to this string.
     */
    public byte[] getBytes() {
        if (this.value == null) {
            return new byte[0];
        }
        String s = trimValue();
        if (s.isBlank()) {
            return new byte[] {0x00};
        }
        return (trimValue().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Get the encoded bytes for this string, subject to a length limit.
     *
     * <p>If the encoded length (after ST 0107 trimming / clean up) would exceed the byte length
     * limit, an empty string will be returned instead.
     *
     * @param maxBytes the byte length limit
     * @return byte array corresponding to this string, or to an empty string if the byte limit
     *     would be exceeded.
     */
    public byte[] getBytesWithLimit(int maxBytes) {
        byte[] bytes = getBytes();
        if (bytes.length > maxBytes) {
            return new byte[] {0x00};
        }
        return bytes;
    }

    private String trimValue() {
        String t = this.value.trim();
        StringBuilder sb = new StringBuilder();
        for (char c : t.toCharArray()) {
            if ((c >= 0x0000) && (c <= 0x0008)) {
                continue;
            }
            if (c == 0x000B) {
                continue;
            }
            if (c == 0x000C) {
                continue;
            }
            if ((c >= 0x000E) && (c <= 0x001F)) {
                continue;
            }
            if (c == 0x007F) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
