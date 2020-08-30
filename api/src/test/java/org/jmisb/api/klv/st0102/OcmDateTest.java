package org.jmisb.api.klv.st0102;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import org.testng.annotations.Test;

/** Tests for OcmDate. */
public class OcmDateTest {
    @Test
    public void testConstructFromValue() {
        LocalDate localDate = LocalDate.of(2020, Month.JANUARY, 25);
        OcmDate ocmDate = new OcmDate(localDate);
        assertNotNull(ocmDate);
        assertEquals(ocmDate.getValue(), localDate);
        byte[] bytes = ocmDate.getBytes();
        assertEquals(
                bytes, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ocmDate.getDisplayableValue(), "2020-01-25");
        assertEquals(ocmDate.getDisplayName(), "Object Country Coding Method Date");
    }

    @Test
    public void testConstructFromEncodedBytes() {
        OcmDate ocmDate =
                new OcmDate(
                        new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertNotNull(ocmDate);
        assertEquals(ocmDate.getValue(), LocalDate.of(2020, Month.JANUARY, 25));
        byte[] bytes = ocmDate.getBytes();
        assertEquals(
                bytes, new byte[] {0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ocmDate.getDisplayableValue(), "2020-01-25");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadArrayLength() {
        new OcmDate(new byte[] {0x01, 0x02, 0x04});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadFormat() {
        new OcmDate(new byte[] {0x2d, 0x2d, 0x2d, 0x2d, 0x2d, 0x30, 0x34, 0x2d, 0x32, 0x35});
    }
}
