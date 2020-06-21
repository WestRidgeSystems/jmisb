package org.jmisb.api.klv.st0102;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import org.testng.annotations.Test;

/** Tests for CcmDate. */
public class CcmDateTest {
    @Test
    public void testConstructFromValue() {
        LocalDate localDate = LocalDate.of(2020, Month.APRIL, 25);
        CcmDate ccmDate = new CcmDate(localDate);
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), localDate);
        byte[] bytes = ccmDate.getBytes();
        assertEquals(
                bytes, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "2020-04-25");
        assertEquals(ccmDate.getDisplayName(), "Country Coding Method Version Date");
    }

    @Test
    public void testConstructFromEncodedBytes() {
        CcmDate ccmDate =
                new CcmDate(
                        new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), LocalDate.of(2020, Month.APRIL, 25));
        byte[] bytes = ccmDate.getBytes();
        assertEquals(
                bytes, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "2020-04-25");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadArrayLength() {
        new CcmDate(new byte[] {0x40});
    }
}
