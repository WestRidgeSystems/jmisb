package org.jmisb.api.klv.st0107;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * Unit tests for the Utf8String implementation.
 *
 * <p>This corresponds to the ST 0107 "utf8" type.
 *
 * <p>References of the form "ST 0107.3-xx" are the EARS requirements numbers.
 */
public class Utf8StringTest {

    public Utf8StringTest() {}

    @Test
    public void checkFromString() {
        Utf8String uut = new Utf8String("aB3");
        assertEquals(uut.getValue(), "aB3");
        assertEquals(uut.getValueUntrimmed(), "aB3");
        assertEquals(uut.getBytes(), new byte[] {0x61, 0x42, 0x33});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromStringEmpty() {
        Utf8String uut = new Utf8String("");
        assertEquals(uut.getValue(), "");
        assertEquals(uut.getValueUntrimmed(), "");
        assertEquals(uut.getBytes(), new byte[] {0x00});
        assertTrue(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromStringUnknown() {
        Utf8String uut = new Utf8String((String) null);
        assertEquals(uut.getValue(), null);
        assertEquals(uut.getValueUntrimmed(), null);
        assertEquals(uut.getBytes(), new byte[0]);
        assertFalse(uut.isEmpty());
        assertTrue(uut.isUnknown());
    }

    @Test
    public void checkFromBytes() {
        Utf8String uut = new Utf8String(new byte[] {0x61, 0x42, 0x33});
        assertEquals(uut.getValue(), "aB3");
        assertEquals(uut.getValueUntrimmed(), "aB3");
        assertEquals(uut.getBytes(), new byte[] {0x61, 0x42, 0x33});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromBytesLimited() {
        Utf8String uut = new Utf8String(new byte[] {0x30, 0x31, 0x32, 0x33});
        assertEquals(uut.getValue(), "0123");
        assertEquals(uut.getValueUntrimmed(), "0123");
        assertEquals(uut.getBytes(), new byte[] {0x30, 0x31, 0x32, 0x33});
        assertEquals(uut.getBytesWithLimit(5), new byte[] {0x30, 0x31, 0x32, 0x33});
        assertEquals(uut.getBytesWithLimit(4), new byte[] {0x30, 0x31, 0x32, 0x33});
        assertEquals(uut.getBytesWithLimit(3), new byte[] {0x00});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromBytesEmpty() {
        Utf8String uut = new Utf8String(new byte[] {0x00});
        assertEquals(uut.getValue(), "");
        assertEquals(uut.getValueUntrimmed(), "");
        assertEquals(uut.getBytes(), new byte[] {0x00});
        assertTrue(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromBytesSingleByteNotEmpty() {
        Utf8String uut = new Utf8String(new byte[] {0x38});
        assertEquals(uut.getValue(), "8");
        assertEquals(uut.getValueUntrimmed(), "8");
        assertEquals(uut.getBytes(), new byte[] {0x38});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    @Test
    public void checkFromBytesUnknown() {
        Utf8String uut = new Utf8String(new byte[0]);
        assertEquals(uut.getValue(), null);
        assertEquals(uut.getValueUntrimmed(), null);
        assertEquals(uut.getBytes(), new byte[0]);
        assertFalse(uut.isEmpty());
        assertTrue(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the "all leading..., ... and space (0x20)" case.
     */
    @Test
    public void checkTrimLeadingSpaces() {
        Utf8String uut = new Utf8String("  aB3");
        assertEquals(uut.getValue(), "aB3");
        assertEquals(uut.getValueUntrimmed(), "  aB3");
        assertEquals(uut.getBytes(), new byte[] {0x61, 0x42, 0x33});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the "all ... and trailing, ... and space (0x20)" case.
     */
    @Test
    public void checkTrimTrailingSpaces() {
        Utf8String uut = new Utf8String("aB3  ");
        assertEquals(uut.getValue(), "aB3");
        assertEquals(uut.getValueUntrimmed(), "aB3  ");
        assertEquals(uut.getBytes(), new byte[] {0x61, 0x42, 0x33});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the "all leading and trailing, ... tab (0x09), line feed (0x0A), carriage
     * return (0xD), and space (0x20)" case. Note that 0x0A is also called new-line.
     */
    @Test
    public void checkTrimWhitespace() {
        Utf8String uut = new Utf8String("\t\r\n aB3\n\r\t \t");
        assertEquals(uut.getValue(), "aB3");
        assertEquals(uut.getValueUntrimmed(), "\t\r\n aB3\n\r\t \t");
        assertEquals(uut.getBytes(), new byte[] {0x61, 0x42, 0x33});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the case where the string is empty after removal of white-space.
     */
    @Test
    public void checkOnlyWhitespace() {
        Utf8String uut = new Utf8String("\t\r\n\n\r\t \t");
        assertEquals(uut.getValue(), "");
        assertEquals(uut.getValueUntrimmed(), "\t\r\n\n\r\t \t");
        assertEquals(uut.getBytes(), new byte[] {0x00});
        assertTrue(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the case where the string is empty after removal of white-space, creating the
     * value from a byte array.
     */
    @Test
    public void checkOnlyWhitespaceFromBytes() {
        Utf8String uut =
                new Utf8String(new byte[] {0x00, 0x09, 0x0D, 0x0A, 0x0D, 0x20, 0x20, 0x00, 0x09});
        assertEquals(uut.getValue(), "");
        assertEquals(uut.getValueUntrimmed(), "\u0000\t\r\n\r  \u0000\t");
        assertEquals(uut.getBytes(), new byte[] {0x00});
        assertTrue(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-12.
     *
     * <p>This covers the case with leading null (0x00) value, but valid values after that.
     */
    @Test
    public void checkMixedWhitespaceFromBytes() {
        Utf8String uut =
                new Utf8String(
                        new byte[] {
                            0x00, 0x09, 0x0D, 0x0A, 0x0D, 0x41, 0x62, 0x35, 0x20, 0x20, 0x00, 0x09
                        });
        assertEquals(uut.getValue(), "Ab5");
        assertEquals(uut.getValueUntrimmed(), "\u0000\t\r\n\rAb5  \u0000\t");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x35});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-13.
     *
     * <p>This covers the case with embedded null (0x00) value, but valid values before and after
     * that.
     */
    @Test
    public void checkST0107_3_13_0x00() {
        Utf8String uut = new Utf8String(new byte[] {0x41, 0x00, 0x62, 0x35, 0x0B, 0x36});
        assertEquals(uut.getValue(), "Ab56");
        assertEquals(uut.getValueUntrimmed(), "A\u0000b5\u000B6");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x35, 0x36});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-13.
     *
     * <p>Checks the cases with 0x01 and 0x0C values.
     */
    @Test
    public void checkST0107_3_13_0x01() {
        Utf8String uut = new Utf8String(new byte[] {0x41, 0x01, 0x62, 0x35, 0x0C, 0x36});
        assertEquals(uut.getValue(), "Ab56");
        assertEquals(uut.getValueUntrimmed(), "A\u0001b5\u000C6");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x35, 0x36});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-13.
     *
     * <p>Checks the cases with 0x08 and 0x0E values.
     */
    @Test
    public void checkST0107_3_13_0x08() {
        Utf8String uut = new Utf8String(new byte[] {0x41, 0x08, 0x62, 0x35, 0x0E, 0x36});
        assertEquals(uut.getValue(), "Ab56");
        assertEquals(uut.getValueUntrimmed(), "A\u0008b5\u000E6");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x35, 0x36});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-13.
     *
     * <p>Checks the cases with 0x7F and 0x1F values.
     */
    @Test
    public void checkST0107_3_13_0x7F() {
        Utf8String uut = new Utf8String(new byte[] {0x41, 0x7F, 0x62, 0x35, 0x1F, 0x36});
        assertEquals(uut.getValue(), "Ab56");
        assertEquals(uut.getValueUntrimmed(), "A\u007Fb5\u001F6");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x35, 0x36});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }

    /**
     * Partly verifies ST 0107.3-13.
     *
     * <p>Checks the cases with 0x08, 0x09 (tab - which is valid when embedded) and 0x0E values.
     */
    @Test
    public void checkST0107_3_13_0x09() {
        Utf8String uut = new Utf8String(new byte[] {0x41, 0x08, 0x62, 0x09, 0x35, 0x0E, 0x36});
        assertEquals(uut.getValue(), "Ab\t56");
        assertEquals(uut.getValueUntrimmed(), "A\u0008b\t5\u000E6");
        assertEquals(uut.getBytes(), new byte[] {0x41, 0x62, 0x09, 0x35, 0x36});
        assertFalse(uut.isEmpty());
        assertFalse(uut.isUnknown());
    }
}
