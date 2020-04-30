package org.jmisb.api.klv.st0102;

import java.time.LocalDate;
import java.time.Month;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for OcmDate.
 */
public class OcmDateTest
{
    @Test
    public void testConstructFromValue()
    {
        LocalDate localDate = LocalDate.of(2020, Month.JANUARY, 25);
        OcmDate ccmDate = new OcmDate(localDate);
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), localDate);
        byte[] bytes = ccmDate.getBytes();
        assertEquals(bytes, new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "2020-01-25");
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        OcmDate ccmDate = new OcmDate(new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), LocalDate.of(2020, Month.JANUARY, 25));
        byte[] bytes = ccmDate.getBytes();
        assertEquals(bytes, new byte[]{0x32, 0x30, 0x32, 0x30, 0x2d, 0x30, 0x31, 0x2d, 0x32, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "2020-01-25");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadArrayLength()
    {
        new OcmDate(new byte[]{0x01, 0x02, 0x04});
    }
}
