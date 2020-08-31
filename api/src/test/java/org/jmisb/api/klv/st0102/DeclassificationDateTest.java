package org.jmisb.api.klv.st0102;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import org.testng.annotations.Test;

/** Tests for DeclassificationDate. */
public class DeclassificationDateTest {
    @Test
    public void testConstructFromValue() {
        LocalDate localDate = LocalDate.of(2051, Month.MAY, 5);
        DeclassificationDate declassificationDate = new DeclassificationDate(localDate);
        assertNotNull(declassificationDate);
        assertEquals(declassificationDate.getValue(), localDate);
        byte[] bytes = declassificationDate.getBytes();
        assertEquals(bytes, new byte[] {0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertEquals(declassificationDate.getDisplayableValue(), "20510505");
        assertEquals(declassificationDate.getDisplayName(), "Declassification Date");
    }

    @Test
    public void testConstructFromEncodedBytes() {
        DeclassificationDate declassificationDate =
                new DeclassificationDate(
                        new byte[] {0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertNotNull(declassificationDate);
        assertEquals(declassificationDate.getValue(), LocalDate.of(2051, Month.MAY, 5));
        byte[] bytes = declassificationDate.getBytes();
        assertEquals(bytes, new byte[] {0x32, 0x30, 0x35, 0x31, 0x30, 0x35, 0x30, 0x35});
        assertEquals(declassificationDate.getDisplayableValue(), "20510505");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadArrayLength() {
        new DeclassificationDate(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fuzz1() {
        new DeclassificationDate(
                new byte[] {
                    (byte) 0xc0, (byte) 0xfe, 0x15, 0x52, (byte) 0xbc, 0x66, (byte) 0xb0, 0x06
                });
    }
}
