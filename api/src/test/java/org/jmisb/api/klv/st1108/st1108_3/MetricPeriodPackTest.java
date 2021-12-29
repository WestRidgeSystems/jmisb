package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.testng.annotations.Test;

/** Tests for Metric Period Pack (ST 1108 Tag 2). */
public class MetricPeriodPackTest {

    @Test
    public void testConstructFromValue() {
        MetricPeriodPack uut = new MetricPeriodPack(new ST0603TimeStamp(1624592992000000L), 500000);
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0xC5,
                    (byte) 0x8F,
                    (byte) 0x08,
                    (byte) 0x31,
                    (byte) 0x58,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x07,
                    (byte) 0xA1,
                    (byte) 0x20
                });
        assertEquals(uut.getDisplayName(), "Metric Period");
        assertEquals(uut.getDisplayableValue(), "1624592992000000, 500000");
        assertEquals(uut.getStartTime().getMicroseconds(), 1624592992000000L);
        assertEquals(uut.getTimeOffset(), 500000);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        MetricPeriodPack uut =
                new MetricPeriodPack(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x05,
                            (byte) 0xC5,
                            (byte) 0x8F,
                            (byte) 0x08,
                            (byte) 0x31,
                            (byte) 0x58,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x05,
                            (byte) 0xdc
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0xC5,
                    (byte) 0x8F,
                    (byte) 0x08,
                    (byte) 0x31,
                    (byte) 0x58,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0xdc
                });
        assertEquals(uut.getDisplayName(), "Metric Period");
        assertEquals(uut.getDisplayableValue(), "1624592992000000, 1500");
        assertEquals(uut.getStartTime().getMicroseconds(), 1624592992000000L);
        assertEquals(uut.getTimeOffset(), 1500);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new MetricPeriodPack(
                new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new MetricPeriodPack(
                new byte[] {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d
                });
    }
}
