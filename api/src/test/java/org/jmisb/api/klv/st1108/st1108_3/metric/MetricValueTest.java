package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.jmisb.api.klv.st1108.*;
import org.testng.annotations.Test;

/** Tests for MetricValue. */
public class MetricValueTest {

    @Test
    public void testConstructFromValue() {
        MetricValue uut = new MetricValue(25.2);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Metric Value");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getValue(), 25.20000, 0.00001);
    }

    @Test
    public void testConstructFromValueFloat() {
        MetricValue uut = new MetricValue(25.2f);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Metric Value");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getValue(), 25.20000, 0.00001);
    }

    @Test
    public void testConstructFromEncodedBytes4() {
        MetricValue uut =
                new MetricValue(new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Metric Value");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getValue(), 25.200000, 0.00001);
    }

    @Test
    public void testConstructFromEncodedBytes8() {
        MetricValue uut =
                new MetricValue(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x39,
                            (byte) 0xF8,
                            (byte) 0x51,
                            (byte) 0xEB,
                            (byte) 0x85,
                            (byte) 0x1E,
                            (byte) 0xB8
                        });
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xcf, (byte) 0xc2, (byte) 0x8f});
        assertEquals(uut.getDisplayName(), "Metric Value");
        assertEquals(uut.getDisplayableValue(), "25.97");
        assertEquals(uut.getValue(), 25.970000, 0.00001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new MetricValue(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new MetricValue(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new MetricValue(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x8, 0x09});
    }
}
