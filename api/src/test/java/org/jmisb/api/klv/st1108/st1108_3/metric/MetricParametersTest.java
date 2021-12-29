package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Metric Parameters (ST 1108 Metric Local Set Tag 4). */
public class MetricParametersTest {

    @Test
    public void testConstructFromValue() {
        MetricParameters uut = new MetricParameters("193,abc");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x31,
                    (byte) 0x39,
                    (byte) 0x33,
                    (byte) 0x2c,
                    (byte) 0x61,
                    (byte) 0x62,
                    (byte) 0x63
                });
        assertEquals(uut.getDisplayName(), "Metric Parameters");
        assertEquals(uut.getDisplayableValue(), "193,abc");
        assertEquals(uut.getParameters(), "193,abc");
    }

    @Test
    public void testConstructFromBytes() {
        MetricParameters uut =
                new MetricParameters(
                        new byte[] {
                            (byte) 0x31,
                            (byte) 0x39,
                            (byte) 0x33,
                            (byte) 0x2c,
                            (byte) 0x61,
                            (byte) 0x62,
                            (byte) 0x63
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x31,
                    (byte) 0x39,
                    (byte) 0x33,
                    (byte) 0x2c,
                    (byte) 0x61,
                    (byte) 0x62,
                    (byte) 0x63
                });
        assertEquals(uut.getDisplayName(), "Metric Parameters");
        assertEquals(uut.getDisplayableValue(), "193,abc");
        assertEquals(uut.getParameters(), "193,abc");
    }
}
