package org.jmisb.api.klv.st0102;

import java.time.LocalDate;
import java.time.Month;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for DeclassificationDate.
 */
public class DeclassificationDateTest
{
    @Test
    public void testConstructFromValue()
    {
        LocalDate localDate = LocalDate.of(2051, Month.MAY, 5);
        DeclassificationDate ccmDate = new DeclassificationDate(localDate);
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), localDate);
        byte[] bytes = ccmDate.getBytes();
        assertEquals(bytes, new byte[]{0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "20510505");
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        DeclassificationDate ccmDate = new DeclassificationDate(new byte[]{0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertNotNull(ccmDate);
        assertEquals(ccmDate.getValue(), LocalDate.of(2051, Month.MAY, 5));
        byte[] bytes = ccmDate.getBytes();
        assertEquals(bytes, new byte[]{0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertEquals(ccmDate.getDisplayableValue(), "20510505");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadArrayLength()
    {
        new DeclassificationDate(new byte[]{0x01, 0x02, 0x03});
    }
}
