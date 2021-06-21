package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.testng.annotations.Test;

/** Tests for Metric Time (ST 1108 Metric Local Set Tag 5). */
public class MetricTimeTest {

    @Test
    public void testConstructFromValue() {
        MetricTime uut = new MetricTime(new ST0603TimeStamp(0));
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(uut.getDisplayName(), "Metric Time");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getTime().getMicroseconds(), 0);
    }

    @Test
    public void testConstructFromBytes() {
        MetricTime uut =
                new MetricTime(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x08
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x08
                });
        assertEquals(uut.getDisplayName(), "Metric Time");
        assertEquals(uut.getDisplayableValue(), "8");
        assertEquals(uut.getTime().getMicroseconds(), 8);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new MetricTime(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new MetricTime(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new MetricTime(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x8, 0x09});
    }
}
